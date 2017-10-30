package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.UserDetailMapper;
import com.hpe.findlover.model.UserDetail;
import com.hpe.findlover.service.UserDetailService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl extends BaseServiceImpl<UserDetail> implements UserDetailService {
    private final UserDetailMapper userDetailMapper;

    @Autowired
    public UserDetailServiceImpl(UserDetailMapper userDetailMapper) {
        this.userDetailMapper=userDetailMapper;
    }

    @Override
    public BaseTkMapper<UserDetail> getMapper() {
        return userDetailMapper;
    }
}
