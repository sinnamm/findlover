package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.NoticeMapper;
import com.hpe.findlover.mapper.SuccessStoryMapper;
import com.hpe.findlover.mapper.UserAssetMapper;
import com.hpe.findlover.model.Notice;
import com.hpe.findlover.model.SuccessStory;
import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.SuccessStoryService;
import com.hpe.findlover.service.UserService;
import com.hpe.findlover.util.Constant;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.util.BaseTkMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.*;

@Service
public class SuccessStoryServiceImpl extends BaseServiceImpl<SuccessStory> implements SuccessStoryService {
    private Logger logger = LogManager.getLogger(SuccessStoryServiceImpl.class);
    @Autowired
    private SuccessStoryMapper successStoryMapper;
    @Autowired
    private UserAssetMapper userAssetMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public BaseTkMapper<SuccessStory> getMapper() {
        return successStoryMapper;
    }
    @Override
    public List<SuccessStory> selectByKeywordAndStatus(String column, String keyword, int status) {
        List list=null;
        if (keyword==null||keyword.equals("null")){
            keyword="";
        }
        String newKeyword="%"+keyword+"%";
        if (status!= -1){
            list=successStoryMapper.selectByKeywordAndStatus(column, newKeyword,status);
        }else{
            list=successStoryMapper.selectByKeyword(column, newKeyword);
        }
        return list;
    }

    @Override
    public Map<UserBasic, Integer> selectVipNotSingle() {
        List<SuccessStory> notSingle = successStoryMapper.selectNotSingle();
        Map<UserBasic, Integer> map = new HashMap<>();
        logger.error(notSingle);
        for (SuccessStory successStory : notSingle) {
            UserBasic user = successStory.getUserLeft();
            //计算出成功牵手历时
            int days = LoverUtil.getDiffOfDays(user.getRegTime(), successStory.getSuccessTime());
            userService.userAttrHandler(user);
            if(user.isVip()) {
                map.put(user, days);
            }
        }

        return map;
    }

    @Override
    public boolean insertStory(SuccessStory successStory) {
       int result1 =  successStoryMapper.insert(successStory);
        Notice notice = new Notice(null,"成功故事审核", Constant.CONFIRM_SUCCESS_STORY,new Date(),successStory.getLeftUser());
       int result2 =  noticeMapper.insert(notice);
       if (result1>0&&result2>0){
           return true;
       }else {
           return false;
       }
    }
}
