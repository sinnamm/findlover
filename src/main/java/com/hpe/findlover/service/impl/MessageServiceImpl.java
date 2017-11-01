package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.FollowMapper;
import com.hpe.findlover.mapper.MessageMapper;
import com.hpe.findlover.model.Message;
import com.hpe.findlover.service.MessageService;
import com.hpe.util.BaseTkMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {
	private final MessageMapper messageMapper;
	private Logger logger = LogManager.getLogger(MessageServiceImpl.class);
	private final FollowMapper followMapper;

	@Autowired
	public MessageServiceImpl(MessageMapper messageMapper,FollowMapper followMapper) {
		this.messageMapper = messageMapper;
		this.followMapper = followMapper;
	}

	@Override
	public BaseTkMapper<Message> getMapper() {
		return messageMapper;
	}

	@Override
	public List<Message> selectList() {
		return messageMapper.selectList();
	}

	@Override
	public List<Message> selectMessageByColumn(String column,String keyword) {
		return messageMapper.selectMessageByColumn(column,keyword);
	}

	@Override
	public List<Message> selectMessageByFollow(int userId) {
		List<Message> messages = messageMapper.selectMessageByIds(userId);
		return messages;
	}

}
