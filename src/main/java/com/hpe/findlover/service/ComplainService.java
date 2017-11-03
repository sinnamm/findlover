package com.hpe.findlover.service;

import com.hpe.findlover.model.Complain;
import com.hpe.findlover.service.BaseService;

import java.util.List;

public interface ComplainService extends BaseService<Complain> {
    /**
     * 按条件查询所有作家
     * @param identity 按状态查询
     * @param column 查询列属性名
     * @param keyword 查询关键字
     * @return
     */
    List<Complain> selectAllByIdentity(String identity, String column, String keyword);

    /**
     * 更新用户为锁定状态和投诉状态为锁定状态
     * @param comId 投诉
     * @param complain
     * @return
     */
    boolean updateUserStatusAndComplainStatus(Integer comId,Complain complain);
}
