package com.hpe.findlover.service;

import com.hpe.findlover.model.VisitTrace;

import java.util.List;

/**
 * @author sinnamm
 * @Date Create in  2017/11/1.
 */
public interface VisitTraceService extends BaseService<VisitTrace> {

    /**
     * 我看过谁
     * @param userId
     * @return
     */
    List<VisitTrace> selectVisitTrace(int userId);

    /**
     * 谁看过我
     * @param userId
     * @return
     */
    List<VisitTrace> selectVisitTracer(int userId);

    Integer selectUnreadCount(int userid);

    List<VisitTrace> selectIndexVisitTracer(int userId);
}
