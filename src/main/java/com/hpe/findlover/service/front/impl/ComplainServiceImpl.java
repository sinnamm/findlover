package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.ComplainMapper;
import com.hpe.findlover.model.Complain;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.ComplainService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements ComplainService {
	private final ComplainMapper complainMapper;

	@Autowired
	public ComplainServiceImpl(ComplainMapper complainMapper) {
		this.complainMapper = complainMapper;
	}

	@Override
	public BaseTkMapper<Complain> getMapper() {
		return complainMapper;
	}
}
