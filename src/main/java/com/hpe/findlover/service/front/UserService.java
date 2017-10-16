package com.hpe.findlover.service.front;

import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.BaseService;
import org.springframework.stereotype.Service;

public interface UserService extends BaseService<UserBasic> {
	UserBasic selectByEmail(String email);
}
