package com.hpe.findlover.service;

import com.hpe.findlover.model.Follow;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.BaseService;

import java.util.List;
import java.util.Set;

public interface FollowService extends BaseService<Follow> {
	List<UserBasic> selectAllFollow(int userId);

	List<UserBasic> selectAllFollower(int followId);

	Set<Integer> selectFollowIdByUserId(int userId);

	List<Follow> selectFollow(int userId);

	List<Follow> selectFollower(int userId);

	int selectFollowCount(int userId);
}
