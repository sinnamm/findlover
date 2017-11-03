package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Notice;
import com.hpe.findlover.model.UserBasic;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseTkMapper<Notice> {

    List<Notice> selectAllByIdentity(@Param("identity") String identity,
                                     @Param("column")String column,
                                     @Param("keyword")String keyword);

    List<Notice> selectUnReadNotice(UserBasic userBasic);

    List<Notice> selectReadNotice(UserBasic userBasic);
}