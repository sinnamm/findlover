$(function () {
    $("#label-table").editableTableWidget();
    initAddBtn();
    initDelBtn();
    initTdOnChange();
});

function initTdOnChange() {
    $("#label-table td.editable").on("change", function (evt, newValue) {
        var labelId = $(this).parent().attr("id").split("-")[1];
        if (newValue === "") {
            swal("提示", "标签名称不能为空！", "error");
            return false;
        } else {
            if (window.confirm("确定修改?")) {
                var result = false;

                $.ajax({
                    url: contextPath + "admin/user/label/exists",
                    type: "post",
                    data: {"name": newValue},
                    dataType: "text",
                    async: false,
                    success: function (data) {
                        if (data!="true") {
                            $.ajax({
                                url: contextPath + "admin/user/label/" + labelId,
                                async: false,
                                data: {"name": newValue},
                                type: "put",
                                dataType: "text",
                                success: function (data) {
                                    if (data) {
                                        swal("提示", "标签名称修改成功！", "success");
                                        result = true;
                                    } else {
                                        errorAlert();
                                    }
                                },
                                error: errorAlert
                            });
                        }else{
                            swal("Error", "标签名称已存在！", "error");
                        }
                    },
                    error: errorAlert
                });
                return result;
            } else {
                return false;
            }
        }
    })
}

function initAddBtn() {
    $("#add-btn").click(function () {
        swal("请输入要添加的标签名称：", {
            content: "input"
        }).then(value => {
            if (value === "") {
                swal("Error", "标签名称不能为空！", "error");
            } else {
                $.post(contextPath + "admin/user/label/exists", {"name": value}, function (result) {
                    if (result!="true") {
                        $.post(contextPath + "admin/user/label", {"name": value}, function (data) {
                            if (data) {
                                swal("提示", "标签添加成功！", "success");
                                $("#label-table").append($('<tr id="tr-' + data + '">\n' +
                                    '                           <td>' + data + '</td>\n' +
                                    '                           <td class="editable">' + value + '</td>\n' +
                                    '                           <td><button id="del-' + data + '" class="button button-caution button-pill button-small">删除</button></td>\n' +
                                    '                       </tr>'));
                                $("#label-table").editableTableWidget();
                                initTdOnChange();
                                initDelBtn();
                            } else {
                                errorAlert();
                            }
                        })
                    } else {
                        swal("Error", "标签名称已存在！", "error");
                    }
                }, "text")
            }
        });
    });
}

function initDelBtn() {
    $("button[id^='del-']").click(function () {
        var id = this.id.split("-")[1];
        swal({
            title: "确定要删除ID为" + id + "的标签？",
            icon: "warning",
            buttons: ["取消", "确定"]
        }).then(result => {
            if (result) {
                $.ajax({
                    url: contextPath + "admin/user/label/" + id,
                    type: "delete",
                    data: {},
                    dataType: "text",
                    success: function (result) {
                        if (result) {
                            swal("提示", "删除成功！", "success");
                            $("tr[id*=" + id + "]").remove();
                        } else {
                            errorAlert();
                        }
                    }
                })
            }
        });


    });
}