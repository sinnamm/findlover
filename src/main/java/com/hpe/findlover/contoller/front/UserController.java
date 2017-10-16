package com.hpe.findlover.contoller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	@GetMapping("index")
	public String index() {
		return "front/index";
	}

	@GetMapping("search")
	public String search() {
		return "front/search";
	}

	@GetMapping("vip")
	public String vip(){
		return "front/vip";
	}
}
