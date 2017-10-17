package com.hpe.findlover.contoller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */
@Controller
@RequestMapping("success_story")
public class SuccessStoryController {

    @GetMapping
    public String success_story(){
        return "front/success_story";
    }

    @GetMapping("/success_story_info")
    public String success_story_info(){
        return "front/success_story_info";
    }
}
