package com.ztq.mycloud.services.audience;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MycloudServicesAudienceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MycloudServicesAudienceApplication.class, args);
	}
	
}
