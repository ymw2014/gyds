package com.fly.common.controller;


import com.fly.domain.UserDO;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
	@Autowired
	private UserService userService;
	/**
	 * 获取用户对象
	 * @return
	 */
	public UserDO getUser() {
		UserDO user = userService.get(ShiroUtils.getUserId());
		return user;
	}
	/**
	 * 获取用户编号
	 * @return
	 */
	public Long getUserId() {
		return getUser().getUserId();
	}
	/**
	 * 获取用户名称
	 * @return
	 */
	public String getUsername() {
		return getUser().getUsername();
	}
}