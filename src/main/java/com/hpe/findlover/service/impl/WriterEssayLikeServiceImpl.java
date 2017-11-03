package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.WriterEssayLikeMapper;
import com.hpe.findlover.model.WriterEssayLike;
import com.hpe.findlover.service.WriterEssayLikeService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WriterEssayLikeServiceImpl extends BaseServiceImpl<WriterEssayLike> implements WriterEssayLikeService {
    private final WriterEssayLikeMapper writerEssayLikeMapper;

    @Autowired
    public WriterEssayLikeServiceImpl(WriterEssayLikeMapper writerEssayLikeMapper ){
        this.writerEssayLikeMapper=writerEssayLikeMapper;
    }

    @Override
    public BaseTkMapper<WriterEssayLike> getMapper() {
        return writerEssayLikeMapper;
    }


}
