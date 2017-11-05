$(function () {
    $('#carousel').flexslider({
        animation: "slide",
        controlNav: false,
        animationLoop: false,
        slideshow: false,
        itemWidth: 50,
        itemMargin: 8,
        asNavFor: '#slider'
    });

    $('#slider').flexslider({
        animation: "slide",
        controlNav: false,
        animationLoop: false,
        slideshow: false,
        sync: "#carousel"
    });
    searchById();
    initComplain();
    initFollowBtn();
    initLetterBtn();
});

function searchById() {
    $("#id-btn").click(function () {
        var $idInput = $("#id-input");
        if (!/^\d+$/.test($idInput.val())) {
            swal("提示", "ID格式错误！", "warning");
        } else {
            $.get(contextPath + "user/exists/" + $idInput.val(), {}, function (result) {
                if (result == "true") {
                    window.location = contextPath + "profile/" + $idInput.val();
                } else {
                    swal("提示", "用户ID不存在！", "warning");
                }
            }, "text")
        }
    });
}

function initComplain() {
    $(".com-btn").click(function () {
        if(userId==$("#obj").text()){
            swal("不能与自己互动！", "", "warning");
            return ;
        }
        $.get(contextPath + "dicts/com_reason", {}, function (data) {
            $("#com-sel").find("option:gt(0)").remove();
            for (var x = 0; x < data.length; x++) {
                $("#com-sel").append($('<option value="' + data[x].value + '">' + data[x].value + '</option>'))
            }
        }, "json");
        $("#comModal").modal("toggle");
    });
    $("#comSubBtn").click(function () {
        var $comSel = $("#com-sel");
        if ($comSel.val() == "-1") {
            swal("提示", "请选择投诉理由！", "warning");
        } else {
            $.post(contextPath + "profile/complain", {
                "comObj": $("#obj").html(),
                "reason": $comSel.val(),
                "content": $("#com-content").val()
            }, function (result) {
                if (result == "true") {
                    swal("投诉成功", "已交由管理员处理。", "success");
                    $("#comModal").modal("hide");
                } else {
                    errorAlert();
                }
            }, "text")
        }
    });
}

function initFollowBtn() {
    $("#follow-btn").click(function () {
        if(userId==$("#obj").text()){
            swal("不能与自己互动！", "", "warning");
            return ;
        }
        if ($("#follow-info").text() == "关注") {
            $.get(contextPath + "session/user", {}, function (userBasic) {
                if(userBasic.authority!=1){
                    swal({
                        title:"提示",
                        text:"您已隐藏资料，无法发起互动！",
                        icon:"warning",
                        buttons: ["取消", "公开我的资料"]
                    }).then(result=>{
                        if(result){
                            $.ajax({
                                url:contextPath+"user",
                                type:"put",
                                data:{"authority":1},
                                dataType:"text",
                                success:function (data) {
                                    if(data=="true"){
                                        swal("提示", "资料已公开", "success");
                                    }else{
                                        errorAlert();
                                    }
                                },
                                error:errorAlert
                            });
                        }
                    });
                }else{
                    $.post(contextPath + "follow", {
                        "followId": $("#obj").text()
                    }, function (result) {
                        if (result == "true") {
                            $("#follow-info").text("已关注");
                        } else {
                            errorAlert();
                        }
                    }, "text");
                }
            }, "json");

        } else {
            swal({
                "title":"不在关注此用户？",
                "icon":"waring",
                buttons: ["取消", "确定"]
            }).then(choice=>{
                if(choice){
                    $.ajax({
                        url: contextPath + "follow?followId=" + $("#obj").text(),
                        type: "delete",
                        dataType: "text",
                        success: function (result) {
                            if (result == "true") {
                                $("#follow-info").text("关注");
                            } else {
                                errorAlert();
                            }
                        },
                        error: errorAlert
                    });
                }
            });
        }
    });
}
function initLetterBtn() {
    $("button[id^='letter-btn-']").click(function () {
        var id = this.id.split("-")[2];
        if(userId==$("#obj").text()){
            swal("不能与自己互动！", "", "warning");
        }else {
            $.get(contextPath + "session/user", {}, function (userBasic) {
                if(userBasic.authority!=1){
                    swal({
                        title:"提示",
                        text:"您已隐藏资料，无法发起互动！",
                        icon:"warning",
                        buttons: ["取消", "公开我的资料"]
                    }).then(result=>{
                        if(result){
                            $.ajax({
                                url:contextPath+"user",
                                type:"put",
                                data:{"authority":1},
                                dataType:"text",
                                success:function (data) {
                                    if(data=="true"){
                                        swal("提示", "资料已公开", "success");
                                    }else{
                                        errorAlert();
                                    }
                                },
                                error:errorAlert
                            });
                        }
                    });
                }else{
                    window.location = contextPath + "letter?id=" + id;
                }
            }, "json");
        }
    });
}