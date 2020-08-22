package com.ztq.mycloud.services.account.service;

import java.util.Map;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import com.ztq.mycloud.services.account.datasource.Read;
import com.ztq.mycloud.services.account.datasource.Write;
import com.ztq.mycloud.services.account.entity.AudienceLevel;
import com.ztq.mycloud.services.account.mapper.AudienceLevelMapper;
import com.ztq.mycloud.services.account.rocketmq.MyBindings;

@Service
@MapperScan("com.ztq.mycloud.services.account.mapper")
@EnableBinding(MyBindings.class)
public class AudienceLevelService {
	@Autowired
	AudienceLevelMapper audienceLevelMapper;
	
	//查询等级
	@Read
	@Cacheable(value = "busy",key = "'AudienceLevel-'+#name+'-'+#roomId")
	public Integer getLevel(String name,int roomId) {
		return audienceLevelMapper.selectLevel(name, roomId);
	}
	
	//初始化等级
	@Write
	@CacheEvict(value = "busy",key = "'AudienceLevel-'+#name+'-'+#roomId")
	public String newLevel(String name,int roomId) {
		if(getLevel(name, roomId)!=null) {
			return "已有记录";
		}
		try{
			AudienceLevel audienceLevel=new AudienceLevel(name,roomId,1);
			audienceLevelMapper.insert(audienceLevel);
			return "初始化成功";
		}catch (Exception e) {
			return "初始化失败";
		}
	}
	
	//增加等级
	@Write
	@CacheEvict(value = "busy",key = "'AudienceLevel-'+#name+'-'+#roomId")
	public String increaseLevel(String name,int roomId,int increase) {
		if(audienceLevelMapper.increaseLevel(name, roomId, increase)==1) {
			return "更新成功";
		}
		return "更新失败";
	}
	
	@Write
	@StreamListener("orderInput")
	public void calculatePresentLevelUp(Map<String,Object> map) {
		String name=(String)map.get("name");
		int roomId=(int)map.get("roomId");
		String presentName=(String)map.get("presentName");
		if(audienceLevelMapper.increaseLevel(name, roomId, 1)!=1) {
			System.out.println("数据库更新等级错误");
		}
	}
}
