# mycloud-services-audience
观众微服务  
端口：10001    
api：ip:port/swagger-ui.html
### 弹幕
1. 与前端建立websocket连接，接收与发送弹幕
2. 微服务向rocketmq生产弹幕消息，并通过广播模式从rocketmq消费（此微服务可以自由扩展)

### 礼物
1. 查询礼物信息
2. 同步调用账号微服务支付功能，成功后向rocketmq生产订单消息(供后续流程异步消费)，并向客户端返回结果
3. 支付和生产消息为分布式事务，具有原子性