package com.hpe.findlover.contoller.back;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Admin;
import com.hpe.findlover.service.AdminRoleService;
import com.hpe.findlover.service.AdminService;
import com.hpe.findlover.service.RoleService;
import com.hpe.findlover.token.CustomToken;
import com.hpe.findlover.util.Identity;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.findlover.util.SessionUtils;
import com.hpe.findlover.util.ShiroHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Date;

@Controller
@RequestMapping("admin")
public class AdminController {
	private Logger logger = LogManager.getLogger(AdminController.class);
	private final AdminService adminService;
	private final RoleService roleService;
	private final AdminRoleService adminRoleService;

	@Autowired
	public AdminController(AdminService adminService, RoleService roleService, AdminRoleService adminRoleService) {
		this.adminService = adminService;
		this.roleService = roleService;
		this.adminRoleService = adminRoleService;
	}

	@GetMapping(value = {"", "index"})
	public String index() {
		return "back/index";
	}

	@GetMapping("logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		SecurityUtils.getSubject().getSession().removeAttribute("admin");
		return "redirect:login";
	}

	@GetMapping("login")
	public String login() {
		return "back/login";
	}

	@PostMapping("login")
	public String login(String username, String password, HttpSession session, RedirectAttributes redirectAttributes) {
		ShiroHelper.flushSession();
		CustomToken token = new CustomToken(username, password, Identity.ADMIN);
		try {
			SecurityUtils.getSubject().login(token);
			if (SecurityUtils.getSubject().isAuthenticated()) {
				Admin admin = new Admin();
				admin.setUsername(token.getUsername());
				admin = adminService.selectOne(admin);
				admin.setLastLogin(new Date());
				adminService.updateByPrimaryKey(admin);
				session.setAttribute("admin", admin);
				return "redirect:index";
			} else {
				return "redirect:login";
			}
		} catch (UnknownAccountException uae) {
			redirectAttributes.addAttribute("message", "用户名不存在");
		} catch (IncorrectCredentialsException ice) {
			redirectAttributes.addAttribute("message", "密码不正确");
		}
		return "redirect:login";
	}

	@GetMapping("admins/page")
	@RequiresRoles("admin")
	public String listPage(@RequestParam String type, Model model) {
		if ("list".equals(type)) {
			return "back/admin/admin_list";
		} else if ("add".equals(type)) {
			model.addAttribute("roles", roleService.selectAll());
			return "back/admin/admin_add";
		}
		throw new InvalidParameterException("没有" + type + "对应的页面。");
	}

	@GetMapping("admins")
	@RequiresRoles("admin")
	@ResponseBody
	public PageInfo<Admin> list(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam String keyword, @RequestParam String column) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<>(adminService.selectByKeyword("%" + keyword + "%", column));
	}

	@GetMapping("admin_roles")
	@RequiresRoles("admin")
	@ResponseBody
	public JSONObject roleList(@RequestParam int adminId) {
		JSONObject result = new JSONObject();
		JSONArray arr = new JSONArray();
		roleService.selectAllByAdminId(adminId).forEach(v -> arr.add(v.getId()));
		result.put("adminRolesId", arr);
		result.put("roles", roleService.selectAll());
		return result;
	}

	@PostMapping("admin_roles")
	@RequiresRoles("admin")
	@ResponseBody
	public boolean updateAdminRole(@RequestParam("roleIds[]") int[] roleIds, @RequestParam int adminId) {
		return adminRoleService.updateAdminRole(roleIds, adminId);
	}

	@PostMapping("admins")
	@RequiresRoles("admin")
	public String addAdmin(Admin admin, int[] rid) {

		adminService.insert(admin, rid);
		return "back/admin/admin_list";
	}

	@GetMapping("admins/check")
	@RequiresRoles("admin")
	@ResponseBody
	public JSONObject usernameIsExists(@RequestParam String username) {
		Admin vo = new Admin();
		vo.setUsername(username);
		JSONObject obj = new JSONObject();
		if (adminService.selectOne(vo) == null) {
			obj.put("ok", "该用户名可用");
		} else {
			obj.put("error", "该用户名已存在，不能使用！");
		}
		return obj;
	}

	@GetMapping("admins/pwdcheck")
	@RequiresRoles("admin")
	@ResponseBody
	public boolean pwdCheck(Admin admin) {
		admin.setId(SessionUtils.getSessionAttr("admin", Admin.class).getId());
		admin.setPassword(LoverUtil.getMd5Password(admin.getPassword(), SessionUtils.getSessionAttr("admin", Admin.class).getUsername()));
		logger.debug("修改密码Admin:" + admin);
		return adminService.selectOne(admin) != null;
	}

	@PutMapping("admins")
	@RequiresRoles("admin")
	@ResponseBody
	public boolean updatePwd(Admin admin) {
		admin.setId(SessionUtils.getSessionAttr("admin", Admin.class).getId());
		admin.setPassword(LoverUtil.getMd5Password(admin.getPassword(), SessionUtils.getSessionAttr("admin", Admin.class).getUsername()));
		return adminService.updateByPrimaryKeySelective(admin);
	}

	@GetMapping("right")
	public String right(){
		return "back/common/right";
	}

}
