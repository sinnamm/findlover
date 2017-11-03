$(function () {
    initCheckedAndDeleteBtn();
    //loadEssayData();
});

function initCheckedAndDeleteBtn() {
    $('a[id$="-complain"]').click(function () {
        var $btn = $(this);
        var atext = $btn.text();
        if(atext === "忽略"){
            allBtn('info',1,'你确定要忽略此投诉？',$btn);
        }else if(atext === "警告"){
            warningBtn($btn);
        }else if(atext === "封号"){
            allBtn('seal',3,'确定要封杀ID为'+comObjId+'用户吗？',$btn);
        }
    });
}

function warningBtn($btn) {
    swal({
        title: '确定要警告ID为'+comObjId+'用户吗？',
        icon: "warning",
        buttons: ["取消", "确定"]
    }).then(result => {
        if (result) {
            $.ajax({
                url: contextPath + "admin/notice/warning_notice/"+complainId,
                type: "POST",
                data:{
                    "title":"你最近行为不当，被其他用户举报！",
                    "content":"你最近行为不当，被其他用户举报！",
                    "pubObj":comObjId
                },
                dataType: "text",
                success: function (data) {
                    //window.location.go(-1);
                    if (data === "true") {
                        $('a[id$="-complain"]').addClass("disabled");
                        swal("状态修改成功！", "", "success");
                    } else {
                        swal("状态修改失败！", "", "error");
                    }
                },
                error: errorAlert
            });
        }
    });
}

/**
 * 通用方法
 * @param urlType 跳转的url
 * @param comStatus 需要修改投诉的状态
 * @param comWarning  swal弹框需要显示的提醒信息
 * @param $btn 被按按钮对象
 */
function allBtn(urlType,comStatus,comWarning,$btn) {
    swal({
        title: comWarning,
        icon: "warning",
        buttons: ["取消", "确定"]
    }).then(result => {
        if (result) {
            $.ajax({
                url: contextPath + "admin/complain/"+urlType+"/" + complainId,
                type: "put",
                data: {"status": comStatus},
                dataType: "text",
                success: function (data) {
                    if (data === "true") {
                        $('a[id$="-complain"]').addClass("disabled");
                        swal("状态修改成功！", "", "success");
                    } else {
                        swal("状态修改失败！", "", "error");
                    }
                },
                error: errorAlert
            });
        }
    });
}



