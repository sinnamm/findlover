package com.hpe.findlover.service.back.impl;

import com.hpe.findlover.mapper.UserAssetMapper;
import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.back.UserAssetServiceBack;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAssetServiceBackImpl extends BaseServiceImpl<UserAsset> implements UserAssetServiceBack {
	@Autowired
	private UserAssetMapper userBasicMapper;
	@Override
	public BaseTkMapper<UserAsset> getMapper() {
		return userBasicMapper;
	}
}
