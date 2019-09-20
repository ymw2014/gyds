package com.fly.pc.team.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.fly.activity.domain.ActivityDO;
import com.fly.activity.service.ActivityService;
import com.fly.adv.domain.AdvertisementDO;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.index.service.IndexService;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.InfoService;
import com.fly.pc.utils.PageUtils;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.proxybusi.service.ProxybusiService;
import com.fly.signin.dao.SigninDao;
import com.fly.system.dao.UserDao;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.utils.DateUtils;
import com.fly.utils.Dictionary;
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
	@Autowired
	private ProxybusiService proxybusiService;
	@Autowired
	private SigninDao signinDao;
	
	@RequestMapping("teamList")
	public String list(@RequestParam Map<String,Object> params, Model model, HttpServletRequest request) {
		String areaId = request.getParameter("areaId");
		if (areaId == null) {
			areaId = "0";
		}
		params.put("pids", areaId);
		String ids = regionService.getTeamAndAreaByUserRole(Long.valueOf(areaId));
		params.clear();
		params.put("ids", ids);
		params.put("offset", 0);
		params.put("limit", 10);
		List<TeamDO> teamList = teamService.list(params);
		model.addAttribute("teamList", teamList);//团队
		params.clear();
		params.put("parentRegionCode", areaId);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		model.addAttribute("areaId", areaId);
		return "pc/teamList";
	}
	
	@RequestMapping("queryTeamList")
	@ResponseBody
	public R teamList(@RequestParam Map<String,Object> params,HttpServletRequest request) {
		R r = new R();
		PageUtils page = new PageUtils(params);
		String areaId = request.getParameter("areaId");
		if (areaId == null) {
			areaId = "0";
		}
		params.put("pids", areaId);
		String ids = regionService.getTeamAndAreaByUserRole(Long.valueOf(areaId));
		page.put("ids", ids);
		List<TeamDO> teamList = teamService.list(page);
		r.put("code","0");
		r.put("teamList",teamList);
		return r;
	}
	
	
	@RequestMapping("teamDetail")
	public String detail(@RequestParam Map<String,Object> params, Model model, @RequestParam Long  teamId,@RequestParam Integer areaId) {
		model.addAttribute("areaId",areaId);
		if (areaId == null) {
			areaId = 0;
		}
		
		params.clear();
		params.put("status", 1);
		params.put("teamId", teamId);
		params.put("offset", 0);
		params.put("limit", 10);
		List<InfoDO> newList = infoService.list(params);
		model.addAttribute("newList", newList);//新闻资讯status
		params.clear();
		params.put("examineStatus",1);
		params.put("teamId", teamId);
		params.put("offset", 0);
		params.put("limit", 10);
		TeamDO team = teamService.get(teamId);
		model.addAttribute("team", team);//团队信息
		params.put("startTime", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		List<ActivityDO> actList = activityService.list(params);//活动
		model.addAttribute("actList", actList);//团队活动
		params.clear();
		params.put("teamId", teamId);
		params.put("offset", 0);
		params.put("limit", 10);
		List<Map<String,Object>> voluntList = volunteerService.voluntList(params);
		params.clear();
		params.put("teamId", teamId);
		Integer count = volunteerService.count(params);
		Integer newCount = infoService.count(params);
		Integer actCount = activityService.count(params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("starteTime", DateUtils.weeHours(new Date(), 0));
		map.put("endTime", DateUtils.weeHours(new Date(), 1));
		map.put("teamId", teamId);
		Integer sigCount = signinDao.count(map);
		
		model.addAttribute("voluntList", voluntList);//志愿者
		model.addAttribute("voluntCount", count);//本团志愿者总数
		model.addAttribute("newCount", newCount);//本团发布新闻总数
		model.addAttribute("actCount", actCount);//本团发布活动总数
		model.addAttribute("sigCount", sigCount);//本团发布活动总数
		params.clear();
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		model.addAttribute("teamId", teamId);
		UserDO user = ShiroUtils.getUser();
		if (user != null ) {
			VolunteerDO vol = volunteerService.getVo(user.getUserId());
			if(vol!=null) {
				if(vol.getTeamId().equals(teamId)) {
					//已是本团成员
					model.addAttribute("status", "3");
				}
				//不是本团团员
				model.addAttribute("status", "2");
			}else {
				//不是志愿者
				model.addAttribute("status", "2");
			}
			params.clear();
			params.put("userId", user.getUserId());
			params.put("teamId",teamId);
			params.put("type",1);
			params.put("status", 1);
			NameDO name = nameDao.applyStatus(params);
			//1申请中 2请申请
			if(name!=null) {
				//1:已申请
			model.addAttribute("status", "1");
			model.addAttribute("nameId",name.getId());
			}
		}
		List<AdvertisementDO> dataList = indexService.getCenterAdvList(teamId, Dictionary.AdvPosition.TUANDUI_XIANGQING);
		model.addAttribute("adv1", dataList.get(0));
		model.addAttribute("adv2", dataList.get(1));
		model.addAttribute("adv3", dataList.get(2));
		model.addAttribute("adv4", dataList.get(3));
		model.addAttribute("adv5", dataList.get(4));
		model.addAttribute("adv6", dataList.get(5));
		model.addAttribute("areaId", areaId);
		return "pc/teamDetail";
	}
	
	
	@ResponseBody
	@RequestMapping("team/query")
	public String query(String areaId, String satuts) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pids", areaId);
		String ids = regionService.getTeamAndAreaByUserRole(Long.valueOf(areaId));
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
	public R apply(Long id,HttpServletRequest request) {
		R r=new R();
		try {
			UserDO user = ShiroUtils.getUser();
			Map<String, Object> params=new HashMap<>(16);
			if (user == null) {
				r.put("code", 2);
				r.put("msg", "您还没登录，请先登录");
				r.put("url", "/login");
				return r;
			}
			params.put("userId", user.getUserId());
			List<ProxybusiDO> proList = proxybusiService.list(params);
			if(proList!=null&&proList.size()>0) {
				r.put("code", 2);
				r.put("msg", "您已经是代理,无需入团");
				return r;
			}
			boolean flag = volunteerService.isVo(user.getUserId());
			if (!flag) {
				r.put("code", "3");
				r.put("msg", "您还不是志愿者，请先申请志愿者");
				r.put("url", "/pc/attestation?teamId="+id+"&type="+"1");
				return r;
			}
			VolunteerDO volunteer = volunteerService.getVo(user.getUserId());
			if(volunteer.getTeamId()==null||volunteer.getTeamId()==-1) {
				user = userDao.get(user.getUserId());
				NameDO name = userToObject.userToverify(user, id);
				name.setType(1);
				if(nameDao.save(name)>0) {
					r.put("code", 1);
					r.put("msg", "操作成功");
				}
			}else {
				r.put("code", "4");
				r.put("msg", "报名失败，您已是团队成员");
			}
		}catch(Exception e) {
			r.put("code", "5");
			r.put("msg", "操作失败");
			e.printStackTrace();
		}
		return r;
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
