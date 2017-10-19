package com.hpe.findlover.service.back;

import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.BaseService;

import java.util.List;

public interface UserBasicServiceBack extends BaseService<UserBasic> {
	List<UserBasic> selectAllByIdentity(String identity);
}
