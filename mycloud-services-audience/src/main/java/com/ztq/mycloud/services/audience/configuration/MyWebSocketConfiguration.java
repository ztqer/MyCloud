package com.ztq.mycloud.services.audience.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.ztq.mycloud.services.audience.websocket.MyHandshakeInterceptor;
import com.ztq.mycloud.services.audience.websocket.MyWebSocketHandler;

@Configuration
@EnableWebSocket
public class MyWebSocketConfiguration implements WebSocketConfigurer{
	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        		// 指定处理器和路径
        registry.addHandler(myWebSocketHandler, "/websocket")
        		// 允许跨域
        		.setAllowedOrigins("*")		
                // 指定自定义拦截器
                .addInterceptors(new MyHandshakeInterceptor());
        		// 开启sockJs支持
        		//.withSockJS();
    }
	
	@Autowired
	private MyWebSocketHandler myWebSocketHandler;
}
