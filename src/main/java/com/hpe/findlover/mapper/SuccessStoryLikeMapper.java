package com.hpe.findlover.mapper;

import com.hpe.findlover.model.SuccessStoryLike;
import com.hpe.util.BaseTkMapper;

public interface SuccessStoryLikeMapper extends BaseTkMapper<SuccessStoryLike> {
    SuccessStoryLike selectByStoryIdAndUserId(int storyId,int userId);
    Integer selectCountByStoryId(int id);
}