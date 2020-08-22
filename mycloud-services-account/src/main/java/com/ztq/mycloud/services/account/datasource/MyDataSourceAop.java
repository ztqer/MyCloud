package com.ztq.mycloud.services.account.datasource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class MyDataSourceAop {
	//对@Read和@Write注解切入，切换数据源
	@Pointcut("@annotation(com.ztq.mycloud.services.account.datasource.Read)")
	public void read() {
		
	}
	
	@Pointcut("@annotation(com.ztq.mycloud.services.account.datasource.Write)")
	public void write() {
		
	}
	
	@Before("read()")
	public void changeRead() {
		MyRoutingDataSource.setReadContext();
	}
	
	@Before("write()")
	public void changeWrite() {
		MyRoutingDataSource.setWriteContext();
	}
}
