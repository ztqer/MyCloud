package com.ztq.mycloud.services.account.rocketmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

//自定义输入输出通道
public interface MyBindings {
	@Input("orderInput")
	SubscribableChannel orderInput();
}
