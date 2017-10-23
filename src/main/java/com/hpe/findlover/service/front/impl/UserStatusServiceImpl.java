package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.UserStatusMapper;
import com.hpe.findlover.model.UserStatus;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.UserStatusService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStatusServiceImpl extends BaseServiceImpl<UserStatus> implements UserStatusService {

    private final UserStatusMapper userStatusMapper;

    @Autowired
    public UserStatusServiceImpl(UserStatusMapper userStatusMapper) {
        this.userStatusMapper=userStatusMapper;
    }

    @Override
    public BaseTkMapper<UserStatus> getMapper() {
        return userStatusMapper;
    }
}
