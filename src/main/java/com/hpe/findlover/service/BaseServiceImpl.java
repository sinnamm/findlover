package com.hpe.findlover.service;

import com.github.pagehelper.PageInfo;
import com.hpe.util.BaseTkMapper;

import java.util.List;

/**
 * 基础接口
 *
 * @author zhangqing
 * @date 2016年09月02日
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	public abstract BaseTkMapper<T> getMapper();

	@Override
	public boolean insert(T pojo) throws Exception {
		return getMapper().insert(pojo) > 0;
	}

	@Override
	public boolean insertSelective(T pojo) throws Exception {
		return getMapper().insertSelective(pojo) > 0;
	}

	@Override
	public boolean updateByPrimaryKey(T pojo) throws Exception {
		return getMapper().updateByPrimaryKey(pojo) > 0;
	}

	@Override
	public boolean updateByPrimaryKeySelective(T pojo) throws Exception {
		return getMapper().updateByPrimaryKeySelective(pojo) > 0;
	}

	@Override
	public int delete(T key) throws Exception {
		return getMapper().delete(key);
	}

	@Override
	public int deleteByPrimaryKey(Object key) throws Exception {
		return getMapper().deleteByPrimaryKey(key);
	}

	@Override
	public boolean deleteByPrimaryKeyList(List<String> keys) throws Exception {
//		if (CollectionUtils.isEmpty(keys))
//			return false;
//		try {
//			keys.forEach(getMapper()::deleteByPrimaryKey);
//			return true;
//		} catch (Exception e) {
//			throw e;
//		}
		return true;
	}

	@Override
	public List<T> select(T pojo) throws Exception {
		return getMapper().select(pojo);
	}

	@Override
	public int selectCount(T record) throws Exception {
		return getMapper().selectCount(record);
	}

	@Override
	public T selectByPrimaryKey(Object key) throws Exception {
		return getMapper().selectByPrimaryKey(key);
	}

	@Override
	public List<T> selectAll() throws Exception {
		return getMapper().selectAll();
	}

	@Override
	public PageInfo<T> findPageList(T pojo) throws Exception {
		return new PageInfo<>(getMapper().select(pojo));
	}
}
