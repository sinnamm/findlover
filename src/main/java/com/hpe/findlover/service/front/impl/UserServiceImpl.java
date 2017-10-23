package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.DictMapper;
import com.hpe.findlover.mapper.UserBasicMapper;
import com.hpe.findlover.mapper.UserLabelMapper;
import com.hpe.findlover.model.Dict;
import com.hpe.findlover.model.Search;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserLabel;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.UserService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserBasic> implements UserService {

	private final UserBasicMapper userBasicMapper;
	private final UserLabelMapper userLabelMapper;

	@Autowired
	public UserServiceImpl(UserBasicMapper userBasicMapper,UserLabelMapper userLabelMapper) {
		this.userBasicMapper = userBasicMapper;
		this.userLabelMapper = userLabelMapper;
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
		if(userBasic.getSalary()!=null){//添加高收入标签
			UserLabel userLabel = userLabelMapper.selectLabelByUserIdAndLabelId(userBasic.getId(),1);
			if(userBasic.getSalary()>=8000){
				if(userLabel==null){
					UserLabel label = new UserLabel();
					label.setUserId(userBasic.getId());
					label.setLabelId(1);//设置标签id为标签表的id，这里是高收入
					userLabelMapper.insert(label);
				}
			}else {//删除高收入标签
				if(userLabel!=null) {
					userLabelMapper.deleteLabelByUserIdAndLabelId(userBasic.getId(), 1);
				}
			}
		}
		if(userBasic.getEducation()!=null){//添加高学历标签
			List<String> education = new ArrayList<>();//用于判断是否属于高学历
			education.add("大学本科");
			education.add("硕士");
			education.add("博士");
			UserLabel userLabel = userLabelMapper.selectLabelByUserIdAndLabelId(userBasic.getId(),2);
			if (education.contains(userBasic.getEducation())){//属于高学历
				if(userLabel==null){
					UserLabel label = new UserLabel();
					label.setUserId(userBasic.getId());
					label.setLabelId(2);//设置标签id为标签表的id，这里是高收入
					userLabelMapper.insert(label);
				}
			}else {//不属于高学历
				if(userLabel!=null) {
					userLabelMapper.deleteLabelByUserIdAndLabelId(userBasic.getId(), 2);
				}
			}
		}
		if(userBasic.getLiveCondition()!=null){//添加有车一族
			UserLabel userLabel = userLabelMapper.selectLabelByUserIdAndLabelId(userBasic.getId(),4);
			if(userBasic.getLiveCondition()==1){
				if(userLabel==null){
					UserLabel label = new UserLabel();
					label.setUserId(userBasic.getId());
					label.setLabelId(4);//设置标签id为标签表的id，这里是有车一族
					userLabelMapper.insert(label);
				}
			}else {//删除有车一族标签
				if(userLabel!=null) {
					userLabelMapper.deleteLabelByUserIdAndLabelId(userBasic.getId(), 4);
				}
			}
		}
		return this.updateByPrimaryKeySelective(userBasic);
	}


}
