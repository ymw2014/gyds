package com.fly.pc.activity.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fly.activity.domain.ActivityDO;
import com.fly.activity.domain.ApplyDO;
import com.fly.activity.service.ActivityService;
import com.fly.activity.service.ApplyService;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.pc.news.controller.BaseDynamicController;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;

@Controller
@RequestMapping("/pc/")
public class PcActivityController extends BaseDynamicController{
	
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
		List<Integer> ids = regionService.getAllTeamByUserRole(params);
		if (CollectionUtils.isEmpty(ids)) {
			ids.add(-1);
		}
		params.clear();
		params.put("ids", ids);
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
		if (CollectionUtils.isEmpty(ids)) {
			ids.add(-1);
		}
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
		
		//查询已报名的人数
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
		
		
		//查询报名状态
		params.clear();
		Integer applyStatus = 4;
		UserDO user = ShiroUtils.getUser();
		if (user == null) {//没用登录可以浏览活动详情
			model.addAttribute("applyStatus", applyStatus);
			return "pc/activityJoin";
		}
		
		params.clear();
		params.put("userId", user.getUserId());
		List<VolunteerDO> list2 = volunteerService.list(params);
		if (CollectionUtils.isEmpty(list2)) {//不是志愿者
			model.addAttribute("applyStatus", applyStatus);
			return "pc/activityJoin";
		}
		
		params.clear();
		params.put("actId", id);
		params.put("zyzId", list2.get(0).getId());
		List<ApplyDO> ApplyDO = applyService.list(params);
		if (CollectionUtils.isEmpty(ApplyDO)) {//还没有报名
			model.addAttribute("applyStatus", applyStatus);
			return "pc/activityJoin";
		}
		
		
		Integer status = ApplyDO.get(0).getStatus();
		if (status == 1) {//审核通过
			applyStatus = 1;
		} else if (status == 2) {//审核拒绝
			applyStatus = 2;
		} else {//待审核
			applyStatus = 0;
		}
		model.addAttribute("applyId", ApplyDO.get(0).getId());
		model.addAttribute("applyStatus", applyStatus);
		return "pc/activityJoin";
	}
	
	
	@ResponseBody
	@RequestMapping("activity/apply")
	public String apply(Integer type, Long actId, Long applyId) {
		Map<String, Object> params = new HashMap<String, Object>();
		JSONObject dataInfo = new JSONObject();
		UserDO user = ShiroUtils.getUser();
		if (user == null) {
			dataInfo.put("status", "2");//还没登录
			return dataInfo.toString();
		}
		
		params.clear();
		params.put("userId", user.getUserId());
		List<VolunteerDO> list2 = volunteerService.list(params);
		if (CollectionUtils.isEmpty(list2)) {
			dataInfo.put("status", "3");//还不是志愿者
			return dataInfo.toString();
		}
		
		int status = 0;
		try {
			if (type == 1) {
				status = applyService.remove(applyId);
			} else {
				ActivityDO activityDO = activityService.get(actId.intValue());
				Integer num = activityDO.getNumberOfApplicants();
				if (num ++ > activityDO.getApplicantsNumMax()) {
					dataInfo.put("status", "4");//报名人数已满
					return dataInfo.toString();
				}
				ApplyDO apply = new ApplyDO();
				apply.setActId(actId);
				apply.setCreateTime(new Date());
				apply.setStatus(0);
				apply.setZyzId(ShiroUtils.getUserId());//通过shiro 获取用户信息
				status = applyService.save(apply);
				activityDO.setNumberOfApplicants(num ++);
				activityService.update(activityDO);
			}
		} catch (Exception e) {
			status = 5;
			e.printStackTrace();
		}
		dataInfo.put("status", status);
		return dataInfo.toString();
	}
	
	@ResponseBody
	@RequestMapping("activity/collect")
	public String collect(@RequestParam Map<String,Object> params) {
		JSONObject dataInfo = new JSONObject();
		Integer integer = dynamic(params, 5);
		dataInfo.put("code", integer);
		return dataInfo.toString();
	}
	
	@RequestMapping("activity/share/{actType}/{type}/{newsId}")
	public String share(@PathVariable("actType") String actType, @PathVariable("type") String type,
			@PathVariable("newsId") String newsId, Model model) {
		model.addAttribute("actType", actType);
		model.addAttribute("type", type);
		model.addAttribute("newsId", newsId);
		return "pc/share";
	}
}
