$(function () {
    $(".treeview>a").click(function () {
        $(".treeview").removeClass("active");
        $(this).parent().addClass("active");
    });
    $(".treeview-menu a").click(function () {
        $(".treeview-menu a").parent().removeClass("active");
        $(this).parent().addClass("active");
        $(this).target("content");
    })
});