package com.fly.pc.persion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fly.common.controller.BaseController;
import com.fly.domain.UserDO;
import com.fly.news.service.DynamicService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.service.TeamService;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;


@Controller
public class PersionController extends BaseController{
	@Autowired
	private TeamService teamService;
	@Autowired
	private VolunteerService volunteerService;
	
	@Autowired
	private DynamicService dynamicService;
	
	@RequestMapping("/pc/personalCenter")
	public String getPersionCenter(Model model) {
		UserDO user = ShiroUtils.getUser();
		if(user==null) {
			return "redirect:/admin";
		}
		return "pc/persion_center";
	}
	
	@GetMapping("/pc/persion_main")
	String main(Model model) {
		UserDO user = ShiroUtils.getUser();
		model.addAttribute("ccount", user.getAccount());//余额
		model.addAttribute("platformIntegral", user.getPlatformIntegral());//平台积分
		Map<String, Object> params=new HashMap<String, Object>(16);
		model.addAttribute("user", user);
		params.put("userId", user.getUserId());
		List<VolunteerDO> voluntList = volunteerService.list(params);
		model.addAttribute("team",voluntList.size()==0?null: teamService.get(voluntList.get(0).getTeamId()));
		return "pc/persion_main";
	}
	
	@RequestMapping("/pc/collect")
	public String getCollect(Model model) {
		Map<String, Object> params=new HashMap<>(16);
		params.put("userId", getUserId());
		List<Map<String, Object>> teamList = dynamicService.dyTeamList(params);//关注的团队
		List<Map<String, Object>> actList = dynamicService.dyActList(params);//关注的活动
		List<Map<String, Object>> newList = dynamicService.dyNewList(params);//关注的新闻
		List<Map<String, Object>> voList = dynamicService.dyVoluList(params);//关注的志愿者
		model.addAttribute("teamList", teamList);
		model.addAttribute("actList", actList);
		model.addAttribute("newList", newList);
		model.addAttribute("voList", voList);
		return "pc/collect";
	}
}
