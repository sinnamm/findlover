package com.hpe.findlover.service;

import com.hpe.findlover.model.SuccessStory;

import java.util.List;

/**
 * @author YYF;
 * @Description 成功故事
 * @Date 2017/10/31
 */
public interface SuccessStoryService extends BaseService<SuccessStory> {
    List<SuccessStory> selectByKeywordAndStatus(String column, String keyword, int status);
}
