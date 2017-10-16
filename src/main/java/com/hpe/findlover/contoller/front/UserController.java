package com.hpe.findlover.contoller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author sinnamm
 * @Date 2017-10-16
 * @Description:
 */
@Controller
public class UserController
{
    @GetMapping("index")
    public String index(){
        return "front/index";
    }
    @GetMapping("search")
    public String search(){
        return "front/search";
    }
    @GetMapping("vip")
    public String vip(){
        return "front/vip";
    }
    @GetMapping("success_story")
    public String success_story(){
        return "front/success_story";
    }
    @GetMapping("otherSays")
    public String otherSays(){
        return "front/otherSays";
    }
    @GetMapping("letter")
    public String letter(){
        return "front/letter";
    }
    @GetMapping("care")
    public String care(){
        return "front/care";
    }
    @GetMapping("visiteTrace")
    public String visiteTrace(){
        return "front/visiteTrace";
    }
    @GetMapping("notice")
    public String notice(){
        return "front/notice";
    }
    @GetMapping("user_center")
    public String user_center(){
        return "front/user_center";
    }

}