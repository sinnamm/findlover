var lineSize = 15;
var currentPage = 1;
window.onload = function () {
    $("#notVipInput").val("");
    $("#vipInput").val("");
    var otherUserId = $("#contactsLeft").find("input:eq(0)").val();
    var userid = $("#userid").val();
    var vip = $("#userVip").val();
    addLetterUser(otherUserId, userid, vip);
    addLetter(otherUserId, userid, vip);
    sendLetter(otherUserId, vip);
    loadMessage(otherUserId,userid,vip);
}

function sendLetter(otherUserId,vip) {
    var content = "";
    $("#inputSubmit").click(function () {
        if (vip === "true") {
            content = $("#vipInput").val();
        } else {
            content = $("#notVipInput").val();
        }
        if ((vip === "true" && content==="")||(vip==="false" && content==="")){
            swal("警告","请输入您要发送的信息","error");
        }else{
            $.ajax({
                url: contextPath + "sendLetter",
                data: {
                    otherUserId: otherUserId,
                    content:content
                },
                type: "post",
                dataType: "text",
                success: function (data) {
                    if (data=="ok"){
                        $("#hiddenLi1").before("<li class = 'chat-right' >" + content + "</li>");
                        $("#notVipInput").val("");
                        $("#vipInput").val("");
                    }else{
                        swal("警告",data,"error");
                    }
                },
                error:function () {
                    swal("错误","失败","error");
                }
            })
        }
    })
}

function addLetter(otherUserId, userid, vip) {
    if(currentPage==1){
        $("#letterUl").empty();
        $("#letterUl").append("  <p id=\"hiddenLi\" hidden=\"true\"></p>");
        $("#hiddenLi").after("<p id=\"hiddenLi1\" hidden></p>");
    }
    $.ajax({
        url: contextPath + "letter",
        data: {
            otherUserId: otherUserId,
            lineSize: lineSize,
            currentPage: currentPage
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            var i = 0;
            if (data.length<lineSize){
                $("#overloadOldLetter").hide();
            }else{
                $("#overloadOldLetter").show();
            }
            for (var x = 0; x < data.length; x++) {
                if (vip == "false" && data[x].status == "0") {
                    userid == data[x].sendId ? $("#hiddenLi").after("<li class = 'chat-right' >" + data[x].content + "</li>") : $("#hiddenLi").after("<li class = 'chat-left'><a id='letter-" + x + "' href='javascript:void(0)'>点击查看对方消息，每条信息将收费五个牵手币</a></li>");
                } else {
                    $("#hiddenLi").after("<li class = 'chat-" + (userid == data[x].sendId ? "right" : "left") + "'>" + data[x].content + "</li>");
                }
            }
            $("a[id^='letter-']").click(function () {
                var x = this.id.split("-")[1];
                readLetter(this, data[x]);
            })
        }
    })
}
function loadMessage(otherUserId,userid,vip) {
    $("#overloadOldLetter").click(function (){
        currentPage = currentPage + 1;
        addLetter(otherUserId, userid, vip);
    });
}
function addLetterUser(otherUserId, userid, vip) {
    $(".userLetter").click(function () {
        otherUserId = $(this).find("input").val();
        currentPage = 1;
        addLetter(otherUserId, userid, vip,currentPage);
    });
}

function readLetter(arg, letter) {
    $.ajax({
        url: contextPath + "readLetter",
        data: {
            letterId: letter.id
        },
        type: "post",
        dataType: "text",
        success: function (data) {
            if (data === "ok") {
                var par = $(arg).parent();
                $(arg).remove();
                par.html(letter.content);
            } else {
                swal("警告", data, "error");
            }
        },
        error: function () {
            swal("警告", "出现未知错误", "error");
        }
    });
}
