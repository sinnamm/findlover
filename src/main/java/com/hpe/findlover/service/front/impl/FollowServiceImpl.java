package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.FollowMapper;
import com.hpe.findlover.model.Follow;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.FollowService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
