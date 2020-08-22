package com.ztq.mycloud.services.audience.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.ztq.mycloud.services.audience.datasource.Read;
import com.ztq.mycloud.services.audience.datasource.Write;
import com.ztq.mycloud.services.audience.entity.Present;
import com.ztq.mycloud.services.audience.mapper.PresentMapper;
import com.ztq.mycloud.services.audience.rocketmq.OrderProducer;
import io.seata.spring.annotation.GlobalTransactional;

@Service
@MapperScan("com.ztq.mycloud.services.audience.mapper")
public class PresentService {
	@Autowired
	private PresentMapper presentMapper;
	
	//查询房间礼物信息
	@Read
	@Cacheable(value = "idle",key = "'PresentInfo-'+#roomId")
	public List<Present> getPresentList(int roomId){
		try {
			List<String> nameList=presentMapper.selectPresentList(roomId);
			return presentMapper.selectPresentInfo(nameList);
		}
		catch (Exception e) {
			return new ArrayList<Present>();
		}
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	//springcloudstream发送不能返回成功与否，故使用此生产者
	@Autowired
	private OrderProducer orderProducer;
    
	//购买礼物
	//分布式事务，更新余额与生产订单消息
	@Write
	@GlobalTransactional
	public String buyPresent(String name,String password,int roomId,String presentName){
		Map<String,String> map1=restTemplate.getForObject("http://account/getAccount?name={name}", Map.class, name);
		if(!map1.get("password").equals(password)) {
			return "密码错误";
		}
		int balance=Integer.parseInt(map1.get("balance"));
		Integer price=presentMapper.selectPrice(presentName);
		if(price==null) {
			return "礼物信息错误";
		}
		if(balance<price) {
			return "余额不足";
		}
		MultiValueMap<String,Object> map2=new LinkedMultiValueMap<>();
		map2.add("name", name);
		map2.add("balance", balance-price);
		String result=restTemplate.postForObject("http://account/updateBalance", map2, String.class);
		if(result.equals("更新成功")) {
			Map<String,Object> map3=new HashMap<>();
			map3.put("name", name);
			map3.put("roomId",roomId);
			map3.put("presentName", presentName);
			boolean sendOK=false;
			//生产消息作为事务的一环
			try {
				sendOK=orderProducer.send(map3);
			} catch (Exception e) {
				throw new RuntimeException("订单消息生产失败,回滚余额");
			}
			if(sendOK) {
				return "购买成功";
			}
			else {
				throw new RuntimeException("订单消息生产失败,回滚余额");
			}
		}
		return "购买失败";
	}
}
