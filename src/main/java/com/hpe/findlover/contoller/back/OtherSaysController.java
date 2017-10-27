package com.hpe.findlover.contoller.back;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.util.locale.LocaleMatcher;

/**
 * @author sinnamm
 * @Date Create in  2017/10/27.
 */

@Controller
@RequestMapping("admin/other_says")
public class OtherSaysController {


    private Logger logger = LogManager.getLogger(UserControllerBack.class);

    @GetMapping("/user_message")
    public String userMessage(){
        logger.info("用户动态管理");
        return "back/othersays/user_message";
    }
}
