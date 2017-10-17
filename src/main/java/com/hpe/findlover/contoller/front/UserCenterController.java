package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserDetail;
import com.hpe.findlover.model.UserLife;
import com.hpe.findlover.service.front.UserDetailService;
import com.hpe.findlover.service.front.UserLifeService;
import com.hpe.findlover.service.front.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/usercenter")
public class UserCenterController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private UserLifeService userLifeService;

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
    public Object selectUserBasic(HttpServletRequest request){
        //UserBasic user = (UserBasic) request.getSession().getAttribute("user");
        UserBasic userBasic = null;
        try {
            userBasic = userService.selectByPrimaryKey(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userBasic;
    }

    /**
     * 查询出用户的详细资料
     * @return 返回Json格式
     */
    @RequestMapping("selectUserDetail")
    @ResponseBody
    public Object selectUserDetail(HttpServletRequest request){
        //UserBasic user = (UserBasic) request.getSession().getAttribute("user");
        UserDetail userDetail = null;
        try {
            userDetail = userDetailService.selectByPrimaryKey(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetail;
    }

    /**
     * 查询出用户的工作生活
     * @return 返回Json格式
     */
    @RequestMapping("selectUserLife")
    @ResponseBody
    public Object selectUserLife(HttpServletRequest request){
        //UserBasic user = (UserBasic) request.getSession().getAttribute("user");
        UserLife userLife = null;
        try {
            userLife = userLifeService.selectByPrimaryKey(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userLife;
    }

}
