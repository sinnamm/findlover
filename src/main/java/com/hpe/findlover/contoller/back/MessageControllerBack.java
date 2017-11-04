package com.hpe.findlover.contoller.back;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiresRoles("stage")
@RequestMapping("admin/message")
public class MessageControllerBack {
	@GetMapping
	public String index(){

		return null;
	}
}
