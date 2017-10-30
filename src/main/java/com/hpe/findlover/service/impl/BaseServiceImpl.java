package com.hpe.findlover.service.impl;

import com.github.pagehelper.PageInfo;
import com.hpe.findlover.service.BaseService;
import com.hpe.util.BaseTkMapper;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 基础接口
 *
 * @author zhangqing
 * @date 2016年09月02日
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	public abstract BaseTkMapper<T> getMapper();

	@Override
	public boolean insert(T pojo) {
		return getMapper().insert(pojo) > 0;
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		return getMapper().existsWithPrimaryKey(key);
	}

	@Override
	public int insertUseGeneratedKeys(T pojo) {
		return getMapper().insertUseGeneratedKeys(pojo);
	}

	@Override
	public boolean insertSelective(T pojo) {
		return getMapper().insertSelective(pojo) > 0;
	}

	@Override
	public boolean updateByPrimaryKey(T pojo) {
		return getMapper().updateByPrimaryKey(pojo) > 0;
	}

	@Override
	public boolean updateByPrimaryKeySelective(T pojo) {
		return getMapper().updateByPrimaryKeySelective(pojo) > 0;
	}

	@Override
	public int delete(T key) {
		return getMapper().delete(key);
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		return getMapper().deleteByPrimaryKey(key);
	}

	@Override
	public boolean deleteByPrimaryKeyList(Set<Integer> keys) throws Exception {
		if (CollectionUtils.isEmpty(keys)){
			return false;
		}
		keys.forEach(getMapper()::deleteByPrimaryKey);
		return true;
	}

	@Override
	public List<T> select(T pojo) {
		return getMapper().select(pojo);
	}

	@Override
	public T selectOne(T pojo) {
		return getMapper().selectOne(pojo);
	}

	@Override
	public int selectCount(T record) {
		return getMapper().selectCount(record);
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		return getMapper().selectByPrimaryKey(key);
	}

	@Override
	public List<T> selectAll() {
		return getMapper().selectAll();
	}

	@Override
	public PageInfo<T> findPageList(T pojo) {
		return new PageInfo<>(getMapper().select(pojo));
	}
}
