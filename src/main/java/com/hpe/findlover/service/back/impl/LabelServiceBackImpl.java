package com.hpe.findlover.service.back.impl;

import com.hpe.findlover.mapper.LabelMapper;
import com.hpe.findlover.model.Label;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.back.LabelServiceBack;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceBackImpl extends BaseServiceImpl<Label> implements LabelServiceBack {
	@Autowired
	private LabelMapper labelMapper;
	@Override
	public BaseTkMapper<Label> getMapper() {
		return labelMapper;
	}
}
