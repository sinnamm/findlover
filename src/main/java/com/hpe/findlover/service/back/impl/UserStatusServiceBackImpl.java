package com.hpe.findlover.service.back.impl;

import com.hpe.findlover.mapper.UserStatusMapper;
import com.hpe.findlover.model.UserStatus;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.back.UserStatusServiceBack;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sinnamm
 * @Date Create in  2017/10/19.
 */
@Service
public class UserStatusServiceBackImpl extends BaseServiceImpl<UserStatus> implements UserStatusServiceBack {
    @Autowired
    private UserStatusMapper userStatusMapper;

    @Override
    public BaseTkMapper<UserStatus> getMapper() {
        return userStatusMapper;
    }
}
