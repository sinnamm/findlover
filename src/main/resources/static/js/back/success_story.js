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
});
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
            for(var i=0;i<data.length;i++){
                var story=data[i];
                var tr=$("  <tr>\n" +
                    '         <td>' + story.id + '</td>\n'+
                    '         <td>' + story.leftUser+'&'+ story.rightUser+ '</td>\n' +
                    ' <td>' + story.title+ '</td>\n'+
                    ' <td>' + story.successTime+ '</td>\n');
                switch (story.status){
                    case 0:
                        tr.append("<td><button class=\"btn btn-sm btn-warning\"><i class=\"fa fa-times\"></i>&nbsp;未审核</button></td>");
                        tr.append("<td><button class=\"btn btn-sm btn-warning\"><i class=\"fa fa-times\"></i>&nbsp;未上架</button></td>");
                        break;
                    case 1:
                        tr.append("<td><button class=\"btn btn-sm btn-success\"><i class=\"fa fa-check\"></i>&nbsp;已审核</button></td>");
                        tr.append("<td><button class=\"btn btn-sm btn-success\"><i class=\"fa fa-check\"></i>&nbsp;已上架</button></td>");
                        break;
                    case 2:
                        tr.append("<td><button class=\"btn btn-sm btn-warning\"><i class=\"fa fa-times\"></i>&nbsp;待右手审核</button></td>");
                        tr.append("<td><button class=\"btn btn-sm btn-warning\"><i class=\"fa fa-times\"></i>&nbsp;未上架</button></td>");
                        break;
                    case 3:
                        tr.append("<td><button class=\"btn btn-sm btn-warning\"><i class=\"fa fa-times\"></i>&nbsp;待审核</button></td>");
                        tr.append("<td><button class=\"btn btn-sm btn-warning\"><i class=\"fa fa-times\"></i>&nbsp;未上架</button></td>");
                        break;
                }
                tr.append("<td><button class=\"btn btn-sm btn-success\">&nbsp;预览</button></td>");
                tr.append("</tr>");
                $("#body").append(tr);
            }
            setPage(data.pageNum,data.total, data.pages, "goPage");
        }
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
function checkAll(inputlist){
    var arr = [];
    var num = inputlist.length;
    for(var i = 0; i < num; i++){
        if(inputlist.eq(i).is(":checked")){
            arr.push(inputlist.eq(i).val());
        }
    }
    return arr;
}
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