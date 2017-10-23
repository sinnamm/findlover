package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Letter;
import com.hpe.findlover.model.LetterUser;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;
@Mapper
public interface LetterMapper extends BaseTkMapper<Letter> {
    List<LetterUser> selectOther(int userid);
}