package com.fly.pc.volunteer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
import com.fly.adv.domain.AdvertisementDO;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.guestlog.domain.GuestlogDO;
import com.fly.guestlog.service.GuestlogService;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.index.service.IndexService;
import com.fly.news.domain.CommentDO;
import com.fly.news.domain.DynamicDO;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.CommentService;
import com.fly.news.service.DynamicService;
import com.fly.news.service.InfoService;
import com.fly.pc.utils.PageUtils;
import com.fly.photo.domain.PhotoDO;
import com.fly.photo.service.PhotoService;
import com.fly.system.service.RegionService;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.Dictionary;
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
	private DynamicService dynamicService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private GuestlogService logService;
	@Autowired
	private PhotoService PhotoService;
	@Autowired
	private IndexService indexService;

	@RequestMapping("volunteerList")
	public String volunteer(@RequestParam Map<String,Object> params, HttpServletRequest request, 
			Model model,String sort, String order, HttpServletResponse response) throws IOException {
		params.clear();
		String areaId = request.getParameter("areaId");
		if (StringUtils.isEmpty(areaId)) {
			areaId = "0";
		}
		params.put("parentRegionCode", areaId);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		params.clear();
		
		params.put("pids", areaId);
		String ids = regionService.getTeamAndAreaByUserRole(Long.valueOf(areaId));
		params.clear();
		params.put("isVo",1);//
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("ids", ids);
		List<Map<String,Object>> voluntList = volunteerService.voluntList(params);
		model.addAttribute("voluntList", voluntList);
		model.addAttribute("areaList", areaList);//全国包含的省
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		model.addAttribute("areaId", areaId);
		return "pc/volunteerList";
	}
	
	@RequestMapping("queryVolunteerList")
	@ResponseBody
	public List<Map<String,Object>> volunteerList(@RequestParam Map<String,Object> params){
		params.put("pids", params.get("areaId"));
		String ids = regionService.getTeamAndAreaByUserRole(Long.valueOf(params.get("areaId").toString()));
		PageUtils page = new PageUtils(params);
		page.put("isVo",1);//
		page.put("ids", ids);
		List<Map<String,Object>> voluntList = volunteerService.voluntList(page);
		return voluntList;
	}
	
	
	
	/**
	    *     志愿者详情
	 * @param params
	 * @param id 会员id
	 * @param model
	 * @return
	 */
	@RequestMapping("volunteerDetail")
	public String volunteerDetail(@RequestParam Map<String,Object> params, @RequestParam Integer areaId, @RequestParam Integer id, Model model) {
		UserDO user = ShiroUtils.getUser();
		params.clear();
		params.put("id", id);
		VolunteerDO vo = volunteerService.get(id);
		List<Map<String,Object>> voluntList = volunteerService.voluntList(params);
		if (user != null) {//当前用户必须是志愿者才记录
			params.clear();
			params.put("userId", user.getUserId());
			params.put("isVo", 1);
			List<Map<String,Object>> volunteer = volunteerService.voluntList(params);
			if (!CollectionUtils.isEmpty(volunteer)) {
				//添加访客记录
				if (Integer.valueOf(volunteer.get(0).get("id").toString()) != id) {
					GuestlogDO log = new GuestlogDO();
					log.setGuestId(Integer.valueOf(volunteer.get(0).get("id").toString()));
					log.setGuestHeadimg(volunteer.get(0).get("headImg") != null ? volunteer.get(0).get("headImg").toString() : "");
					log.setGuestName(user.getName());
					log.setUserId(id.intValue());
					log.setUserHeadimg(voluntList.get(0).get("headImg") != null ? voluntList.get(0).get("headImg").toString() : "");
					log.setUserName(voluntList.get(0).get("volunteerName").toString());
					log.setGuestTime(new Date());
					log.setGuestType(1);
					logService.save(log);
				}
			}
		}
		List<AdvertisementDO> advList = indexService.getCenterAdvList(vo.getTeamId(), Dictionary.AdvPosition.ZHI_YUAN_ZHE);
		model.addAttribute("advList", advList);
		params.clear();
		params.put("guestId", id);
		params.put("offset", 0);
		params.put("limit", 6);
		List<GuestlogDO> guestLogList = logService.list(params);
		model.addAttribute("zyzList",guestLogList);
		params.clear();
		params.put("userId", id);
		params.put("offset", 0);
		params.put("limit", 6);
		List<GuestlogDO> guestList = logService.list(params);//At访客
		model.addAttribute("guestList",guestList);
		params.clear();
		params.put("memberId", voluntList.get(0).get("userId"));
		List<PhotoDO> photolist = PhotoService.list(params);
		model.addAttribute("photolist",photolist);//相册
		params.clear();
			params.put("memberId",voluntList.get(0).get("userId"));
			List<CommentDO> comment = commentService.list(params);
			ArrayList<CommentDO> newComment = comment.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
			new TreeSet<>(Comparator.comparing(CommentDO::getNewsId))), ArrayList::new));
			for (CommentDO bean : newComment) {
				if (user != null) {//查询点赞状态
					params.clear();
					params.put("memberId", user.getUserId());
					params.put("type", 1);
					params.put("act_type", 1);
					params.put("newsId", bean.getNewsId());
					List<DynamicDO> list = dynamicService.list(params);
					if (!CollectionUtils.isEmpty(list)) {
						bean.setIsClick("1");
					} else {
						bean.setIsClick("0");
					}
				}else {
					bean.setIsClick("2");
				}
			}
			
		model.addAttribute("commentList",newComment);//评论信息
		model.addAttribute("volunteer",voluntList.get(0));//志愿者信息
		params.clear();
		
		List<Map<String, Object>> shares = getInfo(0, Long.valueOf(voluntList.get(0).get("userId").toString()));
		List<Map<String, Object>> likes = getInfo(1, Long.valueOf(voluntList.get(0).get("userId").toString()));
		List<Map<String, Object>> collect = getInfo(2, Long.valueOf(voluntList.get(0).get("userId").toString()));
		model.addAttribute("sharesList",shares);//转发
		model.addAttribute("likesList",likes);//点赞
		model.addAttribute("collectList",collect);//收藏
		params.put("parentRegionCode", 0);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);
		model.addAttribute("areaId", areaId);
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
		UserDO user = ShiroUtils.getUser();
		params.put("type", type + "");
		params.put("memberId", id);
		List<Map<String, Object>> dynamicList = new ArrayList<Map<String,Object>>();
		List<DynamicDO> dynamic = dynamicService.list(params);
		for (DynamicDO bean : dynamic) {
			Integer actType = bean.getActType();
			if (actType != null && actType == 1) {
				InfoDO infoDO = infoService.get(bean.getNewsId().intValue());
				if (infoDO != null) {
					Map<String, Object> info = new HashMap<String, Object>();
					info.put("createTime", bean.getCreatTime());
					info.put("title", infoDO.getTitle());
					info.put("img", infoDO.getTitleImg());
					info.put("actType", 1);
					info.put("newsId", infoDO.getId());
					if (user != null) {//查询点赞状态
						params.clear();
						params.put("memberId", user.getUserId());
						params.put("type", 1);
						params.put("act_type", 1);
						params.put("newsId", infoDO.getId());
						List<DynamicDO> list = dynamicService.list(params);
						if (!CollectionUtils.isEmpty(list)) {
							info.put("isClick", 1);
						} else {
							info.put("isClick", 0);
						}
					}else {
						info.put("isClick", 2);
					}
					dynamicList.add(info);
				}
			} else if (actType != null && actType == 2){
				ActivityDO activityDO = activityService.get(bean.getNewsId().intValue());
				if (activityDO != null) {
					Map<String, Object> info = new HashMap<String, Object>();
					info.put("createTime", bean.getCreatTime());
					info.put("title", activityDO.getActTitle());
					info.put("img", activityDO.getActTitleImg());
					info.put("actType", 2);
					info.put("newsId", activityDO.getId());
					params.clear();
					if (user != null) {//查询点赞状态
						params.put("memberId", user.getUserId());
						params.put("actType", 2);
						params.put("newsId", activityDO.getId());
						List<DynamicDO> dyna = dynamicService.list(params);
						if (CollectionUtils.isEmpty(dyna)) {
							info.put("collectStatus", 1);//未收藏
						} else {
							info.put("collectStatus", 2);//已收藏
						}
					} else {
						info.put("collectStatus", 1);//未收藏
					}
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
		String areaId = request.getParameter("areaId");
		String sex = request.getParameter("sex");
		params.clear();
		
		if (StringUtils.isEmpty(areaId)) {
			areaId = "0";
		}
		
		params.put("pids", areaId);
		String ids = regionService.getTeamAndAreaByUserRole(Long.valueOf(areaId));
		params.put("isVo",1);//
		params.put("sex",sex);
		params.put("sort", sort);
		params.put("order", order);
		params.put("ids", ids);
		List<Map<String,Object>> voluntList = volunteerService.voluntList(params);
		JSONObject dataInfo = new JSONObject();
		dataInfo.put("voluntList", voluntList);
		return dataInfo.toString();
	}
}
