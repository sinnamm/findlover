package com.hpe.findlover.service;

import com.hpe.findlover.model.RolePermission;

public interface RolePermissionService extends BaseService<RolePermission>{
	boolean updateRolePermission(int roleId,int[] permIds);
}
