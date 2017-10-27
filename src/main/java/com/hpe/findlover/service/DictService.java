package com.hpe.findlover.service;

import com.hpe.findlover.model.Dict;
import com.hpe.findlover.service.BaseService;

import java.util.List;

public interface DictService extends BaseService<Dict> {
    /**
     * 根据传入的类型返回该类型的数据字典列表
     * @return dict对象列表
     */
    List<Dict> selectDictByType(String type);
}
