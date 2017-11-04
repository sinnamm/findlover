var storyId = 0;
var likeCount = 0;
var currentPage = 1;
var lineSize = 5;
$(function () {
    $("#Message").val("");
    storyId = $("#storyId").val();
    likeCount = $("#likeCount").val();
    loadmore();
    reply();
    like();
});

function loadmore() {
    $("#loadmore").click(function () {
        $.ajax({
            url: contextPath + "success_story/story_reply/load_more",
            data: {
                storyId: storyId,
                currentPage: ++currentPage,
                lineSize: lineSize
            },
            type: "post",
            dataType: "json",
            async: false,
            success: function (data) {
                for (var x = 0; x < data.list.length; x++) {
                    var successStoryReply = data.list[x];
                    var $new = $(' <div class="jobs-item with-thumb" style="margin-top: 50px">\n' +
                        '                                        <div class="thumb_top">\n' +
                        '                                            <div class="thumb"><a href="/profile/' + successStoryReply.user.id + '"><img\n' +
                        '                                                    src="' + contextPath + 'file?path=' + successStoryReply.user.photo + '" class="img-responsive"\n' +
                        '                                                    class="img-responsive" alt=""/></a></div>\n' +
                        '                                            <div class="jobs_right">\n' +
                        '                                                <h6 class="title"><a\n' +
                        '                                                        href="/profile/' + successStoryReply.user.id + '"\n' +
                        '                                                       >'+  successStoryReply.user.nickname + '</a></h6>\n' +
                        '                                                <ul class="login_details1">\n' +
                        '                                                    <li >'+ successStoryReply.replyTime.substring(0, 10) + '"</li>\n' +
                        '                                                </ul>\n' +
                        '                                                <p style="font-size: 20px;" class="description"\n' +
                        '                                                   >'+ successStoryReply.content + '</p>\n' +
                        '                                            </div>\n' +
                        '                                            <div class="clearfix"></div>\n' +
                        '                                        </div>\n' +
                        '                                    </div>');
                    $("#replyDiv").append($new);
                }
                if (currentPage === data.pages) {
                    $("#loadmore").hide();
                }
            },
            error: function () {
                swal("错误", "遇到未知错误..", "error");
            }
        });
    });
}

function like() {
    $("#like-story").click(function () {
        if ($("#like").val() === "false") {
            $.ajax({
                url: contextPath + "success_story/like",
                data: {
                    storyId: storyId
                },
                type: "post",
                dataType: "text",
                async: false,
                success: function (data) {
                    if (data === "true") {
                        $("#like-story").css("color", "red");
                        $("#like-story").text(++likeCount);
                        $("#like").val("true");
                        swal("温馨提示", "点赞成功", "success");
                    } else {
                        swal("错误", "点赞失败..", "error");
                    }
                },
                error: function () {
                    swal("错误", "遇到未知错误..", "error");
                }
            });
        } else {
            swal("警告", "您不能重复点赞", "warning");
        }
    })

}

function reply() {
    $("#submit").click(function () {
        if ($("#Message").val() !== "") {
            var content = $("#Message").val();
            $.ajax({
                url: contextPath + "success_story/reply",
                data: {
                    content: content,
                    storyId: storyId
                },
                type: "post",
                dataType: "text",
                async: false,
                success: function (data) {
                    if (data === "true") {
                        window.location.reload();
                    } else {
                        swal("错误", "更新数据库失败..", "error");
                    }
                },
                error: function () {
                    swal("错误", "遇到未知错误..", "error");
                }
            });
            $("#Message").val("");
        }
    });
}