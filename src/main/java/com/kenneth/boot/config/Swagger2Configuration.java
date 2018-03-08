package com.kenneth.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * Swagger2的配置
 * @author liq
 * @date 2017年2月16日
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
	@Value("${spring.api.own.name:-}")
	private String applicationName = "";
	@Value("${spring.api.own.version:-}")
	private String applicationVersion = "";
	
	@Bean
	public Docket createRestApi(){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.kenneth.boot"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("Kenneth's RESTful API")
				.description("Service：" + applicationName +" &nbsp;&nbsp; Version：" + applicationVersion)
				.license("GitHub,Inc")
				.licenseUrl("https://github.com/richsleo/kenneth")
				.version(applicationVersion)
				.build();
	}
}
