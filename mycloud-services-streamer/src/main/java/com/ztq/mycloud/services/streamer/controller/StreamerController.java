package com.ztq.mycloud.services.streamer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;

@Api(tags = "直播流上传")
@Controller
public class StreamerController {
	@RequestMapping("/upload")
	public String uploadStream() {
		return "test";
	}
}
