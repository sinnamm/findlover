package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.SuccessStoryMapper;
import com.hpe.findlover.model.SuccessStory;
import com.hpe.findlover.service.SuccessStoryService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuccessStoryServiceImpl extends BaseServiceImpl<SuccessStory> implements SuccessStoryService {
    @Autowired
    private SuccessStoryMapper successStoryMapper;
    @Override
    public BaseTkMapper<SuccessStory> getMapper() {
        return successStoryMapper;
    }
    @Override
    public List<SuccessStory> selectByKeywordAndStatus(String column, String keyword, int status) {
        List list=null;
        if (keyword==null||keyword.equals("null")){
            keyword="";
        }
        String newKeyword="%"+keyword+"%";
        if (status!= -1){
            list=successStoryMapper.selectByKeywordAndStatus(column, newKeyword,status);
        }else{
            list=successStoryMapper.selectByKeyword(column, newKeyword);
        }
        return list;
    }
}
