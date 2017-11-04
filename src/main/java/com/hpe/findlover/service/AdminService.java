package com.hpe.findlover.service;

import com.hpe.findlover.model.Admin;

import java.util.List;

public interface AdminService extends BaseService<Admin> {
	/**
	 * 根据关键字和列名进行模糊查询
	 * @param keyword
	 * @param column
	 * @return
	 */
	List<Admin> selectByKeyword(String keyword,String column);

	boolean insert(Admin pojo,int[] rids);
}
