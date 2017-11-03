package com.hpe.findlover.service;

import com.hpe.findlover.model.Notice;
import com.hpe.findlover.model.UserBasic;

import java.util.List;

/**
 * @author sinnamm
 * @Date Create in  2017/11/2.
 */
public interface NoticeService extends BaseService<Notice> {

    List<Notice> selectAllByIdentity(String identity, String column, String keyword);

    List<Notice> selectUnReadNotice(UserBasic userBasic);

    List<Notice> selectReadNotice(UserBasic userBasic);
}
