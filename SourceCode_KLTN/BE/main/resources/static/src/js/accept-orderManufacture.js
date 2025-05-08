$(document).ready(function() {
    $(document).on("click", ".acceptOrder", function() {
        console.log("===============");
        var id = $(this).data('id');
        const baseUrl = document.getElementById("urlContainer").getAttribute("data-url");
        fetch(`${baseUrl}/${id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                       throw errorData;
                });
            }
            return response.text();
        })
        .then(data => {
            if (data === 'ok') {
                alert('Đơn hàng đã được tạo thành công!');
                location.reload();
            } else {
                alert('Có lỗi xảy ra.');
            }
        })
        .catch((error) => {
            console.error('There was a problem with the fetch operation:', error);
            let missingMaterials = '';
                        for (let materialId in error) {
                            const material = error[materialId];
                            missingMaterials += `Sản phẩm: ${material.materialName} - Số lượng thiếu: ${material.amount}\n`;
                        }

            alert('Nguyên Liệu Chưa đủ:\n' + missingMaterials);
        });
    });
    $(document).on("click", ".ProcessBtn", function(){
        var id = $(this).data('id');
        const baseUrl = document.getElementById("urlContainer").getAttribute("data-url");
        $.ajax({
                url: baseUrl + '/' + id,
                type: 'PUT',
                success: function(response) {
                    alert("Order " + id + " has been processed successfully.");
                    window.location.reload();
                },
                error: function(xhr, status, error) {
                    alert("Error processing order " + id + ": " + xhr.responseText);
                }
            });
    });

    $(document).on("click",".CompleteBtn", function(){
        var id = $(this).data('id');
        const baseUrl = document.getElementById("urlContainer1").getAttribute("data-url");
        $.ajax({
                url: baseUrl + '/' + id,
                type: 'POST',
                success: function(response) {
                    alert("Order " + id + " has been complete successfully.");
                    window.location.reload();
                },
                error: function(xhr, status, error) {
                    alert("Error processing order " + id + ": " + xhr.responseText);
                }
        });
    });

    $(document).on("click", ".deleteOrder", function() {
        var orderId = $(this).data('id');
        const baseUrl = document.getElementById("urlContainer").getAttribute("data-url");

        $.ajax({
            url: `${baseUrl}/${orderId}`,
            type: 'DELETE',
            success: function(result) {
                alert('Đơn hàng đã được xóa thành công!');
                location.reload();
            },
            error: function(xhr) {
                alert('Có lỗi xảy ra khi xóa đơn hàng.');
            }
        });
    });
});
