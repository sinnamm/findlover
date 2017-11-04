package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.RolePermissionMapper;
import com.hpe.findlover.model.RolePermission;
import com.hpe.findlover.service.RolePermissionService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements RolePermissionService {
	private final RolePermissionMapper rolePermissionMapper;

	@Autowired
	public RolePermissionServiceImpl(RolePermissionMapper rolePermissionMapper) {
		this.rolePermissionMapper = rolePermissionMapper;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public boolean updateRolePermission(int roleId, int[] permIds) {
		boolean result;
		RolePermission vo = new RolePermission();
		vo.setRoleId(roleId);
		result = rolePermissionMapper.delete(vo) > 0;
		for (int pid : permIds) {
			result = rolePermissionMapper.insert(new RolePermission(roleId,pid)) > 0;
		}
		return result;
	}

	@Override
	public BaseTkMapper<RolePermission> getMapper() {
		return rolePermissionMapper;
	}
}
