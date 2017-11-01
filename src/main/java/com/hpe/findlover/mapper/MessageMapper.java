package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Message;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface MessageMapper extends BaseTkMapper<Message> {
	List<Message> selectList();

	List<Message> selectMessageByColumn(@Param("column")String column,@Param("keyword")String keyword);

	List<Message> selectMessageByIds(int userId);
}