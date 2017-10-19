var pageNum = 1;
var pageSize = 5;
var identity = "all";
var pageSizeArray = [1,3,5,8,10];

$(function () {
    setPageSizeSel(pageSizeArray, pageSize);
    loadData();
    initPageSizeSel();
    initIdentityBtn();
});

function loadData() {
    var searchSel = $("#basic-search-sel").val();
    $.get(contextPath + "admin/user",
        {
            "identity": identity,
            "pageNum": pageNum,
            "pageSize": pageSize,
            searchSel: $("#basic-search-val").val()
        }, function (data) {
            var $userBasicTBody = $("#user-basic-table").find(">tbody");
            $userBasicTBody.empty();
            for (var x = 0; x < data.list.length; x++) {
                var user = data.list[x];
                var tr = $('<tr>\n' +
                    '         <td><img src="' + contextPath + 'upload/user/photo/' + user.photo + '" class="photo"></td>\n' +
                    '         <td>' + user.id + '\n' +
                    '             <img src="' + contextPath + 'images/vip' + (user.vip ? '' : '-grey') + '.png" class="flag">\n' +
                    '             <img src="' + contextPath + 'images/star-0' + (user.star ? '' : '-grey') + '.png" class="flag">\n' +
                    '         </td>\n' +
                    '         <td>' + user.nickname + '</td>\n' +
                    '         <td>' + user.email + '</td>\n' +
                    '         <td>' + user.tel + '</td>\n' +
                    '    </tr>');
                if (user.authenticated) {
                    tr.append($('<td><button class="btn btn-sm btn-success"><i class="fa fa-check-square-o"></i>&nbsp;已认证</button></td>'));
                } else {
                    tr.append($('<td><button class="btn btn-sm btn-warning"><i class="fa fa-times"></i>&nbsp;未认证</button></td>'));
                }
                if (user.status === 0) {
                    tr.append($('<td><button class="btn btn-sm btn-danger"><i class="fa fa-times"></i>&nbsp;已锁定</button></td>'));
                } else if (user.status === 1) {
                    tr.append($('<td><button class="btn btn-sm btn-success"><i class="fa fa-check"></i>&nbsp;已激活</button></td>'));
                } else if (user.status === 2) {
                    tr.append($('<td><button class="btn btn-sm btn-warning"><i class="fa fa-exclamation"></i>&nbsp;未激活</button></td>'));
                }
                tr.append($('<td><a class="btn btn-sm btn-info" href="#"><i class="fa fa-edit"></i>&nbsp;查看详情</a></td>'));
                $userBasicTBody.append(tr);
            }
            setPage(data.pageNum, data.pages, "goPage");
        }, "json");
}

function goPage(_pageNum) {
    pageNum = _pageNum;
    loadData();
}

function initPageSizeSel() {
    $("#pageSize-sel").change(function () {
        pageSize = $(this).val();
        goPage(1);
    });
}

function initIdentityBtn() {
    $("button[id*='user-btn']").click(function () {
        identity = this.id.split("-")[2];
        goPage(1);
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
      "age": 0,
      "id": 100000,
      "email": "gss@qq.com",
      "password": "202CB962AC59075B964B07152D234B70",
      "nickname": "gsssss",
      "tel": "123",
      "sex": "男",
      "birthday": 869241600000,
      "photo": "p7.jpg",
      "marryStatus": "未婚",
      "height": 175,
      "sexual": "男",
      "education": "大学本科",
      "workplace": "山东-济南",
      "salary": 8000.0,
      "liveCondition": 0,
      "authority": 1,
      "status": 1,
      "code": null,
      "regTime": 1508245373000,
      "star": true,
      "vip": true,
      "authenticated":false
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