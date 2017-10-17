package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.MessageMapper;
import com.hpe.findlover.model.Message;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.MessageService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {
	private final MessageMapper messageMapper;

	@Autowired
	public MessageServiceImpl(MessageMapper messageMapper) {
		this.messageMapper = messageMapper;
	}

	@Override
	public BaseTkMapper<Message> getMapper() {
		return messageMapper;
	}
}
