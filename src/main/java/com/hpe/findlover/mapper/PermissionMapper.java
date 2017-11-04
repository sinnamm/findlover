package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Permission;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseTkMapper<Permission> {
	List<Permission> selectAllByRoleId(int roleId);
	List<Permission> selectAllByAdminId(int adminId);
}