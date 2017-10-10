package com.dt.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeguihaoCotroller {

    @RequestMapping("login")
    public String login(){
        return "hello";
    }
}
