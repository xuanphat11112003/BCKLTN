const openMaterialStockModal = (orderId) => {
    document.getElementById("orderIdInput").value = orderId;
    $('#materialStockModal').modal('show');
};

const submitMaterialStock = () => {
        const orderId = document.getElementById("orderIdInput").value;
        const wareHouseId = parseInt(document.getElementById("warehouseId").value, 10);
        const entryDate = document.getElementById("entryDate").value;

        const url = document.getElementById("urlContainer").getAttribute("data-url");
        console.log(url);
        const materialInventoryRequest = {
            wareHouseId: wareHouseId,
            entryDate: entryDate
        };
        console.log(materialInventoryRequest);

        fetch(`${url}/${orderId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(materialInventoryRequest),
        })
    .then(response => {
         if (response.ok) {
              alert("Tồn kho đã được cập nhật!");
              location.reload();
         } else {
             alert("Có lỗi xảy ra trong quá trình cập nhật!");
         }
        })
    .catch(error => {
         console.error("Error:", error);
         alert("Có lỗi xảy ra trong quá trình cập nhật!");
   });
};