package com.fly.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.screen.dao.ScreenDao;
import com.fly.utils.DateUtils;

@Controller
@RequestMapping("/pc/screen")
public class ScreenController {
	@Autowired
	private ScreenDao screen;
	
	@RequestMapping("/queryScreen")
	public String OrgSurvey(Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> teamVo=screen.getTeamByVolSurvey(map);
		List<Map<String, Object>> teamNews=screen.getNewsByTeamSurvey(map);
		List<Map<String, Object>> sexCount = screen.getSexCount(map);
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
		
		//获取 去年第一天与最后一天
		model.addAttribute("newsCount1", newsMon1);
		//获取 今年第一天与最后一天
		model.addAttribute("newsCount", newsMon);
		
		//获取本个月创建团队第一天到当前时间
		model.addAttribute("creTeam1", creTeam1);
		//获取上个月创建团队第一天到最后一天
		model.addAttribute("creTeam", creTeam);
		//获取本个月发布活动第一天到当前时间
		model.addAttribute("creAct1", creAct1);
		//获取上个月发布活动第一天到最后一天
		model.addAttribute("creAct", creAct);
		
		model.addAttribute("sexCount", sexCount);
		model.addAttribute("teamVo", teamVo);
		model.addAttribute("teamNews", teamNews);
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
}
