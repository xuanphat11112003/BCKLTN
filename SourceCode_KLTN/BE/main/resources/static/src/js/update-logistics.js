$(document).ready(function() {
   $('#warehouseTabs li a').click(function() {
        $('#warehouseTabs li').removeClass('active');
        $(this).parent().addClass('active');
   });
});
$(document).ready(function() {
    const baseUrl = $('#urlContainer').data('url');
    const baseUrl1 = $('#urlContainer1').data('url');
    $('.acceptOrder').on('click', function() {
        const orderId = $(this).data('id');
        const warehouseId = $('#warehouseId').val();
        const url = `${baseUrl}/${orderId}/${warehouseId}`;

        $.ajax({
            url: url,
            type: 'PUT',
            success: function(response) {
                alert(response);
                location.reload();
            },
            error: function(xhr, status, error) {
                console.log(error); // Xử lý lỗi
                alert('Có lỗi xảy ra khi nhận đơn hàng.');
            }
        });
    });


    $('.ProcessBtn').on('click', function() {
        const orderId = $(this).data('id'); // Get the order ID
        $('#exportOrderBtn').data('id', orderId); // Set the order ID to the modal button
        $('#warehouseSelectModal').modal('show'); // Show the modal
    });

    $('#selectWarehouseForm').on('submit', function(e) {
            e.preventDefault();
            const orderId = $('#exportOrderBtn').data('id');
            const warehouseId = $('#warehouseId').val();
            const warehouseIds = $('#warehouseIds').val();
            const url = `${baseUrl}/${orderId}/${warehouseId}`;
            const params = {
                warehouse: warehouseIds
            };

            $.ajax({
                url: url,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(params),
                success: function(response) {
                    alert(response);
                    location.reload();
                },
                error: function(xhr, status, error) {
                    console.log(error);
                    alert('Có lỗi xảy ra khi xuất đơn hàng.'); // Error message
                }
            });
        });
        $('#deliverToCustomerBtn').on('click', function() {
                const orderId = $('#exportOrderBtn').data('id');
                const warehouseId = $('#warehouseId').val();
                const url = `${baseUrl1}/${orderId}/${warehouseId}`;

                $.ajax({
                    url: url,
                    type: 'PUT',
                    success: function(response) {
                        alert(response);
                        location.reload();
                    },
                    error: function(xhr, status, error) {
                        console.log(error); // Xử lý lỗi
                        alert('Có lỗi xảy ra khi giao hàng đến khách hàng.');
                    }
            });
        });
});
