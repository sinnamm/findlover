var age_low_limit = 18;
var age_high_limit = 66;
var height_low_limit = 145;
var height_high_limit = 210;
$(function () {
    $("select[id*='search-select-']").each(function () {
        $(this).change(function () {
            $("#dropdown-btn-" + this.id.split("-")[2]).find(".dropdown-value").html(this.value == "-1" ? $(this).find("option:selected").attr("content") : this.value);
        })
    });
    initAgeDropdown("dropdown-btn-2");
    initWorkplaceDropdown("workplace-span", "province-select", "city-select");
    initHeightDropdown("dropdown-btn-4");
});

//初始化年龄下拉列表
function initAgeDropdown(dropdownBtnId) {
    for (var x = age_low_limit; x <= age_high_limit; x++) {
        $("select[id*=age-select-]").append($("<option value='" + x + "'>" + x + "</option>"));
    }
    // 年龄条件控制
    $("#age-select-low").change(function () {
        var low_age = this.value;
        var pre_selected = parseInt($("#age-select-high").val());
        $("#age-select-high").find("option:gt('0')").remove();
        for (var x = low_age == '-1' ? age_low_limit : low_age; x <= age_high_limit; x++) {
            $("#age-select-high").append($("<option value='" + x + "' " + (x == pre_selected ? "selected" : "") + ">" + x + "</option>"));
        }
        updateAgeDropdown(dropdownBtnId);
    });
    $("#age-select-high").change(function () {
        var high_age = this.value;
        var pre_selected = parseInt($("#age-select-low").val());
        $("#age-select-low").find("option:gt('0')").remove();
        for (var x = age_low_limit; x <= (high_age == '-1' ? age_high_limit : high_age); x++) {
            $("#age-select-low").append($("<option value='" + x + "' " + (x == pre_selected ? "selected" : "") + ">" + x + "</option>"));
        }
        updateAgeDropdown(dropdownBtnId);
    });
}

// 更新年龄的下拉列表option
function updateAgeDropdown(dropdownBtnId) {
    var low_age = $("#age-select-low").val();
    var high_age = $("#age-select-high").val();
    var result;
    if (low_age == "-1") {
        if (high_age == "-1")
            result = "年龄不限";
        else if (high_age == age_low_limit)
            result = high_age + "岁";
        else
            result = high_age + "岁以下";
    } else {
        if (high_age == "-1")
            result = low_age + "岁以上";
        else if (low_age == high_age)
            result = low_age + "岁";
        else
            result = low_age + "—" + high_age + "岁";
    }
    $("#" + dropdownBtnId).find(".dropdown-value").html(result);
}

//初始化身高的下拉列表
function initHeightDropdown(dropdownBtnId) {
    for (var x = height_low_limit; x <= height_high_limit; x++) {
        $("select[id*=height-select-]").append($("<option value='" + x + "'>" + x + "</option>"));
    }
    // 身高条件控制
    $("#height-select-low").change(function () {
        var low_age = this.value;
        var pre_selected = parseInt($("#height-select-high").val());
        $("#height-select-high").find("option:gt('0')").remove();
        for (var x = low_age == '-1' ? height_low_limit : low_age; x <= height_high_limit; x++) {
            $("#height-select-high").append($("<option value='" + x + "' " + (x == pre_selected ? "selected" : "") + ">" + x + "</option>"));
        }
        updateHeightDropdown(dropdownBtnId);
    });
    $("#height-select-high").change(function () {
        var high_age = this.value;
        var pre_selected = parseInt($("#height-select-low").val());
        $("#height-select-low").find("option:gt('0')").remove();
        for (var x = height_low_limit; x <= (high_age == '-1' ? height_high_limit : high_age); x++) {
            $("#height-select-low").append($("<option value='" + x + "' " + (x == pre_selected ? "selected" : "") + ">" + x + "</option>"));
        }
        updateHeightDropdown(dropdownBtnId);
    });
}

// 更新身高的下拉列表option
function updateHeightDropdown(dropdownBtnId) {
    var low_age = $("#height-select-low").val();
    var high_age = $("#height-select-high").val();
    var result;
    if (low_age == "-1") {
        if (high_age == "-1")
            result = "身高不限";
        else if (high_age == age_low_limit)
            result = high_age + "cm";
        else
            result = high_age + "cm以下";
    } else {
        if (high_age == "-1")
            result = low_age + "cm以上";
        else if (low_age == high_age)
            result = low_age + "cm";
        else
            result = low_age + "—" + high_age + "cm";
    }
    $("#" + dropdownBtnId).find(".dropdown-value").html(result);
}

