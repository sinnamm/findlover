$(function () {
    initToolBar();
    selectUserBasic();
    selectEducationDict();
    initSingleHeightDropdown("height");
    initWorkplaceDropdown();
    initTabClick();
    initWorkplaceDropdown(undefined,"work_province","work_city");
    initWorkplaceDropdown(undefined,"province","city");
});

//获取Education列表对象
function selectEducationDict() {
    $.ajax({
        url:contextPath+"dicts/education",
        type:"GET",
        dataType:"JSON",
        success:function (data,a,b) {
            $("#education").empty();
            $("#education").append($("<option value=\"请选择\">请选择</option>"));
            $(data).each(function (index, element) {
                $("#education").append($("<option value=\""+element.value+"\">"+element.value+"</option>"));
            });
        }
    });
}

//页面加载完成时第一次获取用户基本信息
function selectUserBasic(){
    $.ajax({
        url:contextPath+"usercenter/selectUserBasic",
        type:"GET",
        dataType:"JSON",
        success:function (data,a,b) {
            var userfixed ="您是一位"+data.sex+"士，您的出生日期为"
                +getMyDate(data.birthday)+"，"
                +data.marryStatus
                +"，"+data.height+"M";
            $("#userfixed").text(userfixed);
            $("#nickname").val(data.nickname);
            $("#tel").val(data.tel);
            $("#email").val(data.email);
            $("#salary").val(data.salary);
            $("#height").val(data.height);
            $("#sexual").val(data.sexual==data.sex?"男":"女");
            $("#education").val(data.education);
        }
    });
}

//初始化点击事件
function initTabClick() {
    $("#basic-tab").click(function () {
        selectUserBasic();
    });
}

//获得年月日
function getMyDate(str){
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        // oHour = oDate.getHours(),
        // oMin = oDate.getMinutes(),
        // oSen = oDate.getSeconds(),
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay);//最后拼接时间
    return oTime;
};

//补0操作
function getzf(num){
    if(parseInt(num) < 10){
        num = '0'+num;
    }
    return num;
}