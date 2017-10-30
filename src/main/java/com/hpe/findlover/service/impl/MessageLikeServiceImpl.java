package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.MessageLikeMapper;
import com.hpe.findlover.model.MessageLike;
import com.hpe.findlover.service.MessageLikeService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sinnamm
 * @Date Create in  2017/10/26.
 */
@Service
public class MessageLikeServiceImpl extends BaseServiceImpl<MessageLike> implements MessageLikeService {

    @Autowired
    private MessageLikeMapper messageLikeMapper;

    @Override
    public BaseTkMapper<MessageLike> getMapper() {
        return messageLikeMapper;
    }
}
