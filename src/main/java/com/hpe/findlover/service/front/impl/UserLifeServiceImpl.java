package com.hpe.findlover.service.front.impl;

import com.hpe.findlover.mapper.LabelMapper;
import com.hpe.findlover.mapper.UserLabelMapper;
import com.hpe.findlover.mapper.UserLifeMapper;
import com.hpe.findlover.model.Label;
import com.hpe.findlover.model.UserLabel;
import com.hpe.findlover.model.UserLife;
import com.hpe.findlover.service.BaseServiceImpl;
import com.hpe.findlover.service.front.UserLifeService;
import com.hpe.findlover.util.Constant;
import com.hpe.util.BaseTkMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLifeServiceImpl extends BaseServiceImpl<UserLife> implements UserLifeService {
    private Logger logger = LogManager.getLogger(UserLifeServiceImpl.class);


    private final UserLifeMapper userLifeMapper;
    private final UserLabelMapper userLabelMapper;
    private final LabelMapper labelMapper;

    @Autowired
    public UserLifeServiceImpl(UserLifeMapper userLifeMapper,UserLabelMapper userLabelMapper,
    LabelMapper labelMapper) {
        this.userLifeMapper=userLifeMapper;
        this.userLabelMapper=userLabelMapper;
        this.labelMapper=labelMapper;
    }

    @Override
    public BaseTkMapper<UserLife> getMapper() {
        return userLifeMapper;
    }

    @Override
    public boolean insertUserLifeAndUserLabel(UserLife userLife) {
        boolean result = false;

        if (this.selectByPrimaryKey(userLife.getId()) != null) {//用户修改工作生活信息
            result = this.updateByPrimaryKey(userLife);
        } else {//用户第一次填写工作生活信息
            result = this.insertSelective(userLife);
        }

        Label lb = new Label();
        lb.setMeaning(Constant.HAVE_CAR);
        Label salary = labelMapper.selectOne(lb);
        logger.error("工资标签实体类="+salary);

        UserLabel label1 = userLabelMapper.selectLabelByUserIdAndLabelId(userLife.getId(),salary.getId());
        if(userLife.getCar()!=null && userLife.getCar() == 1){//添加有车一族标签
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

        lb.setMeaning(Constant.CIVIL_SERVANT);
        Label civil_servant = labelMapper.selectOne(lb);

        UserLabel label2 = userLabelMapper.selectLabelByUserIdAndLabelId(userLife.getId(),civil_servant.getId());
        if(userLife.getJob()!=null && Constant.GOVERNMENT.equals(userLife.getJob())){//添加公务员标签
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

        lb.setMeaning(Constant.PROGRAMMER);
        Label programmer = labelMapper.selectOne(lb);

        UserLabel itlabel = userLabelMapper.selectLabelByUserIdAndLabelId(userLife.getId(),programmer.getId());
        if(userLife.getJob()!=null && Constant.COMPUTER_INTERNET.equals(userLife.getJob())){//添加程序员标签
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
