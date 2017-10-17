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
	private final DictMapper dictMapper;

	@Autowired
	public UserServiceImpl(UserBasicMapper userBasicMapper,DictMapper dictMapper) {
		this.userBasicMapper = userBasicMapper;
		this.dictMapper = dictMapper;
	}

	@Override
	public BaseTkMapper<UserBasic> getMapper() {
		return userBasicMapper;
	}

	@Override
	public UserBasic selectByEmail(String email) {
		return userBasicMapper.selectByEmail(email);
	}

    @Override
    public List<Dict> selectEducationDict() {
        return dictMapper.selectByType("education");
    }
}
