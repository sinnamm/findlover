$(function () {
    niceBaseCofig();
    initUeditor();
    initEssayForm();
})

function initEssayForm() {
    $('#essay-form').validator({
        rules:{
            title: [/^([\u4E00-\u9FA5]|\w){2,30}$/, "标题应为2-30位字符"]
        },
        fields: {
            'photo': 'required;accept[png|jpg|bmp|gif|jpeg]',
            'title': 'required;title',
            'essays': 'required'
        },
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            var tcontent = UE.getEditor('editor').getContentTxt();
            $('#brief').val(tcontent.substring(0,50)+'...');
            form.submit();
        }
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
        if (action == 'uploadimage') {
            return contextPath+'ueditor/uploadfile';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }
}