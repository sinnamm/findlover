package com.hpe.findlover.mapper;

import com.hpe.findlover.model.SuccessStoryReply;
import com.hpe.util.BaseTkMapper;

import java.util.List;

public interface SuccessStoryReplyMapper extends BaseTkMapper<SuccessStoryReply> {
    List<SuccessStoryReply> selectByStoryId(int storyId);
}