package com.fly.common.controller;


import com.fly.domain.UserDO;
import com.fly.system.utils.ShiroUtils;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
	/**
	 * 获取用户对象
	 * @return
	 */
	public UserDO getUser() {
		return ShiroUtils.getUser();
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