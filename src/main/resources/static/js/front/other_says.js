$(function () {
    $("#msgSubBtn").click(function () {
        $.post(contextPath + "other_says/message",{content:$("#content").text()},function(data){

        },"text");
    });
})