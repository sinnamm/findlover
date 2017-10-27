package com.hpe.findlover.service;

import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Message;
import com.hpe.findlover.service.BaseService;

import java.util.List;

public interface MessageService extends BaseService<Message>{
	List<Message> selectList() ;

}
