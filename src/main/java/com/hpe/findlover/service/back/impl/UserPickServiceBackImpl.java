package com.hpe.findlover.service.back.impl;

import com.hpe.findlover.mapper.UserPickMapper;
import com.hpe.findlover.model.UserPick;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.back.UserPickServiceBack;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sinnamm
 * @Date Create in  2017/10/19.
 */
@Service
public class UserPickServiceBackImpl extends BaseServiceImpl<UserPick> implements UserPickServiceBack {
    @Autowired
    private UserPickMapper userPickMapper;

    @Override
    public BaseTkMapper<UserPick> getMapper() {
        return userPickMapper;
    }
}
