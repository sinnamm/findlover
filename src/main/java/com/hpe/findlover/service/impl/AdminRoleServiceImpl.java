package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.AdminRoleMapper;
import com.hpe.findlover.model.AdminRole;
import com.hpe.findlover.service.AdminRoleService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRole> implements AdminRoleService {
	@Autowired
	private AdminRoleMapper adminRoleMapper;

	@Override
	public BaseTkMapper<AdminRole> getMapper() {
		return adminRoleMapper;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public boolean updateAdminRole(int[] roleIds, int adminId) {
		boolean result = false;
		AdminRole vo = new AdminRole();
		vo.setAdminId(adminId);
		result = adminRoleMapper.delete(vo) > 0;
		for (int rid : roleIds) {
			result = adminRoleMapper.insert(new AdminRole(adminId, rid)) > 0;
		}
		return result;
	}
}
