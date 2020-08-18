package com.ztq.mycloud.services.audience.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "观众微服务")
@RestController
public class AudienceController {
	@ApiOperation("获取websocket的url")
	@GetMapping("/getSocketUrl")
	public String getSocketUrl(HttpServletRequest request,HttpServletResponse response) {
		//request.getHeader("HOST") ip+port
		//request.getLocalAddr()==request.getLocalName()==request.getServerName() ip
		return "ws://"+request.getLocalAddr()+":"+"10001/websocket";
	}
}
