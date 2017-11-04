var pageNum = 1;
var pageSize = 5;
var pageSizeArray = [1, 3, 5, 8, 10];

$(function () {
    initPermBtn();
    initSubBtn();
    initDelBtn();
    initFormValidator();
    $("#role-add-btn").click(function () {
        $("#add-role-modal").modal("toggle");
    });
});

function initPermBtn() {
    $("button[id^='btn-']").click(function () {
        var roleId = this.id.split("-")[1];
        $("#role-id-label").text(roleId);
        $.get(contextPath + "admin/perm/role_permissions", {"roleId": roleId}, function (data) {
            var rpids = data.rpids;
            var perms = data.perms;
            var $permTable = $("#perm-table");
            $permTable.empty();
            var tr = $('<tr></tr>');
            for (var x = 0; x < perms.length; x++) {
                var perm = perms[x];
                tr.append($('<td><input type="checkbox" name="perm" value="' + perm.id + '" ' + (rpids.indexOf(perm.id) >= 0 ? 'checked' : '') + '>&nbsp;' + perm.name + '</td>'));
                if ((x + 1) % 3 == 0 || x + 1 == perms.length) {
                    $permTable.append(tr);
                    tr = $('<tr></tr>');
                }
            }
        });
        $("#edit-role-modal").modal("toggle");
    });
}

function initSubBtn() {
    $("#role-sub-btn").click(function () {
        var roleId = $("#role-id-label").text();
        var permIds = [];
        $("input[name='perm']").each(function () {
            if(this.checked) {
                permIds.push(this.value);
            }
        });
        $.post(contextPath+"admin/perm/role_permissions",{"roleId":roleId,"permIds":permIds},function (result) {
            if(result == "true"){
                swal("提示","权限修改成功","success");
            }else{
                errorAlert();
            }
        },"text");
    });
}
function initFormValidator() {
    $("#add-role-form").validator({
        rules:{
            value:[/^\w{2,20}$/,"角色内容必须由2-20位的字母、数字或下划线组成！"]
        },
        fields: {
            name: "required;length(~30)",
            value:"required;value;remote(GET:"+contextPath+"admin/perm/role/check)",
            pid:"checked(1~)"
        }
    })
}
function initDelBtn() {
    $("button[id^='del-']").click(function () {
        swal({
            title:"提示",
            text:"确定删除此角色？",
            icon:"warning",
            buttons:["取消","确定"]
        }).then(result=>{
            if(result){
                var roleId = this.id.split("-")[1];
                $.ajax({
                    url:contextPath+"admin/perm/role/"+roleId,
                    type:"delete",
                    dataType:"text",
                    success:function (data) {
                        if(data=="true"){
                            swal("提示","删除成功！","success");
                            $("#tr-"+roleId).remove();
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