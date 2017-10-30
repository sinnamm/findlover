package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.MessageReplyMapper;
import com.hpe.findlover.model.MessageReply;
import com.hpe.findlover.service.MessageReplyService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sinnamm
 * @Date Create in  2017/10/26.
 */
@Service
public class MessageReplyServiceImpl extends BaseServiceImpl<MessageReply> implements MessageReplyService {

    @Autowired
    private MessageReplyMapper messageReplyMapper;

    @Override
    public BaseTkMapper<MessageReply> getMapper() {

        return messageReplyMapper;
    }
}
