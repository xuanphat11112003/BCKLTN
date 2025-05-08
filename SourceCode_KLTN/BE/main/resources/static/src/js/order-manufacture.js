$(document).ready(function(){
    $('.product-btn').click(function(){
        var productId = $(this).data('id');
        var productName = $(this).data('name');
        var existingItem = $('#selectedProducts li[data-id="' + productId + '"]');
        if(existingItem.length){
            var quantityElement = existingItem.find('.quantity');
            var currentQuantity = parseInt(quantityElement.text());
            quantityElement.text(currentQuantity+1);
        }else{
            var listItem = $('<li class="list-group-item" data-id="' + productId + '"></li>');
            listItem.html(
                '<span>' + productName + '</span><span style="margin-left: 10%" class="quantity">1</span>' +
                '<button type="button" class="close" aria-label="Close" onclick="removeProducts(this)">' +
                '<span aria-hidden="true">&times;</span></button>'
            )
            $('#selectedProducts').append(listItem);
        }
    })
});
function removeProducts(button) {
        $(button).closest('li').remove();
    }

$(document).ready(function() {
    $('#submitButton').click(function() {
        var selectedProducts = [];
        $('#selectedProducts li').each(function() {
            var productId = $(this).data('id');
            var quantity = parseInt($(this).find('.quantity').text());
            if (productId && !isNaN(quantity) && quantity > 0) {
                    selectedProducts.push({
                        productId: productId,
                        quantity: quantity
                    });
                } else {
                    // You can choose to alert or log for invalid entries
                    alert('Vui Lòng Chọn Ít Nhất 1 Sản Phẩm');
                    return;
                }
        });

        var wareHouseId = $('#wareHouseId').val();
        if (!wareHouseId) {
            alert('Chưa chọn kho nhập');
            return;
        }
        const baseUrl = document.getElementById("urlContainer").getAttribute("data-url");

        $.ajax({
            url: baseUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                details: selectedProducts,
                wareHouseId: wareHouseId
            }),
            success: function(response) {
                alert('Tạo đơn hàng thành công!');

                window.location.href = window.location.origin + '/chuoicungung/admin';;
            },
            error: function(xhr) {
                var response = xhr.responseJSON ;
                if (xhr.status === 400) {
                   var responseString = JSON.stringify(response);
                   if (responseString  && typeof responseString === 'string' && responseString.includes("sản phẩm chưa có công thức")) {
                         alert('Có sản phẩm chưa có công thức. Vui lòng kiểm tra lại.');
                  }else{
                    var checklist = xhr.responseJSON;
                    var message = 'Vật liệu trong kho không đủ:\n';
                    for (var key in checklist) {
                        message += checklist[key].materialName + ': ' + checklist[key].amount + ' '+ 'đơn vị tính' + '\n';
                    }
                        if (confirm(message + 'Bạn có muốn tạo đơn hàng kho không?')) {
                       showMaterialForm(checklist);
                  }
                }
            } else {
                    alert('Có lỗi xảy ra. Vui lòng thử lại.');
                }
            }
        });
    });

    function showMaterialForm(checklist) {
            var paymentSelect = document.getElementById('PaymentId').outerHTML;
            console.log(paymentSelect);
            var materialList = $('#materialList');
            materialList.empty();

            var groupedMaterials = {};
            var idCounter = 0;
            for (var key in checklist) {
                var material = checklist[key];
                var supplier = material.supplierName;

                if (!groupedMaterials[supplier]) {
                    groupedMaterials[supplier] = [];
                }
                groupedMaterials[supplier].push(material);
            }
            for (var supplier in groupedMaterials) {
                materialList.append('<h5 id = "supplier_'+ material.supplierId + '">' + supplier + '</h5>');

                var productList = $('<ul></ul>');
                groupedMaterials[supplier].forEach(function(material) {
                    var amount = parseInt(material.amount/material.remain) + 1;
                    console.log(amount);
                    productList.append('<li id="material_'+material.materialId+'">' + material.materialName + ': ' + amount + '</li>');
                });
                idCounter++;
                materialList.append(productList);
                var paymentOptions = '<div class="Payments_' + idCounter + ' ">';

                paymentOptions += paymentSelect;

                paymentOptions += '</select></div>';

                materialList.append(paymentOptions);
            }

            $('#materialModal').modal('show');
    }
});
$(document).ready(function() {
    $('#createOrder').click(function(){
        var selectedProducts = [];
        $('#materialList ul').each(function(index) {
          var supplierProducts = [];
          $(this).find('li').each(function() {
          var materialId = $(this).attr('id').split('_')[1];
          var amount = $(this).text().split(': ')[1];

          supplierProducts.push({
                   materialID: materialId,
                   quantity: amount
                   });
          });
          var paymentMethod = $('.Payments_'+ (index + 1)+ ' .field-payment').val();
          selectedProducts.push({
                  supplier: $(this).prev('h5').attr('id').split('_')[1],
                  payment: paymentMethod,
                  detail: supplierProducts
                  });
          });
          const baseUrl = document.getElementById('urlContainer1').getAttribute("data-url");
          $('#loading-overlay').show();
        $.ajax({
            url: baseUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(selectedProducts),
            success: function(response) {
                    $('#loading-overlay').hide();
                    alert('Đặt hàng thành công!');
                    $('#submitButton').hide();
                    $('#createOrder1').show();
                    $('#materialModal').modal('hide');
            },
            error: function(xhr) {
                alert('Có lỗi xảy ra. Vui lòng thử lại.');
               }
        });
    })
})
$(document).ready(function(){
    $('#createOrder1').click(function(){
        console.log("aaaaaaaaaaaa");
        var wareHouseIds = $('#wareHouseId').val();
        var products = [];
        $('#selectedProducts li').each(function(){
            var productId = $(this).data('id');
            var quantity = parseInt($(this).find('.quantity').text());
            products.push({
                productId: parseInt(productId,10),
                quantity: parseInt(quantity,10)
            });
        });
        var jsonData = {
            wareHouseId: wareHouseIds,
            details: products
        }
        console.log(jsonData);
        const baseUrl = document.getElementById('urlContainer2').getAttribute("data-url");
        $.ajax({
            url: baseUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(jsonData),
            success: function(response) {
                    alert('Đặt hàng thành công!');
                    if (confirm('Đã Lữu Trữ Đơn Hàng Thành Công Bạn Có Muốn Quay Lại Trang Chủ Không?')) {
                         window.location.href = window.location.origin + '/chuoicungung/admin';
                    }else
                        window.location.reload();
            },
            error: function(xhr) {
                alert('Có lỗi xảy ra. Vui lòng thử lại.');
                }
            });

    });
});