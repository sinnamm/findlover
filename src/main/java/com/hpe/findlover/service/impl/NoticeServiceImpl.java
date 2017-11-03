package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.NoticeMapper;
import com.hpe.findlover.model.Notice;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.NoticeService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sinnamm
 * @Date Create in  2017/11/2.
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<Notice> implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public BaseTkMapper<Notice> getMapper() {
        return noticeMapper;
    }

    @Override
    public List<Notice> selectAllByIdentity(String identity, String column, String keyword) {
        return noticeMapper.selectAllByIdentity(identity,column,keyword);
    }

    @Override
    public List<Notice> selectUnReadNotice(UserBasic userBasic) {
        return noticeMapper.selectUnReadNotice(userBasic);
    }

    @Override
    public List<Notice> selectReadNotice(UserBasic userBasic) {
        return noticeMapper.selectReadNotice(userBasic);
    }

}
