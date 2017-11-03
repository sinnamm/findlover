package com.hpe.findlover.contoller.back;

import com.hpe.findlover.model.Admin;
import com.hpe.findlover.service.AdminService;
import com.hpe.findlover.token.CustomToken;
import com.hpe.findlover.util.Identity;
import com.hpe.findlover.util.ShiroHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@GetMapping(value = {"", "index"})
	public String index() {
		return "back/index";
	}

	@GetMapping("login")
	public String login() {
		return "back/login";
	}

	@PostMapping("login")
	public String login(String username, String password, HttpSession session) {
		ShiroHelper.flushSession();
		CustomToken token = new CustomToken(username, password, Identity.ADMIN);
		SecurityUtils.getSubject().login(token);
		if (SecurityUtils.getSubject().isAuthenticated()) {
			Admin admin = new Admin();
			admin.setUsername(token.getUsername());
			session.setAttribute("admin", adminService.selectOne(admin));
			return "redirect:index";
		} else {
			return "redirect:login";
		}
	}

	@GetMapping("left")
	public String left() {
		return "back/common/left";
	}
}
