package com.fly.pc.activity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fly.activity.domain.ActivityDO;
import com.fly.activity.domain.ApplyDO;
import com.fly.activity.service.ActivityService;
import com.fly.activity.service.ApplyService;
import com.fly.domain.RegionDO;
import com.fly.system.service.RegionService;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;

@Controller
@RequestMapping("/pc/")
public class PcActivityController {
	
	@Autowired
	private RegionService regionService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ApplyService applyService;
	@Autowired
	private VolunteerService volunteerService;

	@RequestMapping("activityList")
	public String list(@RequestParam Map<String,Object> params, HttpServletRequest request, 
			Model model) {
		params.clear();
		String areaId = request.getParameter("regionCode");
		params.put("parentRegionCode", 0);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		if (StringUtils.isEmpty(areaId)) {
			areaId = "0";
		}
		params.clear();
		params.put("examineStatus",1);
		params.put("pids", areaId);
		List<ActivityDO> actList = activityService.list(params);//活动
		model.addAttribute("actList", actList);//团队活动
		model.addAttribute("areaList", areaList);
		return "pc/activityList";
	}
	
	@ResponseBody
	@RequestMapping("activityList/query")
	public String query(@RequestParam Map<String,Object> params, HttpServletRequest request, 
			Model model) {
		String areaId = request.getParameter("regionCode");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		if (StringUtils.isEmpty(areaId)) {
			areaId = "0";
		}
		params.put("pids", areaId);
		List<Integer> ids = regionService.getAllTeamByUserRole(params);
		params.clear();
		params.put("examineStatus",1);
		params.put("ids", ids);
		params.put("actType", type);
		params.put("status", status);
		List<ActivityDO> actList = activityService.list(params);//活动
		JSONObject dataInfo = new JSONObject();
		dataInfo.put("actList", actList);
		return dataInfo.toString();
	}
	
	@RequestMapping("activityDetail")
	public String join(Integer id, HttpServletRequest request, 
			Model model) {
		ActivityDO activityDO = activityService.get(id);
		model.addAttribute("activity", activityDO);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("actId", id);
		List<ApplyDO> list = applyService.list(params);
		List<Long> idList = list.stream().map(ApplyDO :: getZyzId).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(idList)) {
			params.clear();
			params.put("idList", idList);
			List<VolunteerDO> volunteerList = volunteerService.list(params);
			List<Map<String,Object>> infoList = new ArrayList<Map<String,Object>>();
			Map<String, Object> info = new HashMap<String, Object>();
			for (int i = 0; i < list.size(); i++) {
				info.put("img", volunteerList.get(i).getHeadImg());
				info.put("name", volunteerList.get(i).getVolunteerName());
				info.put("time", list.get(i).getCreateTimeStr());
				infoList.add(info);
			}
			model.addAttribute("volunteerList", infoList);
		}
		return "pc/activityJoin";
	}
}
