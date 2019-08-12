package com.fly.pc.team.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.fly.activity.service.ActivityService;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.index.service.IndexService;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.InfoService;
import com.fly.system.dao.UserDao;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.domain.ApplyTeamDO;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.ApplyTeamService;
import com.fly.team.service.TeamService;
import com.fly.utils.R;
import com.fly.utils.userToObject;
import com.fly.verifyName.dao.NameDao;
import com.fly.verifyName.domain.NameDO;
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
	@Autowired
	private NameDao nameDao;
	@Autowired
	private UserDao userDao;
	
	
	@RequestMapping("teamList")
	public String list(@RequestParam Map<String,Object> params, Model model, HttpServletRequest request) {
		String areaId = request.getParameter("areaId");
		if (areaId == null) {
			areaId = "0";
		}
		params.put("pids", areaId);
		List<Integer> ids = regionService.getAllTeamByUserRole(params);
		if (CollectionUtils.isEmpty(ids)) {
			ids.add(-1);
		}
		params.clear();
		params.put("status", 1);
		params.put("ids", ids);
		List<TeamDO> teamList = teamService.list(params);
		model.addAttribute("teamList", teamList);//团队
		params.clear();
		params.put("parentRegionCode", areaId);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
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
		
		params.put("teamId", teamId);
		List<Map<String,Object>> voluntList = volunteerService.voluntList(params);
		int count = volunteerService.count(null);
		model.addAttribute("voluntList", voluntList);//志愿者
		model.addAttribute("voluntCount", count);
		params.clear();
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		model.addAttribute("teamId", teamId);
		
		UserDO user = ShiroUtils.getUser();
		if (user != null ) {
			params.clear();
			params.put("userId", user.getUserId());
			params.put("teamId",teamId);
			params.put("type",1);
			params.put("status", 1);
			NameDO name = nameDao.applyStatus(params);
			//1申请中 2请申请
			if(name!=null) {
			model.addAttribute("status", "1");
			model.addAttribute("nameId",name.getId());
			}else {
				VolunteerDO vol = volunteerService.getVo(user.getUserId());
				if(vol!=null) {
					if(vol.getTeamId().equals(teamId)) {
						model.addAttribute("status", "3");
					}
				}else {
					model.addAttribute("status", "2");
				}
			}
		}
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
	
	@ResponseBody
	@RequestMapping("team/apply")
	public String apply(Integer id) {
		JSONObject dataInfo = new JSONObject();
		Integer status = 0;
		try {
			UserDO user = ShiroUtils.getUser();
			if (user == null) {
				dataInfo.put("status", "2");//还没登录
				return dataInfo.toString();
			}
			
			boolean flag = volunteerService.isVo(user.getUserId());
			if (!flag) {
				dataInfo.put("status", "3");
				dataInfo.put("url", "/pc/attestation?teamId="+id+"&type="+"1");
				return dataInfo.toString();
			}
			VolunteerDO volunteer = volunteerService.getVo(user.getUserId());
			if(volunteer.getTeamId()==null||volunteer.getTeamId()==-1) {
				user = userDao.get(user.getUserId());
				NameDO name = userToObject.userToverify(user, id);
				name.setType(1);
				status = nameDao.save(name);
			}else {
				status = 4;
			}
		}catch(Exception e) {
			status = 5;
			e.printStackTrace();
		}
		dataInfo.put("status", status);
		return dataInfo.toString();
	}
	
	@ResponseBody
	@RequestMapping("team/remove")
	public String remove(Integer id) {
		JSONObject dataInfo = new JSONObject();
		Integer status = 0;
		try {
			UserDO user = ShiroUtils.getUser();
			if (user == null) {
				dataInfo.put("status", "2");//还没登录
				return dataInfo.toString();
			}
				status = nameDao.remove(id);
		}catch(Exception e) {
			status = 3;
			e.printStackTrace();
		}
		dataInfo.put("status", status);
		return dataInfo.toString();
	}
	
	
}
