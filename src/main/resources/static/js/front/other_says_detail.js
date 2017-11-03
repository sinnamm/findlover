$(function () {
    loadHotEssayData();
    initLikeEssay();
});

function initLikeEssay() {
    $('#like-essay').click(function () {
        var essayId = $(this).attr('value');
        var $likeDiv = $(this);
        $.get(contextPath + "other_says/like/"+essayId, {}, function (data,b,c) {
            if(data.result){
                $likeDiv.text('('+data.likeCount+')');
                swal("温馨提示","点赞成功！","success");
                $('#like-essay').css("color","red");
            }else {
                swal("温馨提示","您已经为该文章点过赞了！","warning");
            }
        }, "json");
    });
}

//加载好文推荐
function loadHotEssayData() {
    $.get(contextPath + "other_says/hot_essays", {}, function (data) {
        var $hotEssaysUl = $('#hot-essay-detail');
        $hotEssaysUl.find('li:gt(0)').remove();
        for (var x = 0; x < data.length; x++) {
            var essay = data[x];
            var $essay = $('<li>\n' +
                '               <div  class="jobs-item with-thumb">\n' +
                '                   <div class="thumb_top">\n' +
                '                       <a href="'+contextPath+'other_says/essaydetail/'+essay.id+'"  class="thumb">\n' +
                '                           <img src="'+contextPath+'file?path='+essay.photo+'" style="width: 100px" class="img-responsive" alt=""/></a>\n' +
                '                       </a>\n' +
                '                       <div class="jobs_right">\n' +
                '                          <a href="'+contextPath+'other_says/essaydetail/'+essay.id+'"> <h5>'+essay.title+'</h5></a>\n' +
                '                       </div>\n' +
                '                       <div class="clearfix"> </div>\n' +
                '                   </div>\n' +
                '               </div><hr/>\n' +
                '           </li>');
            $hotEssaysUl.append($essay);
        }
    }, "json");
}

