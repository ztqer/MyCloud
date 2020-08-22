package com.ztq.mycloud.clients.pages.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "礼物控制")
@RestController
public class PresentController {
	@Autowired
	private RestTemplate restTemplate;
	
	@ApiOperation("调用audience微服务获取礼物信息")
	@GetMapping("/present/getPresentInfo")
	public List<Object> getPresentInfo(int roomId) {
		return restTemplate.getForObject("http://audience/getPresentInfo?roomId={roomId}", List.class,roomId);
	}
}
