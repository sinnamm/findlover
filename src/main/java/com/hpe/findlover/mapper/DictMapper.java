package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Dict;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface DictMapper extends BaseTkMapper<Dict> {
    /**
     * 按照类型查询字典
     * @return 字典集合
     */
    List<Dict> selectByType(String type);
}