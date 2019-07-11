package com.fly.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fly.activity.domain.ActivityDO;
import com.fly.activity.service.ActivityService;
import com.fly.domain.RegionDO;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.InfoService;
import com.fly.system.servicce.RegionService;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.utils.JudgeIsMoblieUtil;
import com.fly.utils.Query;

@Controller
public class IndexController {
	@Autowired
	private InfoService infoService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private RegionService regionService;

	
	/**
	 * 验证是PC用户还是手机用户
	 * @return 
	 */
	@RequestMapping("/")
	public String indexValidate(@RequestParam Map<String,Object> params, HttpServletRequest request,Model model) {
		if(params.get("areaId")!=null) {
			params.put("parentRegionCode", params.get("areaId"));
		}else {
			params.put("parentRegionCode",0);
		}
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);//全国包含的省
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		List<InfoDO> newList = infoService.list(params);
		model.addAttribute("newList", newList);//新闻资讯status
		params.clear();
		params.put("examineStatus",1);
		List<ActivityDO> actList = activityService.list(params);//活动
		model.addAttribute("actList", actList);//团队活动
		params.clear();
		
		List<TeamDO> teamList = teamService.list(params);
		model.addAttribute("teamList", teamList);//团队
		params.put("regionType",1);//
		
		
		String isMoblie = "/pc/index";
		if(JudgeIsMoblieUtil.judgeIsMoblie(request)) {//判断是否为手机
			isMoblie= "/moblie/index";
		}
		return isMoblie;
	}
	
	




	
}
