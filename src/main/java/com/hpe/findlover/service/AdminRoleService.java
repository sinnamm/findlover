package com.hpe.findlover.service;

import com.hpe.findlover.model.AdminRole;

public interface AdminRoleService extends BaseService<AdminRole> {
	boolean updateAdminRole(int[] role,int adminId);
}
