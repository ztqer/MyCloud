package com.ztq.mycloud.services.audience.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.ztq.mycloud.services.audience.entity.Present;

@Mapper
public interface PresentMapper {
	//通过房间号查礼物列表
	List<String> selectPresentList(int roomId);
	
	//通过礼物名查详细信息
	List<Present> selectPresentInfo(@Param("list")List<String> name);

	//查询指定礼物价格
	Integer selectPrice(String name);
	
	//添加房间礼物列表
	int insertRoomPresent(int roomId,String present);
	
	//添加礼物信息
	int insertPresentInfo(Present present);
	
	//创建PresentInfo表
	int createPresentInfo();

	//创建RoomPensent表
	int createRoomPresent();
}
