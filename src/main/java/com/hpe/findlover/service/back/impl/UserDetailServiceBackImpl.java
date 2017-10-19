package com.hpe.findlover.service.back.impl;

import com.hpe.findlover.mapper.UserBasicMapper;
import com.hpe.findlover.mapper.UserDetailMapper;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserDetail;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.back.UserBasicServiceBack;
import com.hpe.findlover.service.back.UserDetailServiceBack;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.soap.Detail;

@Service
public class UserDetailServiceBackImpl extends BaseServiceImpl<UserDetail> implements UserDetailServiceBack {
	@Autowired
	private UserDetailMapper userDetailMapper;
	@Override
	public BaseTkMapper<UserDetail> getMapper() {
		return userDetailMapper;
	}
}
