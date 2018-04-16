package com.kenneth.boot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/session", method = {RequestMethod.GET, RequestMethod.POST})
public class SessionTestController {
	
	@RequestMapping(value = "/getSessionId")
	public String getSessionId(HttpServletRequest request){
		Object object = request.getSession().getAttribute("springboot");
		if(object == null){
			//生成
			request.getSession().setAttribute("springboot", "123");
		}
		return "sessionId=" + request.getSession().getId();
	}
	
	
}
