package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.ComplainMapper;
import com.hpe.findlover.mapper.UserBasicMapper;
import com.hpe.findlover.model.Complain;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.ComplainService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements ComplainService {
	private final ComplainMapper complainMapper;
	private final UserBasicMapper userBasicMapper;

	@Autowired
	public ComplainServiceImpl(ComplainMapper complainMapper, UserBasicMapper userBasicMapper) {
		this.complainMapper = complainMapper;
		this.userBasicMapper = userBasicMapper;
	}

	@Override
	public BaseTkMapper<Complain> getMapper() {
		return complainMapper;
	}

	@Override
	public List<Complain> selectAllByIdentity(String identity, String column, String keyword) {
		return complainMapper.selectAllByIdentity(identity,column,  keyword);
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public boolean updateUserStatusAndComplainStatus(Integer comId,Complain complain) {
		boolean result= false;
		complain.setId(comId);
		Complain resultCom = complainMapper.selectByPrimaryKey(comId);
		UserBasic userBasic = userBasicMapper.selectByPrimaryKey(resultCom.getComObj());
		userBasic.setStatus(UserBasic.LOCKED_STATUS);
		if(userBasicMapper.updateByPrimaryKeySelective(userBasic)>0){
			result = complainMapper.updateByPrimaryKeySelective(complain)>0;
		}
		return result;
	}
}
