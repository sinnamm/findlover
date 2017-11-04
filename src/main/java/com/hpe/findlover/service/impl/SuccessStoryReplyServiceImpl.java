package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.SuccessStoryReplyMapper;
import com.hpe.findlover.model.SuccessStoryReply;
import com.hpe.findlover.service.SuccessStoryReplyService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuccessStoryReplyServiceImpl extends BaseServiceImpl<SuccessStoryReply> implements SuccessStoryReplyService {
    @Autowired
    SuccessStoryReplyMapper successStoryReplyMapper;
    @Override
    public BaseTkMapper<SuccessStoryReply> getMapper() {
        return successStoryReplyMapper;
    }

    @Override
    public List<SuccessStoryReply> selectByStoryId(int storyId) {
        return successStoryReplyMapper.selectByStoryId(storyId);
    }
}
