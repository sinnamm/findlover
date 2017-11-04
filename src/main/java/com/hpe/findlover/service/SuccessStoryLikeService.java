package com.hpe.findlover.service;

import com.hpe.findlover.model.SuccessStoryLike;
import org.springframework.stereotype.Service;

public interface SuccessStoryLikeService extends BaseService<SuccessStoryLike>{
    Boolean selectByStoryIdAndUserId(int storyId,int userId);
    Integer selectCountByStoryId(int id);
}
