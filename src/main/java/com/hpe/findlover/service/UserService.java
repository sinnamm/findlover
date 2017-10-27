package com.hpe.findlover.service;

import com.hpe.findlover.model.*;
import com.hpe.findlover.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface UserService extends BaseService<UserBasic> {
	UserBasic selectByEmail(String email);

	List<UserBasic> selectStarUser(String date,String sexual,String workspace);

	List<UserBasic> selectUserBySearch(Search search);

	List<UserBasic> selectUserByUserPick(UserPick userPick);

	List<UserBasic> selectUserBySexualAndWorkProvince(Integer id,String sexual,String workProvince);

	List<UserBasic> selectUserByIds(Integer[] ids);

	List<UserBasic> selectAllUser();

	/**
	 * 修改用户基本同时添加高收入、高学历标签
	 * @param userBasic 用户基本信息
	 * @return
	 */
	boolean updateUserBasicAndUserLabel(UserBasic userBasic);

	//--------------------------后台功能------------------------

	List<UserBasic> selectAllByIdentity(String identity,String column,String keyword);
}
