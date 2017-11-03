var pageNum = 1;
var pageSize = 5;
var identity = "all";
var pageSizeArray = [1, 3, 5, 8, 10];

$(function () {
    //初始化nice-validator样式
    niceBaseCofig();
    setPageSizeSel(pageSizeArray, pageSize);
    loadData();
    initPageSizeSel();
    initIdentityBtn();
    initSearchBtn();
});

//初始化表单验证
function initFormValidator() {
    $("#resetPwd").validator({
        fields: {
            'pwd': '密码:required;password',
            'password': 'required;match(pwd)'
        },
        theme:'bootstrap',
        timely:2,
        stopOnError:true,
        valid: function (form) {
            var $form = $(form);
            $.ajax({
                url:contextPath+'admin/complain/info/'+$form.find('#complainid').text(),
                data:$form.serialize(),
                type:"put",
                dataType:'text',
                success:function (data) {
                    $('#modifyPwd').modal('hide');
                    if(data=='true'){
                        swal("温馨提示","修改成功！","success");
                    }else {
                        swal("温馨提示","修改失败！","success");
                    }
                }
            });
        }
    });
}

/*//初始化修改密码按钮
function initRePwdBtn() {
    $('a[id^="show-"]').click(function () {
        var id = this.id.split("-")[1];
        $('#complainid').text(id);
        $('#modifyPwd').modal('show');
    });
}*/
function loadData() {
    $.get(contextPath + "admin/complain/info",
        {
            "identity": identity,
            "pageNum": pageNum,
            "pageSize": pageSize,
            "column": $("#basic-search-sel").val(),
            "keyword": $("#basic-search-val").val()
        }, function (data) {
            var $complainBasicTBody = $("#complain-basic-table").find(">tbody");
            $complainBasicTBody.empty();
            for (var x = 0; x < data.list.length; x++) {
                var complain = data.list[x];
                var tr = $(' <tr>\n' +
                    '             <td>'+complain.id+'</td>\n' +
                    '             <td>'+complain.userId+'</td>\n' +
                    '             <td>'+complain.comObj+'</td>\n' +
                    '             <td>'+complain.reason+'</td>\n' +
                    '             <td>'+complain.comTime+'</td>\n' +
                    '        </tr>');
                if (complain.status === 0) {
                    tr.append($('<td><button class="btn btn-sm btn-default"><i class="fa fa-times"></i>&nbsp;待处理</button></td>'));
                } else if (complain.status === 1) {
                    tr.append($('<td><button class="btn btn-sm btn-info"><i class="fa fa-check-square-o"></i>&nbsp;忽略</button></td>'));
                } else if (complain.status === 2) {
                    tr.append($('<td><button class="btn btn-sm btn-warning"><i class="fa fa-check-square-o"></i>&nbsp;警告</button></td>'));
                } else if (complain.status === 3) {
                    tr.append($('<td><button class="btn btn-sm btn-danger"><i class="fa fa-check-square-o"></i>&nbsp;封号</button></td>'));
                }
                tr.append($('<td><a class="btn btn-sm btn-info" href="' + contextPath + 'admin/complain/detail/' + complain.id + '"><i class="fa fa-edit"></i>&nbsp;查看详情</a></td>'));
                $complainBasicTBody.append(tr);
            }
            setPage(data.pageNum,data.total, data.pages, "goPage");
        }, "json");
}

function initEditUserStatusBtn() {
    $("button[id^='edit-complain-status']").click(function () {
        var complainId = this.id.split("-")[3];
        var status = this.id.split("-")[4];
        var dbstatus = this.value;
        if(dbstatus == 2){
            swal("温馨提醒","请查看详情进行审核后，再上架！", "warning");
            return;
        }
        var text = status === "1" ? "下架ID为" + complainId + "的文章？" : "上架ID为" + complainId + "的文章？";
        var $btn = $(this);
        swal({
            title: text,
            icon: "warning",
            buttons: ["取消", "确定"]
        }).then(result => {
            if (result) {
                $.ajax({
                    url: contextPath + "admin/complain/info/" + complainId,
                    type: "put",
                    data: {"status": status ^ 1},
                    dataType: "text",
                    success: function (data) {
                        if (data === "true") {
                            swal("状态修改成功！", "", "success");
                            if (status === "1") {
                                $btn.removeClass("btn-success");
                                $btn.addClass("btn-warning");
                                $btn.html('<i class="fa fa-times"></i>&nbsp;未上架');
                                $btn.attr("id", "edit-complain-status-" + complainId + "-" + (status ^ 1))
                            } else {
                                $btn.removeClass("btn-warning");
                                $btn.addClass("btn-success");
                                $btn.html("已下架");
                                $btn.html('<i class="fa fa-check"></i>&nbsp;已上架');
                                $btn.attr("id", "edit-complain-status-" + complainId + "-" + (status ^ 1))
                            }
                        } else {
                            swal("状态修改失败！", "", "error");
                        }
                    },
                    error: errorAlert
                });
            }
        });
    })
}

function goPage(_pageNum) {
    pageNum = _pageNum;
    loadData();
}


function initPageSizeSel() {
    $("#pageSize-sel").change(function () {
        pageSize = $(this).val();
        goPage(1);
    });
}

function initIdentityBtn() {
    $("button[id*='complain-btn']").click(function () {
        identity = this.id.split("-")[2];
        goPage(1);
    });
}

function initSearchBtn() {
    $("#complain-search-btn").click(function () {
        goPage(1);
    })
}

