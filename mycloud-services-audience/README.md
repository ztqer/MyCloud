# mycloud-services-audience
观众微服务  
端口：10001    
api：ip:port/swagger-ui.html
### 弹幕
1. 与前端建立websocket连接，接收与发送弹幕
2. 微服务向rocketmq生产，并通过广播模式从rocketmq消费（此微服务可以自由扩展）
