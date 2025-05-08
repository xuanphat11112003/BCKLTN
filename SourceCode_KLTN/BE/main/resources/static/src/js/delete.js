function deleteS(endpoint, id) {
    if (confirm("Bạn chắc chắn xóa không?") === true) {
        console.log(endpoint);
        console.log(id);
//        fetch(endpoint, {
//            method: "delete"
//        }).then(res => {
//            if (res.status === 204) {
//                window.location.reload();
//            } else
//                alert("Something Wrong!");
//        });
    }
}