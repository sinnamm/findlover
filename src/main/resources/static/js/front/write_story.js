$(function() {
    initUeditor();
    niceValidator();
});
function initUeditor() {
    window.UEDITOR_HOME_URL = contextPath+"plugins/ueditor/";
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function(action) {
        if (action == 'uploadimage' || action == 'uploadscrawl' ) {
            return contextPath+'ueditor/uploadfile';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }
}
function niceValidator() {
    $("#storyForm").validator({
        fields: {
            'otherId': 'required;length(3~16);remote['+contextPath+'checkid, otherId]',
            'photo': 'required;accept[png|jpg|bmp|gif|jpeg]'
        },
        valid:  function(form){
            $("#tcontent").val(UE.getEditor('editor').getContentTxt().substring(0,100));
            $("#essays").val(UE.getEditor('editor').getContent());
            form.submit();
            // var otherId=$("#otherId").val();
            // var title=$("#title").val();
            // var content = UE.getEditor('editor').getContent();
            // var tcontent = UE.getEditor('editor').getContentTxt().substring(0,100);
            // var photo = $("#photo").val();
            // alert(photo);
            // $.ajax({
            //         url: contextPath + "success_story/upload_essay",
            //         data: {
            //             essays: content,
            //             otherId:otherId,
            //             title:title,
            //             tcontent:tcontent,
            //
            //         },
            //         type: "POST",
            //         dataType: "text",
            //         success: function (data) {
            //             if (data === 'error'){
            //                 swal("错误","遇到未知错误","error");
            //             }else{
            //                 swal("温馨提示","上传已成功","success");
            //             }
            //         },
            //         error:function () {
            //         swal("错误","遇到未知错误","error");
            //     }
            // });
        },
        theme:'bootstrap',
        timely:2,
        stopOnError:true
    });
}