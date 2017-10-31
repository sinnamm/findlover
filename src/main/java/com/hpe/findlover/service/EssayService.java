package com.hpe.findlover.service;

import com.hpe.findlover.model.Essay;

import java.util.List;

public interface EssayService extends BaseService<Essay> {
    /**
     * 按条件查询
     * @param identity 是否已审核条件
     * @param column 按列查询
     * @param keyword 按关键字查询
     * @return
     */
    List<Essay> selectAllByIdentity(String identity, String column, String keyword);
}
