$(function () {
    //点击一个月的立即开通VIP会员
    $("#modal1").click(function () {
        $("#VIPBuyDay").val("1")
        $("#VIPBuyMoney").val("￥52.10");
        $("#VIPBuyDayLover").val("1")
        $("#VIPBuyMoneyLover").val("52");
    });
    //点击三个月的立即开通VIP会员
    $("#modal2").click(function () {
        $("#VIPBuyDay").val("3")
        $("#VIPBuyMoney").val("￥138.52");
        $("#VIPBuyDayLover").val("3")
        $("#VIPBuyMoneyLover").val("138");
    });
    //点击一年的立即开通VIP会员
    $("#modal3").click(function () {
        $("#VIPBuyDay").val("12")
        $("#VIPBuyMoney").val("￥521.14");
        $("#VIPBuyDayLover").val("12")
        $("#VIPBuyMoneyLover").val("521");
    });
    //切换支付宝支付和牵手币支付VIP的div
    $("#loverPay").hide();
    $("#moneyPay").show();
    $("#VIPBuyWay").change(function () {
        if ($(this).val() == "牵手币") {
            $("#moneyPay").hide();
            $("#loverPay").show();
            $("#VIPSubmit").text("付款");

        } else {
            $("#loverPay").hide();
            $("#moneyPay").show();
            $("#VIPSubmit").text("我已完成付款");
        }
    });
    //切换支付宝支付和牵手币支付星级会员的div
    $("#moneyPayStar").show();
    $("#starBuyWay").change(function () {
        if ($(this).val() == "牵手币") {
            $("#moneyPayStar").hide();
            $("#starSubmit").text("付款");
            if ($("#starBuyDay").val() < 0) {
                $("#starBuyMoney").val('');
            } else {
                $("#starBuyMoney").val(($("#starBuyDay").val() * 52));
            }
        } else {
            $("#moneyPayStar").show();
            $("#starSubmit").text("我已完成付款");
            if ($("#starBuyDay").val() < 0) {
                $("#starBuyMoney").val('');
            }
            {
                $("#starBuyMoney").val("￥" + ($("#starBuyDay").val() * 52.1).toFixed(2));
            }
        }
    });
    //给星级按天选择的下拉框添加30个选项天数
    for (var i = 1; i < 31; i++) {
        $("#starBuyDay").append($("<option value='" + i + "'>" + i + "</option>"));
    }

    //星级会员选择天数后计算需付款金额
    $("#starBuyDay").change(function () {
        if ($("#starBuyWay").val() == "支付宝") {
            if ($(this).val() < 0) {
                $("#starBuyMoney").val('');
                return;
            }
            //计算购买金额
            $("#starBuyMoney").val("￥" + ($(this).val() * 52.1).toFixed(2));
        } else {
            if ($(this).val() < 0) {
                $("#starBuyMoney").val('');
                return;
            }
            //计算购买金额
            $("#starBuyMoney").val(($(this).val() * 52));
        }
    });

    //VIP会员选择月数后计算需付款金额
    $("#VIPBuyDay").change(function () {
        if ($(this).val() < 0) {
            $("#VIPBuyMoney").val('');
            return;
        }
        switch (parseInt($(this).val())) {
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
    //VIP会员选择月数后计算所需牵手币
    $("#VIPBuyDayLover").change(function () {
        if ($(this).val() < 0) {
            $("#VIPBuyMoneyLover").val('');
            return;
        }
        switch (parseInt($(this).val())) {
            case 1:
                $("#VIPBuyMoneyLover").val("52");
                break;
            case 3:
                $("#VIPBuyMoneyLover").val("138");
                break;
            case 12:
                $("#VIPBuyMoneyLover").val("521");
                break;
        }
    });
    $("#VIPSubmit").click(function () {
        if ($("#VIPBuyWay").val() == "牵手币") {
            var VIPBuyMoney = $("#VIPBuyMoneyLover").val();
        } else {
            VIPBuyMoney = $("#VIPBuyMoney").val();
        }
        $.ajax({
            url: contextPath + "vip",
            data: {
                vipBuyWay: $("#VIPBuyWay").val(),
                vipBuyDay: $("#VIPBuyDay").val(),
                vipBuyMoney: VIPBuyMoney
            },
            type: "post",
            dataType: "json",
            success: function (data, b, c) {
                swal(data[1], data[0], data[1]);
            },
            error: function (a, b1, c1) {
                swal(a[1], a[0], a[1]);
            }
        })
    });
    $("#starSubmit").click(function () {
        if ($("#starBuyWay").val() == "牵手币") {
            var starBuyMoney=$("#starBuyDay").val()*52;
        } else {
            starBuyMoney=$("#starBuyDay").val()*52.1;
        }
        $.ajax({
            url: contextPath + "star",
            data: {
                starBuyWay: $("#starBuyWay").val(),
                starBuyDay: $("#starBuyDay").val(),
                starBuyMoney:starBuyMoney
            },
            type: "post",
            dataType: "json",
            success: function (data, b, c) {
                swal(data[1], data[0], data[1]);
            },
            error: function (a, b1, c1) {
                swal(a[1], a[0], a[1]);
            }
        })
    });
});
