var pageNum = 1;
var tab = "readNotice";
$(function () {
    loadNotice();
    clickFun();
    
});

function loadNotice() {
    $.ajax({
        url:contextPath+"notice/"+tab,
        data:{"pageNum":pageNum},
        type:"get",
        success:function (data) {
            $("#notice_div").empty();
            var list = data.list;
            if (tab=="unReadNotice"){
                $("#un_read_notice_a").text("未读("+list.length+")");
                $("#notice-count").text("("+list.length+")");
            }
            if (list.length==0){
                $("#pagetool").hide();
                return;
            }
            for (var i=0;i<list.length;i++){
                $("#notice_div").append('<a onclick="loadModel('+list[i].id+')" style="cursor: pointer"  class="list-group-item">' +
                    '<span class="fa fa-bell-o"></span>'+list[i].title+'</a>');
            }
            setPage(data.pageNum,data.total, data.pages, "goPage");
        }
    })
}

function loadModel(id) {
    $.ajax({
        url:contextPath+"notice/read/"+id,
        type:"get",
        success:function (data) {
            $("#notice-title").html(data.title);
            $("#notice-content").html(data.content);
            $('#notice-model').modal('show');
            loadNotice();
        }
    })

}

function clickFun() {
    $("#un_read_notice").click(function () {
        $("#read_notice").removeClass("active");
        $(this).addClass("active");
        $("#pagetool").show();
        tab = "unReadNotice";
        loadNotice();
    });
    $("#read_notice").click(function () {
        $("#un_read_notice").removeClass("active");
        $(this).addClass("active");
        $("#pagetool").show();
        tab = "readNotice";
        loadNotice();
    });
}

function goPage(_pageNum) {
    pageNum = _pageNum;
    loadNotice();
}