package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.LabelMapper;
import com.hpe.findlover.mapper.UserBasicMapper;
import com.hpe.findlover.mapper.UserLabelMapper;
import com.hpe.findlover.model.*;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.UserService;
import com.hpe.findlover.util.Constant;
import com.hpe.util.BaseTkMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserBasic> implements UserService {

	private Logger logger = LogManager.getLogger(UserServiceImpl.class);

	private final UserBasicMapper userBasicMapper;
	private final UserLabelMapper userLabelMapper;
	private final LabelMapper labelMapper;

	@Autowired
	public UserServiceImpl(UserBasicMapper userBasicMapper,UserLabelMapper userLabelMapper
	,LabelMapper labelMapper) {
		this.userBasicMapper = userBasicMapper;
		this.userLabelMapper = userLabelMapper;
		this.labelMapper = labelMapper;
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
	public List<UserBasic> selectStarUser(String date,String sexual,String workspace) {
		return userBasicMapper.selectStarUser(date,sexual,workspace);
	}

	@Override
	public List<UserBasic> selectUserBySearch(Search search) {
		return userBasicMapper.selectUserBySearch(search);
	}

	@Override
	public List<UserBasic> selectUSerByLabel(UserLabel userLabel) {
		return null;
	}

	@Override
	public List<UserBasic> selectAllUser() {
		return userBasicMapper.selectAll();
	}

	@Override
	public boolean updateUserBasicAndUserLabel(UserBasic userBasic) {

		Label lb = new Label();
		lb.setMeaning(Constant.HIGH_SALARY);
		Label highSalary = labelMapper.selectOne(lb);
		logger.error("高收入标签实体类="+highSalary);

		if(userBasic.getSalary()!=null&&userBasic.getSalary()!=null){//添加高收入标签
			UserLabel userLabel = userLabelMapper.selectLabelByUserIdAndLabelId(userBasic.getId(),highSalary.getId());
			if(userBasic.getSalary()>=8000){
				if(userLabel==null){
					UserLabel label = new UserLabel();
					label.setUserId(userBasic.getId());
					label.setLabelId(highSalary.getId());//设置标签id为标签表的id，这里是高收入
					userLabelMapper.insert(label);
				}
			}else {//删除高收入标签
				if(userLabel!=null) {
					userLabelMapper.deleteLabelByUserIdAndLabelId(userBasic.getId(), highSalary.getId());
				}
			}
		}

		lb.setMeaning(Constant.HIGH_EDUCATION);
		Label highEducation = labelMapper.selectOne(lb);


		if(userBasic.getEducation()!=null&&userBasic.getEducation()!=null){//添加高学历标签
			UserLabel userLabel = userLabelMapper.selectLabelByUserIdAndLabelId(userBasic.getId(),highEducation.getId());
			if (Constant.education.contains(userBasic.getEducation())){//属于高学历
				if(userLabel==null){
					UserLabel label = new UserLabel();
					label.setUserId(userBasic.getId());
					label.setLabelId(highEducation.getId());//设置标签id为标签表的id，这里是高学历
					userLabelMapper.insert(label);
				}
			}else {//不属于高学历
				if(userLabel!=null) {
					userLabelMapper.deleteLabelByUserIdAndLabelId(userBasic.getId(), highEducation.getId());
				}
			}
		}

		lb.setMeaning(Constant.HAVE_HOUSE);
		Label haveHouse = labelMapper.selectOne(lb);

		if(userBasic.getLiveCondition()!=null&&userBasic.getLiveCondition()!=null){//添加有房一族
			UserLabel userLabel = userLabelMapper.selectLabelByUserIdAndLabelId(userBasic.getId(),haveHouse.getId());
			if(Constant.PURCHASED_HOUSE.equals(userBasic.getLiveCondition())){
				if(userLabel==null){
					UserLabel label = new UserLabel();
					label.setUserId(userBasic.getId());
					label.setLabelId(haveHouse.getId());//设置标签id为标签表的id，这里是有房一族
					userLabelMapper.insert(label);
				}
			}else {//删除有房一族标签
				if(userLabel!=null) {
					userLabelMapper.deleteLabelByUserIdAndLabelId(userBasic.getId(), haveHouse.getId());
				}
			}
		}
		return this.updateByPrimaryKeySelective(userBasic);
	}


}
