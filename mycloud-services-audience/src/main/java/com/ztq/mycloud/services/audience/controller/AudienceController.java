package com.ztq.mycloud.services.audience.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;

@Api(tags = "直播流下载")
@Controller
public class AudienceController {
	@RequestMapping("/download")
	public String getStream() {
		return "test";
	}
}
