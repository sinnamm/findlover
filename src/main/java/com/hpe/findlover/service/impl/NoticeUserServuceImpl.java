package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.NoticeUserMapper;
import com.hpe.findlover.model.NoticeUser;
import com.hpe.findlover.service.NoticeUserService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sinnamm
 * @Date Create in  2017/11/3.
 */
@Service
public class NoticeUserServuceImpl extends BaseServiceImpl<NoticeUser> implements NoticeUserService {

    @Autowired
    private NoticeUserMapper noticeUserMapper;
    @Override
    public BaseTkMapper<NoticeUser> getMapper() {
        return noticeUserMapper;
    }
}
