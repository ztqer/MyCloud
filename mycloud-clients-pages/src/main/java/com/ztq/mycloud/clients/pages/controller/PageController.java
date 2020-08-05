package com.ztq.mycloud.clients.pages.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class PageController {
	@Autowired
	private RestTemplate restTemplate;
	
	@ResponseBody
	@GetMapping("hello")
	public String hello() {
		return restTemplate.getForObject("http://audience/hello", String.class);
	}
	
	//观看直播
	@GetMapping("liveStream")
	public String watchLiveStream() {
		return "watchLiveStream";
	}
	
	//推送直播
	@GetMapping("liveStream/publish")
	public String publishLiveStream() {
		return "publishLiveStream";
	}
}
