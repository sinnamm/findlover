package com.hpe.findlover.service;


import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 *
 * 基础接口，已经实现的方法有：
 * 1、增：增加一个实体
 * 2、删：根据主键删除，根据条件删除，根据主键批量删除
 * 3、改：根据主键修改
 * 4、查：条件查询，查询记录数，根据主键查询，查询所有，查询分页。
 * @author zhangqing
 * @date 2016年09月02日
 */

public interface BaseService<T>{
    /**
     *
     * 增加一个实体,增加所有字段
     * @param pojo
     * @return 返回实体类
     */
    public boolean insert(T pojo);


    /**
     *
     * 增加一个实体,只会增加不是null的字段
     * @param pojo
     * @return 返回实体类
     */
    public boolean insertSelective(T pojo);

    /**
     * 根据主键进行更新一个实体类,更新所有字段
     * @param pojo
     * @return 修改成功状态
     */
    public boolean updateByPrimaryKey(T pojo);

    /**
     * 根据主键进行更新一个实体类,只会更新不是null的字段
     * @param pojo
     * @return
     */
    public boolean updateByPrimaryKeySelective(T pojo);

    /**
     * 根据实体类中字段不为null的条件进行删除,条件全部使用=号and条件
     * @param key
     * @return
     */
    public int delete(T key);

    /**
     * 通过主键进行删除,这里最多只会删除一条数据
     * 单个字段做主键时,可以直接写主键的值
     * 联合主键时,key可以是实体类,也可以是Map
     * @param key
     * @return
     */
    public int deleteByPrimaryKey(Object key);

    /**
     * 根据主键的集合批量删除数据
     * @param keys
     * @return 是否删除成功
     */
    public boolean deleteByPrimaryKeyList(List<String> keys) throws Exception;

    /**
     * 根据实体类不为null的字段进行查询集合,条件全部使用=号and条件
     * @param pojo
     * @return
     */
    public List<T> select(T pojo);

    /**
     * 根据实体类不为null的字段查询总数,条件全部使用=号and条件
     * @param pojo
     * @return
     */
    public int selectCount(T pojo);

    /**
     * 根据主键进行查询,必须保证结果唯一
     * 单个字段做主键时,可以直接写主键的值
     * 联合主键时,key可以是实体类,也可以是Map
     * @param key
     * @return
     */
    public T selectByPrimaryKey(Object key);

    /**
     * 查询所有实体集合
     * @return
     */
    public List<T> selectAll();

    /**
     * 查询分页
     * @param pojo
     * @return
     */
    public PageInfo<T> findPageList(T pojo);

}
