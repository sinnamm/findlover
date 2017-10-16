package com.hpe.findlover.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	@GetMapping("search")
	public String search(){
		return "front/search";
	}
	@GetMapping("index")
	public String index(){
		return "front/index";
	}
	@GetMapping("vip")
	public String vip(){
		return "front/vip";
	}
}
