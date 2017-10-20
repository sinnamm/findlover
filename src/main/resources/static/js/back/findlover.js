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

function errorAlert(){
    swal({
        title: "发生未知错误！",
        icon: "danger",
        dangerMode: true
    })
}