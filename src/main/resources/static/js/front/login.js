$(function () {
    niceBaseCofig();
    niceValidator();
});

//验证规则
function niceValidator() {
    $("#login_form").validator({
        rules:{
            password: [/^[\S]{3,16}$/, "请填写3-16位字符，不能包含空格"],
        },
        fields: {
            'email': 'required;email',
            'password': '密码:required;password',
        },
        theme:'bootstrap',
        timely:2,
        stopOnError:true
    });
}

//基础配置
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