package com.fly.pc.persion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.dao.TeamTypeDao;
import com.fly.team.domain.TeamDO;
import com.fly.team.domain.TypeDO;
import com.fly.team.service.TeamService;
import com.fly.utils.R;

@Controller
@RequestMapping("/pc/regTeam")
public class PersionTeamController {
	@Autowired
	private TeamTypeDao typeDao;
	@Autowired
	private RegionService regionService;
	@Autowired
	private TeamService teamService;
	
	@RequestMapping("/createTeam")
	public String createTeam(Model model) {
		List<TypeDO> type = typeDao.list1();
		model.addAttribute("type", type);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentRegionCode",0);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);//全国包含的省
		return "/pc/createTeam";
	}
	
	@GetMapping("/area")
	@ResponseBody
	public R area(@RequestParam Map<String,Object> para) {
		R r = new R();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentRegionCode",para.get("area"));
		List<RegionDO> regList = regionService.list(params);
		Integer code= regList.get(0).getRegionCode();
		params.clear();
		params.put("parentRegionCode",code);
		List<RegionDO> areaList = regionService.list(params);
		if(areaList.size()>0) {
		r.put("areaList", areaList);
		}
		r.put("code", 0);
		r.put("regList", regList);
		return r;
	}
	@PostMapping("/savaTeam")
	@ResponseBody
	public R savaTeam(TeamDO team) {
		R r = new R();
		UserDO user = ShiroUtils.getUser();
		if(user!=null) {
			team.setUserId(Integer.valueOf(user.getUserId().toString()));
			team.setStatus(0);
			teamService.save(team);
			r.put("code", 0);
			
		}
		
		return r;
	}

}
