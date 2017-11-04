package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Admin;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseTkMapper<Admin> {
	List<Admin> selectByKeyword(@Param("keyword") String keyword, @Param("column") String column);
}