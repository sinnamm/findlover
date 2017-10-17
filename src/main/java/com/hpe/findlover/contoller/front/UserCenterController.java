package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.front.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/usercenter")
public class UserCenterController {


    @Autowired
    private UserService userService;

    /**
     * 跳转到用户中心界面
     */
    @GetMapping("index")
    public String userCenter(){
        return "front/user_center";
    }

    /**
     * 查询出用户的基本资料
     * @return 返回Json格式
     */
    @RequestMapping("selectUserBasic")
    @ResponseBody
    public Object selectUserBasic(){
        UserBasic userBasic = null;
        try {
            userBasic = userService.selectByPrimaryKey(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userBasic;
    }

}
