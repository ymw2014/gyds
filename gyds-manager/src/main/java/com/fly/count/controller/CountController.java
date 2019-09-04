package com.fly.count.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fly.system.service.UserService;

public class CountController {
	@Autowired
	private UserService userService;
	private Map<String, Object> params=new HashMap<String, Object>();
	
	@RequestMapping("/userCountInfo")
	public void getUserCountInfo() {
		params.put("sex", 1);
		int man = userService.count(params);//男性
		params.put("sex", 2);
		int woman = userService.count(params);//女性
		
	}
}
