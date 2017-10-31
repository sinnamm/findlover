package com.hpe.findlover.service;

import com.hpe.findlover.model.Letter;
import com.hpe.findlover.model.LetterUser;
import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.BaseService;

import java.util.List;
import java.util.Map;


public interface LetterService extends BaseService<Letter> {
    Map<String,Object> selectOther(int userid);
    List<Letter> selectLetter(int uid,int otherId);
    Boolean updateVipLetterStatus( List<Letter> list , UserBasic user);
    Boolean readLetter(UserAsset userAsset,Letter letter);
    Boolean sendLetter(UserAsset userAsset,Letter letter);
    Integer selectUnreadCount(int userid);

}
