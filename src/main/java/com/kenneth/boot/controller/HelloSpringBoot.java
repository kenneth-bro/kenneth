package com.kenneth.boot.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kenneth.boot.util.SystemInfoUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 欢迎页面
 * 
 * @author liq
 * @date 2017年2月21日
 */
@Api(description = "welcome to API")
@Controller
@RequestMapping(value = "", method = { RequestMethod.GET })
public class HelloSpringBoot {
	
	@Value("${spring.application.name: 微服务}")
	String applicationName;

	@RequestMapping("/")
	public String home() {
		return "forward:/api";
	}

	@RequestMapping("/api")
	public String api() {
		return "redirect:/swagger-ui.html";
	}

	@RequestMapping("/hello")
	@ApiOperation(value = "微服务名称", notes = "微服务名称")
	@ResponseBody
	public String hello() {
		return "hello," + applicationName;
	}

	@RequestMapping(value = "/sysinfo")
	@ApiOperation(value = "系统当前时间、时区、版本", notes = "系统当前时间、时区、版本")
	@ResponseBody
	public HashMap<String, Object> systime() {

		HashMap<String, Object> retMap = new HashMap<String, Object>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		TimeZone timeZone = calendar.getTimeZone();

		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd.HHmmss");
		if (SystemInfoUtil.lastModifiedTime == 0) {
			// 找出class的最大修改时间
			SystemInfoUtil.findLastModified("com.investoday.boot", true);
		}

		String version = "3.1." + format2.format(new Date(SystemInfoUtil.lastModifiedTime));
		retMap.put("time", format.format(calendar.getTime()));
		retMap.put("timeZone", timeZone.getID());
		retMap.put("displayName", timeZone.getDisplayName());
		retMap.put("version", version);

		return retMap;
	}
}
