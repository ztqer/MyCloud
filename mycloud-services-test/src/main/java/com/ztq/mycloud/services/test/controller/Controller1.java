package com.ztq.mycloud.services.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableDiscoveryClient
@RefreshScope
@Controller
public class Controller1 {
	@Value("${test.name}")
	private String name;
	@Value("${test.age}")
	private String age;
	
	@ResponseBody
	@GetMapping("/hello")
	public String hello() {
		return name+"/"+age;
	}
}
