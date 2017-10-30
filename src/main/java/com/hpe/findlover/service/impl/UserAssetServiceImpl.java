package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.UserAssetMapper;
import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.service.UserAssetService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */

@Service
public class UserAssetServiceImpl extends BaseServiceImpl<UserAsset> implements UserAssetService {

    @Autowired
    private UserAssetMapper userAssetMapper;

    @Override
    public BaseTkMapper<UserAsset> getMapper() {
        return userAssetMapper;
    }
}
