package com.hpe.findlover.service.impl;

import com.hpe.findlover.mapper.VisitTraceMapper;
import com.hpe.findlover.model.VisitTrace;
import com.hpe.findlover.service.VisitTraceService;
import com.hpe.util.BaseTkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sinnamm
 * @Date Create in  2017/11/1.
 */
@Service
public class VisitTraceServiceImpl extends BaseServiceImpl<VisitTrace> implements VisitTraceService {
    @Autowired
    private VisitTraceMapper visitTraceMapper;

    @Override
    public BaseTkMapper<VisitTrace> getMapper() {
        return visitTraceMapper;
    }

    @Override
    public List<VisitTrace> selectVisitTrace(int userId) {
        return visitTraceMapper.selectVisitTrace(userId);
    }

    @Override
    public List<VisitTrace> selectVisitTracer(int userId) {
        List<VisitTrace> visitTraces = visitTraceMapper.selectVisitTracer(userId);
        for(VisitTrace visitTrace:visitTraces){
            visitTraceMapper.updateByPrimaryKeySelective(
                    new VisitTrace(visitTrace.getId(),null,null,null,1));
        }
        return visitTraces;
    }

    @Override
    public List<VisitTrace> selectIndexVisitTracer(int userId) {
        List<VisitTrace> visitTraces = visitTraceMapper.selectVisitTracer(userId);
        return visitTraces;
    }

    @Override
    public Integer selectUnreadCount(int userid) {
        return visitTraceMapper.selectUnreadCount(userid);
    }
}
