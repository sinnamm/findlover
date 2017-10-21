package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Search;
import com.hpe.util.BaseTkMapper;

import java.util.List;

/**
 * @author sinnamm
 * @Date Create in  2017/10/20.
 */
public interface SearchMapper extends BaseTkMapper<Search> {

    List<Search> selectAll();
}
