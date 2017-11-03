package com.hpe.findlover.mapper;

import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.Writer;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WriterMapper extends BaseTkMapper<Writer> {

    /**
     * 按条件查询所有作家
     * @param identity 按状态查询
     * @param column 查询列属性名
     * @param keyword 查询关键字
     * @return
     */
    List<Writer> selectAllByIdentity(@Param("identity") String identity,@Param("column") String column, @Param("keyword")String keyword);

    /**
     * shiro登录验证
     * @param username 专栏作家账户
     * @return
     */
    Writer selectByUserName(String username);

    Writer selectByPseudonym(String pseudonym);

    Writer selectByUsername(String username);
}