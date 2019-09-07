package com.fly.screen;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.count.utils.NumberUtils;
import com.fly.screen.dao.ScreenDao;
import com.fly.system.service.RegionService;
import com.fly.utils.DateUtils;

@Controller
@RequestMapping("/pc/screen")
public class ScreenController {
	@Autowired
	private ScreenDao screen;
	@Autowired
	private RegionService regionService;
	
	@RequestMapping("/queryScreen")
	public String OrgSurvey(Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		//实名志愿者数
		List<Map<String, Object>> teamVo=screen.getTeamByVolSurvey(map);
		//活动发布量
		List<Map<String, Object>> teamNews=screen.getNewsByTeamSurvey(map);
		//成员概况
		List<Map<String, Object>> sexCount = screen.getSexCount(map);
		//计算男女比例
		Map<String, Object> userCount = screen.getUserCount(map);
		sexCount.get(0).put("ratioSex", NumberUtils.getPercent(sexCount.get(0).get("c"),userCount.get("value")));
		sexCount.get(1).put("ratioSex", NumberUtils.getPercent(sexCount.get(1).get("c"),userCount.get("value")));
		
		//年龄区间
		map.put("start", 0);
		map.put("end", 25);
		List<Map<String, Object>> ageCount = screen.getAgeCount(map);
		//年龄区间参加活动次数
		List<Map<String, Object>> ageActCount = screen.getAgeAndActCount(map);
		
		
		//省级渠道概况
		map.put("regionLevel", 1);
		Map<String, Object> proCount1 =screen.getProCount(map);
		Map<String, Object> cuxCount1 =screen.getCuxCount(map);
		model.addAttribute("proCount1", proCount1);
		model.addAttribute("cuxCount1", cuxCount1);
		model.addAttribute("ratio1", NumberUtils.getPercent(proCount1.get("value"),cuxCount1.get("value")));
		//市级渠道概况
		map.put("regionLevel", 2);
		Map<String, Object> proCount2 =screen.getProCount(map);
		Map<String, Object> cuxCount2 =screen.getCuxCount(map);
		model.addAttribute("proCount2", proCount2);
		model.addAttribute("cuxCount2", cuxCount2);
		model.addAttribute("ratio2", NumberUtils.getPercent(proCount2.get("value"),cuxCount2.get("value")));
		//县区级渠道概况
		map.put("regionLevel", 3);
		Map<String, Object> proCount3 =screen.getProCount(map);
		Map<String, Object> cuxCount3 =screen.getCuxCount(map);
		model.addAttribute("proCount3", proCount3);
		model.addAttribute("cuxCount3", cuxCount3);
		model.addAttribute("ratio3", NumberUtils.getPercent(proCount3.get("value"),cuxCount3.get("value")));
		//街道级渠道概况
		map.put("regionLevel", 4);
		Map<String, Object> proCount4 =screen.getProCount(map);
		Map<String, Object> cuxCount4 =screen.getCuxCount(map);
		model.addAttribute("proCount4", proCount4);
		model.addAttribute("cuxCount4", cuxCount4);
		model.addAttribute("ratio4", NumberUtils.getPercent(proCount4.get("value"),cuxCount4.get("value")));
		
		
		//团队入驻数
		Map<String, Object> createTeamCount = screen.getCreateTeamSurvey(map);
		//实名成员数
		Map<String, Object> volCount = screen.getVolCount(map);
		//活动发布量
		Map<String, Object> publicActCount = screen.getPublicActivitySurvey(map);
		//咨询发布量
		Map<String, Object> newsCount = screen.getPublicNewsCount(map);
		Date star = DateUtils.weeHours(new Date(),0);
		Date end = DateUtils.weeHours(new Date(),1);
		map.put("starteTime",DateUtils.format(star));
		map.put("endTime",DateUtils.format(end));
		//今日签到
		Map<String, Object> poinCount = screen.getPoinCount(map);
		
		
		
		List<Map<String, Object>> pointsCountList = new ArrayList<Map<String,Object>>();
		Map<String, Object> pointsCountMap = new HashMap<String, Object>();
		
		//签到次数
		map.put("star", 1000);
		map.put("end", 999999999);
		List<Map<String, Object>> pointsCount=screen.getPointsCount(map);
		if(pointsCount.isEmpty()) {
			pointsCountMap.put("value", 0);
		}else {
			pointsCountMap.put("value",pointsCount.size());
		}
		pointsCountMap.put("name", "1000次以上");
		pointsCountList.add(pointsCountMap);
		
		map.put("star", 365);
		map.put("end", 1000);
		pointsCountMap = new HashMap<String, Object>();
		pointsCount = screen.getPointsCount(map);
		if(pointsCount.isEmpty()) {
			pointsCountMap.put("value", 0);
		}else {
			pointsCountMap.put("value",pointsCount.size());
		}
		pointsCountMap.put("name", "365~1000次");
		pointsCountList.add(pointsCountMap);
		
		map.put("star", 201);
		map.put("end", 365);;
		pointsCountMap = new HashMap<String, Object>();
		pointsCount = screen.getPointsCount(map);
		if(pointsCount.isEmpty()) {
			pointsCountMap.put("value", 0);
		}else {
			pointsCountMap.put("value",pointsCount.size());
		}
		pointsCountMap.put("name", "201~365次");
		pointsCountList.add(pointsCountMap);
		
		map.put("star", 31);
		map.put("end", 200);
		pointsCountMap = new HashMap<String, Object>();
		pointsCount = screen.getPointsCount(map);
		if(pointsCount.isEmpty()) {
			pointsCountMap.put("value", 0);
		}else {
			pointsCountMap.put("value",pointsCount.size());
		}
		pointsCountMap.put("name", "31~200次");
		pointsCountList.add(pointsCountMap);
		
		map.put("star", 0);
		map.put("end", 30);
		pointsCountMap = new HashMap<String, Object>();
		pointsCount = screen.getPointsCount(map);
		if(pointsCount.isEmpty()) {
			pointsCountMap.put("value", 0);
		}else {
			pointsCountMap.put("value",pointsCount.size());
		}
		pointsCountMap.put("name", "0~30次");
		pointsCountList.add(pointsCountMap);
		
		
		
		//获取上个月创建团队第一天到最后一天
		map = DateUtils.firstLastDay();
		Map<String, Object> creTeam = screen.getCreateTeamSurvey(map);
		//获取上个月发布活动第一天到最后一天
		Map<String, Object> creAct = screen.getPublicActivitySurvey(map);
		//获取上个月发布咨询第一天到最后一天
		Map<String, Object> newsMon = screen.getPublicNewsCount(map);
		
		
		map.clear();
		map = DateUtils.firstLastDay1();
		
		//获取本个月创建团队第一天到当前时间
		Map<String, Object> creTeam1 = screen.getCreateTeamSurvey(map);
		//获取本个月发布活动第一天到当前时间
		Map<String, Object> creAct1 = screen.getPublicActivitySurvey(map);
		//获取本个月发布咨询第一天到当前时间
		Map<String, Object> newsMon1 = screen.getPublicNewsCount(map);
		
		//获取本个月发布咨询第一天到当前时间
		model.addAttribute("newsCount1", newsMon1);
		//获取上个月发布咨询第一天到最后一天
		model.addAttribute("newsCount", newsMon);
		
		//获取本个月创建团队第一天到当前时间
		model.addAttribute("creTeam1", creTeam1);
		//获取上个月创建团队第一天到最后一天
		model.addAttribute("creTeam", creTeam);
		//获取本个月发布活动第一天到当前时间
		model.addAttribute("creAct1", creAct1);
		//获取上个月发布活动第一天到最后一天
		model.addAttribute("creAct", creAct);
		
		
		model.addAttribute("pointsCountList", pointsCountList);
		model.addAttribute("ageActCount", ageActCount);
		model.addAttribute("ageCount", ageCount);
		model.addAttribute("sexCount", sexCount);
		model.addAttribute("teamVo", teamVo);
		model.addAttribute("teamNews", teamNews);
		
		model.addAttribute("createTeamCount", createTeamCount.get("value"));
		model.addAttribute("volCount", volCount.get("value"));
		model.addAttribute("publicActCount", publicActCount.get("value"));
		model.addAttribute("newsCounts", newsCount.get("value"));
		model.addAttribute("poinCount", poinCount.get("value"));
		return "/pc/screen" ;
	}
	
	@RequestMapping("/ActTypeScreen")
	@ResponseBody
	public List<Map<String, Object>> queryActTypeScreen(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> orgSurvey = screen.getPublicActTypeSurvey(map);
		return orgSurvey ;
	}
	@RequestMapping("/OrgSurvey")
	@ResponseBody
	public List<Map<String, Object>> queryScreen(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> orgSurvey = screen.getOrgSurvey(map);
		return orgSurvey ;
	}
	@RequestMapping("/NewsMon")
	@ResponseBody
	public Map<String, Object> queryNewsMon(){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> outMap = new HashMap<String, Object>();
		//获取 去年第一天与最后一天
		map=DateUtils.firstLastYearDay();
		List<Map<String, Object>> newsMon = screen.getPublicNewsMon(map);
		//获取 今年第一天与最后一天
		map=DateUtils.firstLastYearDay1();
		List<Map<String, Object>> newsMon1 = screen.getPublicNewsMon(map);
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> list1 = new ArrayList<Integer>();
		int o = 0;
		Object n= 0;
		for (int i = 0; i < 12; i++) {
				if(o<newsMon.size()) {
					n = newsMon.get(o).get("name");
				}
				if(n.equals(i+1)) {
					list.add(Integer.parseInt(newsMon.get(o).get("value").toString()));
					o++;
				}else {
					list.add(0);
				}
		}	
		 o = 0;
		 n=null;
		for (int i = 0; i < 12; i++) {
				if(o<newsMon1.size()) {
					n = newsMon1.get(o).get("name");
				}
				if(n.equals(i+1)) {
					list1.add(Integer.parseInt(newsMon1.get(o).get("value").toString()));
					o++;
				}else {
					list1.add(0);
				}
		}	
		//去年数据
		outMap.put("newList", list);
		//今年数据
		outMap.put("newList1", list1);
		return outMap ;
	}
	
	@RequestMapping("/PoliticsSurvey")
	@ResponseBody
	public List<Map<String, Object>> queryPoliticsSurvey(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> politicsSurvey = screen.getPoliticsSurvey(map);
		return politicsSurvey ;
	}
	@RequestMapping("/EducationSurvey")
	@ResponseBody
	public List<Map<String, Object>> queryEducationSurvey(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> educationSurvey = screen.getEducationSurvey(map);
		return educationSurvey ;
	}
	@RequestMapping("/PostSurvey")
	@ResponseBody
	public List<Map<String, Object>> queryPostSurvey(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> postSurvey = screen.getPostSurvey(map);
		return postSurvey ;
	}
	@RequestMapping("/AgeSurvey")
	@ResponseBody
	public List<Map<String, Object>> queryAgeSurvey(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> ageCount = screen.getAgeCount(map);
		return ageCount ;
	}
	
	@RequestMapping("/MapSurvey")
	@ResponseBody
	public Map<String, Object> queryMapSurvey(Long id){
		Map<String, Object> count = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String ids = regionService.getTeamAndAreaByUserRole(id);
		map.put("ids", ids);
		Map<String, Object> createTeamCount = screen.getCreateTeamSurvey(map);
		Map<String, Object> volCount = screen.getVolCount(map);
		Map<String, Object> publicActCount = screen.getPublicActivitySurvey(map);
		Map<String, Object> newsCount = screen.getPublicNewsCount(map);
		Date star = DateUtils.weeHours(new Date(),0);
		Date end = DateUtils.weeHours(new Date(),1);
		map.put("starteTime",DateUtils.format(star));
		map.put("endTime",DateUtils.format(end));
		Map<String, Object> poinCount = screen.getPoinCount(map);
		
		count.put("createTeamCount", createTeamCount.get("value"));
		count.put("volCount", volCount.get("value"));
		count.put("publicActCount", publicActCount.get("value"));
		count.put("newsCount", newsCount.get("value"));
		count.put("poinCount", poinCount.get("value"));
		return count ;
	}
}
