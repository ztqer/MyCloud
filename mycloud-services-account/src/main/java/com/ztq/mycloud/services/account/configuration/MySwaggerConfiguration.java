package com.ztq.mycloud.services.account.configuration;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class MySwaggerConfiguration {
	@Bean
	public Docket docket(){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//过滤网页路径
				.paths(PathSelectors.ant("/**"))
				//设置扫描包
				.apis(RequestHandlerSelectors.basePackage("com.ztq.mycloud.services.account.controller"))
				.build();
	}
	
	//页面设置
	private ApiInfo apiInfo() {
		return new ApiInfo("My Swagger API Document", "希望bug没事", "1.0", "https://github.com/ztqer",
				new Contact("ztq", "https://github.com/ztqer", "ztq18118468648@163.com"), 
				 "license", "https://github.com/ztqer", new ArrayList<VendorExtension>());
	}
}
