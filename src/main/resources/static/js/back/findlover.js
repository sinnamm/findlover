$(function () {
    $(".treeview>a").click(function () {
        $(".treeview").removeClass("active");
        $(this).parent().addClass("active");
    });
    $(".treeview-menu a").click(function () {
        $(".treeview-menu a").parent().removeClass("active");
        $(this).parent().addClass("active");
    })
    $(".treeview-menu a").each(function () {
        $(this).attr("target","content");
    })
});

//全局表单验证初始样式
function niceBaseCofig() {
    //配置nice-validator主题
    $.validator.setTheme('bootstrap', {
        validClass: 'has-success',
        invalidClass: 'has-error',
        bindClassTo: '.form-group',
        formClass: 'n-default n-bootstrap',
        msgClass: 'n-right'
    });
}

function errorAlert(){
    swal({
        title: "发生未知错误！",
        icon: "danger",
        dangerMode: true
    })
}