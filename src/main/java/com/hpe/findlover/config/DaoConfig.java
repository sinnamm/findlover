package com.hpe.findlover.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;

@Controller
public class DaoConfig {
	@Bean(name = "DruidDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource getDataSource(){
		return new DruidDataSource();
	}
	@Bean
	public DataSourceTransactionManager getDataSourceTransactionManager(){
		return new DataSourceTransactionManager(getDataSource());
	}
}
