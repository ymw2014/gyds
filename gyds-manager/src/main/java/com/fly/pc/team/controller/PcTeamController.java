package com.fly.pc.team.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fly.activity.domain.ActivityDO;
import com.fly.activity.service.ActivityService;
import com.fly.domain.RegionDO;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.index.service.IndexService;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.InfoService;
import com.fly.system.service.RegionService;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;

@Controller
@RequestMapping("/pc/")
public class PcTeamController {

	@Autowired
	private TeamService teamService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private VolunteerService volunteerService;
	@Autowired
	private IndexService indexService;
	
	@RequestMapping("teamList")
	public String list(@RequestParam Map<String,Object> params, Model model) {
		Object areaId = params.get("areaId");
		if (areaId == null) {
			areaId = "0";
		}
		params.put("pids", areaId);
		List<Integer> ids = regionService.getAllTeamByUserRole(params);
		params.put("ids", ids);
		List<TeamDO> teamList = teamService.list(params);
		model.addAttribute("teamList", teamList);//团队
		params.clear();
		params.put("parentRegionCode", 0);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);
		return "pc/teamList";
	}
	
	
	@RequestMapping("teamDetail")
	public String detail(@RequestParam Map<String,Object> params, Model model, Integer  teamId) {
		Object areaId = params.get("areaId");
		if (areaId == null) {
			areaId = "0";
		}
		
		params.clear();
		params.put("status", 1);
		params.put("teamId", teamId);
		List<InfoDO> newList = infoService.list(params);
		model.addAttribute("newList", newList);//新闻资讯status
		params.clear();
		params.put("examineStatus",1);
		params.put("teamId", teamId);
		List<ActivityDO> actList = activityService.list(params);//活动
		model.addAttribute("actList", actList);//团队活动
		params.clear();
		
		params.put("auditStatus",1);//
		params.put("teamId", teamId);
		List<VolunteerDO> voluntList = volunteerService.list(params);
		int count = volunteerService.count(null);
		model.addAttribute("voluntList", voluntList);//志愿者
		model.addAttribute("voluntCount", count);
		params.clear();
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		return "pc/teamDetail";
	}
	
	
	@ResponseBody
	@RequestMapping("team/query")
	public String query(String areaId, String satuts) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pids", areaId);
		List<Integer> ids = regionService.getAllTeamByUserRole(params);
		params.clear();
		params.put("status", satuts);
		params.put("ids", ids);
		params.put("auditStatus",1);
		List<TeamDO> teamList = teamService.list(params);
		JSONObject dataInfo = new JSONObject();
		dataInfo.put("teamList", teamList);
		return dataInfo.toString();
	}
}
