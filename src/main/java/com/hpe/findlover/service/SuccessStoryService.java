package com.hpe.findlover.service;

import com.hpe.findlover.model.SuccessStory;
import com.hpe.findlover.model.UserBasic;

import java.util.List;
import java.util.Map;

/**
 * @author YYF;
 * @Description 成功故事
 * @Date 2017/10/31
 */
public interface SuccessStoryService extends BaseService<SuccessStory> {
    List<SuccessStory> selectByKeywordAndStatus(String column, String keyword, int status);

    /**
     * 查找出成功故事用户
     *
     * @return
     */
    Map<UserBasic, Integer> selectVipNotSingle();

    /**
     * 插入成功故事并发通知
     */
    boolean insertStory(SuccessStory successStory,int userId);
    List<SuccessStory> selectAllByStatus();

    /**
     * 判断是否已经发布成功故事
     * @param userId
     * @return
     */
    boolean checkUser(int userId,int left);
}
