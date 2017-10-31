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
                url:contextPath+'admin/essay/info/'+$form.find('#essayid').text(),
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
        $('#essayid').text(id);
        $('#modifyPwd').modal('show');
    });
}*/
function loadData() {
    $.get(contextPath + "admin/essay/info",
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
                var essay = data.list[x];
                var tr = $(' <tr>\n' +
                    '             <td>'+essay.id+'</td>\n' +
                    '             <td>'+essay.writer.pseudonym+'</td>\n' +
                    '             <td>'+essay.title+'</td>\n' +
                    '        </tr>');
                if (essay.pubTime ==null) {
                    tr.append($('<td>-</td>'));
                } else {
                    tr.append($('<td>'+essay.pubTime+'</td>'));
                }
                if (essay.status === 2) {
                    tr.append($('<td><button class="btn btn-sm btn-warning"><i class="fa fa-times"></i>&nbsp;未审核</button></td>'));
                } else {
                    tr.append($('<td><button class="btn btn-sm btn-success"><i class="fa fa-check-square-o"></i>&nbsp;已审核</button></td>'));
                }
                if (essay.status === 1) {
                    tr.append($('<td><button id="edit-essay-status-' + essay.id + '-1" value="'+essay.status+'" class="btn btn-sm btn-success"><i class="fa fa-times"></i>&nbsp;已上架</button></td>'));
                } else {
                    tr.append($('<td><button id="edit-essay-status-' + essay.id + '-0" value="'+essay.status+'" class="btn btn-sm btn-warning"><i class="fa fa-check"></i>&nbsp;未上架</button></td>'));
                }
                tr.append($('<td><a class="btn btn-sm btn-info" href="' + contextPath + 'admin/essay/detail/' + essay.id + '"><i class="fa fa-edit"></i>&nbsp;查看详情</a></td>'));
                $userBasicTBody.append(tr);
            }
            initEditUserStatusBtn();
            setPage(data.pageNum,data.total, data.pages, "goPage");
        }, "json");
}

function initEditUserStatusBtn() {
    $("button[id^='edit-essay-status']").click(function () {
        var essayId = this.id.split("-")[3];
        var status = this.id.split("-")[4];
        var dbstatus = this.value;
        if(dbstatus == 2){
            swal("温馨提醒","请查看详情进行审核后，再上架！", "warning");
            return;
        }
        var text = status === "1" ? "下架ID为" + essayId + "的文章？" : "上架ID为" + essayId + "的文章？";
        var $btn = $(this);
        swal({
            title: text,
            icon: "warning",
            buttons: ["取消", "确定"]
        }).then(result => {
            if (result) {
                $.ajax({
                    url: contextPath + "admin/essay/info/" + essayId,
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
                                $btn.attr("id", "edit-user-status-" + essayId + "-" + (status ^ 1))
                            } else {
                                $btn.removeClass("btn-warning");
                                $btn.addClass("btn-success");
                                $btn.html("已下架");
                                $btn.html('<i class="fa fa-check"></i>&nbsp;已上架');
                                $btn.attr("id", "edit-user-status-" + essayId + "-" + (status ^ 1))
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
    $("button[id*='essay-btn']").click(function () {
        identity = this.id.split("-")[2];
        goPage(1);
    });
}

function initSearchBtn() {
    $("#essay-search-btn").click(function () {
        goPage(1);
    })
}


/*
{
  "pageNum": 2, //页码
  "pageSize": 3, //每页要显示的条数
  "size": 3, //本页条数
  "startRow": 4,
  "endRow": 6,
  "total": 7, //总记录数
  "pages": 3, //总页数
  "list": [
    {
      "age": 0,
      "id": 100000,
      "email": "gss@qq.com",
      "password": "202CB962AC59075B964B07152D234B70",
      "nickname": "gsssss",
      "tel": "123",
      "sex": "男",
      "birthday": 869241600000,
      "photo": "p7.jpg",
      "marryStatus": "未婚",
      "height": 175,
      "sexual": "男",
      "education": "大学本科",
      "workplace": "山东-济南",
      "salary": 8000.0,
      "liveCondition": 0,
      "authority": 1,
      "status": 1,
      "code": null,
      "regTime": 1508245373000,
      "star": true,
      "vip": true,
      "authenticated":false
    }
  ],
  "prePage": 1,
  "nextPage": 3,
  "isFirstPage": false,
  "isLastPage": false,
  "hasPreviousPage": true,
  "hasNextPage": true,
  "navigatePages": 8,
  "navigatepageNums": [
  ],
  "navigateFirstPage": 1,
  "navigateLastPage": 3,
  "lastPage": 3,
  "firstPage": 1
}
*/