package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Admin;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface AdminMapper extends BaseTkMapper<Admin> {
}