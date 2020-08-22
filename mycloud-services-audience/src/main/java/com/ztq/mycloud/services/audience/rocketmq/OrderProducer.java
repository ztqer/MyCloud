package com.ztq.mycloud.services.audience.rocketmq;

import javax.annotation.PostConstruct;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@ConfigurationProperties("rocketmq.myconfig.order-producer")
@Component
public class OrderProducer{
	//从配置文件读取
	private String nameServer;
	private String group;
	private String topic;
	
	public String getNameServer() {
		return nameServer;
	}

	public void setNameServer(String nameServer) {
		this.nameServer = nameServer;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	private DefaultMQProducer producer;

	public OrderProducer() {

	}

	public OrderProducer(String nameServer, String group, String topic) {
		this.nameServer = nameServer;
		this.group = group;
		this.topic = topic;
	}

	@PostConstruct
	public void init() throws Exception {
		producer=new DefaultMQProducer(group);
		producer.setNamesrvAddr(nameServer);
		producer.start();
	}
	
	//返回发送消息成功与否
	public boolean send(Object message) throws Exception {
		Message msg = new Message(topic,JSON.toJSONString(message).getBytes());
		SendResult sendResult=producer.send(msg);
		if(sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
			return true;
		}
		return false;
	}
}
