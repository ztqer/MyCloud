package com.ztq.mycloud.services.test.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "people")
public class People{
	public People() {
		
	}
	public People(String age) {
		super();
		this.age = age;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "People [age=" + age + "]";
	}

	private String age;
}