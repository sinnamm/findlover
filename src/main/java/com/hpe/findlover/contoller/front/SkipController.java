package com.hpe.findlover.contoller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SkipController {
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
	/*
	* 退出系统
	* */
	@GetMapping("loginout")
	public String loginout(){
		return "";
	}
	@GetMapping("login")
	public String login(){
		return "front/login";
	}
	@GetMapping("otherSaysDetail")
	public String otherSaysDetail(){
		return "front/otherSaysDetail";
	}
	@GetMapping("register")
	public String register(){
		return "front/register";
	}
	@GetMapping("success_story_info")
	public String success_story_info(){
		return "front/success_story_info";
	}
	@GetMapping("view_profile")
	public String view_profile(){
		return "front/view_profile";
	}
}
