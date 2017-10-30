package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.LabelMapper;
import com.hpe.findlover.model.Label;
import com.hpe.findlover.service.LabelService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sinnamm
 * @Date Create in  2017/10/23.
 */
@Service
public class LabelServiceImpl extends BaseServiceImpl<Label> implements LabelService {

    @Autowired
    LabelMapper labelMapper;

    @Override
    public BaseTkMapper<Label> getMapper() {
        return labelMapper;
    }
}
