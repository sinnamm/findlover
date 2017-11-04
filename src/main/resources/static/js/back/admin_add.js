$(function () {
    initFormValidator();
});
function initFormValidator() {
    $("#admin-reg-form").validator({
        rules:{
          username:[/^\w{3,12}$/,"用户名必须由3-12位的字母、数字或下划线组成！"]
        },
        fields: {
            username: "required;username;remote(GET:"+contextPath+"admin/admins/check)",
            password:"required;length(3~16)",
            rid:"checked(1~)",
            confpassword:"required;match(password)"
        }
    })
}