package com.hpe.findlover.service;

import com.hpe.findlover.model.Follow;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.BaseService;

import java.util.List;

public interface FollowService extends BaseService<Follow> {
	List<UserBasic> selectAllFollow(int userId);

	List<UserBasic> selectAllFollower(int followId);
}
