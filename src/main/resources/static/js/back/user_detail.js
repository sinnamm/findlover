var pageNum = 1;
var pageSize = 5;
var pageSizeArray = [1, 3, 5, 8, 10];
var relationType = "follower";
$(function () {
    loadUserData("basic");
    initUserTab();
});

function loadUserRelation(){
    var data={"pageNum":pageNum,
        "pageSize":pageSize,
        "id":userId};
    $.ajax({
        url:contextPath+"admin/user/"+relationType,
        type:"get",
        data:data,
        success:function (data) {
            var $relationTableBody = $("#user-follow-table").find(">tbody");
            $relationTableBody.empty();
            var list = data.list;
            if (list.length==0){
                $("#user-follow-table").hide();
                $("#pagetool").hide();
                $("#message").show();
                return;
            }else {
                $("#user-follow-table").show();
                $("#pagetool").show();
                $("#message").hide();
            }
            for(var i=0;i<list.length;i++){
                var user = data.list[i].userBasic;
                var tr = $('<tr>\n' +
                    '         <td><img src="' + contextPath + 'file?path=' + user.photo + '" class="photo"></td>\n' +
                    '         <td>' + user.id + '\n' +
                    '             <img src="' + contextPath + 'images/vip' + (user.vip ? '' : '-grey') + '.png" class="flag">\n' +
                    '             <img src="' + contextPath + 'images/star-0' + (user.star ? '' : '-grey') + '.png" class="flag">\n' +
                    '         </td>\n' +
                    '         <td>' + user.nickname + '</td>\n' +
                    '         <td>' + user.email + '</td>\n' +
                    '         <td>' + user.sex + '</td>\n' +
                    '    </tr>');
                if (relationType=="follow"||relationType=="follower") {
                    $("#time-th").text("关注时间");
                    tr.append(' <td>' + list[i].followTime + '</td>\n');
                }
                if (relationType=="trace"||relationType=="tracer") {
                    $("#time-th").text("访问时间");
                    tr.append(' <td>' + list[i].visitTime + '</td>\n')
                }


                tr.append($('<td><a class="btn btn-sm btn-info" href="' + contextPath + 'admin/user/details/' + user.id + '"><i class="fa fa-edit"></i>&nbsp;查看详情</a></td>'));
                $relationTableBody.append(tr);
            }
            setPage(data.pageNum,data.total, data.pages, "goPage");
        }
    })
}

function loadUserData(type) {
    $.get(contextPath + "admin/user/" + type + "/" + userId, {}, function (data) {
        var content = $("#" + type + "-pane").find(".container-fluid");
        content.empty();
        for (var name in colmap[type]) {
            if (colmap[type][name] != undefined) {
                var nameSpan = $('<span class="col-md-2 col-sm-5 col-xs-5 col-md-offset-1 title"></span>');
                var valueSpan = $('<span class="col-md-3 col-sm-6 col-xs-6"></span>');
                if ((typeof colmap[type][name]) != "string") {
                    nameSpan.text(colmap[type][name]["name"]);
                    var tv = colmap[type][name][data[name]];
                    if (tv == undefined || tv == "null")
                        valueSpan.text("暂无");
                    else
                        valueSpan.text(tv);
                } else {
                    nameSpan.text(colmap[type][name]);
                    if (name == "id")
                        valueSpan.text(userId);
                    else if (data[name] == undefined || data[name] == "null")
                        valueSpan.text("暂无");
                    else
                        valueSpan.text(data[name]);

                }
                content.append(nameSpan);
                content.append(valueSpan);
            }
        }
    })
}

function initUserTab() {
    $("#user-tab a:lt(6)").click(function () {
        loadUserData(this.id);
    });
    $("#photos").click(function () {
        
    });
    $("#relation").click(function () {
        setPageSizeSel(pageSizeArray,pageSize);
        initPageSizeSel();
        loadUserRelation();
    });

    $("#relation-div button").click(function () {
        relationType = this.id.split("-")[0];
       loadUserRelation(relationType);
    })
}

function initPageSizeSel() {
    $("#pageSize-sel").change(function () {
        pageSize = $(this).val();
        goPage(1);
    });
};

function goPage(_pageNum) {
    pageNum = _pageNum;
    loadUserRelation();
};

var colmap = {
    "basic": {
        "id": "用户ID",
        "nickname": "昵称",
        "tel": "手机号",
        "workplace": "工作地区",
        "email": "邮箱",
        "sex": "性别",
        "height": "身高",
        "sexual": "性取向",
        "birthday": "生日",
        "education": "学历",
        "salary": "月收入",
        "marryStatus": "婚姻状况",
        "regTime": "注册时间",
        "authority": {
            "0": "所有用户不可见",
            "1": "所有用户可见",
            "2": "仅我关注的人可见",
            "name": "资料可见性"
        },
        "liveCondition": {
            "name": "住房条件",
            "0": "无房",
            "1": "有房"
        },
        "status": {
            "0": "已锁定",
            "1": "已激活",
            "2": "未激活",
            "name": "账号状态"
        }
    },
    "asset": {
        "id": "用户ID",
        "asset": "牵手币余额",
        "starDeadline": "star到期时间",
        "cost": "总消费",
        "vipDeadline": "vip到期时间"
    },
    "detail": {
        "id": "用户ID",
        "birthplace": "籍贯",
        "realname": "真实姓名",
        "nation": "民族",
        "cardnumber": "身份证号",
        "graduation": "毕业院校",
        "zodiac": "星座",
        "weight": "体重",
        "animal": "生肖",
        "religion": "信仰",
        "hobby": "爱好简述"
    },
    "pick": {
        "id": "用户ID",
        "sex": "性别",
        "heightHigh": "身高上限",
        "birthday": "籍贯",
        "heightLow": "身高下限",
        "education": "学历",
        "smoke": {
            "name": "在意Ta抽烟吗",
            "0": "不在意",
            "1": "在意"
        },
        "drink": {
            "name": "在意Ta喝酒吗",
            "0": "不在意",
            "1": "在意"
        },
        "ageHigh": "年龄上限",
        "marryStatus": "婚姻状况",
        "ageLow": "年龄下限",
        "salaryHigh": "月收入上限",
        "job": "职业",
        "salaryLow": "月收入下限",
        "workplace": "工作地区"
    },
    "life": {
        "id": "用户ID",
        "job": "职业",
        "car": {
            "name": "是否有车",
            "0": "没车",
            "1": "有车"
        },
        "jobTime": "工作繁忙度",
        "smoke": {
            "name": "是否抽烟",
            "0": "不抽烟",
            "1": "抽烟"
        },
        "drink": {
            "name": "是否喝酒",
            "0": "不怎么喝",
            "1": "喜欢喝"
        },
        "character": "个人性格",
        "jobBrief": "工作情况简述"
    },
    "status": {
        "id": "用户ID",
        "familyBrief": "家庭情况简述",
        "marryTime": "想何时结婚",
        "ldr": {
            "name": "接受异地恋？",
            "0": "不接受",
            "1": "可以接受"
        },
        "broAnd": "兄弟姐妹",
        "loveHistory": "婚恋史",
        "parentStatus": "父母状况"
    }
};