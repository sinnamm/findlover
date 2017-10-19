package com.hpe.findlover.service.back.impl;

import com.hpe.findlover.mapper.UserBasicMapper;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.back.UserBasicServiceBack;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBasicServiceBackImpl extends BaseServiceImpl<UserBasic> implements UserBasicServiceBack {
	@Autowired
	private UserBasicMapper userBasicMapper;
	@Override
	public BaseTkMapper<UserBasic> getMapper() {
		return userBasicMapper;
	}

	@Override
	public List<UserBasic> selectAllByIdentity(String identity) {
		return userBasicMapper.selectAllByIdentity(identity);
	}
}
