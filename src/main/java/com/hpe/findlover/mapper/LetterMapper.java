package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Letter;
import com.hpe.findlover.model.LetterUser;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface LetterMapper extends BaseTkMapper<Letter> {
    List<LetterUser> selectOther(int userid);
    List<Letter> selectLetter(int uid, int otherId);
    Integer selectAmount(int sendid, int userid);

    Integer selectUnreadCount(int userid);
}