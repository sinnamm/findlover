package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.UserLifeMapper;
import com.hpe.findlover.model.UserLife;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.UserLifeService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLifeServiceImpl extends BaseServiceImpl<UserLife> implements UserLifeService {

    private final UserLifeMapper userLifeMapper;

    @Autowired
    public UserLifeServiceImpl(UserLifeMapper userLifeMapper) {
        this.userLifeMapper=userLifeMapper;
    }

    @Override
    public BaseTkMapper<UserLife> getMapper() {
        return userLifeMapper;
    }
}
