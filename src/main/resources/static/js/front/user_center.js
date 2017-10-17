$(function () {
    initToolBar();
    initTabClick();
    initWorkplaceDropdown(undefined, "work_province", "work_city");
    selectDict("education", "education");
    selectDict("zodiac", "zodiac");
    selectDict("animal", "animal");
    selectDict("religion", "religion");
    selectDict("job", "job");
    selectDict("job_time", "jobTime");
    initNationalDropdown("nation");
    selectUserBasic();
    initSingleHeightDropdown("height");
    initWorkplaceDropdown();
});


//初始化点击事件
function initTabClick() {
    $("#basic-tab").click(function () {
        selectUserBasic();
    });
    $("#more-tab").click(function () {
        selectUserDetail();
    });
    $("#work-tab").click(function () {
        selectUserLife();
    });
}

/*//获取Education列表对象
function selectEducationDict() {
    $.ajax({
        url:contextPath+"dicts/education",
        type:"GET",
        dataType:"JSON",
        async:false,
        success:function (data,a,b) {
            $("#education").empty();
            $("#education").append($("<option value=\"请选择\">请选择</option>"));
            $(data).each(function (index, element) {
                $("#education").append($("<option value=\""+element.value+"\">"+element.value+"</option>"));
            });
        }
    });
}*/


/**
 * 获取字典列表对象
 * @param dictType 字典数据库中type字段类型
 * @param selectId 需要加载的select控件
 */
function selectDict(dictType, selectId) {
    $.ajax({
        url: contextPath + "dicts/" + dictType,
        type: "GET",
        dataType: "JSON",
        async: false,
        success: function (data, a, b) {
            $("#" + selectId).empty();
            $("#" + selectId).append($("<option value=\"请选择\">请选择</option>"));
            $(data).each(function (index, element) {
                $("#" + selectId).append($("<option value=\"" + element.value + "\">" + element.value + "</option>"));
            });
        }
    });
}


//页面加载完成时第一次获取用户基本信息
function selectUserBasic() {
    $.ajax({
        url: contextPath + "usercenter/selectUserBasic",
        type: "GET",
        dataType: "JSON",
        success: function (data, a, b) {
            var userfixed = "您是一位" + data.sex + "士，您的出生日期为"
                + getMyDate(data.birthday) + "，"
                + data.marryStatus
                + "，" + data.height + "M";
            $("#userfixed").text(userfixed);
            $("#nickname").val(data.nickname);
            $("#tel").val(data.tel);
            $("#email").val(data.email);
            $("#salary").val(data.salary);
            $("#height").val(data.height);
            $("#sexual").val(data.sexual == data.sex ? "男" : "女");
            $("#education").val(data.education);
            var wpArray = data.workplace.split("-");
            //alert(wpArray[0]);
            //alert(wpArray[1]);
            $("#work_province").val($.trim(wpArray[0]));
            $("#work_city").val($.trim(wpArray[1]));
            $("#house").val(data.liveCondition);
        }
    });
}

//点击用户详细时，获取详情信息
function selectUserDetail() {
    $.ajax({
        url: contextPath + "usercenter/selectUserDetail",
        type: "GET",
        dataType: "JSON",
        success: function (data, a, b) {
            //alert(b.responseText);
            $("#realname").val(data.realname);
            $("#weight").val(data.weight);
            $("#cardnumber").val(data.cardnumber);
            if (data.zodiac != '') {
                $("#zodiac").val(data.zodiac);
            }
            if (data.zodiac != '') {
                $("#nation").val(data.nation);
            }
            if (data.animal != '') {
                $("#animal").val(data.animal);
            }
            if (data.religion != '') {
                $("#religion").val(data.religion);
            }
            // $("#work_province").val($.trim(wpArray[0]));
            // $("#work_city").val($.trim(wpArray[1]));
            $("#hobby").val(data.hobby);
            $("#signature").val(data.signature);
        }
    });
}

//点击工作生活时，获取工作生活信息
function selectUserLife() {
    $.ajax({
        url: contextPath + "usercenter/selectUserLife",
        type: "GET",
        dataType: "JSON",
        success: function (data, a, b) {
            if ($.type(data.smoke) !='null') {
                $("#smoke").val(data.smoke);
            }
            if ($.type(data.drink) !='null') {
                $("#drink").val(data.drink);
            }
            if ($.type(data.car) !='null') {
                $("#car").val(data.car);
            }
            if (data.job != '') {
                $("#job").val(data.job);
            }
            if (data.jobTime != '') {
                $("#jobTime").val(data.jobTime);
            }
            $("#character").val(data.character);
            $("#jobBrief").val(data.jobBrief);
        }
    });
}


//获得年月日
function getMyDate(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        // oHour = oDate.getHours(),
        // oMin = oDate.getMinutes(),
        // oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay);//最后拼接时间
    return oTime;
};

//补0操作
function getzf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}