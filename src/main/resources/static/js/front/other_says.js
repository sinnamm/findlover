var pageNum = 1;
var type = "new";
var tab = "message";
var msgId = 0;

$(function () {
    btnClick();
});
function getMessage() {
    $("#news-follow-div").empty();
    data = {"pageNum":pageNum,"type":type};
    $.ajax({
        url:contextPath+"other_says/message",
        data:data,
        type:"get",
        dataType:"json",
        success:function (data) {
            loadMessage("news-div",data);
        }

    })
}

function loadMessage(div_id,data){
    var list = data.list;
    $("#"+div_id).empty();
    for(var i=0;i<list.length;i++) {
        $("#"+div_id).append(" <div class='jobs-item with-thumb'>" +
            "                                <div class='thumb_top'>" +
            "                                    <div class='thumb1'><a href='" + contextPath + "profile/" + list[i].userId + "'>" +
            "                                        <img src='" + contextPath + "file?path="+list[i].userBasic.photo+"' class='img-responsive' alt=''/></a>" +
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
            "                                    <p style='margin-bottom: 0px; font-size: medium '>" + list[i].content + "</p>" +
            "                                </div>" +
            "                                <div class='row text-right'>" +
            "                                    <div class='col-sm-7'></div>" +
            "                                    <div class='col-sm-2' style='color: "+(list[i].like? 'red':'')+"'>" +
            "                                       <div onclick='likeMessage(" + list[i].id + ")' style='cursor: pointer; color: "+(list[i].like? 'red':'')+"'   class='fa fa-lg glyphicon-thumbs-up fa-thumbs-o-up'></div>("
            + list[i].likeCount + ")" +
            "                                   </div>" +
            "                                    <div class='col-sm-2'>" +
            "                                           <div onclick='replyMessage(" + list[i].id + ")' style='cursor: pointer'  class='fa fa-lg fa-comment-o'></div>(" +
            +list[i].replyCount + ")" +
            "                                           <a   class='accordion-toggle fa fa-angle-double-down ' href='#collapse-div-"+i+"' data-toggle='collapse'></a>" +
            "                                    </div>" +
            "                                    <div class='clearfix'></div>" +
            "                                </div>" +
            "                                <div class='row'>" +
            "                                   <div class='col-md-9 col-md-offset-1'>" +
            "                                        <ul id='collapse-div-"+i+"' class='media-list accordion-body collapse'>"+
            "                                        </ul>"+
            "                                   </div>"+
            "                                </div>"+
            "                            </div><hr/>"
        );
        var reply_list = list[i].replies;
        if (reply_list!=""&&reply_list!=undefined&&reply_list!=null&&reply_list.length>0){
            for (var j=0;j<reply_list.length;j++){
                var user = reply_list[j].userBasic;
                $("#collapse-div-"+i).append("<li class='media'>" +
                    "                <div class='thumb1'>" +
                    "                    <a href='#'>" +
                    "                        <img class='media-object img-circle' width='30px' " +
                    "src='" + contextPath + "file?path= "+user.photo+"' alt='菜鸟'>" +
                    "                    </a>" +
                    "                </div>" +
                    "                <div class='jobs_right'>" +
                    "                    <h6 class='media-heading'><span class='label label-info'>"+user.nickname+"</span> "+reply_list[j].replyTime+"</h6>" +
                    "                    <p style='margin-bottom: 5px'>"+reply_list[j].content+"</p>" +
                    "                </div>" +
                    "            <hr/></li>");
            }
        }

    }
    setPage(data.pageNum,data.total, data.pages, "goPage");
}

function followMessage() {
    $("#news-div").empty();
    $.ajax({
        url:contextPath+"other_says/followMessage?pageNum="+pageNum,
        type:"get",
        dataType:"json",
        success:function (data) {
            if (data.list.length==0){
                swal("暂时没有数据哟","Error");
                $("#divide-page").hide();
                return;
            }
            loadMessage("news-follow-div",data);
        }

    })
}

function likeMessage(messageId) {
    $.ajax({
        url:contextPath+"other_says/likeMessage/"+messageId,
        type:"get",
        success:function (data) {
            if (data=="success"){
                if (tab=="message"){
                    getMessage();
                }else {
                    followMessage();
                }
            }else {
                swal("您已经点赞过了哟，不能重复点赞！", "error");
            }
        }
    })
}

function replyMessage(messageId) {
    msgId = messageId;
    $('#reply').modal();
}

function btnClick() {
    //发布动态按钮
    $("#msgSubBtn").click(function () {
        $.post(contextPath + "other_says/message", {content: $("#content").val()}, function (data) {
            if (data == "true") {
                $("#pubMsgModal").modal("hide");
                //发布成功跳转到动态页面查看自己的动态
                $("#news-btn").trigger("click");
            }
            else {
                swal("发布失败！", "error");
            }
        }, "text");
    });

    //回复模态框评论提交按钮
    $("#reply-submit-btn").click(function () {
        var reply = $("#reply-content").val();
        var data = {"reply":reply,"messageId":msgId};
        $.ajax({
            url:contextPath+"other_says/replyMessage",
            type:"get",
            data:data,
            success:function (data) {
                if (data=="success"){
                    swal("评论成功！", "success");
                    $("#reply").modal("hide");
                    $("#reply-content").val("");
                    if (tab=="message"){
                        getMessage();
                    }else {
                        followMessage();
                    }
                }else {
                    swal("评论失败！", "error");
                    $("#reply").modal("hide");
                }
            }
        });
    })

    $("#essay-btn").click(function () {
    });

    $("#news-btn").click(function () {
        type="new";
        tab="message";
        getMessage();
    });
    $("#new-li").click(function () {
        type="new";
        tab="message";
        pageNum=1;
        $("#sort-btn").html("最新")
        getMessage();
    });

    $("#hot-li").click(function () {
        type="hot";
        tab="message";
        pageNum=1;
        $("#sort-btn").html("最热")
        getMessage();
    });

    $("#follow-btn").click(function () {
        pageNum=1;
        tab="follow-message";
        followMessage();
    })
}

function goPage(_pageNum) {
    pageNum = _pageNum;
    if (tab=="message"){
        getMessage();
    }else {
        followMessage();
    }

}

