function deleteFunction(id){
    swal({
        title: "¿Esta seguro que quiere eliminar este empleado?",
        text: "Once deleted, you will not be able to recover this imaginary file!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((OK) => {
            if (OK) {
                $.ajax({
                    url:"/delete/"+id,
                    success: function (res){
                        console.log(res);
                    }
                })
                swal("Poof! Your imaginary file has been deleted!", {
                    icon: "success",
                }).then((ok)=>{
                    location.href="/employee/index";
                });
            } else {
                swal("Your imaginary file is safe!");
            }
        });
}