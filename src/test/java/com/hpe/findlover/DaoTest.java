package com.hpe.findlover;

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
	@Autowired
	@Qualifier("DruidDataSource")
	private DataSource dataSource;
	@Test
	public void getDatasource() {
		System.out.println(dataSource);
	}

}
