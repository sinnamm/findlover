package com.hpe.findlover.service.back.impl;

import com.hpe.findlover.mapper.UserBasicMapper;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.back.UserBasicServiceBack;
import com.hpe.util.BaseTkMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBasicServiceBackImpl extends BaseServiceImpl<UserBasic> implements UserBasicServiceBack {
	private Logger logger = LogManager.getLogger(UserBasicServiceBackImpl.class);
	@Autowired
	private UserBasicMapper userBasicMapper;
	@Override
	public BaseTkMapper<UserBasic> getMapper() {
		return userBasicMapper;
	}

	@Override
	public List<UserBasic> selectAllByIdentity(String identity,String column,String keyword) {
		return userBasicMapper.selectAllByIdentity(identity,column,keyword);
	}
}
