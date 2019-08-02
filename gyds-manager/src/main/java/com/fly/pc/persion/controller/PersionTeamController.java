package com.fly.pc.persion.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.fly.team.dao.TeamDao;
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
	@Autowired
	private TeamDao teamDao;
	
	@RequestMapping("/createTeam")
	public String createTeam(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		Long userId = ShiroUtils.getUserId(); 
		params.put("status", 2);
		params.put("userId", userId);
		List<TeamDO> list = teamDao.is_apply(params);
		if(list.size()>0) {
			TeamDO team = list.get(0);
			if(0 == team.getStatus()) {
				model.addAttribute("message", "您已提交过建团申请,不可重复提交!请耐心等待!");
				return "pc/message";
			}else if(1 == team.getStatus()){
				String imgStr = team.getTeamImg();
				String[] img = imgStr.split(",");
				List<String> imgList = new ArrayList<String>();
				for(int i =0;i<img.length;i++) {
					imgList.add(img[i]);
				}
				model.addAttribute("imgList", imgList);
				model.addAttribute("team", team);
				return "pc/teamInfo";
			}
		}
		
		List<TypeDO> type = typeDao.list1();
		model.addAttribute("type", type);
		params.put("parentRegionCode",0);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);//全国包含的省
		return "pc/createTeam";
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
	public R savaTeam(TeamDO team,String reg) {
		Integer regCode = null;
		if(reg!=null) {
			String [] regStr = reg.split(",");
			regCode = Integer.parseInt(regStr[regStr.length-1]);
			team.setRegCode(regCode);
		}
		UserDO user = ShiroUtils.getUser();
		if(user!=null) {
			Integer id = randomCode(team.getRegCode());
			team.setId(id);
			team.setTeamIntroduce(team.getTeamIntroduce().trim());
			team.setUserId(Integer.valueOf(user.getUserId().toString()));
			team.setStatus(0);
			if(teamService.save(team)>0);
			return R.ok();
		}
		return R.error();
	}
	public Integer randomCode(Integer regCode) {
		Integer random =(int) (Math.random()*(999-100+1)+100);
		String strCode = regCode+""+random;
		Integer code = Integer.valueOf(strCode);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",code);
		List<TeamDO> list = teamService.list(map);
		if(list.size()>0) {
			randomCode(regCode);
		}
		return code;
	}

}
