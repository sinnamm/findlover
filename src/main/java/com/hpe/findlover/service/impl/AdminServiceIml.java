package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.AdminMapper;
import com.hpe.findlover.model.Admin;
import com.hpe.findlover.service.AdminService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceIml extends BaseServiceImpl<Admin> implements AdminService {
	private final AdminMapper adminMapper;

	@Autowired
	public AdminServiceIml(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}

	@Override
	public BaseTkMapper<Admin> getMapper() {
		return adminMapper;
	}
}
