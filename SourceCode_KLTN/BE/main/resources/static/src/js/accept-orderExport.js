$(document).ready(function() {
    var orderId;
    $('.acceptOrder').click(function() {
        orderId = $(this).data('id');
        $('#submitOrderBtn').data('id', orderId);
        $('#confirmOrderModal').modal('show');
    });

    $('#submitOrderBtn').click(function() {
        var orderId = $(this).data('id');
        var transportId = $('#transportSelect').val();


        if (!transportId) {
            alert("Vui lòng nhập đủ thông tin!");
            return;
        }
        const url = document.getElementById("urlContainer").getAttribute("data-url");

        $.ajax({
             url: url  + orderId + '/' + transportId,
             type: 'POST',
             success: function(response) {
                alert('Order confirmed successfully');
                location.reload();
            },
            error: function(error) {
                var response = JSON.parse(error.responseText);

                var message = "Tồn kho không đủ cho các sản phẩm sau:\n";
                response.forEach(function(item) {
                   message += "- " + item.productName + " (Số lượng: " + item.amount + ")\n";
                });
                 message += "\nBạn có muốn đi tới trang sản xuất không?";
                 if (confirm(message)) {
                    window.location.href =  window.location.origin + "/chuoicungung/manufacture/order";
                 }

            }
        });
    });
});
