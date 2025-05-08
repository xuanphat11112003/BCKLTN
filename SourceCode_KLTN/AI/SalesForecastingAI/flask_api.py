import math
from flask import Flask, request, jsonify
from flask_cors import CORS
import torch
import torch.nn as nn
import numpy as np
import pickle
from sentence_transformers import SentenceTransformer

app = Flask(__name__)
CORS(app)

INPUT_SIZE = 1
HIDDEN_SIZE = 512
NUM_LAYERS = 5
OUTPUT_SIZE = 1
SEQUENCE_LENGTH = 12

sentence_model = SentenceTransformer("sentence-transformers/all-MiniLM-L6-v2")

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

# Khởi tạo mô hình GRU
model = SalesForecastGRU(INPUT_SIZE, HIDDEN_SIZE, NUM_LAYERS, OUTPUT_SIZE)

# Tải trọng số đã huấn luyện từ mô hình GRU
model.load_state_dict(torch.load("models/sales_forecast_model.pth"))
model.eval()

# Tải scaler từ file pickle
with open('scaler.pkl', 'rb') as f:
    scaler = pickle.load(f)

def denormalize(value):
    return value * scaler.scale_ + scaler.mean_

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.get_json()

        if not isinstance(data, list):
            return jsonify({"error": "Input must be a list of sales history"}), 400

        results = []
        for item in data:
            sales_history = item.get("sales_history")
            product_id = item.get("product_id", None)
            zScore = 1.645
            leadTime = 1
            mean = sum(sales_history) / len(sales_history)
            variance = sum((x - mean) ** 2 for x in sales_history) / len(sales_history) if sales_history else 0.0
            standard_deviation = math.sqrt(variance)
            safety_stock = zScore * standard_deviation * math.sqrt(leadTime)

            if not isinstance(sales_history, list):
                return jsonify({"error": f"sales_history for product {product_id} must be a list of numbers"}), 400
            if len(sales_history) == 0:
                return jsonify({"error": f"sales_history for product {product_id} cannot be empty"}), 400

            sales_history = np.array(sales_history).reshape(-1, 1)

            sales_history = scaler.transform(sales_history).flatten()

            if len(sales_history) < SEQUENCE_LENGTH:
                pad_value = sales_history.mean() if len(sales_history) > 0 else 0
                padding = [pad_value] * (SEQUENCE_LENGTH - len(sales_history))
                sales_history = padding + sales_history.tolist()
            else:
                sales_history = sales_history[-SEQUENCE_LENGTH:]

            sales_history = torch.tensor(sales_history, dtype=torch.float32).unsqueeze(0).unsqueeze(-1)

            with torch.no_grad():
                prediction_normalized = model(sales_history).item()

            # Convert the prediction to a float
            prediction = denormalize(prediction_normalized)

            results.append({
                "product_id": product_id,
                "next_sale": float(prediction),  # Ensure prediction is a float
                "safety_stock": float(safety_stock)  # Ensure safety_stock is a float
            })

        return jsonify(results), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
