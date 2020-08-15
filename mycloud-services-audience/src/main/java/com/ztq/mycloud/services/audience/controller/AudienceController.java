package com.ztq.mycloud.services.audience.controller;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztq.mycloud.services.audience.websocket.MyBindings;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "观众微服务")
@RestController
public class AudienceController {
	@ApiOperation("获取websocket的url")
	@GetMapping("/getSocketUrl")
	public String getSocketUrl() {
		try {
			return "ws://"+Inet4Address.getLocalHost().getHostAddress().toString()+":"+"10001/websocket";
		} catch (UnknownHostException e) {
			return "无法获取地址";
		}
	}
}
