$(function () {
    //点击一个月的立即开通VIP会员
    $("#modal1").click(function () {
        $("#VIPBuyDay").val("1")
        $("#VIPBuyMoney").val("￥52.10");
    });
    //点击三个月的立即开通VIP会员
    $("#modal2").click(function () {
        $("#VIPBuyDay").val("3")
        $("#VIPBuyMoney").val("￥138.52");
    });
    //点击一年的立即开通VIP会员
    $("#modal3").click(function () {
        $("#VIPBuyDay").val("12")
        $("#VIPBuyMoney").val("￥521.14");
    });

    //给星级按天选择的下拉框添加30个选项天数
    for(var i =1;i<31;i++) {
        $("#starBuyDay").append($("<option value='" + i + "'>" + i + "</option>"));
    }

    //星级会员选择天数后计算需付款金额
    $("#starBuyDay").change(function () {
        if ($(this).val()<0) {
            $("#starBuyMoney").val('');
            return;
        }
        //计算购买金额
        $("#starBuyMoney").val("￥"+($(this).val() * 52.1).toFixed(2));
    });

    //VIP会员选择月数后计算需付款金额
    $("#VIPBuyDay").change(function () {
        if ($(this).val()<0) {
            $("#VIPBuyMoney").val('');
            return;
        }
        switch (parseInt($(this).val())){
            case 1:
                $("#VIPBuyMoney").val("￥52.10");
                break;
            case 3:
                $("#VIPBuyMoney").val("￥138.52");
                break;
            case 12:
                $("#VIPBuyMoney").val("￥521.14");
                break;
        }
    });
});