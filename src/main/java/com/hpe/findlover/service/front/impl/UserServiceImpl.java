package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.DictMapper;
import com.hpe.findlover.mapper.UserBasicMapper;
import com.hpe.findlover.model.Dict;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.UserService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserBasic> implements UserService {

	private final UserBasicMapper userBasicMapper;

	@Autowired
	public UserServiceImpl(UserBasicMapper userBasicMapper) {
		this.userBasicMapper = userBasicMapper;
	}

	@Override
	public BaseTkMapper<UserBasic> getMapper() {
		return userBasicMapper;
	}

	@Override
	public UserBasic selectByEmail(String email) {
		return userBasicMapper.selectByEmail(email);
	}

}
