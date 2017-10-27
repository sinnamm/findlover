package com.hpe.findlover.service;

import com.hpe.findlover.model.UserPhoto;
import com.hpe.findlover.service.BaseService;

/**
 * @author sinnamm
 * @Date Create in  2017/10/19.
 */

public interface UserPhotoService extends BaseService<UserPhoto> {
    /**
     * 删除用户单张照片
     * @param id 照片id
     * @return
     */
    boolean deletePhotoByUserPhotoId(Integer id);
}
