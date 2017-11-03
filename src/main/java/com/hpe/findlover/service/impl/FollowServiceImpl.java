package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.FollowMapper;
import com.hpe.findlover.model.Follow;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.FollowService;
import com.hpe.findlover.service.impl.BaseServiceImpl;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FollowServiceImpl extends BaseServiceImpl<Follow> implements FollowService {
	private final FollowMapper followMapper;

	@Autowired
	public FollowServiceImpl(FollowMapper followMapper) {
		this.followMapper = followMapper;
	}

	@Override
	public BaseTkMapper<Follow> getMapper() {
		return followMapper;
	}

	@Override
	public List<UserBasic> selectAllFollow(int userId) {
		return followMapper.selectAllFollow(userId);
	}

	@Override
	public List<UserBasic> selectAllFollower(int followId) {
		return followMapper.selectAllFollower(followId);
	}

	@Override
	public Set<Integer> selectFollowIdByUserId(int userId) {
		return followMapper.selectFollowIdByUserId(userId);
	}

	@Override
	public List<Follow> selectFollow(int userId) {
		return followMapper.selectFollow(userId);
	}

	@Override
	public List<Follow> selectFollower(int userId) {
		return followMapper.selectFollower(userId);
	}

	@Override
	public int selectFollowCount(int userId) {
		return followMapper.selectFollowCount(userId);
	}
}
