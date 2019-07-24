package com.fly.pc.volunteer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fly.activity.domain.ActivityDO;
import com.fly.activity.service.ActivityService;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.guestlog.domain.GuestlogDO;
import com.fly.guestlog.service.GuestlogService;
import com.fly.news.domain.CommentDO;
import com.fly.news.domain.DynamicDO;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.CommentService;
import com.fly.news.service.DynamicService;
import com.fly.news.service.InfoService;
import com.fly.photo.domain.PhotoDO;
import com.fly.photo.service.PhotoService;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;


@Controller
@RequestMapping("/pc/")
public class PcVolunteerController {
	
	@Autowired
	private RegionService regionService;
	@Autowired
	private VolunteerService volunteerService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private DynamicService dynamicServoce;
	@Autowired
	private InfoService infoService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private GuestlogService logService;
	@Autowired
	private PhotoService PhotoService;

	@RequestMapping("volunteerList")
	public String volunteer(@RequestParam Map<String,Object> params, HttpServletRequest request, 
			Model model,String sort, String order, HttpServletResponse response) throws IOException {
		params.clear();
		String areaId = request.getParameter("regionCode");
		String sex = request.getParameter("sex");
		params.put("parentRegionCode", 0);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		params.clear();
		
		if (StringUtils.isEmpty(areaId)) {
			areaId = "0";
		}
		
		params.put("pids", areaId);
		List<Integer> ids = regionService.getAllTeamByUserRole(params);
		if (CollectionUtils.isEmpty(ids)) {
			ids.add(-1);
		}
		
		params.put("auditStatus",1);//
		params.put("sex",sex);
		params.put("sort", sort);
		params.put("order", order);
		params.put("ids", ids);
		List<VolunteerDO> voluntList = volunteerService.list(params);
		model.addAttribute("voluntList", voluntList);
		model.addAttribute("areaList", areaList);//全国包含的省
		return "pc/volunteerList";
	}
	/**
	    *     志愿者详情
	 * @param params
	 * @param id 会员id
	 * @param model
	 * @return
	 */
	@RequestMapping("volunteerDetail")
	public String volunteerDetail(@RequestParam Map<String,Object> params, Long id, Model model) {
		UserDO user = ShiroUtils.getUser();
		VolunteerDO volunteer = volunteerService.get(user.getUserId());
		VolunteerDO volunteerDO = volunteerService.get(id);
		if (volunteer != null) {//当前用户必须是志愿者才记录
			//添加访客记录
			GuestlogDO log = new GuestlogDO();
			log.setGuestId(volunteer.getId().intValue());
			log.setGuestHeadimg(volunteer.getHeadImg());
			log.setGuestName(user.getName());
			log.setUserId(id.intValue());
			log.setUserHeadimg(volunteerDO.getHeadImg());
			log.setUserName(volunteerDO.getVolunteerName());
			log.setGuestTime(new Date());
			log.setGuestType(1);
			logService.save(log);
		}
		params.clear();
		params.put("memberId", user.getUserId());
		List<PhotoDO> photolist = PhotoService.list(params);
		
		String region = volunteerDO.getProvince() + " " + volunteerDO.getCity();
		volunteerDO.setProvince(region);
		params.clear();
		params.put("memberId",id);
		List<CommentDO> comment = commentService.list(params);
		
		params.clear();
		if (volunteer != null) {
			params.put("guestId", volunteer.getId());
			//params.put("guestType", 1);
			List<GuestlogDO> guestLogList = logService.list(params);
			model.addAttribute("zyzList",guestLogList);
			params.clear();
			params.put("userId", volunteer.getId());
			//params.put("guestType", 2);
			List<GuestlogDO> guestList = logService.list(params);//At访客
			params.clear();
			model.addAttribute("guestList",guestList);
		}
		
		
		model.addAttribute("commentList",comment);//评论信息
		model.addAttribute("volunteer",volunteerDO);//志愿者信息
		model.addAttribute("photolist",photolist);//相册
		params.clear();
		List<Map<String, Object>> shares = getInfo(0, id);
		List<Map<String, Object>> likes = getInfo(1, id);
		List<Map<String, Object>> collect = getInfo(2, id);
		model.addAttribute("sharesList",shares);//转发
		model.addAttribute("likesList",likes);//点赞
		model.addAttribute("collectList",collect);//收藏
		return "pc/volunteerDetail";
	}
	
	/**
	 * 转发，收藏，点赞 信息
	 * @param type 
	 * @param id 会员id
	 * @return
	 */
	public List<Map<String, Object>> getInfo(Integer type, Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("memberId", id);
		List<Map<String, Object>> dynamicList = new ArrayList<Map<String,Object>>();
		List<DynamicDO> dynamic = dynamicServoce.list(params);
		for (DynamicDO bean : dynamic) {
			Integer actType = bean.getActType();
			if (actType != null && actType == 1) {
				InfoDO infoDO = infoService.get(bean.getNewsId().intValue());
				if (infoDO != null) {
					Map<String, Object> info = new HashMap<String, Object>();
					info.put("createTime", infoDO.getCreateTimeStr());
					info.put("title", infoDO.getTitle());
					info.put("img", infoDO.getTitleImg());
					info.put("actType", 1);
					dynamicList.add(info);
				}
			} else if (actType != null && actType == 2){
				ActivityDO activityDO = activityService.get(bean.getNewsId().intValue());
				if (activityDO != null) {
					Map<String, Object> info = new HashMap<String, Object>();
					info.put("createTime", activityDO.getCreateTimeStr());
					info.put("title", activityDO.getActTitle());
					info.put("img", activityDO.getActTitleImg());
					info.put("actType", 2);
					dynamicList.add(info);
				}
			}
		}
		
		return dynamicList;
	}
	
	
	/**
	 * 进入页面后，执行的地区查询，
	 * 性别，参与活动次数，点击量评论，转发查询
	 */
	@ResponseBody
	@RequestMapping("volunteerList/query")
	public String query(@RequestParam Map<String,Object> params, HttpServletRequest request, 
			Model model,String sort, String order, HttpServletResponse response) {
		params.clear();
		String areaId = request.getParameter("regionCode");
		String sex = request.getParameter("sex");
		params.clear();
		
		if (StringUtils.isEmpty(areaId)) {
			areaId = "0";
		}
		
		params.put("pids", areaId);
		List<Integer> ids = regionService.getAllTeamByUserRole(params);
		if (CollectionUtils.isEmpty(ids)) {
			ids.add(-1);
		}
		params.put("auditStatus",1);//
		params.put("sex",sex);
		params.put("sort", sort);
		params.put("order", order);
		params.put("ids", ids);
		List<VolunteerDO> voluntList = volunteerService.list(params);
		JSONObject dataInfo = new JSONObject();
		dataInfo.put("voluntList", voluntList);
		return dataInfo.toString();
	}
}
