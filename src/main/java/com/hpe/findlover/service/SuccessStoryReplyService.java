package com.hpe.findlover.service;

import com.hpe.findlover.model.SuccessStoryReply;

import java.util.List;

public interface SuccessStoryReplyService extends BaseService<SuccessStoryReply> {
    List<SuccessStoryReply> selectByStoryId(int storyId);
}
