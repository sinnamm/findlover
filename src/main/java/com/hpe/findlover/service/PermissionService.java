package com.hpe.findlover.service;

import com.hpe.findlover.model.Permission;

import java.util.List;

public interface PermissionService extends BaseService<Permission>{
	List<Permission> selectAllByRoleId(int roleId);
	List<Permission> selectAllByAdminId(int adminId);
}
