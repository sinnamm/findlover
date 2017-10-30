var age_low_limit = 18;
var age_high_limit = 66;
var height_low_limit = 145;
var height_high_limit = 210;
var salary_array = ['3000','5000','8000','12000','15000','20000'];


// 会员、星级用户和牵手币logo toolbar
function initToolBar() {
    $('img[data-toolbar="vip-toolbar"]').toolbar({
        content: '#vip-deadline',
        position: 'top',
        adjustment: 25
    });
    $('img[data-toolbar="star-toolbar"]').toolbar({
        content: '#star-deadline',
        position: 'top',
        adjustment: 25
    });
    $('img[data-toolbar="asset-toolbar"]').toolbar({
        content: '#user-asset',
        position: 'top',
        adjustment: 25
    });
}

//初始化Dropdown为hover事件
function initDropdown() {
    $(".dropdown").hover(
        function () {
            $('.dropdown-menu', this).stop(true, true).slideDown(100);
            $(this).toggleClass('open');
        },
        function () {
            $('.dropdown-menu', this).stop(true, true).slideUp(100);
            $(this).toggleClass('open');
        }
    );
}

//初始化年龄下拉列表
function initAgeDropdown(dropdownBtnId, age_low_value, age_high_value) {
    $("select[id^=age-select-]").find("option:gt('0')").remove();
    for (var x = age_low_limit; x <= age_high_limit; x++) {
        //[attr*=val]	匹配attr属性值含有val的元素
        $("select[id=age-select-low]").append($("<option value='" + x + "' " + (x == age_low_value ? "selected" : "") + ">" + x + "</option>"));
        $("select[id=age-select-high]").append($("<option value='" + x + "' " + (x == age_high_value ? "selected" : "") + ">" + x + "</option>"));
    }
    // 年龄条件控制
    $("#age-select-low").change(function () {
        var low_age = this.value;
        var pre_selected = parseInt($("#age-select-high").val());
        $("#age-select-high").find("option:gt('0')").remove();
        for (var x = low_age == '-1' ? age_low_limit : low_age; x <= age_high_limit; x++) {
            $("#age-select-high").append($("<option value='" + x + "' " + (x == pre_selected ? "selected" : "") + ">" + x + "</option>"));
        }
        if (dropdownBtnId != undefined)
            updateAgeDropdown(dropdownBtnId);
    });
    $("#age-select-high").change(function () {
        var high_age = this.value;
        var pre_selected = parseInt($("#age-select-low").val());
        $("#age-select-low").find("option:gt('0')").remove();
        for (var x = age_low_limit; x <= (high_age == '-1' ? age_high_limit : high_age); x++) {
            $("#age-select-low").append($("<option value='" + x + "' " + (x == pre_selected ? "selected" : "") + ">" + x + "</option>"));
        }
        if (dropdownBtnId != undefined)
            updateAgeDropdown(dropdownBtnId);
    });
}

//初始化身高的下拉列表
function initHeightDropdown(dropdownBtnId, height_low_value, height_high_value) {
    $("select[id^=height-select-]").find("option:gt('0')").remove();
    for (var x = height_low_limit; x <= height_high_limit; x++) {
        $("select[id=height-select-low]").append($("<option value='" + x + "' " + (x == height_low_value ? "selected" : "") + ">" + x + "</option>"));
        $("select[id=height-select-high]").append($("<option value='" + x + "' " + (x == height_high_value ? "selected" : "") + ">" + x + "</option>"));
    }
    // 身高条件控制
    $("#height-select-low").change(function () {
        var low_age = this.value;
        var pre_selected = parseInt($("#height-select-high").val());
        //:gt(0) 选出 index 大于 0 的元素，移除
        $("#height-select-high").find("option:gt('0')").remove();
        for (var x = low_age == '-1' ? height_low_limit : low_age; x <= height_high_limit; x++) {
            $("#height-select-high").append($("<option value='" + x + "' " + (x == pre_selected ? "selected" : "") + ">" + x + "</option>"));
        }
        if (dropdownBtnId != undefined)
            updateHeightDropdown(dropdownBtnId);
    });
    $("#height-select-high").change(function () {
        var high_age = this.value;
        var pre_selected = parseInt($("#height-select-low").val());
        $("#height-select-low").find("option:gt('0')").remove();
        for (var x = height_low_limit; x <= (high_age == '-1' ? height_high_limit : high_age); x++) {
            $("#height-select-low").append($("<option value='" + x + "' " + (x == pre_selected ? "selected" : "") + ">" + x + "</option>"));
        }
        if (dropdownBtnId != undefined)
            updateHeightDropdown(dropdownBtnId);
    });
}

//初始化地区下拉列表
function initWorkplaceDropdown(spanId, provinceId, cityId, provinceValue, cityValue) {
    var $provinceSel = $("#" + provinceId);
    var $citySel = $("#" + cityId);
    $provinceSel.find("option:gt('0')").remove();
    $.ajaxSettings.async = false;
    $.getJSON(contextPath + "json/cities.json", function (data) {
        for (var x = 0; x < data.length; x++) {
            if (data[x].name == provinceValue)
                $provinceSel.append($("<option value='" + data[x].name + "' selected>" + data[x].name + "</option>"));
            else
                $provinceSel.append($("<option value='" + data[x].name + "'>" + data[x].name + "</option>"));
        }
    });
    if (provinceValue != undefined && provinceValue != "-1") {
        $.getJSON(contextPath + "json/cities.json", function (data) {
            for (var x = 0; x < data.length; x++) {
                if (data[x].name == $provinceSel.val()) {
                    for (var y = 1; y < data[x].sub.length; y++) {
                        if (data[x].sub[y].name == cityValue) {
                            $citySel.append($("<option value='" + data[x].sub[y].name + "' selected>" + data[x].sub[y].name + "</option>"));
                        } else {
                            $citySel.append($("<option value='" + data[x].sub[y].name + "'>" + data[x].sub[y].name + "</option>"));
                        }
                    }
                    break;
                }
            }
        });
    }
    $provinceSel.change(function () {
        var preCityValue = $citySel.val();
        $citySel.find(":gt('0')").remove();
        $.ajaxSettings.async = false;
        $.getJSON(contextPath + "json/cities.json", function (data) {
            for (var x = 0; x < data.length; x++) {
                if (data[x].name == $("#" + provinceId).val()) {
                    for (var y = 1; y < data[x].sub.length; y++) {
                        if (data[x].sub[y].name == preCityValue) {
                            $citySel.append($("<option value='" + data[x].sub[y].name + "' selected>" + data[x].sub[y].name + "</option>"));
                        } else {
                            $citySel.append($("<option value='" + data[x].sub[y].name + "'>" + data[x].sub[y].name + "</option>"));
                        }
                    }
                }
            }
        });
        $.ajaxSettings.async = true;
        if (spanId != undefined)
            updateWorkplaceDropdown(spanId, provinceId, cityId);
    });
    $citySel.change(function () {
        if (spanId != undefined)
            updateWorkplaceDropdown(spanId, provinceId, cityId);
    });
    $.ajaxSettings.async = true;
}

//初始化月收入的下拉列表
function initSalaryDropdown(dropdownBtnId, salary_low_value, salary_high_value) {
    $("select[id^=salary-select-]").find("option:gt('0')").remove();
    for (var x = 0; x < salary_array.length; x++) {
        $("select[id=salary-select-low]").append($("<option value='" + salary_array[x] + "' " + (salary_array[x] == salary_low_value ? "selected" : "") + ">" + salary_array[x] + "</option>"));
        $("select[id=salary-select-high]").append($("<option value='" + salary_array[x] + "' " + (salary_array[x] == salary_high_value ? "selected" : "") + ">" + salary_array[x] + "</option>"));
    }
    // 月收入条件控制c
    $("#salary-select-low").change(function () {
        var low_index = salary_array.indexOf(this.value);
        var pre_selected_index = salary_array.indexOf($("#salary-select-high").val());
        $("#salary-select-high").find("option:gt('0')").remove();
        for (var x = low_index == "-1" ? 0 : low_index; x < salary_array.length; x++) {
            $("#salary-select-high").append($("<option value='" + salary_array[x] + "' " + (x == pre_selected_index ? "selected" : "") + ">" + salary_array[x] + "</option>"));
        }
        if (dropdownBtnId!=undefined)
            updateSalaryDropdown(dropdownBtnId);
    });
    $("#salary-select-high").change(function () {
        var high_index = salary_array.indexOf(this.value);
        var pre_selected_index = salary_array.indexOf($("#salary-select-low").val());
        $("#salary-select-low").find("option:gt('0')").remove();
        for (var x = 0; x < (high_index == '-1' ? salary_array.length : high_index); x++) {
            $("#salary-select-low").append($("<option value='" + salary_array[x] + "' " + (x == pre_selected_index ? "selected" : "") + ">" + salary_array[x] + "</option>"));
        }
        if (dropdownBtnId!=undefined)
            updateSalaryDropdown(dropdownBtnId);
    });
}

// 动态更新地区下拉列表
function updateWorkplaceDropdown(spanId, provinceId, cityId) {
    var provinceSel = $("#" + provinceId);
    var citySel = $("#" + cityId);
    var result;
    if (provinceSel.val() == "-1") {
        result = "地区不限";
    } else {
        if (citySel.val() == "-1")
            result = provinceSel.val();
        else
            result = provinceSel.val() + "&nbsp;" + citySel.val();
    }
    $("#" + spanId).html(result);
}

//初始化民族下拉列表
function initNationalDropdown(nationalId) {
    $("#" + nationalId).find("option:gt('0')").remove();
    $.getJSON(contextPath + "json/nations.json", function (data) {
        for (var x = 0; x < data.length; x++) {
            $("#" + nationalId).append($("<option value='" + data[x].name + "'>" + data[x].name + "</option>"));
        }
    });
}

//初始化单个身高下拉列表
function initSingleHeightDropdown(heightId) {
    $("#" + heightId).empty();
    $("#" + heightId).append($("<option value='-1'>请选择</option>"));
    for (var i = height_low_limit; i < height_high_limit + 1; i++) {
        $("#" + heightId).append($("<option value='" + i + "'>" + i + "</option>"));
    }
}

/**
 * 初始化单个月薪下拉列表
 * @param select_salary_id 下拉列表select元素id
 * @param salary_value 回填值
 */
function initSingleSalaryDropdown(select_salary_id, salary_value) {
    $("#" + select_salary_id).find("option:gt('0')").remove();
    for (var x = 0; x < salary_array.length; x++) {
        $("#" + select_salary_id).append($("<option value='" + salary_array[x] + "' " + (salary_array[x] == salary_value ? "selected" : "") + ">" + salary_array[x] + "</option>"));
    }
}

Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

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
            $("#" + selectId).find("option:gt(0)").remove();
            $(data).each(function (index, element) {
                $("#" + selectId).append($("<option value='" + element.value + "'>" + element.value + "</option>"));
            });
        }
    });
}

//全局表单验证初始样式
function niceBaseCofig() {
    //配置nice-validator主题
    $.validator.setTheme('bootstrap', {
        validClass: 'has-success',
        invalidClass: 'has-error',
        bindClassTo: '.form-group',
        formClass: 'n-default n-bootstrap',
        msgClass: 'n-right'
    });
}

//取消关注
function cancelFollow(followId) {
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
                        $("#follow-info").text("关注");
                    } else {
                        errorAlert();
                    }
                },
                error: errorAlert
            });
        }
    });
}

function errorAlert(){
    swal({
        title: "发生未知错误，请联系管理员！",
        icon: "danger",
        dangerMode: true
    })
}

