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
    initFormValidator();
});

//初始化表单验证
function initFormValidator() {
    //初始化修改作家密码表单验证
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
                url:contextPath+'admin/writer/info/'+$form.find('#writerid').text(),
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
    //初始化新增作家表单验证
    $("#add-writer-form").validator({
        rules: {
            nickname: [/^([\u4E00-\u9FA5]|[a-z0-9]{1,2}){2,6}$/, "笔名应为4-12位字符"]
        },
        fields: {
            'pseudonym': 'required;nickname,remote['+contextPath+'admin/writer/checkPseudonym, pseudonym]',
            'username': 'required;username,remote['+contextPath+'admin/writer/checkUsername, username]',
            'wpwd': '密码:required;password',
            'password': 'required;match(wpwd)'
        },
        theme:'bootstrap',
        timely:2,
        stopOnError:true,
        valid: function (form) {
            var $form = $(form);
            $.ajax({
                url:contextPath+'admin/writer/add',
                data:$form.serialize()+'&identity=all&pageNum='+pageNum+'&pageSize='+pageSize+'&column=id&keyword=',
                type:"post",
                dataType:'json',
                success:function (data) {
                    if(data.result){
                        $('#addWriter').modal('hide');
                        swal("温馨提示","添加成功！",'success');
                        var page = data.page;
                        var $userBasicTBody = $("#user-basic-table").find(">tbody");
                        $userBasicTBody.empty();
                        for (var x = 0; x < page.list.length; x++) {
                            var writer = page.list[x];
                            var tr = $(' <tr>\n' +
                                '             <td>'+writer.id+'</td>\n' +
                                '             <td>'+writer.username+'</td>\n' +
                                '             <td>'+writer.pseudonym+'</td>\n' +
                                '             <td>'+writer.regTime+'</td>\n' +
                                '             <td>'+writer.essayCount+'</td>\n' +
                                '        </tr>');
                            if (writer.status === 0) {
                                tr.append($('<td><button id="edit-writer-status-' + writer.id + '-0" class="btn btn-sm btn-danger"><i class="fa fa-times"></i>&nbsp;已锁定</button></td>'));
                            } else {
                                tr.append($('<td><button id="edit-writer-status-' + writer.id + '-1" class="btn btn-sm btn-success"><i class="fa fa-check"></i>&nbsp;已激活</button></td>'));
                            }
                            tr.append($('<td><a id="update-'+writer.id+'" class="btn btn-sm btn-info"><i class="fa fa-edit"></i>&nbsp;修改密码</a></td>'));
                            $userBasicTBody.append(tr);
                        }
                        initRePwdBtn();
                        initEditUserStatusBtn();
                        setPage(page.pageNum,page.total, page.pages, "goPage");
                    }else {
                        swal("温馨提示","添加失败！",'error');
                    }
                }
            });
        }
    });
}

//初始化修改密码按钮
function initRePwdBtn() {
    $('a[id^="update-"]').click(function () {
        var id = this.id.split("-")[1];
        $('#writerid').text(id);
        $('#modifyPwd').modal('show');
    });
    $('#add-writer').click(function () {
        $('#addWriter').modal('show');
        $("#add-writer-form")[0].reset();
    });
}

function loadData() {
    $.get(contextPath + "admin/writer/info",
        {
            "identity": identity,
            "pageNum": pageNum,
            "pageSize": pageSize,
            "column": $("#basic-search-sel").val(),
            "keyword": $("#basic-search-val").val()
        }, function (data) {
            var $userBasicTBody = $("#user-basic-table").find(">tbody");
            $userBasicTBody.empty();
            for (var x = 0; x < data.list.length; x++) {
                var writer = data.list[x];
                var tr = $(' <tr>\n' +
                    '             <td>'+writer.id+'</td>\n' +
                    '             <td>'+writer.username+'</td>\n' +
                    '             <td>'+writer.pseudonym+'</td>\n' +
                    '             <td>'+writer.regTime+'</td>\n' +
                    '             <td>'+writer.essayCount+'</td>\n' +
                    '        </tr>');
                if (writer.status === 0) {
                    tr.append($('<td><button id="edit-writer-status-' + writer.id + '-0" class="btn btn-sm btn-danger"><i class="fa fa-times"></i>&nbsp;已锁定</button></td>'));
                } else {
                    tr.append($('<td><button id="edit-writer-status-' + writer.id + '-1" class="btn btn-sm btn-success"><i class="fa fa-check"></i>&nbsp;已激活</button></td>'));
                }
                tr.append($('<td><a id="update-'+writer.id+'" class="btn btn-sm btn-info"><i class="fa fa-edit"></i>&nbsp;修改密码</a></td>'));
                $userBasicTBody.append(tr);
            }
            initRePwdBtn();
            initEditUserStatusBtn();
            setPage(data.pageNum,data.total, data.pages, "goPage");
        }, "json");
}

function initEditUserStatusBtn() {
    $("button[id^='edit-writer-status']").click(function () {
        var userId = this.id.split("-")[3];
        var status = this.id.split("-")[4];
        var text = status === "1" ? "锁定ID为" + userId + "的用户？" : "激活ID为" + userId + "的用户？";
        var $btn = $(this);
        swal({
            title: text,
            icon: "warning",
            buttons: ["取消", "确定"]
        }).then(result => {
            if (result) {
                $.ajax({
                    url: contextPath + "admin/writer/info/" + userId,
                    type: "put",
                    data: {"status": status ^ 1},
                    dataType: "text",
                    success: function (data) {
                        if (data === "true") {
                            swal("账号状态修改成功！", "", "success");
                            if (status === "1") {
                                $btn.removeClass("btn-success");
                                $btn.addClass("btn-danger");
                                $btn.html('<i class="fa fa-times"></i>&nbsp;已锁定');
                                $btn.attr("id", "edit-user-status-" + userId + "-" + (status ^ 1))
                            } else {
                                $btn.removeClass("btn-danger");
                                $btn.addClass("btn-success");
                                $btn.html("已激活");
                                $btn.html('<i class="fa fa-check"></i>&nbsp;已激活');
                                $btn.attr("id", "edit-user-status-" + userId + "-" + (status ^ 1))
                            }
                        } else {
                            swal("账号状态修改失败！", "", "error");
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
    $("button[id*='writer-btn']").click(function () {
        identity = this.id.split("-")[2];
        goPage(1);
    });
}

function initSearchBtn() {
    $("#writer-search-btn").click(function () {
        goPage(1);
    })
}


