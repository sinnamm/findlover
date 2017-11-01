package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Essay;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EssayMapper extends BaseTkMapper<Essay> {
    /**
     * 按条件查询
     * @param identity 是否已审核条件
     * @param column 按列查询
     * @param keyword 按关键字查询
     * @return
     */
    List<Essay> selectAllByIdentity(@Param("identity") String identity, @Param("column")String column,@Param("keyword") String keyword);

    /**
     * 查询已上架文章
     * @return  文章列表
     */
    List<Essay> selectAllByPutaway();

    /**
     * 查询文章的同时查询作家
     * @return  文章对象中包含作家对象
     */
    Essay selectEssayAndWriter(Integer id);

    /**
     * 查询5条访问量大的文章
     * @return
     */
    List<Essay> selectHotEssay();
}