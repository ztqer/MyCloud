package com.ztq.mycloud.services.account.configuration;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.pool.DruidDataSource;
import com.ztq.mycloud.services.account.datasource.MyRoutingDataSource;

@Configuration
public class MyDataSourceConfiguration {
	//三个数据源
	@Bean
	@ConfigurationProperties("spring.datasource.master")
	public DataSource masterDataSource() {
		return new DruidDataSource();
	}
	
	@Bean
	@ConfigurationProperties("spring.datasource.slave1")
	public DataSource slave1DataSource() {
		return new DruidDataSource();
	}
	
	@Bean
	@ConfigurationProperties("spring.datasource.slave2")
	public DataSource slave2DataSource() {
		return new DruidDataSource();
	}
	
	//通过determineCurrentLookupKey去map找出对应的datasource
	@Bean
	public DataSource myRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
            							  @Qualifier("slave1DataSource") DataSource slave1DataSource,
            							  @Qualifier("slave2DataSource") DataSource slave2DataSource){
		HashMap<Object, Object> map=new HashMap<>();
		map.put("matser",masterDataSource);
		map.put("slave1", slave1DataSource);
		map.put("slave2", slave2DataSource);
		MyRoutingDataSource myRoutingDataSource=new MyRoutingDataSource();
		myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
		myRoutingDataSource.setTargetDataSources(map);
		return myRoutingDataSource;
	}
}