package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.AdminMapper;
import com.hpe.findlover.mapper.AdminRoleMapper;
import com.hpe.findlover.model.Admin;
import com.hpe.findlover.model.AdminRole;
import com.hpe.findlover.service.AdminService;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceIml extends BaseServiceImpl<Admin> implements AdminService {
	private final AdminMapper adminMapper;
	private final AdminRoleMapper adminRoleMapper;

	@Autowired
	public AdminServiceIml(AdminMapper adminMapper, AdminRoleMapper adminRoleMapper) {
		this.adminMapper = adminMapper;
		this.adminRoleMapper = adminRoleMapper;
	}

	@Override
	public BaseTkMapper<Admin> getMapper() {
		return adminMapper;
	}

	@Override
	public List<Admin> selectByKeyword(String keyword, String column) {
		return adminMapper.selectByKeyword(keyword,column);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public boolean insert(Admin pojo, int[] rids) {
		boolean result;
		pojo.setCreateTime(new Date());
		try {
			pojo.setLastLogin(DateFormat.getDateTimeInstance().parse("1970-01-01 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		pojo.setFlag(1);
		pojo.setPassword(LoverUtil.getMd5Password(pojo.getPassword(),pojo.getUsername()));
		result = adminMapper.insertUseGeneratedKeys(pojo) > 0;
		for(int rid:rids){
			AdminRole vo = new AdminRole(pojo.getId(),rid);
			result = adminRoleMapper.insertSelective(vo) > 0;
		}
		return result;
	}
}
