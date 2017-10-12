//初始化地区下拉列表
function initWorkplaceDropdown(spanId,provinceId,cityId) {
    $.getJSON(contextPath + "json/cities.json", function (data) {
        for (var x = 0; x < data.length; x++) {
            $("#" + provinceId).append($("<option value='" + data[x].name + "'>" + data[x].name + "</option>"));
        }
    });
    $("#" + provinceId).change(function () {
        $("#" + cityId).find(":gt('0')").remove();
        $.getJSON(contextPath + "json/cities.json", function (data) {
            for (var x = 0; x < data.length; x++) {
                if (data[x].name == $("#" + provinceId).val()) {
                    for (var y = 1; y < data[x].sub.length; y++) {
                        $("#" + cityId).append($("<option value='" + data[x].sub[y].name + "'>" + data[x].sub[y].name + "</option>"));
                    }
                    break;
                }
            }
        });
        updateWorkplaceDropdown(spanId,provinceId,cityId);
    });
    $("#" + cityId).change(function () {
        updateWorkplaceDropdown(spanId,provinceId,cityId);
    });
}
// 动态更新地区下拉列表
function updateWorkplaceDropdown(spanId,provinceId,cityId) {
    var provinceSel = $("#" + provinceId);
    var citySel = $("#" + cityId);
    var result;
    if(provinceSel.val() == "-1"){
        result = "地区不限";
    }else{
        if(citySel.val() == "-1")
            result = provinceSel.val();
        else
            result = provinceSel.val() + "&nbsp;" + citySel.val();
    }
    $("#" + spanId).html(result);
}