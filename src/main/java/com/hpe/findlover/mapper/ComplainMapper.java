package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Complain;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ComplainMapper extends BaseTkMapper<Complain> {
    /**
     * 按条件查询所有作家
     * @param identity 按状态查询
     * @param column 查询列属性名
     * @param keyword 查询关键字
     * @return
     */
    List<Complain> selectAllByIdentity(@Param("identity") String identity, @Param("column") String column, @Param("keyword")String keyword);

}