$(function () {
    initDelBtn();
    initFormValidator();
    $("#perm-add-btn").click(function () {
        $("#add-perm-modal").modal("toggle");
    });
});

function initFormValidator() {
    $("#add-perm-form").validator({
        rules:{
            value:[/^[\w:]{2,20}$/,"权限内容必须由2-20位的字母、数字、英文冒号或下划线组成！"]
        },
        fields: {
            name: "required;length(~30)",
            value:"required;value;remote(GET:"+contextPath+"admin/perm/permission/check)",
        }
    })
}
function initDelBtn() {
    $("button[id^='del-']").click(function () {
        swal({
            title:"提示",
            text:"确定删除此权限？",
            icon:"warning",
            buttons:["取消","确定"]
        }).then(result=>{
            if(result){
                var permId = this.id.split("-")[1];
                $.ajax({
                    url:contextPath+"admin/perm/permission/"+permId,
                    type:"delete",
                    dataType:"text",
                    success:function (data) {
                        if(data=="true"){
                            swal("提示","删除成功！","success");
                            $("#tr-"+permId).remove();
                        }else{
                            swal("提示","出现错误，删除失败！","error");
                        }
                    },
                    error:errorAlert
                });
            }
        });

    });
}