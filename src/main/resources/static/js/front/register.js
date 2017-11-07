$(function () {
    niceBaseCofig();
    niceValidator();
    initWorkplaceDropdown();
    initWorkplaceDropdown(undefined,"province","city");
    initSingleHeightDropdown("height");
    initSingleSalaryDropdown("salary",-1);
    selectDict("education", "education");
});

//验证规则
function niceValidator() {
    $("#reform").validator({
        rules: {
            // 使用正则表达式定义规则
            mobile: [/^1[3-9]\d{9}$/, "请填写有效的手机号"],
            //password: [/^[\S]{3,16}$/, "请填写3-16位字符，不能包含空格"],
            nickname: [/^([\u4E00-\u9FA5]|\w{1,2}){2,6}$/, "昵称应为4-12位字符"]
        },
        fields: {
            'nickname': 'required;nickname',
            'birthday': 'required',
            'sexual': 'required',
            'province': 'required;',
            'city': 'required;',
            'marry_status': 'required;',
            'education': 'required;',
            'height': 'required;range(100~249)',
            'salary': 'required;',
            'tel': 'required;mobile',
            'email': 'required;email;remote[checkEmail, email]',
            'password': '密码:required;password',
            'confpassword': 'required;match(password)'
        },
        theme:'bootstrap',
        timely:2,
        stopOnError:true
        //错误信息显示位置
        /*target: function(elem){
            var $formitem = $(elem).closest('.form-item'),
                $msgbox = $formitem.find('span.msg-box');
            if (!$msgbox.length) {
                $msgbox = $('<span class="msg-box"></span>').appendTo($formitem);
            }
            return $msgbox;
        }*/
    });
}

