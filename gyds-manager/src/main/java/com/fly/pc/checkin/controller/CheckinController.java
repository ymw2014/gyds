package com.fly.pc.checkin.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fly.domain.UserDO;
import com.fly.signin.domain.SigninDO;
import com.fly.signin.service.SigninService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.R;

@Controller
@RequestMapping("/pc/checkin/")
public class CheckinController {

	@Autowired
	private SigninService signinService;
	
	@RequestMapping("show")
	public String show() {
		return "pc/checkin";
	}
	
	
	@ResponseBody
	@RequestMapping("data")
	public String data(Model model, String queryMonth) {
		JSONObject dataInfo = new JSONObject();
		UserDO user = ShiroUtils.getUser();
		Map<String, Object> params = new HashMap<String, Object>();
		LocalDate date = LocalDate.now();
		int month = 0;
		if (StringUtils.isEmpty(queryMonth)) {
			month = date.getMonthValue();
		} else {
			Integer.valueOf(queryMonth);
		}
		params.put("month", month);
		params.put("voId", user.getUserId());
		int status = 0;
		List<SigninDO> list = signinService.list(params);
		if (!CollectionUtils.isEmpty(list)) {
			SigninDO signinDO = list.get(0);
			int day = date.getDayOfMonth();
			if (day == signinDO.getDay()) {
				status = 1;
			}
		}
		/*
		 * model.addAttribute("status", status); model.addAttribute("signin", list);
		 */
		dataInfo.put("signin", list);
		dataInfo.put("status", status);
		return dataInfo.toString();
	}
	
	@ResponseBody
	@RequestMapping("/do")
	public R signin(SigninDO signinDo) {
		UserDO user = ShiroUtils.getUser();
		signinDo.setVoId(user.getUserId());//现在测试，用用户id
		if (signinService.save(signinDo) > 0) {
			return R.ok();
		}
		return R.error();
	}
}
