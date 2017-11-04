package com.hpe.findlover.service;

import com.hpe.findlover.model.Role;

import java.util.List;

public interface RoleService extends BaseService<Role> {
	List<Role> selectAllByAdminId(int adminId);

	boolean insert(Role role,int[] pid);
}
