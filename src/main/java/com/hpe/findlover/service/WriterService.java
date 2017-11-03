package com.hpe.findlover.service;

import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.Writer;

import java.util.List;

public interface WriterService extends BaseService<Writer> {
    /**
     * 插入文章的同时生成作家
     * @return
     */
    boolean insertWriterAndEssay(Writer writer,String filePath);

    /**
     * 按条件查询所有作家
     * @param identity 按状态查询
     * @param column 查询列属性名
     * @param keyword 查询关键字
     * @return
     */
    List<Writer> selectAllByIdentity(String identity,String column, String keyword);

    /**
     * shiro登录验证
     * @param username 专栏作家账户
     * @return
     */
    Writer selectByUserName(String username);

    Writer selectByPseudonym(String pseudonym);

    Writer selectByUsername(String username);
}
