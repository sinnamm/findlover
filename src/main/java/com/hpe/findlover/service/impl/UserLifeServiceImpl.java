package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.LabelMapper;
import com.hpe.findlover.mapper.UserLabelMapper;
import com.hpe.findlover.mapper.UserLifeMapper;
import com.hpe.findlover.model.UserLife;
import com.hpe.findlover.service.UserLabelService;
import com.hpe.findlover.service.UserLifeService;
import com.hpe.findlover.util.Constant;
import com.hpe.util.BaseTkMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLifeServiceImpl extends BaseServiceImpl<UserLife> implements UserLifeService {
	private Logger logger = LogManager.getLogger(UserLifeServiceImpl.class);


	private final UserLifeMapper userLifeMapper;
	private final UserLabelMapper userLabelMapper;
	private final LabelMapper labelMapper;
	private final UserLabelService userLabelService;

	@Autowired
	public UserLifeServiceImpl(UserLifeMapper userLifeMapper, UserLabelMapper userLabelMapper,
	                           LabelMapper labelMapper, UserLabelService userLabelService) {
		this.userLifeMapper = userLifeMapper;
		this.userLabelMapper = userLabelMapper;
		this.labelMapper = labelMapper;
		this.userLabelService = userLabelService;
	}

	@Override
	public BaseTkMapper<UserLife> getMapper() {
		return userLifeMapper;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public boolean insertUserLifeAndUserLabel(UserLife userLife) {
		boolean result = false;

		if (this.selectByPrimaryKey(userLife.getId()) != null) {//用户修改工作生活信息
			result = this.updateByPrimaryKey(userLife);
		} else {//用户第一次填写工作生活信息
			result = this.insertSelective(userLife);
		}

		//添加有车一族标签
		userLabelService.reloadLabel(Constant.HAVE_CAR, userLife.getId(), userLife.getCar() != null && userLife.getCar() == 1);
		//公务员标签
		userLabelService.reloadLabel(Constant.CIVIL_SERVANT, userLife.getId(), userLife.getJob() != null && Constant.GOVERNMENT.equals(userLife.getJob()));
		//程序员标签
		userLabelService.reloadLabel(Constant.PROGRAMMER, userLife.getId(), userLife.getJob() != null && Constant.COMPUTER_INTERNET.equals(userLife.getJob()));


		return result;
	}
}
