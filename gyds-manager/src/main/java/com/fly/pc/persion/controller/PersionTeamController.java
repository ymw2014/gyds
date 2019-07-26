package com.fly.pc.persion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.domain.RegionDO;
import com.fly.system.service.RegionService;
import com.fly.team.dao.TeamTypeDao;
import com.fly.team.domain.TypeDO;
import com.fly.utils.R;

@Controller
@RequestMapping("/pc/regTeam")
public class PersionTeamController {
	@Autowired
	private TeamTypeDao typeDao;
	@Autowired
	private RegionService regionService;
	
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
		List<RegionDO> areaList = regionService.list(params);
		r.put("code", 0);
		r.put("regList", areaList);
		return r;
	}

}
