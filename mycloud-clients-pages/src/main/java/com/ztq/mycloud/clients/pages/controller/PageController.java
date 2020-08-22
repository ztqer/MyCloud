package com.ztq.mycloud.clients.pages.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "前端页面控制")
@Controller
public class PageController {
	//测试弹幕页面
	@GetMapping("test")
	public String test() {
		return "test";
	}
	
	@ApiOperation("观看直播页面")
	@GetMapping("liveStream/watch")
	public String watchLiveStream() {
		return "watchLiveStream";
	}

	@ApiOperation("推送直播页面")
	@GetMapping("liveStream/publish")
	public String publishLiveStream() {
		return "publishLiveStream";
	}
}
