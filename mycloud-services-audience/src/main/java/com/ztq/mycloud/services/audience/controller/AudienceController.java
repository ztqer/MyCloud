package com.ztq.mycloud.services.audience.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ztq.mycloud.services.audience.entity.Present;
import com.ztq.mycloud.services.audience.service.PresentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "观众微服务")
@RestController
public class AudienceController {
	@ApiOperation("获取弹幕websocket的url")
	@GetMapping("/getSocketUrl")
	public String getSocketUrl(HttpServletRequest request,HttpServletResponse response) {
		//request.getHeader("HOST") ip+port
		//request.getLocalAddr()==request.getLocalName()==request.getServerName() ip
		return "ws://"+request.getLocalAddr()+":"+"10001/websocket";
	}
	
	@Autowired
	private PresentService presentService;
	
	@ApiOperation("获取某直播间的礼物信息")
	@GetMapping("/getPresentInfo")
	public List<Present> getPresentInfo(int roomId) {
		return presentService.getPresentList(roomId);
	}
	
	@ApiOperation("购买礼物")
	@PostMapping("/buyPresent")
	public String buyPresent(String name,String password,int roomId,String presentName) {
		return presentService.buyPresent(name, password, roomId, presentName);
	}
}
