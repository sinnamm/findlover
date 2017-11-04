package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.SuccessStoryLikeMapper;
import com.hpe.findlover.model.SuccessStoryLike;
import com.hpe.findlover.service.SuccessStoryLikeService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuccessStoryLikeServiceImpl extends BaseServiceImpl<SuccessStoryLike> implements SuccessStoryLikeService {
    @Autowired
    SuccessStoryLikeMapper successStoryLikeMapper;
    @Override
    public BaseTkMapper<SuccessStoryLike> getMapper() {
        return successStoryLikeMapper;
    }

    @Override
    public Boolean selectByStoryIdAndUserId(int storyId, int userId) {
        return successStoryLikeMapper.selectByStoryIdAndUserId(storyId,userId)!=null;
    }

    @Override
    public Integer selectCountByStoryId(int id) {
        return successStoryLikeMapper.selectCountByStoryId(id);
    }
}
