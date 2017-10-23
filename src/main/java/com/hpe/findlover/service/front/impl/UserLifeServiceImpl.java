package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.UserLabelMapper;
import com.hpe.findlover.mapper.UserLifeMapper;
import com.hpe.findlover.model.UserLabel;
import com.hpe.findlover.model.UserLife;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.UserLifeService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLifeServiceImpl extends BaseServiceImpl<UserLife> implements UserLifeService {

    private final UserLifeMapper userLifeMapper;
    private final UserLabelMapper userLabelMapper;

    @Autowired
    public UserLifeServiceImpl(UserLifeMapper userLifeMapper,UserLabelMapper userLabelMapper) {
        this.userLifeMapper=userLifeMapper;
        this.userLabelMapper=userLabelMapper;
    }

    @Override
    public BaseTkMapper<UserLife> getMapper() {
        return userLifeMapper;
    }

    @Override
    public boolean insertUserLifeAndUserLabel(UserLife userLife) {
        boolean result = false;
        if (this.selectByPrimaryKey(userLife.getId()) != null) {//用户修改工作生活信息
            result = this.updateByPrimaryKeySelective(userLife);
        } else {//用户第一次填写工作生活信息
            result = this.insertSelective(userLife);
        }

        UserLabel label1 = userLabelMapper.selectLabelByUserIdAndLabelId(userLife.getId(),3);
        if(userLife.getCar() == 1){//添加有车一族标签
            if (label1 == null ) {
                UserLabel label = new UserLabel();
                label.setUserId(userLife.getId());
                label.setLabelId(3);
                userLabelMapper.insert(label);
            }
        }else {//删除有车一族标签
            if (label1 != null ) {
                userLabelMapper.deleteLabelByUserIdAndLabelId(userLife.getId(), 3);
            }
        }

        UserLabel label2 = userLabelMapper.selectLabelByUserIdAndLabelId(userLife.getId(),5);
        if("政府机构".equals(userLife.getJob())){//添加公务员标签
            if (label2 == null ) {
                UserLabel label = new UserLabel();
                label.setUserId(userLife.getId());
                label.setLabelId(5);
                userLabelMapper.insert(label);
            }
        }else {//删除公务员标签
            if (label2 != null ) {
                userLabelMapper.deleteLabelByUserIdAndLabelId(userLife.getId(), 5);
            }
        }

        UserLabel itlabel = userLabelMapper.selectLabelByUserIdAndLabelId(userLife.getId(),6);
        if("计算机/互联网".equals(userLife.getJob())){//添加程序员标签
            if (itlabel == null ) {
                UserLabel label = new UserLabel();
                label.setUserId(userLife.getId());
                label.setLabelId(6);
                userLabelMapper.insert(label);
            }
        }else {//删除程序员标签
            if (itlabel != null ) {
                userLabelMapper.deleteLabelByUserIdAndLabelId(userLife.getId(), 6);
            }
        }
        return result;
    }
}
