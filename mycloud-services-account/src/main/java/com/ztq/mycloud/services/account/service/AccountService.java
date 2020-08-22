package com.ztq.mycloud.services.account.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztq.mycloud.services.account.datasource.Write;
import com.ztq.mycloud.services.account.entity.Account;
import com.ztq.mycloud.services.account.mapper.AccountMapper;

//涉及支付，不使用缓存，全部走主库
@Service
@MapperScan("com.ztq.mycloud.services.account.mapper")
public class AccountService {
	@Autowired
	private AccountMapper accountMapper;
	
	//查询账号
	@Write
	public Account getAccount(String name) {
		return accountMapper.select(name);
	}
	
	//注册账号
	@Write
	public String addAccount(String name,String password) {
		if(getAccount(name)!=null) {
			return "用户名重复";
		}
		try{
			Account account=new Account(name,password,0);
			accountMapper.insert(account);
			return "注册成功";
		}catch (Exception e) {
			return "注册失败";
		}
	}
	
	//更新账号
	@Write
	@Transactional
	public String updateAccount(String name,String password,Integer balance) {
		if(accountMapper.update(name,password,balance)==1) {
			return "更新成功";
		}
		else {
			rollback();
			return "更新失败";
		}
	}
	public void rollback() {
		throw new RuntimeException();
	}
}
