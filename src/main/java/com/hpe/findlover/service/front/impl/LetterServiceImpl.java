package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.LetterMapper;
import com.hpe.findlover.model.Letter;
import com.hpe.findlover.model.LetterUser;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.LetterService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LetterServiceImpl extends BaseServiceImpl<Letter> implements LetterService {
    private LetterMapper letterMapper;
    @Autowired
    public LetterServiceImpl(LetterMapper letterMapper) {
        this.letterMapper = letterMapper;
    }

    @Override
    public List<LetterUser> selectOther(int userid) {
        return letterMapper.selectOther(userid);
    }

    @Override
    public BaseTkMapper<Letter> getMapper() {
        return null;
    }
}
