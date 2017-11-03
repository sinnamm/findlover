package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Follow;
import com.hpe.findlover.model.UserBasic;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface FollowMapper extends BaseTkMapper<Follow> {

	List<UserBasic> selectAllFollow(int userId);

	List<UserBasic> selectAllFollower(int followId);

	Set<Integer> selectFollowIdByUserId(int UserId);

	List<Follow> selectFollow(int userId);

	List<Follow> selectFollower(int userId);

	int selectFollowCount(int userId);
}