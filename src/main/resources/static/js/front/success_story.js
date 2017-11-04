var lineSize = 8;
var currentPage = 1;
$(function () {
    loadMore();
    initMasonry();
});

function loadMore() {
    $("#loadmore").click(function () {
        currentPage++;
        $.ajax({
            url: contextPath + "success_story/load_more",
            data: {
                lineSize: lineSize,
                currentPage: currentPage
            },
            type: "post",
            dataType: "json",
            async: true,
            success: function (data) {
                for (var x = 0; x < data.list.length; x++) {
                    var successStory = data.list[x];
                    var $result = $('  <div class="box paid_people-left">\n' +
                        '                        <ul class="profile_item">\n' +
                        '                            <a href="'+contextPath+'success_story/success_story_info/'+successStory.id+'">\n' +
                        '                                <li class="profile_left-top" style="padding: 0px">\n' +
                        '                                    <img width="320" height="auto" src="'+contextPath+'file?path='+successStory.photo+'" class="img-responsive"\n' +
                        '                                         alt=""/>\n' +
                        '                                </li>\n' +
                        '                                <li class="profile_item-desc">\n' +
                        '                                    <div class="suceess_story-date">\n' +
                        '                                        <span class="entry-1" >'+successStory.successTime.substring(0,10)+'</span>\n' +
                        '                                    </div>\n' +
                        '                                    <div class="suceess_story-content-info">\n' +
                        '                                        <h4><a href="'+contextPath+'success_story/success_story_info/'+successStory.id+'">'+successStory.leftUser+'&'+successStory.rightUser+'</a></h4>\n' +
                        '                                        <p>'+successStory.brief+'</p>\n' +
                        '                                    </div>\n' +
                        '                                    <div class="text-left"><a  href="'+contextPath+'success_story/success_story_info/'+successStory.id+'"\n' +
                        '                                                               class="fa fa-heart grey-heart btn btn-default"\n' +
                        '                                                               style="font-size: 15px;margin-left: 5px;">围观幸福</a></div>\n' +
                        '                                </li>\n' +
                        '                                <div class="clearfix"></div>\n' +
                        '                            </a>\n' +
                        '                        </ul>\n' +
                        '                    </div>');
                    $("#masonry").append($result).masonry('appended', $result);
                    if (currentPage===data.pages){
                        $("#loadmore").hide();

                    }
                }
            },
            error: function () {
                swal("错误", "遇到未知错误..", "error");
            }
        });
    });
}

function initMasonry() {
    var $container = $('#masonry');
    $container.imagesLoaded(function () {
        $container.masonry({
            itemSelector: '.box',
            gutter: 20,
            isAnimated: true,
        });
    });
}