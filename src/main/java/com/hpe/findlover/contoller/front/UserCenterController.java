package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.Dict;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.front.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/usercenter")
public class UserCenterController {
    @Autowired
    private UserService userService;
    @GetMapping("index")
    public String userCenter(){
        return "front/user_center";
    }

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

    @RequestMapping("selectEducationDict")
    @ResponseBody
    public Object selectEducationDict(){
        List<Dict> dicts = null;
        try {
            dicts = userService.selectEducationDict();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dicts;
    }

}
