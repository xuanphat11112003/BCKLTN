import torch
import torch.nn as nn
import torch.optim as optim
import numpy as np
import json
from torch.utils.data import Dataset, DataLoader
from sklearn.preprocessing import StandardScaler  # Sử dụng StandardScaler
import pickle
import os
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_absolute_error, mean_squared_error, r2_score
import matplotlib.pyplot as plt


def analyze_predictions(model, dataset, device, scaler, output_dir="reports"):
    model.eval()
    y_true = []
    y_pred = []

    with torch.no_grad():
        for i in range(len(dataset)):
            sales_history, true_next_sales = dataset[i]
            sales_history = sales_history.unsqueeze(0).to(device)

            predicted_next_sales = model(sales_history).cpu().numpy().flatten()
            true_next_sales = true_next_sales.cpu().numpy().flatten()

            predicted_original = scaler.inverse_transform(predicted_next_sales.reshape(-1, 1)).flatten()[0]
            true_original = scaler.inverse_transform(true_next_sales.reshape(-1, 1)).flatten()[0]

            y_true.append(true_original)
            y_pred.append(predicted_original)

    # Đánh giá chỉ số
    mae = mean_absolute_error(y_true, y_pred)
    rmse = mean_squared_error(y_true, y_pred, squared=False)
    r2 = r2_score(y_true, y_pred)

    print(f"\n🔍 Evaluation Report:")
    print(f"MAE: {mae:.2f}")
    print(f"RMSE: {rmse:.2f}")
    print(f"R² Score: {r2:.4f}")

    # Tạo thư mục lưu nếu chưa có
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    # Biểu đồ Real vs Predicted
    plt.figure(figsize=(10, 6))
    plt.plot(y_true, label='True Sales', marker='o', alpha=0.7)
    plt.plot(y_pred, label='Predicted Sales', marker='x', alpha=0.7)
    plt.title("Real vs Predicted Sales")
    plt.xlabel("Sample Index")
    plt.ylabel("Sales")
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(os.path.join(output_dir, "real_vs_predicted.png"))
    plt.close()

    # Biểu đồ Histogram lỗi tuyệt đối
    errors = np.abs(np.array(y_true) - np.array(y_pred))
    plt.figure(figsize=(8, 5))
    plt.hist(errors, bins=30, color='skyblue', edgecolor='black')
    plt.title("Distribution of Absolute Errors")
    plt.xlabel("Absolute Error")
    plt.ylabel("Frequency")
    plt.tight_layout()
    plt.savefig(os.path.join(output_dir, "error_histogram.png"))
    plt.close()

class SalesDataset(Dataset):
    def __init__(self, data, sequence_length, scaler=None, augment = False):
        self.data = data
        self.sequence_length = sequence_length
        self.scaler = scaler if scaler else StandardScaler()
        self.augment = augment

        all_sales_history = []
        for item in data:
            for product_data in item['data']:
                all_sales_history.append(product_data['sales_history'])

        self.scaler.fit(np.array(all_sales_history).reshape(-1, 1))

    def __len__(self):
        total_len = sum([len(item['data']) for item in self.data])
        return total_len

    def __getitem__(self, idx):
        product_idx = 0
        while idx >= len(self.data[product_idx]['data']):
            idx -= len(self.data[product_idx]['data'])
            product_idx += 1

        sample = self.data[product_idx]['data'][idx]

        sales_history = np.array(sample['sales_history']).reshape(-1, 1)
        next_sales = np.array([sample['next_sales']]).reshape(-1, 1)

        sales_history = self.scaler.transform(sales_history).flatten()
        next_sales = self.scaler.transform(next_sales).flatten()

        if self.augment:
            noise_factor = 0.15
            noise = np.random.normal(loc=0, scale=noise_factor, size=sales_history.shape)
            sales_history += noise

        if len(sales_history) < self.sequence_length:
            pad_value = sales_history.mean() if len(sales_history) > 0 else 0
            padding = [pad_value] * (self.sequence_length - len(sales_history))
            sales_history = padding + sales_history.tolist()
        else:
            sales_history = sales_history[-self.sequence_length:]

        sales_history = torch.tensor(sales_history, dtype=torch.float32).unsqueeze(-1)
        target = torch.tensor(next_sales, dtype=torch.float32)

        return sales_history, target

class Attention(nn.Module):
    def __init__(self, hidden_dim):
        super(Attention, self).__init__()
        self.attn = nn.Linear(hidden_dim, 1)

    def forward(self, gru_out):
        attn_weights = torch.softmax(self.attn(gru_out), dim=1)
        context = torch.sum(gru_out * attn_weights, dim=1)
        return context


class SalesForecastGRU(nn.Module):
    def __init__(self, input_size=1, hidden_size=128, num_layers=3, output_size=1, dropout=0.3, bidirectional=False):
        super(SalesForecastGRU, self).__init__()
        self.gru = nn.LSTM(input_size, hidden_size, num_layers, batch_first=True, dropout=dropout, bidirectional=False)
        self.attention = Attention(hidden_size)
        fc_input_size = hidden_size * 2 if bidirectional else hidden_size
        self.fc1 = nn.Linear(fc_input_size, 128)
        self.bn1 = nn.BatchNorm1d(128)
        self.fc2 = nn.Linear(128, 64)
        self.bn2 = nn.BatchNorm1d(64)
        self.fc3 = nn.Linear(64, output_size)

        self.relu = nn.ReLU()
        self.dropout = nn.Dropout(0.3)

    def forward(self, x):
        out, _ = self.gru(x)
        context = self.attention(out)
        out = self.fc1(context)
        out = self.relu(out)
        out = self.dropout(out)
        out = self.fc2(out)
        out = self.relu(out)
        out = self.fc3(out)
        return out

def train_model(model, dataloader, criterion, optimizer, device, num_epochs=30, patience=10):
    model.train()
    scheduler = torch.optim.lr_scheduler.ReduceLROnPlateau(optimizer, mode='min', factor=0.8, patience=5, min_lr=1e-6)
    best_loss = float('inf')
    patience_counter = 0

    for epoch in range(num_epochs):
        epoch_loss = 0.0
        for sales_history, targets in dataloader:
            sales_history, targets = sales_history.to(device), targets.to(device)

            optimizer.zero_grad()
            outputs = model(sales_history)

            loss = criterion(outputs, targets)
            loss.backward()
            optimizer.step()

            epoch_loss += loss.item()

        avg_loss = epoch_loss / len(dataloader)
        scheduler.step(avg_loss)
        print(f"Epoch {epoch+1}/{num_epochs}, Loss: {avg_loss:.4f}, LR: {scheduler.optimizer.param_groups[0]['lr']:.6f}")

        if avg_loss < best_loss:
            best_loss = avg_loss
            patience_counter = 0
        else:
            patience_counter += 1
            if patience_counter >= patience:
                print(f"Early stopping at epoch {epoch+1}")
                break

    return model


def evaluate_model(model, dataloader, device):
    model.eval()
    total_loss = 0
    correct = 0
    total = 0
    criterion = nn.MSELoss()

    with torch.no_grad():
        for sales_history, targets in dataloader:
            sales_history, targets = sales_history.to(device), targets.to(device)
            outputs = model(sales_history)

            loss = criterion(outputs, targets)
            total_loss += loss.item()

            correct += ((torch.abs(outputs - targets) / (targets + 1e-8)) <= 0.1).sum().item()
            total += targets.size(0)

    avg_loss = total_loss / len(dataloader)
    accuracy = (correct / total) * 100
    print(f"Test Loss: {avg_loss:.4f}")
    print(f"Accuracy: {accuracy:.2f}%")


def read_data_from_file(file_path):
    with open(file_path, 'r') as f:
        data = json.load(f)
    return data


def main():
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

    data = read_data_from_file('data.txt')

    train_data, test_data = train_test_split(data, test_size=0.2, random_state=42)

    sequence_length = 12
    batch_size = 16

    train_dataset = SalesDataset(train_data, sequence_length, augment=True)
    train_dataloader = DataLoader(train_dataset, batch_size=batch_size, shuffle=True)

    test_dataset = SalesDataset(test_data, sequence_length)
    test_dataloader = DataLoader(test_dataset, batch_size=batch_size, shuffle=False)

    model = SalesForecastGRU(input_size=1, hidden_size=512, num_layers=5, output_size=1, dropout=0.3).to(device)

    criterion = nn.SmoothL1Loss(beta=0.3)
    optimizer = torch.optim.AdamW(model.parameters(), lr=0.0001, weight_decay=1e-5)

    trained_model = train_model(model, train_dataloader, criterion, optimizer, device, num_epochs=150, patience=20)

    evaluate_model(trained_model, test_dataloader, device)

    if not os.path.exists("models"):
        os.makedirs("models")

    analyze_predictions(trained_model, test_dataset, device, train_dataset.scaler)

    torch.save(trained_model.state_dict(), "models/sales_forecast_model.pth")
    print("Model saved successfully.")

    # Save the scaler
    with open('scaler.pkl', 'wb') as f:
        pickle.dump(train_dataset.scaler, f)
    print("Scaler saved successfully.")


# Run the main function
if __name__ == "__main__":
    main()
# Epoch 100/100, Loss: 0.0924, LR: 0.000033
# Test Loss: 0.0653
# Accuracy: 75.39%
#
# 🔍 Evaluation Report:
# MAE: 19.74
# RMSE: 26.74
# R² Score: 0.9042
# Model saved successfully.
# Scaler saved successfully.


#
#Test Loss: 0.0637
# Accuracy: 74.61%
#
# 🔍 Evaluation Report:
# MAE: 19.30
# RMSE: 26.42
# R² Score: 0.9065
# Model saved successfully.
# Scaler saved successfully.

