package com.hpe.findlover.service;

import com.hpe.findlover.model.UserLife;
import com.hpe.findlover.service.BaseService;

public interface UserLifeService extends BaseService<UserLife> {
    /**
     * 添加用户工作生活的同时添加用户有车一族标签
     * @param userLife
     * @return
     */
    boolean insertUserLifeAndUserLabel(UserLife userLife);
}
