package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.LabelMapper;
import com.hpe.findlover.mapper.UserBasicMapper;
import com.hpe.findlover.mapper.UserPhotoMapper;
import com.hpe.findlover.model.*;
import com.hpe.findlover.mapper.UserLabelMapper;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.UserLabelService;
import com.hpe.findlover.service.UserService;
import com.hpe.findlover.util.Constant;
import com.hpe.util.BaseTkMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserBasic> implements UserService {

	private Logger logger = LogManager.getLogger(UserServiceImpl.class);

	private final UserBasicMapper userBasicMapper;
	private final UserLabelMapper userLabelMapper;
	private final LabelMapper labelMapper;
	private final UserLabelService userLabelService;
	private final UserPhotoMapper userPhotoMapper;

	@Autowired
	public UserServiceImpl(UserBasicMapper userBasicMapper, UserLabelMapper userLabelMapper
			, LabelMapper labelMapper, UserLabelService userLabelService, UserPhotoMapper userPhotoMapper) {
		this.userBasicMapper = userBasicMapper;
		this.userLabelMapper = userLabelMapper;
		this.labelMapper = labelMapper;
		this.userLabelService = userLabelService;
		this.userPhotoMapper = userPhotoMapper;
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
	public List<UserBasic> selectStarUser(String date, String sexual, String workspace) {
		return userBasicMapper.selectStarUser(date, sexual, workspace);
	}

	@Override
	public List<UserBasic> selectUserBySearch(Search search) {
		return userBasicMapper.selectUserBySearch(search);
	}

	@Override
	public List<UserBasic> selectUserByUserPick(UserPick userPick) {
		return userBasicMapper.selectUserByUserPick(userPick);
	}

	@Override
	public List<UserBasic> selectUserBySexualAndWorkProvince(Integer id, String sexual, String workProvince) {
		return userBasicMapper.selectUserBySexualAndWorkProvince(id,sexual,workProvince);
	}

	@Override
	public List<UserBasic> selectUserByIds(Integer[] ids) {
		return userBasicMapper.selectUsersByIds(ids);
	}

	@Override
	public List<UserBasic> selectAllUser() {
		return userBasicMapper.selectAll();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public boolean updateUserBasicAndUserLabel(UserBasic userBasic) {


		//添加高收入标签
		userLabelService.reloadLabel(Constant.HIGH_SALARY, userBasic.getId(), userBasic.getSalary() != null && userBasic.getSalary() >= 8000);

		//添加高学历标签
		userLabelService.reloadLabel(Constant.HIGH_EDUCATION, userBasic.getId(), userBasic.getEducation() != null && Constant.education.contains(userBasic.getEducation()));

		//添加有房一族
		userLabelService.reloadLabel(Constant.HAVE_HOUSE, userBasic.getId(), userBasic.getLiveCondition() != null && Constant.PURCHASED_HOUSE.equals(userBasic.getLiveCondition()));

		return this.updateByPrimaryKeySelective(userBasic);
	}

	//-----------------------------后台------------------------------
	@Override
	public List<UserBasic> selectAllByIdentity(String identity,String column,String keyword) {
		return userBasicMapper.selectAllByIdentity(identity,column,keyword);
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public boolean updatePhoto(UserPhoto photo, UserBasic user) {
		String tmp = user.getPhoto();
		user.setPhoto(photo.getPhoto());
		photo.setPhoto(tmp);
		if (!this.updateByPrimaryKey(user)){
			return false;
		}
		if (userPhotoMapper.updateByPrimaryKey(photo)<0){
			return false;
		}
		return true;
	}
}
