package com.fly.login.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.fly.adv.domain.AdvertisementDO;
import com.fly.adv.service.AdvertisementService;
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
	@Autowired
	private AdvertisementService advertisementService;

	
	/**
	 * 验证是PC用户还是手机用户
	 * @return 
	 */
	@RequestMapping("/")
	public String indexValidate(@RequestParam Map<String,Object> params, HttpServletRequest request,Model model) {
		Object areaId = params.get("areaId");
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
		params.put("offset", 0);
		params.put("limit", 10);
		//优先置顶排序,
		params.put("sort","n.is_top desc,n.public_time desc");
		List<InfoDO> newList = infoService.list(params);
		model.addAttribute("newList", newList);//新闻资讯status
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
		List<AdvertisementDO> dataList=new ArrayList<AdvertisementDO>();
		if(areaId!=null) {
			RegionDO region = regionService.get(Integer.parseInt(areaId.toString()));
			params.put("positionNum", 1);
			params.put("regionCode", region.getRegionCode());//所选择区域首页广告
    		List<AdvertisementDO> allList = advertisementService.list(params);
    		Integer size=allList.size();
			switch(region.getRegionLevel()){  
		    case 0:  //全国
	    		if(allList.size()>=5) {
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (5-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				allList.add(adv);//广告位不足五个添加广告展示位
					}
	    			dataList=allList;
	    		}
		    	break;
		    case 1:  //省级
	    		if(allList.size()>=4) {//省级代理四个广告位
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (4-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				allList.add(adv);//广告位不足四个添加广告展示位
					}
	    		}
	    		params.put("regionCode", region.getParentRegionCode());//所选择区域首页广告
	    		allList.add(advertisementService.list(params).get(0));
	    		dataList=allList;
		    	break;
		    case 2:  //市级
		    	if(allList.size()>=3) {//省级代理四个广告位
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (3-allList.size()); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				allList.add(adv);//广告位不足四个添加广告展示位
					}
	    		}
		    	
	    		RegionDO proRegion = regionService.get(region.getParentRegionCode());
	    		params.put("regionCode", proRegion.getRegionCode());//所选择区域首页广告
	    		allList.add(advertisementService.list(params).get(0));
	    		params.put("regionCode", proRegion.getParentRegionCode());//所选择区域首页广告
	    		allList.add(advertisementService.list(params).get(0));
	    		dataList=allList;
		    	break;
		    case 3:  //县级
		    	if(allList.size()>=2) {//县级代理两个广告位
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (2-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				allList.add(adv);//广告位不足两个添加广告展示位
					}
	    		}
	    		RegionDO cityRegion = regionService.get(region.getParentRegionCode());
	    		params.put("regionCode", cityRegion.getRegionCode());//市级代理首页广告
	    		AdvertisementDO advs = advertisementService.list(params)==null?new AdvertisementDO():advertisementService.list(params).get(0);
	    		allList.add(advs);
	    		params.put("regionCode", cityRegion.getParentRegionCode());//省级代理首页广告
	    		allList.add(advertisementService.list(params)==null?new AdvertisementDO():advertisementService.list(params).get(0));
	    		RegionDO pubRegion = regionService.get(cityRegion.getParentRegionCode());
	    		params.put("regionCode", pubRegion.getParentRegionCode());//平台首页广告
	    		allList.add(advertisementService.list(params)==null?new AdvertisementDO():advertisementService.list(params).get(0));
	    		dataList=allList;
		    	break;
		    default:  
		    	;
		}
		}
		model.addAttribute("adv1", dataList.get(0));
		model.addAttribute("adv2", dataList.get(1));
		model.addAttribute("adv3", dataList.get(2));
		model.addAttribute("adv4", dataList.get(3));
		model.addAttribute("adv5", dataList.get(4));
		String isMoblie = "/pc/index";
		if(JudgeIsMoblieUtil.judgeIsMoblie(request)) {//判断是否为手机
			isMoblie= "/moblie/index";
		}
		return isMoblie;
	}


	public void validateSwitch(Integer positionNum,Integer regionCode,Integer level ) {
		List<AdvertisementDO> dataList=new ArrayList<AdvertisementDO>();
		Map<String,Object> params=new HashMap<String,Object>();
		switch(positionNum){ 
	    case 1:  //首页
	    	if(level==1) {
	    		params.put("positionNum", positionNum);
	    		params.put("regionCode", regionCode);
	    		List<AdvertisementDO> list = advertisementService.list(params);
	    		if(list.size()>=5) {
	    			dataList=list;
	    		}else {
	    			for (int i = 0; i < (5-list.size()); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				list.add(adv);
					}
	    			dataList=list;
	    		}
	    	}
	    	break;
	    case 2: 
	    	
	    	break;
	    case 3:  
	    	
	    	break;
	    case 4:  
	    	
	    	break;
	    case 5:  
	    	
	    	break;
	    case 6:  
	    	
	    case 7:  
	    	
	    	break;
	    default:  
	    	;
	}
		
	}
	
	




	
}
