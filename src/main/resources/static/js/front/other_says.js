$(function () {
    $("#msgSubBtn").click(function () {
        $.post(contextPath + "other_says/message", {content: $("#content").val()}, function (data) {
            if (data == "true") {
                swal("发布成功！", "success");
                $("#pubMsgModal").modal("hide");
            }
            else {
                swal("发布失败！", "error");
            }
        }, "text");
    });
    $("#essay-btn").click(function () {
    })
});