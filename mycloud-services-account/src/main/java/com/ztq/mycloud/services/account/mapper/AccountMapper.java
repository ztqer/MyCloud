package com.ztq.mycloud.services.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.ztq.mycloud.services.account.entity.Account;

@Mapper
public interface AccountMapper {
	//通过名字查询账户信息
	Account select(String name);
	
	//更新账户
	int update(String name,String password,Integer balance);
	
	//插入账户
	int insert(Account account);
	
	//创建Account表
	int createAccount();
}
