package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.DictMapper;
import com.hpe.findlover.model.Dict;
import com.hpe.findlover.service.DictService;
import com.hpe.util.BaseTkMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {
	private Logger logger = LogManager.getLogger(DictServiceImpl.class);

	private final DictMapper dictMapper;

	@Autowired
	public DictServiceImpl(DictMapper dictMapper) {
		this.dictMapper = dictMapper;
	}

	@Override
	public BaseTkMapper<Dict> getMapper() {
		return dictMapper;
	}

	@Override
	@Cacheable(cacheNames = "dict-cache")
	public List<Dict> selectDictByType(String type) {
		logger.debug("查询数据字典，type：" + type);
		return dictMapper.selectByType(type);
	}
}
