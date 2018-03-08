package com.kenneth.boot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
public class TestController {

	Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping("/1")
	public String test1(){
		logger.info("123");
		return "ok";
	}
}
