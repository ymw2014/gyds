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
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;

@Controller
@RequestMapping("/pc/checkin/")
public class CheckinController {

	@Autowired
	private SigninService signinService;
	@Autowired
	private VolunteerService volunteerService;
	
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
		Map<String, Object> params = new HashMap<String, Object>();
		UserDO user = ShiroUtils.getUser();
		params.put("userId", user.getUserId());
		params.put("auditStatus", 1);
		List<VolunteerDO> volList = volunteerService.list(params);
		if (CollectionUtils.isEmpty(volList)) {
			return R.error("您还不是志愿者，请先申请为志愿者");
		}
		signinDo.setVoId(Long.valueOf(volList.get(0).getId()));
		if (signinService.save(signinDo) > 0) {
			return R.ok();
		}
		return R.error();
	}
}
