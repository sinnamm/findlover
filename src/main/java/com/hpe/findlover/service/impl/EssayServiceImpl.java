package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.EssayMapper;
import com.hpe.findlover.model.Essay;
import com.hpe.findlover.service.EssayService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EssayServiceImpl extends BaseServiceImpl<Essay> implements EssayService {

    private final EssayMapper essayMapper;

    @Autowired
    public EssayServiceImpl(EssayMapper essayMapper) {
        this.essayMapper=essayMapper;
    }

    @Override
    public BaseTkMapper<Essay> getMapper() {
        return essayMapper;
    }

    @Override
    public List<Essay> selectAllByIdentity(String identity, String column, String keyword) {
        return essayMapper.selectAllByIdentity(identity,column,keyword);
    }

    @Override
    public List<Essay> selectAllByPutaway() {
        return essayMapper.selectAllByPutaway();
    }

    @Override
    public Essay selectEssayAndWriter(Integer id) {
        return essayMapper.selectEssayAndWriter(id);
    }

    @Override
    public List<Essay> selectHotEssay() {
        return essayMapper.selectHotEssay();
    }
}
