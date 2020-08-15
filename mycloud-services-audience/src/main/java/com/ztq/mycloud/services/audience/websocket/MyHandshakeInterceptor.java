package com.ztq.mycloud.services.audience.websocket;

import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class MyHandshakeInterceptor implements HandshakeInterceptor {
	
	//不做拦截
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, 
			WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		return true;
	}
	
	//什么都不干
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, 
			WebSocketHandler wsHandler, Exception exception) {
	}

}
