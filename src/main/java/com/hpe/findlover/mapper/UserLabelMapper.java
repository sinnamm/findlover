package com.hpe.findlover.mapper;

import com.hpe.findlover.model.UserLabel;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLabelMapper extends BaseTkMapper<UserLabel> {

    UserLabel selectLabelByUserIdAndLabelId(Integer id, Integer i);

    void deleteLabelByUserIdAndLabelId(Integer id, Integer i);
}