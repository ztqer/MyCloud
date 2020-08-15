package com.ztq.mycloud.clients.pages.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "弹幕控制")
@RestController
public class CommentController {
	@Autowired
	private RestTemplate restTemplate;
	
	@ApiOperation("获取audience微服务websocket的url")
	@GetMapping("/comment/getSocketUrl")
	public String getSocketUrl() {
		return restTemplate.getForObject("http://audience/getSocketUrl", String.class);
	}
}
