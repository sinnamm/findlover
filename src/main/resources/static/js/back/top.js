$(function () {
    initUpdatePwd();
    initFormValidator();
    initLogout();
});

function initUpdatePwd() {
    $("#updatePwd-btn").click(function () {
        $("#admin-pwd-modal").modal("toggle");
    });
}

function initFormValidator() {
    $("#update-pwd-form").validator({
        fields: {
            oldpwd: "required;length(3~16)",
            newpwd: "required;length(3~16)",
            confpwd: "required;length(3~16);match(newpwd)",
        },
        valid: function (form) {
            $.get(contextPath + "admin/admins/pwdcheck", {"password": $("#oldpwd").val()}, function (flag) {
                if (flag == "true") {
                    $.ajax({
                        url: contextPath + "admin/admins",
                        type: "put",
                        data: {
                            "password": $("#newpwd").val()
                        },
                        dataType: "text",
                        success: function (flag) {
                            if (flag == "true") {
                                swal({
                                    title: "提示",
                                    text: "密码修改成功，请重新登录！",
                                    icon: "success",
                                    buttons: ["关闭","确定"]
                                }).then(result => {
                                    window.location = contextPath + "admin/logout";
                                });
                            }
                        },
                        error: errorAlert
                    });
                } else {
                    swal("提示", "原密码错误！", "error");
                    form.reset();
                }
            }, "text");
        }
    });
}

function initLogout() {
    $("#logout-btn").click(function () {
        swal({
            title: "提示",
            text: "确定退出登录？",
            icon: "warning",
            buttons: ["取消","确定"]
        }).then(result => {
            if(result) {
                window.location = contextPath + "admin/logout";
            }
        });
    });
}