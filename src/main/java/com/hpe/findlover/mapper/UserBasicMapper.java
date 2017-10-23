package com.hpe.findlover.mapper;

import com.hpe.findlover.model.Search;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserLabel;
import com.hpe.util.BaseTkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserBasicMapper extends BaseTkMapper<UserBasic> {
	UserBasic selectByEmail(String email);
	List<UserBasic> selectAllByIdentity(@Param("identity") String identity);

	/**
	* 查询符合用户性取向和地区的对应星级用户显示在广告位
	* @param sexual 用户的性取向
	* @param workplace 用户的工作地
	 *@param date 当前日期
	 * @return  List</UserBasic> 返回的星级用户
	**/
	List<UserBasic> selectStarUser(@Param("date") String date,
								   @Param("sexual") String sexual,
								   @Param("workplace") String workplace);

	/**
	 * 通过搜索条件搜索用户
	 * @param search
	 * @return
	 */
	List<UserBasic> selectUserBySearch(Search search);

	List<UserBasic> selectUsersByIds(String[] ids);

	List<UserBasic> selectAllByIdentity(@Param("identity") String identity,@Param("column") String column,@Param("keyword") String keyword);
}