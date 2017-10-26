var pageNum = 1;
var type = "new";
var likeMessageValue = false;

$(function () {
    btnClick();
});
function getMessage() {
    data = {"pageNum":pageNum,"type":type};
    $.ajax({
        url:contextPath+"other_says/message",
        data:data,
        type:"get",
        dataType:"json",
        success:function (data) {
            var list = data.list;
            $("#news-div").empty();
            for(var i=0;i<list.length;i++) {
                $("#news-div").append(" <div class='jobs-item with-thumb'>" +
                    "                                <div class='thumb_top'>" +
                    "                                    <div class='thumb1'><a href='" + contextPath + "profile/" + list[i].userId + "'>" +
                    "                                        <img src='" + contextPath + "images/p1.jpg' class='img-responsive' alt=''/></a>" +
                    "                                    </div>" +
                    "                                    <div class='jobs_right'>" +
                    "                                        <h6 class='title'><a href='" + contextPath + "profile/" + list[i].userId + "'>" + list[i].userBasic.nickname + " (" + list[i].userId + ")</a></h6>" +
                    "                                        <ul class='login_details1'>" +
                    "                                            <li>" + list[i].pubTime + "</li>" +
                    "                                        </ul>" +
                    "                                    </div>" +
                    "                                    <div class='clearfix'></div>" +
                    "                                </div>" +
                    "                                <div class='message_content' style='padding: 0px 10px 0 10px'>" +
                    "                                    <p style='margin-bottom: 0px; '>" + list[i].content + "</p>" +
                    "                                </div>" +
                    "                                <div class='row text-right'>" +
                    "                                    <div class='col-sm-7'></div>" +
                    "                                    <div class='col-sm-2'>" +
                    "                                       <div onclick='likeMessage(" + list[i].id + ")' style='cursor: pointer'  class='fa fa-lg glyphicon-thumbs-up fa-thumbs-o-up'></div>("
                    + list[i].likeCount + ")" +
                    "                                   </div>" +
                    "                                    <div class='col-sm-2'>" +
                    "                                           <div onclick='replyMessage(" + list[i].id + ")' style='cursor: pointer'  class='fa fa-lg fa-comment-o'></div>(" +
                    +list[i].replyCount + ")" +
                    "                                           <a   class='accordion-toggle fa fa-angle-double-down ' href='#collapse-div-"+i+"' data-toggle='collapse'></a>" +
                    "                                    </div>" +
                    "                                    <div class='clearfix'></div>" +
                    "                                </div>" +
                    "                                <div id='collapse-div-"+i+"' class='accordion-body collapse '>" +
                    "                                评论</div>" +
                    "                            </div><hr/>"
                );
                var reply_list = list[i].replies;
                for (var j=0;j<reply_list.length;j++){
                    $("#collapse-div-"+i).append("<div>"+reply_list[i].content+"</div>");
                }
            }

            setPage(data.pageNum,data.total, data.pages, "goPage");
        }

    })
}

function likeMessage(massageId) {
    if (likeMessageValue){
        swal("您已经点赞过了哟，不能重复点赞！", "error");
        return;
    }
    $.ajax({
        url:contextPath+"other_says/likeMessage/"+massageId,
        type:"get",
        success:function (data) {
            if (data=="success"){
                swal("点赞成功！", "success");
                likeMessageValue=true;
                getMessage();
            }else {
                swal("您已经点赞过了哟，不能重复点赞！", "error");
            }
        }
    })
}

function replyMessage(messageId) {
    $('#reply').modal();
    $("#reply-submit-btn").click(function () {
        var reply = $("#reply-content").val();
        var data = {"reply":reply,"messageId":messageId}
        $.ajax({
            url:contextPath+"other_says/replyMessage",
            type:"get",
            data:data,
            success:function (data) {
                if (data=="success"){
                    swal("评论成功！", "success");
                    likeMessageValue=true;
                    getMessage();
                }else {
                    swal("评论失败！", "error");
                }
            }
        })
    })
}

function btnClick() {
    $("#msgSubBtn").click(function () {
        $.post(contextPath + "other_says/message", {content: $("#content").val()}, function (data) {
            if (data == "true") {
                swal("发布成功！", "success");
                $("#pubMsgModal").modal("hide");
                getMessage();
            }
            else {
                swal("发布失败！", "error");
            }
        }, "text");
    });

    $("#essay-btn").click(function () {
    });

    $("#news-btn").click(function () {
        type="new";
        getMessage();
    });
    $("#new-li").click(function () {
        type="new";
        $("#sort-btn").html("最新")
        getMessage();
    });
    $("#hot-li").click(function () {
        type="hot";
        $("#sort-btn").html("最热")
        getMessage();
    });
}

function goPage(_pageNum) {
    pageNum = _pageNum;
    getMessage();
}
