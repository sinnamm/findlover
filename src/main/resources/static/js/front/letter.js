var lineSize = 15;
var currentPage = 1;
var otherUserId=0;
$(function () {
    $("#notVipInput").val("");
    $("#vipInput").val("");
    otherUserId= $("#contactsLeft").find("input:eq(0)").val();
    var userid = $("#userid").val();
    var vip = $("#userVip").val();
    var myurl=GetQueryString("id");
    init(myurl,userid,vip);
    addLetterUser( userid, vip);
    addLetter( userid, vip);
    sendLetter( vip);
    loadMessage(userid,vip);
});
function init(myurl,userid,vip){
    var idArr = [];
    $("div[name='userLetter']").each(function () {
        idArr.push(this.id);
    });
    if (myurl!==null){
        if (idArr.indexOf(myurl)<0){
            addNewWindow(myurl);
            addNewWindowClick(myurl,userid,vip);
        }else{
            otherUserId=myurl;
        }
    }
}
function addNewWindowClick(myurl,userid,vip) {
    $("#newWindow").click(function () {
        otherUserId = myurl;
        currentPage = 1;
        addLetter(userid, vip);
    });
}
function addNewWindow(myurl) {
    if (myurl!==null){
        otherUserId=myurl;
        $.ajax({
            url: contextPath + "getUserById",
            data: {
                otherUserId: otherUserId
            },
            type: "post",
            dataType: "json",
            async:false,
            success: function (data) {
                if (data==null){
                    swal("错误","该用户不存在","error");
                }else if(data.authority != 1){
                    swal("提示","该用户已隐藏资料，不能发起互动","warning");
                }else{
                    $("#hiddenInput").after(" <li>\n" +
                        "                                    <div style=\"cursor:pointer; height: 50px;\" name=\"newWindow\"\n" +
                        "                                         class=\"jobs-item with-thumb userLetter\" id='newWindow'>\n" +
                        "                                        <div class=\"thumb_top\">\n" +
                        "                                            <div class=\"thumb\">\n" +
                        "                                                <img src=\""+contextPath+"file?path="+data.photo+"\"\n" +
                        "                                                     style=\"width: 50px ;height: 50px;\"\n" +
                        "                                                     class=\"img-responsive\" alt=\"\"/>\n" +
                        "                                            </div>\n" +
                        "                                            <div class=\"jobs_right\">\n" +
                        "                                                <div>\n" +
                        "                                                    <p>"+data.nickname+"</p>\n" +
                        "                                                </div>\n" +
                        "                                            </div>\n" +
                        "                                        </div>\n" +
                        "                                    </div>\n" +
                        "                                </li>\n" +
                        " <hr/>");
                }
            },
            error:function () {
                swal("错误","该用户不存在..","error");
            }
        });
    }
}
function GetQueryString(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
function sendLetter(vip) {
    var content = "";
    $("#inputSubmit").click(function () {
        if (vip === "true") {
            content = $("#vipInput").val();
        } else {
            content = $("#notVipInput").val();
        }
        if (content===""){
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
                        var $li = $("<li class = 'chat-right right-photo' ></li>");
                        $li.text(content);
                        $("#hiddenLi1").before($li);
                        $("#notVipInput").val("");
                        $("#vipInput").val("");
                        $('#letterUl').scrollTop(100000);
                    }else{
                        swal("警告",data,"warning");
                    }
                },
                error:function () {
                    swal("错误","失败","error");
                }
            });
        }
    })
}

function addLetter( userid, vip) {
    if(currentPage==1){
        $("#letterUl").empty();
        $("#letterUl").append("  <p id=\"hiddenLi\" hidden></p>");
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
            if(data!==null){
                var i = 0;
                if (data.length<lineSize){
                    $("#overloadOldLetter").hide();
                }else{
                    $("#overloadOldLetter").show();
                }
                for (var x = 0; x < data.length; x++) {
                    if (vip == "false" && data[x].status == "0") {
                        if(userid == data[x].sendId){
                           var $li = $("<li  class = 'chat-right right-photo' ></li>");
                            $li.text(data[x].content);
                           $("#hiddenLi").after($li)
                        }else {
                            $("#hiddenLi").after("<li class = 'chat-left'><a id='letter-" + x + "' href='javascript:void(0)'>点击查看对方消息，每条信息将收费五个牵手币</a></li>");
                        }

                    } else {
                        var $li = $("<li class = 'chat-" + (userid == data[x].sendId ? "right" : "left") + "'></li>");
                        $li.text( data[x].content);
                        $("#hiddenLi").after($li);
                    }
                }
                $("a[id^='letter-']").click(function () {
                    var x = this.id.split("-")[1];
                    readLetter(this, data[x]);

                });
                if(currentPage===1){
                    $('#letterUl').scrollTop(100000);
                }else{
                    $('#letterUl').scrollTop(0);
                }

            }
        }
    });
}
function loadMessage(userid,vip) {
    $("#overloadOldLetter").click(function (){
        currentPage = currentPage + 1;
        addLetter( userid, vip);
        $('#letterUl').scrollTop(0);
    });
}
function addLetterUser(userid, vip) {
    $(".oldUserLetter").click(function () {
        otherUserId = $(this).find("input").val();
        currentPage = 1;
        addLetter( userid, vip);
        $(this).find("div[class='redPoint']").hide();
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
                par.text(letter.content);

            } else {
                swal("警告", data, "error");
            }
        },
        error: function () {
            swal("警告", "出现未知错误", "error");
        }
    });
}
