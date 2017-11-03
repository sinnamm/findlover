var pageNum = 1;
var pageSize = 5;
var pageSizeArray = [1, 3, 5, 8, 10];
var status=-1;
$(function () {
    setPageSizeSel(pageSizeArray, pageSize);
    loadMessage();
    initPageSizeSel();
    initIdentityBtn();
    initSearchBtn();
    search();
});
function search() {
    $("#story-search-btn").click(function () {
        loadMessage();
    });
}
function loadMessage() {
    var column=$("#story-search-sel").val();
    var keyword=$("#story-search-val").val();
    $.ajax({
        url: contextPath + "admin/success_story/load_message",
        type: "post",
        data: {
            status:status,
            pageSize:pageSize,
            pageNum:pageNum,
            column:column,
            keyword:keyword
        },
        dataType: "JSON",
        success:function (data) {
            $("#body").empty();
            for(var i=0;i<data.list.length;i++){
                var story=data.list[i];
                var tr=$("  <tr>\n" +
                    '         <td>' + story.id + '</td>\n'+
                    '         <td>' + story.leftUser+'&'+ story.rightUser+ '</td>\n' +
                    ' <td>' + story.title+ '</td>\n'+
                    ' <td>' + story.successTime+ '</td>\n');
                switch (story.status){
                    case 0:
                        tr.append("<td><button class=\"btn btn-sm btn-warning\"><i class=\"fa fa-times\"></i>&nbsp;已审核</button></td>");
                        tr.append("<td><button id = 'story-status-"+ story.id+"-0' class=\"btn btn-sm btn-danger\"><i class=\"fa fa-times\"></i>&nbsp;已下架</button></td>");
                        tr.append($('<td><a class="btn btn-sm btn-info" href="' + contextPath + 'admin/success_story/success_story_detail/' + story.id + '"><i class="fa fa-edit"></i>&nbsp;查看详情</a></td>'));
                        break;
                    case 1:
                        tr.append("<td><button class=\"btn btn-sm btn-success\"><i class=\"fa fa-check\"></i>&nbsp;已审核</button></td>");
                        tr.append("<td><button id = 'story-status-"+ story.id+"-1' class=\"btn btn-sm btn-success\"><i class=\"fa fa-check\"></i>&nbsp;已上架</button></td>");
                        tr.append($('<td><a class="btn btn-sm btn-info" href="' + contextPath + 'admin/success_story/success_story_detail/' + story.id + '"><i class="fa fa-edit"></i>&nbsp;查看详情</a></td>'));
                        break;
                    case 2:
                        tr.append("<td><button class=\"btn btn-sm btn-warning\"><i class=\"fa fa-times\"></i>&nbsp;待右手审核</button></td>");
                        tr.append("<td><button id = 'story-status-"+ story.id+"-2' class=\"btn btn-sm btn-warning\"><i class=\"fa fa-times\"></i>&nbsp;未上架</button></td>");
                        tr.append($('<td><a class="btn btn-sm btn-info" href="' + contextPath + 'admin/success_story/success_story_detail/' + story.id + '"><i class="fa fa-edit"></i>&nbsp;查看详情</a></td>'));
                        break;
                    case 3:
                        tr.append("<td><button class=\"btn btn-sm btn-warning\"><i class=\"fa fa-times\"></i>&nbsp;待审核</button></td>");
                        tr.append("<td><button id = 'story-status-"+ story.id+"-3' class=\"btn btn-sm btn-warning\"><i class=\"fa fa-times\"></i>&nbsp;未上架</button></td>");
                        tr.append($('<td><a class="btn btn-sm btn-info" href="' + contextPath + 'admin/success_story/success_story_detail/' + story.id + '"><i class="fa fa-edit"></i>&nbsp;查看详情</a></td>'));
                        break;
                }

                tr.append("</tr>");
                $("#body").append(tr);
            }
            initEditStoryStatusBtn();
            setPage(data.pageNum,data.total, data.pages, "goPage");
        }
    })
}

function initEditStoryStatusBtn() {
    $("button[id^='story-status']").click(function () {
        var storyId = this.id.split("-")[2];
        var status = this.id.split("-")[3];
        if(status === "2"||status==="3"){
            swal("温馨提醒","请查看详情进行审核后，再上架！", "warning");
            return;
        }
        var text = status !== "0" ? "下架ID为" + storyId + "的文章？" : "上架ID为" + storyId + "的文章？";
        var $btn = $(this);
        swal({
            title: text,
            icon: "warning",
            buttons: ["取消", "确定"]
        }).then(result => {
            if (result) {
                $.ajax({
                    url: contextPath + "admin/success_story/alt_status" ,
                    type: "put",
                    data: {
                        "status": status ,
                        storyId:storyId
                    },
                    method:"post",
                    dataType: "text",
                    async:false,
                    success: function (data) {
                        if (data === "true") {
                            if (status === "1") {
                                $btn.removeClass("btn-success");
                                $btn.addClass("btn-danger");
                                $btn.html('<i class="fa fa-times"></i>&nbsp;已下架');
                                $btn.attr("id", "story-status-" + storyId + "-0");
                            } else {
                                $btn.removeClass("btn-danger");
                                $btn.addClass("btn-success");
                                $btn.html('<i class="fa fa-check"></i>&nbsp;已上架');
                                $btn.attr("id", "story-status-" + storyId + "-1");
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
    loadMessage();
};

function initPageSizeSel() {
    $("#pageSize-sel").change(function () {
        pageSize = $(this).val();
        goPage(1);
    });
};
function initIdentityBtn() {
    $("#story-btn-all").click(function () {
        status=-1;
        goPage(1);
    });
    $("#story-btn-checked").click(function () {
        status=1;
        goPage(1);
    });
    $("#story-btn-uncheck").click(function () {
        status=2;
        goPage(1);
    });
    $("#story-btn-rightcheched").click(function () {
        status=3;
        goPage(1);
    });
    $("#story-btn-down").click(function () {
        status=0;
        goPage(1);
    });
}

function initSearchBtn() {
    $("#user-search-btn").click(function () {
        goPage(1);
    })
}