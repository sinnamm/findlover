package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.UserPhotoMapper;
import com.hpe.findlover.model.UserPhoto;
import com.hpe.findlover.service.UploadService;
import com.hpe.findlover.service.UserPhotoService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sinnamm
 * @Date Create in  2017/10/19.
 */
@Service
public class UserPhotoServiceImpl extends BaseServiceImpl<UserPhoto> implements UserPhotoService {

    private final UserPhotoMapper userPhotoMapper;
    private final UploadService uploadService;

    @Autowired
    public UserPhotoServiceImpl(UserPhotoMapper userPhotoMapper, UploadService uploadService) {
        this.userPhotoMapper=userPhotoMapper;
        this.uploadService = uploadService;
    }

    @Override
    public BaseTkMapper<UserPhoto> getMapper() {
        return userPhotoMapper;
    }

    @Override
    public boolean deletePhotoByUserPhotoId(Integer id) {
        boolean result = false;
        //查询出需要删除图片的信息
        UserPhoto delPhoto = this.selectByPrimaryKey(id);
        //获取路径进行删除
        result = uploadService.deleteFile(delPhoto.getPhoto());
        //删除数据库数据
        this.deleteByPrimaryKey(id);
        return result;
    }
}
