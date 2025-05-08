$(document).ready(function () {
        // Xử lý sự kiện khi nhấn nút vật liệu
        $('.material-btn').click(function () {
            var materialId = $(this).data('id');
            var materialName = $(this).data('name');
            var materialUnit = $(this).data('unit');

            var existingItem = $('#selectedMaterials li[data-id="' + materialId + '"]');

            if (existingItem.length) {
                var quantityElement = existingItem.find('.quantity');
                var currentQuantity = parseInt(quantityElement.text());
                quantityInput.val(currentQuantity + 1);
            } else {
                var listItem = $('<li class="list-group-item" style="position: relative;" data-id="' + materialId + '"></li>');
                listItem.html('<span>' + materialName +
                    '<input type="number" class="quantity-input" style="position: absolute; left: 29%; width: 15%; border-radius: 5px;" value="1" min="1" />' +
                    '<span style="position: absolute; left: 56%;">' + materialUnit + '</span>' +
                    '<button type="button" class="close" aria-label="Close" onclick="removeMaterial(this)">' +
                    '<span aria-hidden="true">&times;</span></button>');
                $('#selectedMaterials').append(listItem);
            }
        });


        $('#submitButton').click(function () {
            var manufacture = [];

            $('#selectedMaterials li').each(function () {
                var materialId = $(this).data('id');
                var quantity = parseInt($(this).find('.quantity-input').val());
                var productSelect = $('#productSelect').val();
                manufacture.push({
                    materialId: materialId,
                    amount: quantity,
                    productId : parseInt(productSelect, 10)
                });
            });
            if (manufacture.length === 0) {
                alert('Vui Lòng thêm ít nhất 1 vật liệu để tạo công thức');
                return;
            }
            const baseUrl = document.getElementById("urlContainer").getAttribute("data-url");
            console.log(baseUrl);
            console.log(manufacture);
            fetch(baseUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(manufacture)
            }).then(function (response) {
                if (!response.ok) {
                    throw new Error('Có lỗi xảy ra khi tạo Công Thức. Vui lòng thử lại sau.');
                }
                else{
                    if (confirm("Công thức đã tạo thành công \n Bạn có muốn quay về trang chủ không?")) {
                        window.location.href = window.location.origin + '/chuoicungung/admin';
                    } else {
                        location.reload();
                    }
                }
            });
        });
    });

    function removeMaterial(button) {
        $(button).closest('li').remove();
    }
