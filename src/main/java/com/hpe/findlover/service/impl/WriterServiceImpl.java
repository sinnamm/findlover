package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.EssayMapper;
import com.hpe.findlover.mapper.WriterMapper;
import com.hpe.findlover.model.Essay;
import com.hpe.findlover.model.Writer;
import com.hpe.findlover.service.WriterService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public boolean insertWriterAndEssay(String filePath,String pseudonym,String title) {
        boolean result = false;
        Writer writer = new Writer();
        writer.setPseudonym(pseudonym);
        writer.setRegTime(new Date());
        if(writerMapper.insertUseGeneratedKeys(writer)>0){
            Essay essay = new Essay();
            essay.setWriterId(writer.getId());
            essay.setTitle(title);
            essay.setFilename(filePath);
            essay.setStatus(Essay.DEFAUlT_STATUS);
            result = essayMapper.insert(essay)>0;
        }
        return result;
    }
}
