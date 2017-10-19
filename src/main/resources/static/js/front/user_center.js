$(function () {
    initToolBar();
    initTabClick();
    // initWorkplaceDropdown(undefined, "work_province", "work_city");
    selectDict("education", "education");
    selectDict("zodiac", "zodiac");
    selectDict("animal", "animal");
    selectDict("religion", "religion");
    selectDict("job", "job");
    selectDict("job_time", "jobTime");
    initNationalDropdown("nation");
    selectUserBasic();
    initSingleHeightDropdown("height");
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

// 获取学校信息并回填用户数据
function initCampus(provinceSelId, campusSelId, province_value, campus_value) {
    var $provinceSel = $("#" + provinceSelId);
    var $campusSel = $("#" + campusSelId);
    $provinceSel.find("option:gt(0)").remove();
    $campusSel.find("option:gt(0)").remove();
    $.getJSON(contextPath + "json/campus.json", function (data) {
        for (var x = 0; x < data.length; x++) {
            for (var province in data[x]) {
                $provinceSel.append($('<option value="' + province + '" ' + (province === province_value ? 'selected' : '') + '>' + province + '</option>'));
                if (province === province_value) {
                    for (var y = 0; y < data[x].province.length; y++) {
                        $campusSel.append($('<option value="' + data[x].province[y] + '" ' + (data[x].province[y] === campus_value ? 'selected' : '') + '>' + data[x].province[y] + '</option>'));
                    }
                }
            }
        }
    })
}

//页面加载完成时第一次获取用户基本信息
function selectUserBasic() {
    $.ajax({
        url: contextPath + "usercenter/selectUserBasic",
        type: "GET",
        dataType: "JSON",
        success: function (data, a, b) {
            var userfixed = "您是一位" + data.sex + "士，您的出生日期为"
                + new Date(data.birthday).format("yyyy-MM-dd") + "，"
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
            if (data.workplace == "null") {
                initWorkplaceDropdown(undefined, "work_province", "work_city", -1, -1);
            } else {
                var arr = data.workplace.split("-");
                initWorkplaceDropdown(undefined, "work_province", "work_city", arr[0], arr.length > 0 ? arr[1] : -1);
            }
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
            if (data.zodiac != "null") {
                $("#zodiac").val(data.zodiac);
            }
            if (data.zodiac != 'null') {
                $("#nation").val(data.nation);
            }
            if (data.animal != 'null') {
                $("#animal").val(data.animal);
            }
            if (data.religion != 'null') {
                $("#religion").val(data.religion);
            }
            if (data.birthplace == "null") {
                initWorkplaceDropdown(undefined, "birthplace_province", "birthplace_city", -1, -1);
            } else {
                var arr = data.birthplace.split("-");
                initWorkplaceDropdown(undefined, "birthplace_province", "birthplace_city", arr[0], arr.length > 0 ? arr[1] : -1);
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
            if (data.smoke != 'null') {
                $("#smoke").val(data.smoke);
            }
            if (data.drink != 'null') {
                $("#drink").val(data.drink);
            }
            if (data.car != 'null') {
                $("#car").val(data.car);
            }
            if (data.job != 'null') {
                $("#job").val(data.job);
            }
            if (data.jobTime != 'null') {
                $("#jobTime").val(data.jobTime);
            }
            $("#character").val(data.character);
            $("#jobBrief").val(data.jobBrief);
        }
    });
}