$(document).on("click", "#aiPredictBtn", function () {
    const springApiUrl = "http://localhost:8080/chuoicungung/api/sales-history";
    const flaskApiUrl = "http://localhost:5000/predict";
    const calculationApiUrl = "http://localhost:8080/chuoicungung/product-calculation";
    $('#loading-overlay').show();
    $.ajax({
        url: springApiUrl,
        method: "GET",
        contentType: "application/json",
        success: function (salesData) {
            console.log("📢 Dữ liệu từ Spring Boot:", salesData);

            $.ajax({
                url: flaskApiUrl,
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(salesData),
                success: function (prediction) {
                    console.log("✅ Kết quả dự đoán từ AI:", prediction);
                    $.ajax({
                        url: calculationApiUrl,
                        method: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(prediction),
                        success: function (manufactureData) {
                            console.log("🛠️ Dữ liệu tính toán sản xuất:", manufactureData);
                            window.manufactureData = manufactureData;
                            let groupedData = {};
                            manufactureData.forEach(item => {
                                if (!groupedData[item.productId]) {
                                    groupedData[item.productId] = {
                                        productName: item.productName,
                                        productId: item.productId,
                                        amount: item.weight,
                                        materials: []
                                    };
                                }
                                groupedData[item.productId].materials.push({
                                    materialName: item.materialName,
                                    materialId: item.materialId,
                                    weight: item.amount,
                                    unit: item.unit

                                });
                            });

                            let resultHtml = "<table class='table table-bordered'>";
                            resultHtml += "<thead><tr><th>📦 Sản phẩm</th><th>🔢 Số lượng cần</th><th>🛑 Nguyên liệu</th><th>📏 Số lượng đặt</th><th>⚖️ Đơn vị</th></tr></thead><tbody>";

                            Object.values(groupedData).forEach(product => {
                                let rowSpan = product.materials.length;

                                resultHtml += `<tr>
                                    <td rowspan="${rowSpan}">${product.productName} (ID: ${product.productId})</td>
                                    <td rowspan="${rowSpan}">${product.amount}</td>`;
                                let firstMaterial = product.materials[0];
                                resultHtml += `<td>${firstMaterial.materialName} (ID: ${firstMaterial.materialId})</td>
                                    <td>${firstMaterial.weight}</td>
                                    <td>${firstMaterial.unit || "N/A"}</td>
                                </tr>`;
                                for (let i = 1; i < product.materials.length; i++) {
                                    let material = product.materials[i];
                                    resultHtml += `<tr>
                                        <td>${material.materialName} (ID: ${material.materialId})</td>
                                        <td>${material.weight}</td>
                                        <td>${material.unit || "N/A"}</td>
                                    </tr>`;
                                }
                            });

                            resultHtml += "</tbody></table>";
                            $("#predictionResult").html(resultHtml);
                            $("#predictionModal").modal("show");
                            $('#loading-overlay').hide();
                        },
                        error: function (xhr, status, error) {
                            console.error("❌ Lỗi từ Spring Boot API:", error);
                            $("#predictionResult").html("<div class='alert alert-danger'>⚠️ Lỗi khi tính toán sản xuất.</div>");
                            $("#predictionModal").modal("show");
                            $('#loading-overlay').hide();
                        }
                    });

                },
                error: function (xhr, status, error) {
                    console.error("❌ Lỗi từ Flask API:", error);
                    $("#predictionResult").html("<div class='alert alert-danger'>⚠️ Lỗi khi dự đoán doanh số.</div>");
                    $("#predictionModal").modal("show");
                    $('#loading-overlay').hide();
                }
            });

        },
        error: function (xhr, status, error) {
            console.error("❌ Lỗi từ Spring Boot API:", error);
            $("#predictionResult").html("<div class='alert alert-danger'>⚠️ Không thể lấy dữ liệu doanh số.</div>");
            $("#predictionModal").modal("show");
            $('#loading-overlay').hide();
        }
    });
});

$(document).on("click", "#acceptOrder", function () {
    $("#predictionModal").modal("hide");
    if (!window.manufactureData) {
            alert("⚠️ Không có dữ liệu đặt hàng.");
            return;
        }

        let orderData = groupByAgencyMaterial(window.manufactureData);
        displayOrderDetails(orderData);
        $("#confirmOrderModal").modal("show");
});

function groupByAgencyMaterial(manufactureData) {
    let groupedData = {};

    manufactureData.forEach(item => {
        let supplierId = item.agencyMaterial;
        let supplierName = `Nhà Cung Cấp ${supplierId}`;

        if (!groupedData[supplierId]) {
            groupedData[supplierId] = {
                supplierId: supplierId,
                supplierName: supplierName,
                totalCost: 0,
                paymentMethod: "V1",
                detail: []
            };
        }

        let materialPrice = 10000;
        let total = item.weight * materialPrice;

        groupedData[supplierId].detail.push({
            materialID: item.materialId,
            materialName: item.materialName,
            quantity: item.weight,
            price: materialPrice,
            total: total
        });

        groupedData[supplierId].totalCost += total;
    });

    return groupedData;
}


function displayOrderDetails(orderData) {
    let resultHtml = "";
    Object.values(orderData).forEach(supplier => {
        resultHtml += `<div class='card mb-3'>
            <div class='card-header'>
                <h4>🏢 Nhà Cung Cấp: ${supplier.supplierName} (ID: ${supplier.supplierId})</h4>
                <strong>💰 Tổng Tiền: ${supplier.totalCost.toLocaleString("vi-VN")} VND</strong>
                <select class="form-control paymentMethod" data-supplier="${supplier.supplierId}">
                    <option value="V1">💳 Thanh Toán Ngay</option>
                    <option value="V2">📅 Sau 1 Ngày Nhận Hóa Đơn</option>
                    <option value="V3">📦 Sau Khi Nhận Hàng</option>
                </select>
            </div>
            <div class='card-body'>
                <table class='table table-bordered'>
                    <thead><tr><th>🛑 Nguyên Liệu</th><th>📏 Số Lượng</th><th>💰 Giá Đơn Vị</th><th>💵 Tổng Tiền</th></tr></thead>
                    <tbody>`;

        supplier.detail.forEach(material => {
            resultHtml += `<tr>
                <td>${material.materialName} (ID: ${material.materialId})</td>
                <td>${material.quantity}</td>
                <td>${material.price.toLocaleString("vi-VN")} VND</td>
                <td>${material.total.toLocaleString("vi-VN")} VND</td>
            </tr>`;
        });

        resultHtml += `</tbody></table></div></div>`;
    });

    $("#confirmOrderDetails").html(resultHtml);
}
$(document).on("click", ".btn-primary, .btn-secondary", function () {
    $("#confirmOrderModal").modal("hide"); // Đóng modal xác nhận đơn hàng
    $("#predictionModal").modal("hide");   // Đóng modal dự đoán
});

$(document).on("click", "#submitOrderBtn", function () {
    let orderData = groupByAgencyMaterial(window.manufactureData);
    $(".paymentMethod").each(function () {
        let supplierId = $(this).data("supplier");
        let paymentMethod = $(this).val();
        orderData[supplierId].payment = paymentMethod;
    });
    console.log(orderData);

    $.ajax({
        url: "http://localhost:8080/chuoicungung/import-order/suppliers",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(Object.values(orderData)),
        success: function () {
            alert("✅ Đơn hàng đã được đặt thành công!");
            $("#confirmOrderModal").modal("hide");
            location.reload();
        },
        error: function () {
            alert("❌ Có lỗi xảy ra khi đặt hàng.");
        }
    });
});
