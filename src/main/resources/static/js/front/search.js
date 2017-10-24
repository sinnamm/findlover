var pageNum = 1;
var pageLabel = 0;
// window.onload = function () {
//     $('.flexslider').flexslider({
//         animation: "slide",
//         start: function (slider) {
//             $('body').removeClass('loading');
//         }
//     });
// }
$(function () {
    $.ajaxSettings.async = false;
    $("select[id*='search-select-']").each(function () {
        $(this).change(function () {
            $("#dropdown-btn-" + this.id.split("-")[2]).find(".dropdown-value").html(this.value == "-1" ? $(this).find("option:selected").attr("content") : this.value);
        })
    });
    //初始化搜索下拉框，完成根据userpick表的赋值
    selectUserBasic();

    initNationalDropdown("search-select-13");
    //根据下拉框选中的值，更新显示的span信息
    initDropdownSpan();
    //初始化标签
    initLabel();
    //初始化搜索用户
    initPickUser();
    //根据搜索条件搜索用户
    getSearchUser();
    $.ajaxSettings.async = true;

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
    if(dropdownBtnId!=undefined)
        $("#" + dropdownBtnId).html(result);
}

/*
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
*/

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
    $("#" + dropdownBtnId).html(result);

}

//页面加载完成时第一次获取用户择偶条件的基本信息
function selectUserBasic() {
    $.ajax({
        url: contextPath + "search/initUserPick",
        type: "GET",
        async:false,
        dataType: "JSON",
        success: function (data) {
            //性别
            //年龄
            $("#age-span").html(data.ageLow + "-" + data.ageHigh);
            initAgeDropdown("dropdown-btn-2",data.ageLow,data.ageHigh);
            //地区
            if (data.workplace == null||data.workplace.indexOf("-1")==0) {
                $("#workplace-span").html("地区不限");
                initWorkplaceDropdown("workplace-span", "province-select", "city-select", -1, -1);
            } else {
               $("#workplace-span").html(data.workplace);
                var arr = data.workplace.split("-");
                initWorkplaceDropdown("workplace-span", "province-select", "city-select", arr[0], arr.length > 1 ? arr[1] : -1);
            }
            //身高
            // if(data.heightLow==null&&data.heightHigh!=null){
            //     $("#height-span").html(data.heightHigh+"cm以下");
            // }else if (data.heightHigh==null&&data.heightLow!=null){
            //     $("#height-span").html(data.heightLow+"cm以上");
            // }else if(data.heightLow!=null&&data.heightHigh!=null){
            //     alert((data.heightLow!=null)+"..."+data.heightHigh);
            //     $("#height-span").html(data.heightLow+"-"+data.heightHigh);
            // }else if(data.heightLow==null&&data.heightHigh==null){
            //     $("#height-span").html("身高不限");
            // }
            initHeightDropdown("height-span",data.heightLow,data.heightHigh);
            //职业
            //婚史
            //月收入
            // if(data.salaryLow==null&&data.salaryHigh!=null){
            //     $("#salary-span").html(data.salaryHigh+"元以下");
            // }else if (data.salaryHigh==null&&data.salaryLow!=null){
            //     $("#salary-span").html(data.salaryLow+"元以上");
            // }else if(data.salaryLow!=null&&data.salaryHigh!=null){
            //     $("#salary-span").html(data.salaryLow+"-"+data.salaryHigh);
            // }else {
            //     $("#salary-span").html("月收入不限");
            // }
            initSalaryDropdown("salary-span",data.salaryLow,data.salaryHigh)
            //学历
            //籍贯
            if (data.birthplace == null || data.birthplace.trim()=="") {
               $("#birthplace-span").html("籍贯不限");
                initWorkplaceDropdown("birthplace-span", "province-select-bp", "city-select-bp", -1, -1);
            } else {
               $("#birthplace-span").html(data.birthplace);
                var arr = data.birthplace.split("-");
                initWorkplaceDropdown("birthplace-span", "province-select-bp", "city-select-bp", arr[0], arr.length > 1 ? arr[1] : -1);
            }
        }
    });
}

//页面加载时获取标签信息
function initLabel() {
    $.ajax({
        url:contextPath+"search/getLabel",
        type:"get",
        dataType:"json",
        success:function (data) {
            $("#hotLabel").empty();
            for (var i=0;i<data.length;i++){
                if(i%5==0)
                    $("#hotLabel").append("<a onclick='getLabelUser("+data[i].id+")'  style='margin-left: 5px' " +
                    "class='button button-small button-glow button-rounded button-highlight'>"+data[i].name+"</a>");
                if(i%5==1)
                    $("#hotLabel").append("<a  onclick='getLabelUser("+data[i].id+")' style='margin-left: 5px'" +
                    "class='button button-small button-glow button-rounded button-caution'>"+data[i].name+"</a>")
                if(i%5==2)
                    $("#hotLabel").append("<a  onclick='getLabelUser("+data[i].id+")' style='margin-left: 5px'" +
                    "class='button button-small button-glow button-rounded button-royal'>"+data[i].name+"</a>")
                if(i%5==3)
                    $("#hotLabel").append("<a  onclick='getLabelUser("+data[i].id+")' style='margin-left: 5px'" +
                    "class='button button-small button-glow button-rounded button-primary'>"+data[i].name+"</a>")
                if(i%5==4)
                    $("#hotLabel").append("<a onclick='getLabelUser("+data[i].id+")' style='margin-left: 5px'" +
                    "class='button button-small button-glow button-rounded button-inverse'>"+data[i].name+"</a>")
            }
        }
    })
}

function getLabelUser(label) {
    pageNum=1;
    pageLabel = label;
    initLabelUser();
}
//加载标签条件对应的用户
function initLabelUser() {
    var data={"labelId":pageLabel,"pageNum":pageNum}
    $.ajax({
        url:contextPath+"search/getLabelUser",
        type:"get",
        data:data,
        dataType:"json",
        success:function (data) {
            if (pageNum==1) {
                $(".paid_people").empty();
            }
            if(data.message=="error"){
                swal("提示","没有更多数据啦，扩大一下搜索条件试试？","error");
                return;
            }
            var list = data.pageInfo.list;
            for(i=0;i<list.length;i++){
                $(".paid_people").append("<div class='col-sm-4 paid_people-left'>" +
                    "                        <ul class='profile_item'>" +
                    "                                <li class='profile_item-img'>" +
                    "                                    <a href='view_profile.html'>" +
                    "                                        <img src=" + contextPath + "images/a7.jpg "+
                    "                                         class='img-responsive zoom-img' alt=''/>" +
                    "                                    </a>" +
                    "                                </li>" +
                    "                            <li>" +
                    "                                <div style='height: 22px;line-height: 22px'>" +
                    "                                <a href=''>"+list[i].nickname+"</a>" +
                    "                                <img src='" + contextPath + "images/vip" + (list[i].vip ? "" : "-grey") + ".png' class='flag'>"  +
                    "                                 <img src='" + contextPath + "images/star-0" + (list[i].star ? "" : "-grey") + ".png' class='flag'></div>" +
                    "                                <p>" +
                    "                                    <span>"+list[i].age+"</span>&nbsp;" +
                    "                                    <span>"+list[i].height+"</span>&nbsp;" +
                    "                                    <span>"+list[i].workplace+"</span>&nbsp;" +
                    "                                    <span>"+list[i].marryStatus+"</span>" +
                    "                                </p>" +
                    "                                <p>"+list[i].userDetail.signature+"</p>" +
                    "                            </li>" +
                    "                            <div class='clearfix'></div>" +
                    "                        </ul>" +
                    "                    </div>");
            }

            $("#load-more-div").empty();

            $("#load-more-div").append("<button id='load-more-user' class='col-md-12 btn'>加载更多</button>")
              $("#load-more-user").click(function () {
                    pageNum++;
                    //alert("labelClick");
                    initLabelUser();
            });

        }
    })
}

//页面加载时，根据user-pick搜索的用户
function initPickUser(){
    $.ajax({
        url:contextPath+"search/initSearchUser?pageNum="+pageNum,
        type:"POST",
        async : true,
        dataType:"JSON",
        success:function (data) {
            if (pageNum==1) {
                $(".paid_people").empty();
            }
            if(data.message=="error"){
                swal("提示","没有更多数据啦，扩大一下搜索条件试试？","error");
                return;
            }
            var list = data.pageInfo.list;
            for(i=0;i<list.length;i++){
                $(".paid_people").append("<div class='col-sm-4 paid_people-left'>" +
                    "                        <ul class='profile_item'>" +
                    "                                <li class='profile_item-img'>" +
                    "                                    <a href='view_profile.html'>" +
                    "                                        <img src=" + contextPath + "images/a7.jpg "+
                    "                                         class='img-responsive zoom-img' alt=''/>" +
                    "                                    </a>" +
                    "                                </li>" +
                    "                            <li>" +
                    "                                <div style='height: 22px;line-height: 22px'>" +
                    "                                <a href=''>"+list[i].nickname+"</a>" +
                    "                                 <img src='" + contextPath + "images/vip" + (list[i].vip ? '' : '-grey') + ".png' class='flag'>" +
                    "                                 <img src='" + contextPath + "images/star-0" + (list[i].star ? '' : '-grey') + ".png' class='flag'></div>" +
                    "                                <p>" +
                    "                                    <span>"+list[i].age+"</span>&nbsp;" +
                    "                                    <span>"+list[i].height+"</span>&nbsp;" +
                    "                                    <span>"+list[i].workplace+"</span>&nbsp;" +
                    "                                    <span>"+list[i].marryStatus+"</span>" +
                    "                                </p>" +
                    "                                <p>"+list[i].userDetail.signature+"</p>" +
                    "                            </li>" +
                    "                            <div class='clearfix'></div>" +
                    "                        </ul>" +
                    "                    </div>");
            }

            $("#load-more-div").empty();
            $("#load-more-div").append("<button id='load-more-user' class='col-md-12 btn'>加载更多</button>")
            $("#load-more-user").click(function () {
                pageNum++;
                initPickUser();
            });
        }
    });
}

//根据条件搜索用户
function searchUser(){
    //获取表单信息
    $.ajax({
        url:contextPath+"search/getSearchUser",
        type:"POST",
        async : true,
        data:$("#search-form").serialize()+"&&pageNum="+pageNum,
        dataType:"JSON",
        success:function (data) {
            if (pageNum==1) {
                $(".paid_people").empty();
            }
            if(data.message=="error"){
                swal("提示","没有更多数据啦，扩大一下搜索条件试试？","error");
                return;
            }
            var list = data.pageInfo.list;
            for(i=0;i<list.length;i++){
                $(".paid_people").append("<div class='col-sm-4 paid_people-left'>" +
                    "                        <ul class='profile_item'>" +
                    "                                <li class='profile_item-img'>" +
                    "                                    <a href='view_profile.html'>" +
                    "                                        <img src=" + contextPath + "images/a7.jpg "+
                    "                                         class='img-responsive zoom-img' alt=''/>" +
                    "                                    </a>" +
                    "                                </li>" +
                    "                            <li>" +
                    "                                <div style='height: 22px;line-height: 22px'>" +
                    "                                <a href=''>"+list[i].nickname+"</a>" +
                    "                                 <img src='" + contextPath + "images/vip" + (list[i].vip ? '' : '-grey') + ".png' class='flag'>" +
                    "                                 <img src='" + contextPath + "images/star-0" + (list[i].star ? '' : '-grey') + ".png' class='flag'></div>" +
                    "                                <p>" +
                    "                                    <span>"+list[i].age+"</span>&nbsp;" +
                    "                                    <span>"+list[i].height+"</span>&nbsp;" +
                    "                                    <span>"+list[i].workplace+"</span>&nbsp;" +
                    "                                    <span>"+list[i].marryStatus+"</span>" +
                    "                                </p>" +
                    "                                <p>内心独白："+list[i].userDetail.signature+"</p>" +
                    "                            </li>" +
                    "                            <div class='clearfix'></div>" +
                    "                        </ul>" +
                    "                    </div>");
            }

            $("#load-more-div").empty();
            $("#load-more-div").append("<button id='load-more-user' data-loading-text='Loading...' class='col-md-12 btn'>加载更多</button>")
            $("#load-more-user").click(function () {
                    pageNum++;
                    searchUser();
            });
        }
    });
}

//异步获取搜索条件对应的用户
function getSearchUser() {
    $("#search-form-submit").click(function () {
        //查询用户
        pageNum=1;
        searchUser();
    })
}


