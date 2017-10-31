$(function () {
    niceBaseCofig();
    initEssayForm();
    initUeditor();
    initHtmlBtn();
})

function initEssayForm() {
    $('#essay-form').validator({
        rules:{
            photo:[/^.*[^a][^b][^c]\.(?:png|jpg|bmp|gif|jpeg)$/,'请正确上传图片格式！']
        },
        fields: {
            'pseudonym': 'required;chinese;length(2~12)',
            'photo': 'required;photo',
            'title': 'required'
        },
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            alert(11);
        }
    });
}

//测试
function initHtmlBtn() {
    $("#htmlbtn").click(function () {
        var hcontent = UE.getEditor('editor').getContent();
        var tcontent = UE.getEditor('editor').getContentTxt();
        alert(tcontent);
        /*$.ajax({
            url: contextPath + "writer/upload",
            data: {"essays": hcontent},
            type: "POST",
            dataType: "JSON",
            success: function (data) {
                alert(data.msg);
                $("form").before($(data.msg));
            },
            error:function (error,msg) {
                alert(msg);
            }
        });*/
    });
}

//初始化ueditor插件
function initUeditor() {
    window.UEDITOR_HOME_URL = contextPath + "plugins/ueditor/";
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        if (action == 'uploadimage' || action == 'uploadscrawl') {
            return contextPath+'ueditor/uploadfile';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }
}