package com.ztq.mycloud.services.audience.websocket;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

//自定义输入输出通道
public interface MyBindings {
    @Input("myInput")
    SubscribableChannel myInput();
    	
	@Output("myOutput")
	MessageChannel myOutput();
}
