package com.hpe.findlover.mapper;

import com.hpe.findlover.model.SuccessStory;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SuccessStoryMapper extends BaseTkMapper<SuccessStory> {
    List<SuccessStory> selectByKeywordAndStatus(String column, String keyword, int status);
    List<SuccessStory> selectByKeyword(String column, String keyword);
}