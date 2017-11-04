package com.hpe.findlover.contoller.back;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hpe.findlover.model.Admin;
import com.hpe.findlover.model.Permission;
import com.hpe.findlover.model.Role;
import com.hpe.findlover.service.PermissionService;
import com.hpe.findlover.service.RolePermissionService;
import com.hpe.findlover.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiresRoles("permission")
@RequestMapping("admin/perm")
public class PermController {
	private final RoleService roleService;
	private final PermissionService permissionService;
	private final RolePermissionService rolePermissionService;

	@Autowired
	public PermController(RoleService roleService, PermissionService permissionService, RolePermissionService rolePermissionService) {
		this.roleService = roleService;
		this.permissionService = permissionService;
		this.rolePermissionService = rolePermissionService;
	}

	@GetMapping("roles")
	public String roleList(Model model){
		model.addAttribute("roles", roleService.selectAll());
		model.addAttribute("perms", permissionService.selectAll());
		return "back/perm/role_list";
	}
	@PostMapping("role")
	public String addRole(Role role,int[] pid,Model model){
		roleService.insert(role, pid);
		return roleList(model);
	}
	@DeleteMapping("role/{roleId}")
	@ResponseBody
	public boolean delRole(@PathVariable int roleId){
		return roleService.deleteByPrimaryKey(roleId) > 0;
	}
	@GetMapping("role_permissions")
	@ResponseBody
	public JSONObject permList(@RequestParam int roleId) {
		JSONObject result = new JSONObject();
		JSONArray arr = new JSONArray();
		permissionService.selectAllByRoleId(roleId).forEach(v -> arr.add(v.getId()));
		result.put("rpids", arr);
		result.put("perms", permissionService.selectAll());
		return result;
	}
	@PostMapping("role_permissions")
	@ResponseBody
	public boolean updateRolePermissions(int roleId,@RequestParam("permIds[]") int[] permIds){
		return rolePermissionService.updateRolePermission(roleId, permIds);
	}
	@GetMapping("role/check")
	@ResponseBody
	public JSONObject valueIsExists(@RequestParam String value){
		Role role = new Role();
		role.setValue(value);
		JSONObject obj = new JSONObject();
		if(roleService.selectOne(role) == null){
			obj.put("ok","该内容可用");
		}else{
			obj.put("error","该内容已存在，不能使用！");
		}
		return obj;
	}


	@GetMapping("permission")
	public String permList(Model model){
		model.addAttribute("perms",permissionService.selectAll());
		return "back/perm/perm_list";
	}
	@PostMapping("permission")
	public String addRole(Permission permission,Model model){
		permissionService.insert(permission);
		return permList(model);
	}
	@DeleteMapping("permission/{permId}")
	@ResponseBody
	public boolean delPerm(@PathVariable int permId){
		return permissionService.deleteByPrimaryKey(permId) > 0;
	}

	@GetMapping("permission/check")
	@ResponseBody
	public JSONObject permValueIsExists(@RequestParam String value){
		Permission perm = new Permission();
		perm.setValue(value);
		JSONObject obj = new JSONObject();
		if(permissionService.selectOne(perm) == null){
			obj.put("ok","该内容可用");
		}else{
			obj.put("error","该内容已存在，不能使用！");
		}
		return obj;
	}
}
