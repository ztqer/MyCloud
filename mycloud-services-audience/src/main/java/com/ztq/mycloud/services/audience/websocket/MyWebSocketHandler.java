package com.ztq.mycloud.services.audience.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.PongMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import com.ztq.mycloud.services.audience.rocketmq.MyBindings;

@EnableBinding(MyBindings.class)
public class MyWebSocketHandler extends AbstractWebSocketHandler {
    //存储sessionId和webSocketSession
    private static CopyOnWriteArraySet<WebSocketSession> sessionSet=new CopyOnWriteArraySet<WebSocketSession>();

    //webSocket连接创建后调用
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionSet.add(session);
    }

    @Autowired
    private MyBindings myBindings;
    
    //接收到消息会调用
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
        	//生产到rocketmq
        	myBindings.commentOutput().send(MessageBuilder.withPayload(message.getPayload()).build());
        } else if (message instanceof BinaryMessage) {
        	
        } else if (message instanceof PongMessage) {
        	
        } else { 
            System.out.println("Unexpected WebSocket message type: " + message);
        }
    }

    //连接出错会调用
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
    	sessionSet.remove(session);
    }

    //连接关闭会调用
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    	sessionSet.remove(session);
    }
    
    //不支持部分消息接收（长消息或未知大小消息被分割传输）
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    //后端广播消息
    public void broadcastMessage(String message){
        for(WebSocketSession session:sessionSet) {
            try {
				session.sendMessage(new TextMessage(message));
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    //从rocketmq消费弹幕信息
    @StreamListener("commentInput")
    public void consume(String rocketMessage) {
    	broadcastMessage(rocketMessage);
    }
}
