var pageNum =1;
var type ="tracer";
$(function () {
    loadVisit();
    btnClick();
});
function loadVisit() {
    var data ={pageNum:pageNum}
    $.ajax({
        url:contextPath+"visit_trace/"+type,
        data:data,
        type:"get",
        success:function (data) {
            $("#visitors").empty();
            var list = data.list;
            if (list.length==0){
                swal("暂时没有数据哟");
                return;
            }
            for (var i = 0;i<list.length;i++){
                var user = list[i].userBasic;
                $("#visitors").append("<div class='jobs-item with-thumb'>" +
                    "                                <div class='thumb_top'>" +
                    "                                    <div class='thumb1'>" +
                    "                                        <a href='" + contextPath + "profile/" + list[i].intervieweeId + "'>" +
                    "                                        <img src='" + contextPath + "file?path="+list[i].userBasic.photo+"' class='img-responsive' alt=''/></a>" +
                    "                                    </div>" +
                    "                                    <div class='jobs_right'>" +
                    "                                        <h6 class='title'>" +
                    "                                           <a href='" + contextPath + "profile/" + list[i].intervieweeId + "'>" + user.nickname + "</a>" +
                    "                                           <img class='flag' src='"+contextPath+"images/vip"+(user.vip?'':'-grey')+".png'>"+
                    "                                           <img class='flag' src='"+contextPath+"images/star-0"+(user.star?'':'-grey')+".png'>"+
                    "                                        </h6>" +
                    "                                        <ul class='login_details1'>" +
                    "                                            <li>于 " + list[i].visitTime + " 访问了您的资料</li>" +
                    "                                        </ul>" +
                    "                                   <div class='thumb_but'>" +
                    "                                       <a class='photo_view' href='"+contextPath+"letter?id="+user.id+"'>" +
                    "                                       <i class='fa fa-envelope-o'></i>&nbsp;发私信</a>"+
                    "                                   <div>"+
                    "                                    </div>" +
                    "                                   <div class='clearfix'></div>" +
                    "                                </div>" +
                    "                      </div><hr/>"
                )
            }
            setPage(data.pageNum,data.total, data.pages, "goPage");
        }
    })
}

function btnClick() {
    $("#trace").click(function () {
        type = "trace";
        $("#trace").addClass("active");
        $("#tracer").removeClass("active");
        loadVisit();
    });
    $("#tracer").click(function () {
        type = "tracer";
        $("#tracer").addClass("active");
        $("#trace").removeClass("active");
        loadVisit()
    })
}

function goPage(_pageNum) {
    pageNum = _pageNum;
    loadVisit()
}