var pageNum = 1;
var pageSize = 5;
var identity = "all";
var pageSizeArray = [1, 3, 5, 8, 10];

$(function () {
    setPageSizeSel(pageSizeArray, pageSize);
    loadNotice();
    initIdentityBtn();
    initSearchBtn();
    initPageSizeSel();
})

function loadNotice() {
    var data = {
        "identity": identity,
        "pageNum": pageNum,
        "pageSize": pageSize,
        "column": $("#notice-search-sel").val(),
        "keyword": $("#notice-search-val").val()}
    $.ajax({
        url:contextPath+"admin/notice/getNotice",
        type:"get",
        data:data,
        success:function (data) {
            var $tBody = $("#notice-table").find(">tbody");
            $tBody.empty();
            var list = data.list;
            for (var i=0;i<list.length;i++){
                var tr = $('<tr>\n' +
                    '         <td>' + list[i].id + '</td>\n' +
                    '         <td>' + list[i].adminId + '</td>\n' +
                    '         <td>' + list[i].title + '</td>\n' +
                    '         <td>' + list[i].content + '</td>\n' +
                    '         <td>' + list[i].pubTime + '</td>\n' +
                    '    </tr>');
                if (list[i].pubObj===0)
                    tr.append("<td>所有用户</td>");
                else if (list[i].pubObj===1)
                    tr.append("<td>VIP用户</td>");
                else if (list[i].pubObj===2)
                    tr.append("<td>星级用户</td>");
                else
                    tr.append("<td>用户Id:"+list[i].pubObj+"</td>");
                tr.append("<td><button onclick='deleteOne("+list[i].id+")' class='btn btn-sm btn-warning'><i class='fa fa-gavel'></i>&nbsp;删除</button></td>");
                $tBody.append(tr);
            }
            setPage(data.pageNum,data.total, data.pages, "goPage");
        }
    })
}
function deleteOne(id) {
    swal({
        title: "确定要删除这条用户动态吗",
        icon: "warning",
        buttons: ["取消", "确定"]
    }).then(result => {
        if (result) {
            $.ajax({
                url: contextPath + "admin/notice/delete/" + id,
                type: "delete",
                success: function (data) {
                    if (data=="success"){
                        loadNotice();
                    }else {
                        swal("删除失败！", "", "Error");
                    }
                }
            });
        }
    });
}

function initPageSizeSel() {
    $("#pageSize-sel").change(function () {
        pageSize = $(this).val();
        goPage(1);
    });
}

function initIdentityBtn() {
    $("button[id*='notice-btn']").click(function () {
        identity = this.id.split("-")[2];
        goPage(1);
    });
}

function initSearchBtn() {
    $("#notice-search-btn").click(function () {
        goPage(1);
    })
}

function goPage(_pageNum) {
    pageNum = _pageNum;
    loadNotice();
}
