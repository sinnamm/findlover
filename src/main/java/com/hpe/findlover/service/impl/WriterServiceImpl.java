package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.EssayMapper;
import com.hpe.findlover.mapper.WriterMapper;
import com.hpe.findlover.model.Essay;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.Writer;
import com.hpe.findlover.service.WriterService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WriterServiceImpl extends BaseServiceImpl<Writer> implements WriterService {
    private final WriterMapper writerMapper;
    private final EssayMapper essayMapper;

    @Autowired
    public WriterServiceImpl(WriterMapper writerMapper, EssayMapper essayMapper) {
        this.writerMapper=writerMapper;
        this.essayMapper = essayMapper;
    }

    @Override
    public BaseTkMapper<Writer> getMapper() {
        return writerMapper;
    }

    @Override
    public Writer selectByUserName(String username) {
        return writerMapper.selectByUserName(username);
    }

    @Override
    public boolean insertWriterAndEssay(Writer writer,String filePath) {
        boolean result = false;
        if(writerMapper.insertUseGeneratedKeys(writer)>0){
            Essay essay = new Essay();
            essay.setWriterId(writer.getId());
            essay.setFilename(filePath);
            essay.setStatus(Essay.UNCHECKED_STATUS);
            result = essayMapper.insert(essay)>0;
        }
        return result;
    }

    /*-------------------------------后台功能-----------------------------------*/
    @Override
    public List<Writer> selectAllByIdentity(String identity,String column, String keyword) {
        return writerMapper.selectAllByIdentity(identity,column,keyword);
    }

    @Override
    public Writer selectByPseudonym(String pseudonym) {
        return writerMapper.selectByPseudonym(pseudonym);
    }

    @Override
    public Writer selectByUsername(String username) {
        return writerMapper.selectByUsername(username);
    }
}
