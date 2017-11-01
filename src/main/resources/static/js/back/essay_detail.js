$(function () {
    initCheckedAndDeleteBtn();
    //loadEssayData();
});

function initCheckedAndDeleteBtn() {
    $('a[id$="-essay"]').click(function () {
        var $btn = $(this);
        var atext = $btn.text();
        if(atext === "审核"){
            checkBtn($btn);
        }else {
            deleteBtn($btn);
        }
    });
}

function deleteBtn($btn) {
    swal({
        title: '确定要删除此文章吗？',
        icon: "warning",
        buttons: ["取消", "确定"]
    }).then(result => {
        if (result) {
            $.ajax({
                url: contextPath + "admin/essay/delete/" + essayId,
                type: "delete",
                dataType: "text",
                success: function (data) {
                    //window.location.go(-1);
                    if (data === "true") {
                        $btn.addClass("disabled");
                        history.go(-1);
                        // location.reload();
                    } else {
                        swal("状态修改失败！", "", "error");
                    }
                },
                error: errorAlert
            });
        }
    });
}

//审核按钮
function checkBtn($btn) {
    swal({
        title: '确定审核通过吗？',
        icon: "warning",
        buttons: ["取消", "确定"]
    }).then(result => {
        if (result) {
            $.ajax({
                url: contextPath + "admin/essay/info/" + essayId,
                type: "put",
                data: {"status": 1},
                dataType: "text",
                success: function (data) {
                    if (data === "true") {
                        $btn.addClass("disabled");
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

/*function loadEssayData() {
    $.get(contextPath + "admin/essay/detail/" + essayId, {}, function (data) {
        var content = $("#" + type + "-pane").find(".container-fluid");
        content.empty();
        data

    })
}*/


