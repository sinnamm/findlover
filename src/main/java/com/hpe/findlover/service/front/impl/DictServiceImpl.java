package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.DictMapper;
import com.hpe.findlover.model.Dict;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.DictService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {

    private final DictMapper dictMapper;

    @Autowired
    public DictServiceImpl(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public BaseTkMapper<Dict> getMapper() {
        return dictMapper;
    }

    @Override
    public List<Dict> selectDictByType(String type){
        return dictMapper.selectByType(type);
    }
}
