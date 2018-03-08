package com.kenneth.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableDiscoveryClient
@EnableZuulProxy
@EnableFeignClients
@SpringBootApplication
public class Application {
	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		System.setProperty("file.encoding", "UTF-8");
		new SpringApplicationBuilder(Application.class).web(true).run(args);
		logger.info("==> kenneth started.");
	}
}
