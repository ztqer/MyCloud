package com.ztq.mycloud.services.audience.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags = "观众微服务")
@RestController
public class AudienceController {
	
	@GetMapping("/hello")
	public String hello(){
		return "hello";
	}
	
}
