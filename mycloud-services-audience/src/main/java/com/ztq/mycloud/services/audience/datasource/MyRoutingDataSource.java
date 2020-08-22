package com.ztq.mycloud.services.audience.datasource;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyRoutingDataSource extends AbstractRoutingDataSource{
	private static final ThreadLocal<String> context=new ThreadLocal<>();
	private static final AtomicInteger counter = new AtomicInteger(0);
	
	//将写请求转到主节点
	public static void setWriteContext() {
		context.set("master");
	}
	
	//利用原子类轮询，将读请求负载均衡
	public static void setReadContext() {
		int i=counter.incrementAndGet()%3;
		if(counter.get()>1000) {
			counter.set(0);
		}
		switch (i) {
		case 0:
			context.set("master");
			break;
		case 1:
			context.set("slave1");
			break;
		case 2:
			context.set("slave2");
			break;
		}
	}
	
	//返回线程私有的context
	@Override
	protected Object determineCurrentLookupKey() {
		System.out.println("当前数据源:"+context.get());
		return context.get();
	}
}
