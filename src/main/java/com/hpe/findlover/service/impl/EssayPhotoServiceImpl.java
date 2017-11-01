package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.EssayPhotoMapper;
import com.hpe.findlover.model.EssayPhoto;
import com.hpe.findlover.service.EssayPhotoService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EssayPhotoServiceImpl extends BaseServiceImpl<EssayPhoto> implements EssayPhotoService {

    private final EssayPhotoMapper essayPhotoMapper;

    @Autowired
    public EssayPhotoServiceImpl(EssayPhotoMapper essayPhotoMapper) {
        this.essayPhotoMapper=essayPhotoMapper;
    }

    @Override
    public BaseTkMapper<EssayPhoto> getMapper() {
        return essayPhotoMapper;
    }

}
