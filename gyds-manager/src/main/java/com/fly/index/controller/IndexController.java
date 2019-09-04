package com.fly.index.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.activity.domain.ActivityDO;
import com.fly.activity.service.ActivityService;
import com.fly.adv.domain.AdvertisementDO;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.index.service.IndexService;
import com.fly.index.utils.JudgeIsMoblieUtil;
import com.fly.index.utils.ShowPosition;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.InfoService;
import com.fly.sys.domain.SetupDO;
import com.fly.sys.service.SetupService;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.utils.DateUtils;
import com.fly.utils.Dictionary;
import com.fly.utils.JSONUtils;
import com.fly.utils.R;
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
	private IndexService indexService;
	
	@Autowired
	private SetupService setupService;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	
	/**
	 * 验证是PC用户还是手机用户
	 * @return 
	 */
	@RequestMapping("/")
	public String indexValidate(@RequestParam Map<String,Object> params, HttpServletRequest request,Model model) {
		String code = request.getParameter("areaId");
		Long areaId = 0L;
		if (!StringUtils.isEmpty(code)) {
			areaId =Long.parseLong(request.getParameter("areaId"));
		}
		if(params.get("areaId")!=null) {
			params.put("parentRegionCode", params.get("areaId"));
		}else {
			areaId=0L;
			params.put("parentRegionCode",0);
		}
		params.put("regionType",1);
		List<RegionDO> areaList1 = regionService.list(params);
		List<RegionDO> areaList=new ArrayList<RegionDO>();
		RegionDO retion = regionService.get(areaId);
		retion.setSelected(true);
		if(retion.getRegionCode()!=0) {
			areaList.add(retion);
		}
		areaList.addAll(areaList1);
		model.addAttribute("areaList", areaList);//全国包含的省
		params.put("pids", areaId);
		String ids = regionService.getTeamAndAreaByUserRole(Long.valueOf(areaId));
		params.clear();
		params.put("ids", ids);
		List<TeamDO> teamList = teamService.list(params);
		model.addAttribute("teamList", teamList);//团队
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("ids", ids);
		params.put("sort","n.is_top desc,n.public_time desc");
		List<InfoDO> newList = infoService.list(params);
		model.addAttribute("newList", newList);//新闻资讯status
		params.clear();
		params.put("examineStatus",1);
		params.put("startTime", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("ids", ids);
		List<ActivityDO> actList = activityService.list(params);//活动
		model.addAttribute("actList", actList);//团队活动
		params.clear();
		params.put("isVo",1);//
		params.put("offset", 0);
		params.put("limit", 16);
		params.put("ids", ids);
		List<Map<String,Object>> voluntList = volunteerService.voluntList(params);
		int count = volunteerService.count(null);
		model.addAttribute("voluntList", voluntList);//志愿者
		model.addAttribute("voluntCount", count);
		params.clear();
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		List<AdvertisementDO> dataList = indexService.getIndexAdvList(areaId,Dictionary.AdvPosition.SHOUYE,params);//获取首页广告
		model.addAttribute("areaId", areaId);
		model.addAttribute("adv1", dataList.get(0));
		model.addAttribute("adv2", dataList.get(1));
		model.addAttribute("adv3", dataList.get(2));
		model.addAttribute("adv4", dataList.get(3));
		model.addAttribute("adv5", dataList.get(4));
		model.addAttribute("adv6", dataList.get(5));
		String isMoblie = "pc/index";
		if(JudgeIsMoblieUtil.judgeIsMoblie(request)) {//判断是否为手机
			isMoblie= "mobile/index";
		}
		return isMoblie;
	}
	
	@RequestMapping("/setup")
	@ResponseBody
	public R getSetup() {
		SetupDO setup = setupService.list(new HashMap<>(16)).get(0);
		Map<String, Object> map = JSONUtils.jsonToMap(JSONUtils.beanToJson(setup));
		UserDO user = ShiroUtils.getUser();
		if(user!=null) {
			logger.info("logger:userDO 对象==="+user.toString());
			map.put("name", user.getName()==null?user.getNikeName():user.getName());
			map.put("userName", user.getUsername());
			map.put("head_img", user.getHeadImg());
		}
		R r=new R();
		r.put("code", 0);
		r.put("sucess", map);
		return r;
	}
}
