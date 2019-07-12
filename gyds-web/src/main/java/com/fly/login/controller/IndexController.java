package com.fly.login.controller;

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
import com.fly.helpCenter.domain.CenterDO;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.helpCenter.service.CenterService;
import com.fly.helpCenter.service.TypeTitleService;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.InfoService;
import com.fly.system.servicce.RegionService;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.utils.JudgeIsMoblieUtil;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;

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
	@Autowired
	private VolunteerService volunteerService;
	@Autowired
	private CenterService centerService;
	@Autowired
	private TypeTitleService typeTitleService;

	
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
		if (newList.size() < 10) {
			model.addAttribute("newList", newList);//新闻资讯status
		} else {
			List<InfoDO> subList = newList.subList(0, 10); //展示前10条
			model.addAttribute("newList", subList);//新闻资讯status
		}
		params.clear();
		params.put("examineStatus",1);
		List<ActivityDO> actList = activityService.list(params);//活动
		model.addAttribute("actList", actList);//团队活动
		params.clear();
		
		List<TeamDO> teamList = teamService.list(params);
		model.addAttribute("teamList", teamList);//团队
		params.put("auditStatus",1);//
		
		List<VolunteerDO> voluntList = volunteerService.list(params);
		int count = volunteerService.count(null);
		model.addAttribute("voluntList", voluntList);//志愿者
		model.addAttribute("voluntCount", count);
		
		params.clear();
		List<TypeTitleDO> list2 = typeTitleService.list(params);
		for (TypeTitleDO type : list2) {
			params.put("helpTypeId", type.getId());
			List<CenterDO> list = centerService.list(params);
			type.setCenter(list);
		}
		model.addAttribute("centerList", list2);
		String isMoblie = "/pc/index";
		if(JudgeIsMoblieUtil.judgeIsMoblie(request)) {//判断是否为手机
			isMoblie= "/moblie/index";
		}
		return isMoblie;
	}
	
	




	
}
