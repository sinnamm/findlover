package com.hpe.findlover.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ErrorPageController  {

    @RequestMapping("404")
    public String toPage(){
        return "404";
    }

    @RequestMapping("403")
    public String notAuthorized(){
        return "403";
    }

}