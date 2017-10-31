pageSize = 5;
$(function () {
    follower(1);
    myfollow(1);
    $("#myfollow-tab").click(function () {
        myfollow(1);
    });
    $("#follower-tab").click(function () {
        follower(1);
    });
});
function myfollow(pageNum) {
    loadData(pageNum, "myfollow");
}
function follower(pageNum) {
    loadData(pageNum, "follower");
}

function loadData(pageNum, type) {
    $.post(contextPath+"follow/"+type,{"pageNum":pageNum,"pageSize":pageSize},function (data) {
        var $items = $("#"+type+"Panel").find(">.jobs-item");
        $items.empty();
        if(type == 'myfollow') {
            $("#follow-count").text('('+data.total+')');
        }
        $("#"+type+"-count").text(data.total);
        for(var x = 0; x < data.list.length; x++){
            var user = data.list[x];
            var $item = $('<div class="thumb_top" id="item-'+user.id+'">\n' +
                '        <div class="thumb" style="width: 150px">\n' +
                '            <a href="'+contextPath+'profile/'+user.id+'"><img src="'+contextPath+'file?path='+(user.authority != 1?(user.sex=='男'?'male':'female'):user.photo)+'"\n' +
                '                                             class="img-responsive"/></a>' +
                '        </div>\n' +
                '        <div class="jobs_right">\n' +
                '            <h6 class="title"><a href="'+contextPath+'profile/'+user.id+'">'+user.nickname+'</a>&nbsp;<img class="flag" title="VIP用户标识" src="'+contextPath+'images/vip'+(user.vip?'':'-grey')+'.png"></h6>\n' +
                '            <ul class="login_details1">\n' +
                '               <li>ID：' + user.id + '</li>\n' +
                '            </ul>\n' + ((user.authority != 1) ? ('<ul class="login_details1"><li>该用户已隐藏个人资料</li></ul>') : (
                    '            <ul class="login_details1">\n' +
                    '                <li>'+user.age+'岁 '+user.workplace+' '+user.education+' '+user.marryStatus+'</li>\n' +
                    '            </ul>\n')) + (type=='follower'?'':
                    '            <div class="thumb_but"><a href="javascript:cancelFollowAndGoPage('+user.id+','+pageNum+')" class="photo_view"><i class="fa fa-heart-o"></i>&nbsp;取消关注</a>\n' +
                    '            </div>\n') + (user.authority != 1 ? '' :
                    '            <div class="thumb_but"><a href="'+contextPath+'letter?id='+user.id+'" class="photo_view"><i class="fa fa-envelope-o"></i>&nbsp;发私信</a>\n') +
                '        </div>\n' +
                '        <div class="clearfix"></div>\n' +
                '    </div>');
            $items.append($item);
            $items.append($('<hr>'));
        }
        setPage(data.pageNum,data.total,data.pages,type);
    },"json");
}

function cancelFollowAndGoPage(followId,pageNum,type) {
    swal({
        "title":"不在关注此用户？",
        "icon":"waring",
        buttons: ["取消", "确定"]
    }).then(choice=>{
        if(choice){
            $.ajax({
                url: contextPath + "follow?followId=" + followId,
                type: "delete",
                dataType: "text",
                success: function (result) {
                    if (result == "true") {
                        myfollow(pageNum);
                    } else {
                        errorAlert();
                    }
                },
                error: errorAlert
            });
        }
    });
}


/*
{
  "pageNum": 2, //页码
  "pageSize": 3, //每页要显示的条数
  "size": 3, //本页条数
  "startRow": 4,
  "endRow": 6,
  "total": 7, //总记录数
  "pages": 3, //总页数
  "list": [
    {

    }
  ],
  "prePage": 1,
  "nextPage": 3,
  "isFirstPage": false,
  "isLastPage": false,
  "hasPreviousPage": true,
  "hasNextPage": true,
  "navigatePages": 8,
  "navigatepageNums": [
  ],
  "navigateFirstPage": 1,
  "navigateLastPage": 3,
  "lastPage": 3,
  "firstPage": 1
}
*/