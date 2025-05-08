    const LoadPrice = (productId, productPrice) => {
        var $productList = $('#productList');
        var $existingRow = $productList.find('tr[data-id="' + productId + '"]');

        if ($existingRow.length) {
            var unitPrice = productPrice;
            var quantity = parseInt($existingRow.find('input.quantity').val());
            $existingRow.find('td.custom-td:nth-child(2)').text((unitPrice * quantity).toFixed(2));
        }
    };

    $(document).ready(function() {
        $('#agencyId').on('change', function() {

            var selectedOption = $(this).find('option:selected');
            var address = selectedOption.data('address');

            if (address) {
                $('#agency-address').text('Địa chỉ: ' + address);
            } else {
                $('#agency-address').text('');
            }
        });
    });

   $('#confirmButton').on('click', function(e) {
       e.preventDefault();

       // Clear previous details
       $('#orderDetailsList').empty();

       // Get the selected products and calculate costs
       var orderDetails = [];
       var totalWarehouseCost = 0;
       var totalTransportCost = 0;

       $('#productList tr').each(function() {
           var productId = $(this).data('id');
           var productName = $(this).find('td.name').text();
           var quantity = $(this).find('input.quantity').val();

           orderDetails.push({
               productId: productId,
               quantity: parseInt(quantity)
           });

           $('#orderDetailsList').append(`<li>${productName} - Số lượng: ${quantity}</li>`);
       });

       var selectedOption = $('#agencyId').find('option:selected');
       var userAddress = selectedOption.data('address');
       const selectedTransportPrice = parseFloat($('#transportId').find('option:selected').val()) || 0;
       console.log(selectedTransportPrice);
       const data = {
           userAddress: userAddress,
           details: orderDetails
       };

       const baseUrl = document.getElementById('url-container').getAttribute('data-url');
       $.ajax({
           url: baseUrl,
           method: 'POST',
           contentType: 'application/json',
           data: JSON.stringify(data),
           success: function(response) {
               console.log('API Response:', response);
               const distance = response.trans;
               totalWarehouseCost = response.warehouseCost;
               totalTransportCost = selectedTransportPrice * distance;
               total_price =parseInt($('#totalPrice').text(), 10);
               $('#modalWarehouseCost').text(totalWarehouseCost);
               $('#modalTransportCost').text(totalTransportCost);
               const grandTotal = totalWarehouseCost + totalTransportCost + total_price;
               $('#modalTotalamount').text(grandTotal)
               $('#confirmOrderModal').modal('show');
           },
           error: function(xhr, status, error) {
               console.log(error);
           }
       });
   });

    $(document).on('click', '.btn-product', function() {
    var productId = $(this).data('id');
    var productName = $(this).text();
    var productPrice = $(this).data('price');

    var $productChoice = $('#productList');
    var $existingRow = $productChoice.find('tr[data-id="' + productId + '"]');

    if ($existingRow.length) {
        var $quantityInput = $existingRow.find('input.quantity');
        var newQuantity = parseInt($quantityInput.val()) + 1;
        $quantityInput.val(newQuantity);
        var unitPrice = parseFloat(productPrice); // ensure we parse as float for proper calculation
        var newTotalPrice = unitPrice * newQuantity;
        $existingRow.find('td.custom-td:nth-child(2)').text(newTotalPrice.toFixed(2)); // Update total price
    } else {
        $productChoice.append(
            '<tr data-id="' + productId + '">' +
            '<td class = "name">' + productName + '</td>' +
            '<td class="custom-td">' + productPrice + '</td>' +
            '<td class="custom-td"><input type="number" class="form-control quantity" data-price="'+ productPrice +'" value="1" min="1" data-id="' + productId + '"></td>' +
            '<td class="custom-td"><button class="btn btn-danger remove-product">X</button></td>' +
            '</tr>'
        );
    }
        calculateTotalPrice();
    });
    $(document).on('click', '.remove-product', function() {
        $(this).closest('tr').remove();
        calculateTotalPrice();
    });

    $(document).on('change', '.quantity', function() {
        var productId = $(this).data('id');
        var productPrice = $(this).data('price');
        LoadPrice(productId, productPrice);
        calculateTotalPrice();
    });

    const calculateTotalPrice = () => {
        let totalPrice = 0;
        $('#productList tr').each(function() {
            const unitPrice = parseFloat($(this).find('td:nth-child(2)').text());
            const quantity = parseInt($(this).find('input.quantity').val());
            console.log(unitPrice);
            console.log(quantity);
            if (!isNaN(unitPrice) && !isNaN(quantity)) {
                totalPrice += unitPrice * quantity;
            }
        });
        $('#totalPrice').text(totalPrice.toFixed(2));
    };

    $('#finalizeOrderButton').on('click', function() {
        const baseUrl = document.getElementById('url-container1').getAttribute('data-url');
        const baseUrl1 = document.getElementById('url-container2').getAttribute('data-url');

        var orderDetails = [];
        $('#productList tr').each(function() {
            var productId = $(this).data('id');
            var productName = $(this).data('name');
            var quantity = parseInt($(this).find('input.quantity').val());
            var price = parseInt($(this).find('td:nth-child(2)').text());

            orderDetails.push({
                productId: productId,
                amount: quantity,
                total_price: price,
                productName: productName
            });
        });
        total  = parseInt($('#modalTotalamount').text());
        trans = parseInt($('#modalTransportCost').text());
        warehouseCost = parseInt($('#modalWarehouseCost').text()); /////////
        transSelect = $('#transportId').find('option:selected');
        tranId = parseInt(transSelect.data('id'),10) ;

        if (orderDetails.length === 0) {
            alert("Chọn ít nhất một sản phẩm để tạo đơn hàng");
            return;
        }

        var exportOrderRequest = {
            orderForId: $('#agencyId').val(),
            totalPrice : total,
            transPrice : trans,
            transId : tranId,
            wareHousePrice : warehouseCost,
            details: orderDetails
        };

        if (!exportOrderRequest.orderForId) {
            alert("Chọn agency trước khi tạo đơn hàng");
            return;
        }
        console.log(exportOrderRequest);

        $('#loading-overlay').show();

        $.ajax({
            type: 'POST',
            url: baseUrl,
            contentType: 'application/json',
            data: JSON.stringify(exportOrderRequest),
            success: function(data) {
                window.location.href = baseUrl1;
            },
            error: function(xhr, status, error) {
                console.error("Lỗi:", error);
                alert("Lỗi khi tạo đơn hàng: " + error);
            },
            complete: function() {
                $('#loading-overlay').hide();
            }
        });
    });