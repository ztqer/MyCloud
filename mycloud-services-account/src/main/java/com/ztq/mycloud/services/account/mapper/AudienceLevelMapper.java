package com.ztq.mycloud.services.account.mapper;

import com.ztq.mycloud.services.account.entity.AudienceLevel;

public interface AudienceLevelMapper {
	//查询登级
	Integer selectLevel(String name,int roomId);
	
	//更新等级
	int increaseLevel(String name,int roomId,int increase);
	
	//插入记录
	int insert(AudienceLevel audienceLevel);
}
