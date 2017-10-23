window.onload = function () {
    addLetterUser();
}
function addLetterUser(){
    $(".userLetter").click(function(){
        var otherUserId =$(this).find("input").val();
        alert(otherUserId);
    });
}
