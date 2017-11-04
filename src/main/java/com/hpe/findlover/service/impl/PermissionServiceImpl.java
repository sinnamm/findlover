package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.PermissionMapper;
import com.hpe.findlover.model.Permission;
import com.hpe.findlover.service.PermissionService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
	private final PermissionMapper permissionMapper;

	@Autowired
	public PermissionServiceImpl(PermissionMapper permissionMapper) {
		this.permissionMapper = permissionMapper;
	}

	@Override
	public BaseTkMapper<Permission> getMapper() {
		return permissionMapper;
	}

	@Override
	public List<Permission> selectAllByRoleId(int roleId) {
		return permissionMapper.selectAllByRoleId(roleId);
	}

	@Override
	public List<Permission> selectAllByAdminId(int adminId) {
		return permissionMapper.selectAllByAdminId(adminId);
	}
}
