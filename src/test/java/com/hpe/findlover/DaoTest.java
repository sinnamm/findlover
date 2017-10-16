package com.hpe.findlover;

import com.hpe.findlover.mapper.UserBasicMapper;
import com.hpe.findlover.util.MD5Code;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {
	@Qualifier("DruidDataSource")
	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserBasicMapper userBasicMapper;
	@Test
	public void getDatasource() {
		System.out.println(dataSource.getClass());
	}
	@Test
	public void getMD5(){
		System.out.println(new MD5Code().getMD5ofStr("123"));
	}
	@Test
	public void testMapper(){
		System.err.println(userBasicMapper.selectByEmail("a@a"));
	}
}
