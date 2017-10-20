var salary_array = ['3000','5000','8000','12000','15000','20000'];
window.onload = function () {
    $('.flexslider').flexslider({
        animation: "slide",
        start: function (slider) {
            $('body').removeClass('loading');
        }
    });
}
$(function () {
    $("select[id*='search-select-']").each(function () {
        $(this).change(function () {
            $("#dropdown-btn-" + this.id.split("-")[2]).find(".dropdown-value").html(this.value == "-1" ? $(this).find("option:selected").attr("content") : this.value);
        })
    });
    initAgeDropdown("dropdown-btn-2");
    initWorkplaceDropdown("workplace-span", "province-select", "city-select");
    initHeightDropdown("dropdown-btn-4");
    initSalaryDropdown("dropdown-btn-7");
    initWorkplaceDropdown("birthplace-span", "province-select-bp", "city-select-bp");
    initNationalDropdown("search-select-13");
    selectUserBasic();
    getSearchUser();
    initDropdownSpan();

});
function initDropdownSpan() {
    //模拟操作，模拟select改变
    $("select[id*='select']").trigger("change");
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
            result = low_age + "-" + high_age + "岁";
    }
    $("#" + dropdownBtnId).find(".dropdown-value").html(result);
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
            result = low_age + "-" + high_age + "cm";
    }
    $("#" + dropdownBtnId).find(".dropdown-value").html(result);
}

//初始化月收入的下拉列表
function initSalaryDropdown(dropdownBtnId, salary_low_value, salary_high_value) {
    $("select[id^=salary-select-]").find("option:gt('0')").remove();
    for (var x = 0; x < salary_array.length; x++) {
        $("select[id=salary-select-low]").append($("<option value='" + salary_array[x] + "' " + (salary_array[x] == salary_low_value ? "selected" : "") + ">" + salary_array[x] + "</option>"));
        $("select[id=salary-select-high]").append($("<option value='" + salary_array[x] + "' " + (salary_array[x] == salary_high_value ? "selected" : "") + ">" + salary_array[x] + "</option>"));
    }
    // 月收入条件控制
    $("#salary-select-low").change(function () {
        var low_index = salary_array.indexOf(this.value);
        var pre_selected_index = salary_array.indexOf($("#salary-select-high").val());
        $("#salary-select-high").find("option:gt('0')").remove();
        for (var x = low_index == "-1" ? 0 : low_index; x < salary_array.length; x++) {
            $("#salary-select-high").append($("<option value='" + salary_array[x] + "' " + (x == pre_selected_index ? "selected" : "") + ">" + salary_array[x] + "</option>"));
        }
        updateSalaryDropdown(dropdownBtnId);
    });
    $("#salary-select-high").change(function () {
        var high_index = salary_array.indexOf(this.value);
        var pre_selected_index = salary_array.indexOf($("#salary-select-low").val());
        $("#salary-select-low").find("option:gt('0')").remove();
        for (var x = 0; x < (high_index == '-1' ? salary_array.length : high_index); x++) {
            $("#salary-select-low").append($("<option value='" + salary_array[x] + "' " + (x == pre_selected_index ? "selected" : "") + ">" + salary_array[x] + "</option>"));
        }
        updateSalaryDropdown(dropdownBtnId);
    });
}

// 更新月收入的下拉列表option
function updateSalaryDropdown(dropdownBtnId) {
    var low_index = salary_array.indexOf($("#salary-select-low").val());
    var high_index = salary_array.indexOf($("#salary-select-high").val());
    var result;
    if (low_index == "-1") {
        if (high_index == "-1")
            result = "月收入不限";
        else
            result = salary_array[high_index] + "元以下";
    } else {
        if (high_index == "-1")
            result = salary_array[low_index] + "元以上";
        else if (low_index == high_index)
            result = salary_array[low_index] + "元";
        else
            result = salary_array[low_index] + "-" + salary_array[high_index] + "元";
    }
    $("#" + dropdownBtnId).find(".dropdown-value").html(result);
}

//页面加载完成时第一次获取用户择偶条件的基本信息
function selectUserBasic() {
    $.ajax({
        url: contextPath + "search/initUserPick",
        type: "GET",
        dataType: "JSON",
        success: function (data) {
            //性别
            //年龄
            $("#age-span").html(data.ageLow + "-" + data.ageHigh);
            initAgeDropdown("dropdown-btn-2",data.ageLow,data.ageHigh);
            //地区
            if (data.workplace == "null") {
                $("#workplace-span").html("地区不限");
                initWorkplaceDropdown("workplace-span", "province-select", "city-select", -1, -1);
            } else {
                $("#workplace-span").html(data.workplace);
                var arr = data.workplace.split("-");
                initWorkplaceDropdown("workplace-span", "province-select", "birthplace_city", arr[0], arr.length > 0 ? arr[1] : -1);
            }
            //身高
            if(data.heightLow=="null"&&data.heightHigh!="null"){
                $("#height-span").html(data.heightHigh+"cm以下");
            }else if (data.heightHigh=="null"&&data.heightLow!="null"){
                $("#height-span").html(data.heightLow+"cm以上");
            }else if(data.heightLow!="null"&&data.heightHigh!="null"){
                $("#height-span").html(data.heightLow+"-"+data.heightHigh);
            }else {
                $("#height-span").html("身高不限");
            }
            initHeightDropdown("height-span",data.heightLow,data.heightHigh);
            //职业
            //婚史
            //月收入
            if(data.salaryLow=="null"&&data.salaryHigh!="null"){
                $("#salary-span").html(data.salaryHigh+"元以下");
            }else if (data.salaryHigh=="null"&&data.salaryLow!="null"){
                $("#salary-span").html(data.salaryLow+"元以上");
            }else if(data.salaryLow!="null"&&data.salaryHigh!="null"){
                $("#salary-span").html(data.salaryLow+"-"+data.salaryHigh);
            }else {
                $("#salary-span").html("月收入不限");
            }
            initSalaryDropdown("salary-span",data.salaryLow,data.salaryHigh)
            //学历
            //籍贯
            if (data.birthplace == "null") {
                $("#birthplace-span").html(data.birthplace);
                initWorkplaceDropdown("birthplace-span", "province-select-bp", "city-select-bp", -1, -1);
            } else {
                $("#birthplace-span").html("籍贯不限");
                var arr = data.birthplace.split("-");
                initWorkplaceDropdown("birthplace-span", "province-select-bp", "city-select-bp", arr[0], arr.length > 0 ? arr[1] : -1);
            }
        }
    });
}

//异步获取搜索条件对应的用户
function getSearchUser() {
    $("#search-form-submit").click(function () {
        alert($("#search-select-6 option:selected").val());
        alert("form"+$("#search-form").serialize());
        $.ajax({
            url:contextPath+"search/getSearchUser",
            type:"POST",
           // data:$("#search-form").serialize(),
            dataType:"JSON",
            success:function (data) {
                alert(data)
            }
        })
    })
}

