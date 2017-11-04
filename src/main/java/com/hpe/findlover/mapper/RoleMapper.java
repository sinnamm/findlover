package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Role;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseTkMapper<Role> {
	List<Role> selectAllByAdminId(int adminId);
 }