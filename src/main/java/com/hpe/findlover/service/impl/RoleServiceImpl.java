package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.RoleMapper;
import com.hpe.findlover.mapper.RolePermissionMapper;
import com.hpe.findlover.model.Role;
import com.hpe.findlover.model.RolePermission;
import com.hpe.findlover.service.RoleService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	private final RoleMapper roleMapper;
	private final RolePermissionMapper rolePermissionMapper;

	@Autowired
	public RoleServiceImpl(RoleMapper roleMapper, RolePermissionMapper rolePermissionMapper) {
		this.roleMapper = roleMapper;
		this.rolePermissionMapper = rolePermissionMapper;
	}

	@Override
	public BaseTkMapper<Role> getMapper() {
		return roleMapper;
	}

	@Override
	public List<Role> selectAllByAdminId(int adminId) {
		return roleMapper.selectAllByAdminId(adminId);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public boolean insert(Role role, int[] pids) {
		boolean result;
		result = roleMapper.insertUseGeneratedKeys(role) > 0;
		for (int pid : pids) {
			result = rolePermissionMapper.insert(new RolePermission(role.getId(),pid)) > 0;
		}
		return result;
	}
}
