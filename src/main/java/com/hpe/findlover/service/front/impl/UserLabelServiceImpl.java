package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.UserLabelMapper;
import com.hpe.findlover.model.UserLabel;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.UserLabelService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sinnamm
 * @Date Create in  2017/10/23.
 */

@Service
public class UserLabelServiceImpl extends BaseServiceImpl<UserLabel> implements UserLabelService {

    @Autowired
    UserLabelMapper userLabelMapper;

    @Override
    public BaseTkMapper<UserLabel> getMapper() {
        return userLabelMapper;
    }
}
