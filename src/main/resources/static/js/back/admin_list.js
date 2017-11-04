var pageNum = 1;
var pageSize = 5;
var pageSizeArray = [1, 3, 5, 8, 10];

$(function () {
    setPageSizeSel(pageSizeArray, pageSize);
    loadData();
    initPageSizeSel();
    initSearchBtn();
    initSubBtn();
});

function loadData() {
    $.get(contextPath + "admin/admins",
        {
            "pageNum": pageNum,
            "pageSize": pageSize,
            "column": $("#admin-search-sel").val(),
            "keyword": $("#admin-search-val").val()
        }, function (data) {
            var $adminTBody = $("#admin-table").find(">tbody");
            $adminTBody.empty();
            for (var x = 0; x < data.list.length; x++) {
                var admin = data.list[x];
                var tr = $('<tr>\n' +
                    '         <td>' + admin.id + '</td>\n' +
                    '         <td>' + admin.username + '</td>\n' +
                    '         <td>' + admin.createTime + '</td>\n' +
                    '         <td>' + admin.lastLogin + '</td>\n' +
                    '         <td><button id="btn-' + admin.id + '" class="btn btn-sm btn-info"><i class="fa fa-edit"></i>&nbsp;查看权限</button></td>\n' +
                    '    </tr>');
                $adminTBody.append(tr);
            }
            initPermBtn();
            setPage(data.pageNum, data.total, data.pages, "goPage");
        }, "json");
}

function goPage(_pageNum) {
    pageNum = _pageNum;
    loadData();
}

function initPermBtn() {
    $("button[id^='btn-']").click(function () {
        var adminId = this.id.split("-")[1];
        $("#admin-id-label").text(adminId);
        $.get(contextPath + "admin/admin_roles", {"adminId": adminId}, function (data) {
            var adminRolesId = data.adminRolesId;
            var roles = data.roles;
            var $roleTable = $("#role-table");
            $roleTable.empty();
            var tr = $('<tr></tr>');
            for (var x = 0; x < roles.length; x++) {
                var role = roles[x];
                tr.append($('<td><input type="checkbox" name="role" value="' + role.id + '" ' + (adminRolesId.indexOf(role.id) >= 0 ? 'checked' : '') + '>&nbsp;' + role.name + '</td>'));
                if ((x + 1) % 3 == 0 || x + 1 == roles.length) {
                    $roleTable.append(tr);
                    tr = $('<tr></tr>');
                }
            }
        });
        $("#edit-admin-modal").modal("toggle");
    });
}

function initSubBtn() {
    $("#admin-sub-btn").click(function () {
        var adminId = $("#admin-id-label").text();
        var roleIds = [];
        $("input[name='role']").each(function () {
            if(this.checked) {
                roleIds.push(this.value);
            }
        });
        $.post(contextPath+"admin/admin_roles",{"adminId":adminId,"roleIds":roleIds},function (result) {
            if(result == "true"){
                swal("提示","角色修改成功","success");
            }else{
                errorAlert();
            }
        },"text");
    });
}

function initPageSizeSel() {
    $("#pageSize-sel").change(function () {
        pageSize = $(this).val();
        goPage(1);
    });
}

function initSearchBtn() {
    $("#admin-search-btn").click(function () {
        goPage(1);
    })
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