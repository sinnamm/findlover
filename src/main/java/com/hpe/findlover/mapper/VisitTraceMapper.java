package com.hpe.findlover.mapper;

import com.hpe.findlover.model.VisitTrace;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VisitTraceMapper extends BaseTkMapper<VisitTrace> {
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
}