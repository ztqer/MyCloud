package com.ztq.mycloud.services.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ztq.mycloud.services.account.entity.Account;
import com.ztq.mycloud.services.account.service.AccountService;
import com.ztq.mycloud.services.account.service.AudienceLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "账号微服务")
@RestController
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@ApiOperation("获取账号信息")
	@GetMapping("/getAccount")
	public Account getAccount(String name) {
		return accountService.getAccount(name);
	}
	
	@ApiOperation("注册账号")
	@PostMapping("/addAccount")
	public String addAccount(String name,String password) {
		return accountService.addAccount(name,password);
	}
	
	@ApiOperation("更新密码")
	@PostMapping("/updatePassword")
	public String updatePassword(String name,String password) {
		return accountService.updateAccount(name,password,null);
	}
	
	@ApiOperation("更新余额")
	@PostMapping("/updateBalance")
	public String updateBalance(String name,int balance) {
		return accountService.updateAccount(name,null,balance);
	}
	
	@Autowired
	AudienceLevelService audienceLevelService;
	
	@ApiOperation("获取用户在指定直播间等级")
	@GetMapping("/getAudienceLevel")
	public Integer getAudienceLevel(String name,int roomId) {
		return audienceLevelService.getLevel(name, roomId);
	}
	
	@ApiOperation("初始化直播间等级")
	@PostMapping("/newAudienceLevel")
	public String newAudienceLevel(String name,int roomId) {
		return audienceLevelService.newLevel(name, roomId);
	}
	
	@ApiOperation("等级增长")
	@PostMapping("/increaseAudienceLevel")
	public String increaseAudienceLevel(String name,int roomId,int increase) {
		return audienceLevelService.increaseLevel(name, roomId, increase);
	}
}
