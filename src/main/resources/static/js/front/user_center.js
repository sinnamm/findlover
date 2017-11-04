$(function () {
    setPanel();
    initSetPhotoBtn();
    niceBaseCofig();
    initUserBasicValidator();
    initUserDetailValidator();
    initUserLifeValidator();
    initUserStatusValidator();
    initUserPickValidator();
    initRechargeValidator();
    initUserComfirmValidator();
    initSalaryDropdown(undefined, -1, -1);
    initHeightDropdown(undefined, -1, -1);
    initResetPwd();
    initResetAuth();
    initPhotoWall();
    initToolBar();
    initTabClick();
    initPhotoUpload();
    selectDict("education", "education");
    selectDict("education", "education1");
    selectDict("zodiac", "zodiac");
    selectDict("animal", "animal");
    selectDict("religion", "religion");
    selectDict("job", "job");
    selectDict("job", "job1");
    selectDict("job_time", "jobTime");
    selectDict("love_history", "loveHistory");
    selectDict("marry_time", "marryTime");
    selectDict("marry_status", "marryStatus");
    selectDict("parent_status", "parentStatus");
    selectDict("bro_and_sis", "broAndSis");
    selectDict("live_condition", "liveCondition");
    initNationalDropdown("nation");
    initSingleHeightDropdown("height");
    initCampus("graduation0", "graduation1", -1, -1);
    initWorkplaceDropdown(undefined, "workplace_province1", "workplace_city1", -1, -1);
    initWorkplaceDropdown(undefined, "birthplace_province1", "birthplace_city1", -1, -1);
    initWorkplaceDropdown(undefined, "birthplace_province", "birthplace_city", -1, -1);
    initWorkplaceDropdown(undefined, "work_province", "work_city", -1, -1);
    initSingleSalaryDropdown("salary", -1);
    initAgeDropdown(undefined);
    selectUserBasic1();
});
function setPanel() {
    var $panels = $("div[id$='Panel']");
    $panels.removeClass("active");
    if(type == null || type == ""){
        $("#basicPanel").addClass("active");
    }else{
        $("#"+type+"Panel").addClass("active");
    }

}
// 设置设为头像按钮的显示
function initSetPhotoBtn() {
    $("div[id^='photo-single']").each(function () {
        var id = this.id.split("-")[2];
        var $btn = $("button[id='photo-btn-" + id + "']");
        var $del = $("#photo-del-" + id);
        $(this).hover(function () {
            $btn.fadeIn(50);
        }, function () {
            $btn.fadeOut(50);
        });
        $del.click(function () {
            deletePhoto(this);
        });
        $btn.click(function () {
            setHeadPortrait(this);
        });
    })
}

//在相册中设置头像
function setHeadPortrait(btnInstance) {
    $.ajax({
        url: contextPath + "usercenter/photo/head/" + $(btnInstance).attr("id").split('-')[2],
        type: "PUT",
        dataType: "TEXT",
        async:false,
        success: function (data) {
            if (data== "true") {
                location.replace(contextPath+"usercenter?type=photo");
                // swal("温馨提示", "设置成功！", "success");
                //$("#basicPanel").removeClass("active");
                //$("#photoPanel").addClass("active");
            } else {
                swal("温馨提示", "设置失败！", "error");
            }
        }
    });

}

//单个删除相册中的图片
function deletePhoto(delBtnInstance) {
    //找到他父节点div
    if(confirm("您确定删除此照片吗？")) {
        $.ajax({
            url: contextPath + "usercenter/photo/" + $(delBtnInstance).attr("id").split("-")[2],
            type: "DELETE",
            dataType: "TEXT",
            success: function (data) {
                if (data == "true") {
                    var $delId = $(delBtnInstance).closest("div");
                    //隐藏不可上传按钮
                    $("#max-upload").hide();
                    //显示可上传按钮
                    $("#min-upload").show();
                    $delId.remove();
                    swal("温馨提示", "删除成功！", "success");
                } else {
                    swal("温馨提示", "删除失败！", "error");
                }
            }
        });
    }

}

//照片上传
function initPhotoUpload() {
    //照片批量上传
    $("#file-1").fileinput({
        language: 'zh',
        theme: 'fa',
        uploadUrl: contextPath + "usercenter/upload", // you must set a valid URL here else you will get an error
        allowedFileExtensions: ['jpg', 'png', 'gif'],
        overwriteInitial: false,
        maxFileSize: 1024*5,
        //maxFilesNum: 3,
        minFileCount: 1,
        maxFileCount: 8,
        enctype: 'multipart/form-data',
        allowedFileTypes: ['image'],
        uploadAsync: false, //同步上传
        slugCallback: function (filename) {
            return filename.replace('(', '_').replace(']', '_').replace("-", "_");
        }
    });
    //上传成功回调,没上传成功一次就回调一次 filepreupload fileuploaded
   /* $("#file-1").on("filepreupload", function (event, data, previewId, index) {
        $("#myModal").modal("hide");
        if(data.response.result=="true"){
            alert(JSON.stringify(data.response));
            alert(data.response.result);
            swal("温馨提示","上传成功！","success");
            return true;
        }else {
            alert(JSON.stringify(data.response));
            alert(data.response.result);
            swal("温馨提示","不能超过八张","error");
            return false;
        }

    });
*/
    //同步上传成功回调
    $('#file-1').on('filebatchuploadsuccess', function(event, data, previewId, index) {
        $("#myModal").modal("hide");
        if(data.response.result=="true"){
            var photos = data.response.photos;
            // alert(JSON.stringify(data.response));
            // alert(photos);
            for(var i=0;i<photos.length;i++){
                var photoDiv="<div class='photo-single' id='photo-single-"+photos[i].id+"'>" +
                    "             <a href='"+contextPath+"file?path="+photos[i].photo+"'" +
                    "                data-toggle='lightbox' data-gallery='sigma-gallery'" +
                    "                data-title='Image Title 01'>" +
                    "                 <img src='"+contextPath+"file?path="+photos[i].photo+"'" +
                    "                      alt='第1张'" +
                    "                      class='img-fluid sigmapad'>" +
                    "                 <a id='photo-del-"+photos[i].id+"' onclick='deletePhoto(this)' class='photo-del'><i" +
                    "                         class='fa fa-times'></i></a>" +
                    "             </a>" +
                    "             <div class='submit inline-block'>" +
                    "                 <button id='photo-btn-"+photos[i].id+"' class='hvr-wobble-vertical set-photo-btn'>设为头像</button>" +
                    "             </div>" +
                    "         </div>";

                $("div[class='photo-single']:last").after($(photoDiv));
                //成功之后给所用div再次添加响应事件
                initSetPhotoBtn();
            }
            //这里是判断如果图片达到8张了，就把图片上传按钮禁用
            if($("div[id^='photo-single']").size()<8){
                //隐藏不可上传按钮
                $("#max-upload").hide();
                //显示可上传按钮
                $("#min-upload").show();
            }else {
                //显示不可上传按钮
                $("#max-upload").show();
                //隐藏可上传按钮
                $("#min-upload").hide();
            }
            swal("温馨提示","上传成功！","success");
        }else {
            // alert(JSON.stringify(data.response));
            // alert(data.response.result);
            swal("温馨提示","不能超过八张","error");
        }
    });

    //头像上传
    $("#img").fileinput({
        language: 'zh',
        theme: 'fa',
        uploadUrl: contextPath + "usercenter/photo", // you must set a valid URL here else you will get an error
        allowedFileExtensions: ['jpg', 'png', 'gif'],
        overwriteInitial: false,
        maxFileSize: 1024*5,
        maxFilesNum: 1,
        minFileCount: 1,
        maxFileCount: 1,
        enctype: 'multipart/form-data',
        allowedFileTypes: ['image'],
        //uploadAsync: false, //同步上传
        slugCallback: function (filename) {
            return filename.replace('(', '_').replace(']', '_').replace("-", "_");
        }
    });
    //头像异步上传成功回调
    $("#img").on("fileuploaded", function (event, data, previewId, index) {
        $("#myImg").modal("hide");
        if(data.response.result){
            var photo = data.response.photo;
            $("#head-sculpture").attr("src",contextPath+"file?path="+photo.photo);
            swal("温馨提示","上传成功！","success");
            var photoDiv="<div class='photo-single' id='photo-single-"+photo.id+"'>" +
                "             <a href='"+contextPath+"file?path="+photo.photo+"'" +
                "                data-toggle='lightbox' data-gallery='sigma-gallery'" +
                "                data-title='Image Title 01'>" +
                "                 <img src='"+contextPath+"file?path="+photo.photo+"'" +
                "                      alt='第1张'" +
                "                      class='img-fluid sigmapad'>" +
                "             </a>" +
                "             <div class='submit inline-block'>" +
                "                 <button id='photo-btn-"+photo.id+"' class='hvr-wobble-vertical set-photo-btn'>当前头像</button>" +
                "             </div>" +
                "         </div>";
            $("div[class='photo-single']:first").remove();
            $("#photoContent").prepend($(photoDiv));
            initSetPhotoBtn();
        }else{
            swal("温馨提示","上传失败！","error");
        }
    });
}

//重置密码验证
function initResetPwd() {
    //alert($("#form-reset-pwd").serialize());
    $("#form-reset-pwd").validator({
        fields: {
            'newpwd': '密码:required;password',
            'password': 'required;match(newpwd)'
        },
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            /*var me = this;
            //提交表单之前，hold住表单，防止表单重复提交
            me.holdSubmit();*/
            resetUser(form, "basic");
        }
    });
}

//修改查看权限
function initResetAuth() {
    $("#form-reset-auth").validator({
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            resetUser(form, "basic");
        }
    });
}

//充值
function initRechargeValidator() {
    $("#recharge").validator({
        fields: {
            'cost': 'required;digits'
        },
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            var cost = $(form).find(":input[name='cost']").val();
            $.ajax({
                url: contextPath + "usercenter/pay",
                data: {"cost": cost},
                type: "PUT",
                dataType: "TEXT",
                success: function (data) {
                    if (data == "true") {
                        swal("温馨提示", "充值成功", "success");
                    } else {
                        swal("温馨提示", "充值失败", "error");
                    }
                }
            });
        }
    });
}

//用户基本信息验证
function initUserBasicValidator() {
    $("#form-user-basic").validator({
        rules: {
            // 使用正则表达式定义规则
            mobile: [/^1[3-9]\d{9}$/, "请填写有效的手机号"],
            password: [/^[\S]{3,16}$/, "请填写3-16位字符，不能包含空格"],
            nickname: [/^([\u4E00-\u9FA5]|\w{1,2}){2,6}$/, "昵称应为4-12位字符"]
        },
        fields: {
            'nickname': 'required;nickname',
            'tel': 'required;mobile',
            'email': 'required;email',
            'salary': 'required',
            'sexual': 'required',
            'education': 'required',
            'work_province': 'required',
            'work_city': 'required',
            'liveCondition': 'required'
        },
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            resetUser(form, "basic");
        }
    });
}

//用户详细信息验证
function initUserDetailValidator() {
    $("#form-user-detail").validator({
        fields: {
            'weight': 'digits;range(40~120)',
            'hobby': 'length(~140)',
            'signature': 'length(~140)'
        },
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            resetUser(form, "detail");
        }
    });
}
//用户详情资料验证
function initUserDetailValidator() {
    $("#form-user-detail").validator({
        fields: {
            'weight': 'digits;range(40~120)',
            'hobby': 'length(~140)',
            'signature': 'length(~140)'
        },
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            resetUser(form, "detail");
        }
    });
}

//用户工作生活验证
function initUserLifeValidator() {
    $("#form-user-life").validator({
        fields: {
            'character': 'length(~140)',
            'jobBrief': 'length(~140)'
        },
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            resetUser(form, "life");
        }
    });
}
//用户婚姻观验证
function initUserStatusValidator() {
    $("#form-user-status").validator({
        fields: {
            'familyBrief': 'length(~140)'
        },
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            resetUser(form, "status");
        }
    });
}

//用户身份认证
function initUserComfirmValidator() {
    $("#form-user-comfirm").validator({
        rules: {
            chinese: [/^[\u4E00-\u9FA5\w·]+$/, "名字不能包含特殊字符"]
        },
        fields: {
            'realname': 'chinese;length(2~20)',
            'cardnumber': 'IDcard'
        },
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            var me = this;
            //提交表单之前，hold住表单，防止表单重复提交
            me.holdSubmit();
            resetUser(form, "confirm");
        }
    });
}




//用户择偶条件验证
function initUserPickValidator() {
    $("#form-user-pick").validator({
        theme: 'bootstrap',
        timely: 2,
        stopOnError: true,
        valid: function (form) {
            resetUser(form, "pick");
        }
    });
}

//修改或添加用户通用方法
function resetUser(form, exturl) {
    $.ajax({
        url: contextPath + "usercenter/" + exturl,
        data: $(form).serialize(),
        type: "PUT",
        dataType: "TEXT",
        success: function (data) {
            if (data == "true") {
                swal("温馨提示", "修改成功", "success");
            } else {
                swal("温馨提示", "修改失败", "error");
            }
        }
    });
}

//初始化点击事件
function initTabClick() {
    $("#basic-tab").click(function () {
        selectUserBasic1();
    });
    $("#more-tab").click(function () {
        selectUserDetail();
    });
    $("#work-tab").click(function () {
        selectUserLife();
    });
    $("#marry-tab").click(function () {
        selectUserStatus();
    });
    $("#other-tab").click(function () {
        selectUserPick();
    });
    //支付方式下拉框
    $("#weChatpayImg").hide();
    $("#payway").change(function () {
        var text = $(this).val();
        //alert(text);
        if ("支付宝" == text) {
            $("#weChatpayImg").hide();
            $("#payImg").show();
        } else {
            $("#weChatpayImg").show();
            $("#payImg").hide();
        }
    });

    $("#cost").change(function () {
        var text = $(this).val();
        if (text != undefined && text != '') {
            $("#needpayspan").text("￥ " + parseInt(text));
        }
    });
}


// 获取学校信息并回填用户数据                         山东         青岛科技大学
function initCampus(provinceSelId, campusSelId, province_value, campus_value) {
    var $provinceSel = $("#" + provinceSelId);
    var $campusSel = $("#" + campusSelId);
    $provinceSel.find("option:gt(0)").remove();
    $campusSel.find("option:gt(0)").remove();
    var tempProvice = undefined;
    $.getJSON(contextPath + "json/campus.json", function (data) {
        for (var x = 0; x < data.length; x++) {
            for (var province in data[x]) {
                $provinceSel.append($('<option value="' + province + '" ' + (province === province_value ? 'selected' : '') + '>' + province + '</option>'));
                if (province == province_value) {
                    for (var y = 0; y < data[x][province].length; y++) {
                        $campusSel.append($('<option value="' + data[x][province][y] + '" ' + (data[x][province][y] === campus_value ? 'selected' : '') + '>' + data[x][province][y] + '</option>'));
                    }
                }
            }
        }
    });

    $provinceSel.change(function () {
        $.getJSON(contextPath + "json/campus.json", function (data) {
            var provinceSelVal = $provinceSel.val();
            $campusSel.find("option:gt(0)").remove();
            for (var x = 0; x < data.length; x++) {
                for (var province in data[x]) {
                    if (province == provinceSelVal) {
                        for (var y = 0; y < data[x][province].length; y++) {
                            $campusSel.append($('<option value="' + data[x][province][y] + '" ' + (data[x][province][y] === campus_value ? 'selected' : '') + '>' + data[x][province][y] + '</option>'));
                        }
                    }
                }
            }
        });
    });

}

//页面加载完成时第一次获取用户基本信息
function selectUserBasic1() {
    $.ajax({
        url: contextPath + "usercenter/userbasic",
        type: "GET",
        dataType: "JSON",
        success: function (data, a, b) {
            var userfixed = "您是一位" + data.sex + "士，您的出生日期为"
                + new Date(data.birthday).format("yyyy-MM-dd") + "，"
                + data.marryStatus
                + "，" + data.height + "M";
            $("#userfixed").text(userfixed);
            loadDataByKeyArr(data,
                ["nickname", "tel", "email", "height", "education",
                    "liveCondition"]);
            /* $("#nickname").val(data.nickname);
             $("#tel").val(data.tel);
             $("#email").val(data.email);*/
            if (data.salary != null && data.salary != '') {
                initSingleSalaryDropdown("salary", data.salary);
            }
            /*if (data.height != null && data.height != '') {
                $("#height").val(data.height);
            }*/
            $("#sexual").val(data.sexual == data.sex ? "男" : "女");
            // $("#education").val(data.education);
            if (data.workplace != null && data.workplace != '') {
                var arr = data.workplace.split("-");
                initWorkplaceDropdown(undefined, "work_province", "work_city", arr[0], arr.length > 0 ? arr[1] : -1);
            }
            //$("#house").val(data.liveCondition);
        }
    });
}

//点击用户详细时，获取详情信息
function selectUserDetail() {
    $.ajax({
        url: contextPath + "usercenter/userdetail",
        type: "GET",
        dataType: "JSON",
        success: function (data, a, b) {
            loadDataByKeyArr(data,
                ["realname", "weight", "cardnumber", "zodiac",
                    "nation", "animal", "religion",
                    "hobby", "signature"]);
            /*$("#realname").val(data.realname);
            $("#weight").val(data.weight);
            $("#cardnumber").val(data.cardnumber);
            if (data.zodiac != null && data.zodiac != '') {
                $("#zodiac").val(data.zodiac);
            }
            if (data.nation != null && data.nation != '') {
                $("#nation").val(data.nation);
            }
            if (data.animal != null && data.animal != '') {
                $("#animal").val(data.animal);
            }
            if (data.religion != null && data.religion != '') {
                $("#religion").val(data.religion);
            }*/
            if (data.birthplace != null && data.birthplace != '') {
                var arr = data.birthplace.split("-");
                initCampus("graduation0", "graduation1", arr[0], arr[1]);
            }
            if (data.birthplace != null && data.birthplace != '') {
                var arr = data.birthplace.split("-");
                initWorkplaceDropdown(undefined, "birthplace_province", "birthplace_city", arr[0], arr.length > 0 ? arr[1] : -1);
            }
            /*$("#hobby").val(data.hobby);
            $("#signature").val(data.signature);*/
        }
    });
}

//点击工作生活时，获取工作生活信息
function selectUserLife() {
    $.ajax({
        url: contextPath + "usercenter/userlife",
        type: "GET",
        dataType: "JSON",
        success: function (data, a, b) {
            loadDataByKeyArr(data, ["smoke", "drink", "car", "job", "jobTime", "character", "jobBrief"]);
            // $("#smoke").val(data.smoke);
            // $("#drink").val(data.drink);
            // if (data.car != null) {
            //     $("#car").val(data.car);
            // }
            // if (data.job != null && data.job != '') {
            //     $("#job").val(data.job);
            // }
            //
            // if (data.jobTime != null && data.jobTime != '') {
            //     $("#jobTime").val(data.jobTime);
            // }
            // $("#character").val(data.character);
            // $("#jobBrief").val(data.jobBrief);
        }
    });
}

//点击婚姻观时，获取婚姻观信息
function selectUserStatus() {
    $.ajax({
        url: contextPath + "usercenter/userstatus",
        type: "GET",
        dataType: "JSON",
        success: function (data, a, b) {
            if (data.loveHistory != null && data.loveHistory != '') {
                $("#loveHistory").val(data.loveHistory);
            }
            if (data.marryTime != null && data.marryTime != '') {
                $("#marryTime").val(data.marryTime);
            }
            if (data.ldr != null && data.ldr != '') {
                $("#ldr").val(data.ldr);
            }
            if (data.parentStatus != null && data.parentStatus != '') {
                $("#parentStatus").val(data.parentStatus);
            }
            if (data.broAndSis != null && data.broAndSis != '') {
                $("#broAndSis").val(data.broAndSis);
            }
            $("#familyBrief").val(data.familyBrief);
        }
    });
}

//点击择偶条件时，获取择偶条件信息
function selectUserPick() {
    $.ajax({
        url: contextPath + "usercenter/userpick",
        type: "GET",
        dataType: "JSON",
        success: function (data, a, b) {
            loadDataByJson(data, {
                "sex": "sex",
                "marryStatus": "marryStatus",
                "job1": "job",
                "smoke1": "smoke",
                "drink1": "drink",
                "age-select-low": "ageLow",
                "age-select-high": "ageHigh",
                "height-select-low": "heightLow",
                "height-select-high": "heightHigh",
                "education1": "education"
            });
            // if (data.sex != null && data.sex != '') {//择偶性别
            //     $("#sex").val(data.sex);
            // }
            // if (data.marryStatus != null && data.marryStatus != '') {//择偶婚姻状况
            //     $("#marryStatus").val(data.marryStatus);
            // }
            // if (data.job != null && data.job != '') {//择偶职业类别
            //     $("#job1").val(data.job);
            // }
            // if (data.smoke != null && data.smoke != '') {//择偶是否抽烟
            //     $("#smoke1").val(data.smoke);
            // }
            // if (data.drink != null && data.drink != '') {//择偶是否喝酒
            //     $("#drink1").val(data.drink);
            // }
            // if (data.ageLow != null && data.ageLow != '') {//择偶年龄
            //     $("#age-select-low").val(data.ageLow);
            // }
            // if (data.ageHigh != null && data.ageHigh != '') {//择偶年龄
            //     $("#age-select-high").val(data.ageHigh);
            // }
            if (data.salaryLow != null && data.salaryLow != '' && data.salaryHigh != null && data.salaryHigh != '') {//择偶月收入
                initSalaryDropdown(undefined, data.salaryLow, data.salaryHigh);
            }
            // if (data.heightLow != null && data.heightLow != '') {//择偶身高
            //     $("#height-select-low").val(data.heightLow);
            // }
            // if (data.heightHigh != null && data.heightHigh != '') {//择偶身高
            //     $("#height-select-high").val(data.heightHigh);
            // }
            // if (data.education != null && data.education != '') {//择偶学历
            //     $("#education1").val(data.education);
            // }
            if (data.workplace != null && data.workplace != '') {//择偶工作地区
                var arr = data.workplace.split("-");
                initWorkplaceDropdown(undefined, "workplace_province1", "workplace_city1", arr[0], arr.length > 0 ? arr[1] : -1);
            }
            if (data.birthplace != null && data.birthplace != '') {//择偶所在地区
                var arr = data.birthplace.split("-");
                initWorkplaceDropdown(undefined, "birthplace_province1", "birthplace_city1", arr[0], arr.length > 0 ? arr[1] : -1);
            }
        }
    });
}

function loadDataByKeyArr(data, keyArr) {
    for (var x = 0; x < keyArr.length; x++) {
        var key = keyArr[x];
        if (data[key] != null) {
            $("#" + keyArr[x]).val(data[key]);
        }
    }
}

function loadDataByJson(data, json) {
    for (var key in json) {
        if (data[json[key]] != null) {
            $("#" + key).val(data[json[key]]);
        }
    }
}


function initPhotoWall() {
    $(document).delegate('*[data-toggle="lightbox"]', 'click', function (event) {
        event.preventDefault();
        return $(this).ekkoLightbox({
            always_show_close: true
        });
    });
}