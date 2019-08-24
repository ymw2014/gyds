package com.fly.pc.persion.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fly.common.controller.BaseController;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.proxybusi.service.ProxybusiService;
import com.fly.system.dao.UserDao;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.dao.TeamDao;
import com.fly.team.dao.TeamTypeDao;
import com.fly.team.domain.TeamDO;
import com.fly.team.domain.TypeDO;
import com.fly.team.service.TeamService;
import com.fly.utils.JSONUtils;
import com.fly.utils.R;
import com.fly.utils.userToObject;
import com.fly.verifyName.dao.NameDao;
import com.fly.verifyName.domain.NameDO;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;

@Controller
@RequestMapping("/pc/regTeam")
public class PersionTeamController extends BaseController{
	@Autowired
	private TeamTypeDao typeDao;
	@Autowired
	private RegionService regionService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private VolunteerService volunteerService;
	@Autowired
	private NameDao nameDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProxybusiService proxybusiService;


	@RequestMapping("/createTeam")
	public String createTeam(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		UserDO user = userDao.get(ShiroUtils.getUserId());
		if(user==null) {
			return "redirect:/login";
		}
		params.put("userId", user.getUserId());
		List<ProxybusiDO> proxylist = proxybusiService.list(params);
		if(!CollectionUtils.isEmpty(proxylist)) {
			model.addAttribute("message", "您已经是代理商，代理商不能建团，感谢您的参与" );
			return "pc/message";
		}
		params.put("userId", user.getUserId());
		params.put("type",2);
		params.put("status", 1);
		NameDO name = nameDao.applyStatus(params);
		if(name!=null) {
			if(name.getStatus()==1) {
				model.addAttribute("message", "您已提交过建团申请,不可重复提交!请耐心等待!");
				return "pc/message";
			} 
		}	

		if (user.getIsIdentification()==null||user.getIsIdentification()!=1) {
			Map<String, Object> map=new HashMap<>(16);
			map.put("parentRegionCode", 0);
			List<RegionDO> areaList = regionService.list(map);
			List<TypeDO> type = typeDao.list1();
			model.addAttribute("teamType", type);
			model.addAttribute("areaList", areaList);
			model.addAttribute("type", 2);
			return "pc/attestationTeam";
		}

		params.clear();
		params.put("userId", user.getUserId());
		List<TeamDO> list = teamDao.list(params);
		if(list.size()>0) {
			TeamDO team = list.get(0);
			List<String> collect = typeDao.list1().stream().filter(bean -> bean.getId() == team.getTeamType()).map(bean -> bean.getTypeName()).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(collect)) {
				team.setTypeName(collect.get(0));
			}
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
		VolunteerDO Vol = volunteerService.getVo(user.getUserId());
		if(Vol.getTeamId()!=null&&Vol.getTeamId()!=-1){
			model.addAttribute("message", "您已是团队成员");
			return "pc/message";
		}
		List<TypeDO> type = typeDao.list1();
		model.addAttribute("type", type);
		params.put("regionType",1);
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
		params.put("regionType",1);
		params.put("parentRegionCode",para.get("area"));
		List<RegionDO> regList = regionService.list(params);
		Long code= regList.get(0).getRegionCode();
		params.clear();
		params.put("regionType",1);
		params.put("parentRegionCode",code);
		List<RegionDO> areaList = regionService.list(params);
		if(areaList.size()>0) {
			r.put("areaList", areaList);
			code= areaList.get(0).getRegionCode();
			params.clear();
			params.put("regionType",1);
			params.put("parentRegionCode",code);
			List<RegionDO> streetList = regionService.list(params);
			if(areaList.size()>0) {
				r.put("streetList", streetList);
			}
		}
		r.put("code", 0);
		r.put("regList", regList);
		return r;
	}
	@PostMapping("/savaTeam")
	@ResponseBody
	public R savaTeam(TeamDO team,String reg) {
		Integer i = 0 ;
		String flag = "0";
		Long regCode = null;
		UserDO user = ShiroUtils.getUser();
		if(user!=null) {
			user.getUserId();
			user = userDao.get(user.getUserId());
			NameDO name = userToObject.userToverify(user, null);
		R r = countCost(team.getTeamType());
		if(r.get("price")!=null||r.get("price").toString()!="0") {
			i = deductMoney(r);
			if(i==1) {
				r.put("orderType", 2);
				r.put("expIncType", 7);
				i = creadOrder(r);
				if(i>0) {
					flag="1";
					name.setOrderId(i);
				}
			}
		}else {
			flag="1";
		}
		if("1".equals(flag)) {
			if(reg!=null) {
				String [] regStr = reg.split(",");
				regCode = Long.parseLong(regStr[regStr.length-1]);
				team.setRegCode(regCode);
			}
				/*
				 * Integer id = randomCode(team.getRegCode()); team.setId(id);
				 */
				team.setTeamIntroduce(team.getTeamIntroduce().trim());
				team.setUserId(user.getUserId());
				name.setText(JSONUtils.beanToJson(team));
				name.setType(2);
				if(nameDao.save(name)>0);
				return R.ok();
			}
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

	@PostMapping("/queryCost")
	@ResponseBody
	public R queryCost(@RequestParam Map<String,Object> para) {
		return countCost(Integer.valueOf(para.get("id").toString()));
	}
}
