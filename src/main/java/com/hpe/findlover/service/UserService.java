package com.hpe.findlover.service;

import com.hpe.findlover.model.*;
import com.hpe.findlover.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface UserService extends BaseService<UserBasic> {
	UserBasic selectByEmail(String email);

	List<UserBasic> selectStarUser(UserPick userPick);

	List<UserBasic> selectUserBySearch(Search search);

	List<UserBasic> selectUserByUserPick(UserPick userPick);

	List<UserBasic> selectUserBySexualAndWorkProvince(Integer id,String sexual,String workProvince);

	List<UserBasic> selectUserByIds(Integer[] ids);

	/**
	 * 修改用户基本同时添加高收入、高学历标签
	 * @param userBasic 用户基本信息
	 * @return
	 */
	boolean updateUserBasicAndUserLabel(UserBasic userBasic);

	boolean updatePhoto(UserPhoto photo, UserBasic user);

	//--------------------------后台功能------------------------

	List<UserBasic> selectAllByIdentity(String identity,String column,String keyword);

	/**
	 * 接收一个UserBasic集合，为集合中每一个UserBasic对象
	 * 设置age, isVip, isStar, isAuthenticated四个属性，便于前台展示
	 * @param basics
	 */
	void userAttrHandler(List<UserBasic> basics);

	/**
	 * 为UserBasic对象初始化age, isVip, isStar, isAuthenticated四个属性，便于前台展示
	 * @param basic
	 */
	void userAttrHandler(UserBasic basic);
}
