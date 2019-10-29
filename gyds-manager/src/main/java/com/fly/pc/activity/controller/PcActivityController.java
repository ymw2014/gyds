package com.fly.pc.activity.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fly.activity.domain.ActivityDO;
import com.fly.activity.domain.ApplyDO;
import com.fly.activity.service.ActivityService;
import com.fly.activity.service.ApplyService;
import com.fly.adv.domain.AdvertisementDO;
import com.fly.common.controller.BaseController;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.index.service.IndexService;
import com.fly.news.domain.DynamicDO;
import com.fly.news.service.DynamicService;
import com.fly.sys.service.SetupService;
import com.fly.system.service.RegionService;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.utils.DateUtils;
import com.fly.utils.Dictionary;
import com.fly.utils.R;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;

@Controller
@RequestMapping("/pc/")
public class PcActivityController extends BaseController{
	@Autowired
	private TeamService teamService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ApplyService applyService;
	@Autowired
	private VolunteerService volunteerService;
	@Autowired
	private DynamicService dynamicService;
	@Autowired
	private UserService userService;
	@Autowired
	private IndexService indexService;
	@Autowired
	private SetupService setupService;

	@RequestMapping("activityList")
	public String list(@RequestParam Map<String,Object> params, HttpServletRequest request, 
			Model model) {
		params.clear();
		String areaId = request.getParameter("areaId");
		if (StringUtils.isEmpty(areaId)) {
			areaId = "0";
		}
		if(areaId.length()>9) {
			TeamDO team = teamService.get(Long.valueOf(areaId));
			model.addAttribute("team", team);
		}
		params.put("parentRegionCode", areaId);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);
		
		params.clear();
		params.put("pids", areaId);
		String ids = regionService.getTeamAndAreaByUserRole(Long.valueOf(areaId));
		params.clear();
		/*
		 * if(JudgeIsMoblieUtil.judgeIsMoblie(request)) { params.put("offset", 0);
		 * params.put("limit", 8); }
		 */
		params.put("ids", ids);
		params.put("startTime", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		params.put("examineStatus",1);
		List<ActivityDO> actList = activityService.list(params);//活动
		model.addAttribute("actList", actList);//团队活动
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		model.addAttribute("areaId", areaId);
		model.addAttribute("title",setupService.get(1).getTitle());
		return "pc/activityList";
	}
	
	@ResponseBody
	@RequestMapping("activityList/query")
	public String query(@RequestParam Map<String,Object> params, HttpServletRequest request, 
			Model model) {
		String areaId = request.getParameter("areaId");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		if (StringUtils.isEmpty(areaId)) {
			areaId = "0";
		}
		params.put("pids", areaId);
		String ids = regionService.getTeamAndAreaByUserRole(Long.valueOf(areaId));
		params.clear();
		params.put("examineStatus",1);
		params.put("ids", ids);
		params.put("actType", type);
		params.put("status", status);
		List<ActivityDO> actList = activityService.list(params);//活动
		JSONObject dataInfo = new JSONObject();
		dataInfo.put("actList", actList);
		return dataInfo.toString();
	}
	
	@RequestMapping("activityDetail")
	public String join(Integer id, HttpServletRequest request, @RequestParam Long areaId,
			Model model) {
		model.addAttribute("areaId", areaId);
		ActivityDO activityDO = activityService.get(id);
		model.addAttribute("activity", activityDO);
		List<AdvertisementDO> advList = indexService.getCenterAdvList(activityDO.getTeamId(), Dictionary.AdvPosition.HUODONG_BAOMING);
		model.addAttribute("advList", advList);
		
		
		//查询已报名的人数
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("parentRegionCode", areaId);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);
		
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		
		params.put("actId", id);
		List<ApplyDO> list = applyService.list(params);
		List<Integer> idList = list.stream().map(ApplyDO :: getUserId).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(idList)) {
			params.clear();
			params.put("userIds", idList);
			List<UserDO> userList = userService.list(params);
			List<Map<String,Object>> infoList = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < userList.size(); i++) {
				Map<String, Object> info = new HashMap<String, Object>();
				info.put("img", userList.get(i).getHeadImg());
				info.put("name", userList.get(i).getName());
				info.put("time", list.get(i).getCreateTimeStr());
				infoList.add(info);
			}
			model.addAttribute("title",setupService.get(1).getTitle());
			model.addAttribute("volunteerList", infoList);
		}
		
		
		
		//查询报名状态
		params.clear();
		Integer applyStatus = 4;
		UserDO user = ShiroUtils.getUser();
		if (user == null) {//没用登录可以浏览活动详情
			model.addAttribute("applyStatus", applyStatus);
			model.addAttribute("collectStatus", 1);//未收藏
			return "pc/activityJoin";
		}
		
		
		//收藏状态
		params.clear();
		params.put("memberId", user.getUserId());
		params.put("actType", 2);
		params.put("newsId", id);
		params.put("type", 2);
		List<DynamicDO> dynamic = dynamicService.list(params);
		if (CollectionUtils.isEmpty(dynamic)) {
			model.addAttribute("collectStatus", 1);//未收藏
		} else {
			model.addAttribute("collectStatus", 2);//已收藏
		}
		
		boolean flag = volunteerService.isVo(user.getUserId());
		if (!flag) {//不是志愿者
			model.addAttribute("applyStatus", applyStatus);
			return "pc/activityJoin";
		}
		params.clear();
		params.put("actId", id);
		params.put("userId", ShiroUtils.getUserId());
		List<ApplyDO> ApplyDO = applyService.list(params);
		if (CollectionUtils.isEmpty(ApplyDO)) {//还没有报名
			model.addAttribute("applyStatus", applyStatus);
			return "pc/activityJoin";
		}
		model.addAttribute("areaId", areaId);
		//1:审核通过 2:审核拒绝0:待审核
		applyStatus = ApplyDO.get(0).getStatus();
		model.addAttribute("applyId", ApplyDO.get(0).getId());
		model.addAttribute("applyStatus", applyStatus);
		model.addAttribute("areaId", areaId);
		return "pc/activityJoin";
	}
	
	/**
	 * 
	 * @param type 1：取消报名 2：报名申请
	 * @param actId 活动编号
	 * @param applyId 活动报名编号
	 * @return
	 */
	@ResponseBody
	@Transactional
	@RequestMapping("activity/apply")
	public String apply(Integer type, Long actId, Long applyId) {
		JSONObject dataInfo = new JSONObject();
		UserDO user = ShiroUtils.getUser();
		BigDecimal price = null;
		Integer i = null ; 
		ActivityDO activityDO = activityService.get(actId.intValue());
		if(activityDO.getStatus()==2) {
			dataInfo.put("status", "9");//活动进行中
			return dataInfo.toString();
		}
		if(activityDO.getStatus()==3) {
			dataInfo.put("status", "10");//活动已结束
			return dataInfo.toString();
		}
		if (user == null) {
			dataInfo.put("status", "2");//还没登录
			return dataInfo.toString();
		}
		user = userService.get(user.getUserId());
		boolean flag = volunteerService.isVo(user.getUserId());
		if (!flag) {
			dataInfo.put("status", "3");//还不是志愿者
			dataInfo.put("url", "/pc/attestation?teamId="+activityDO.getTeamId()+"&type="+"1");
			return dataInfo.toString();
		}
		VolunteerDO vol = volunteerService.getVo(user.getUserId());
		/*if(!vol.getTeamId().equals(activityDO.getTeamId())) {
			dataInfo.put("status", "4");//还不是本团成员
			return dataInfo.toString();
		}*/
		int status = 0;
		Integer num = activityDO.getNumberOfApplicants();
		try {
			if (type == 1) {
				if(activityDO.getActType()==1) {
					Map<String, Object> map = new HashMap<String, Object>();
					price = activityDO.getActPrice();
					BigDecimal account =  user.getAccount();
					account = account.add(price);
					user.setAccount(account);
					i = userService.update(user);
					if(i==1) {
						map.put("price", price);
						map.put("orderType", 2);
						map.put("expIncType", 8);
						creadOrder(map);
					}else {
						dataInfo.put("status", "5");
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return dataInfo.toString();
					}
				}
				status = applyService.remove(applyId);
				num--;
				activityDO.setNumberOfApplicants(num);
				activityService.update(activityDO);
				dataInfo.put("applyId", -1);//
			} else {
				/*
				 * if (num++ > activityDO.getApplicantsNumMax()) { dataInfo.put("status",
				 * "4");//报名人数已满 return dataInfo.toString(); }
				 */
				/*if(activityDO.getActType()==1) {
					Map<String, Object> map = new HashMap<String, Object>();
					price = activityDO.getActPrice();
					map.put("price", price);
					i = deductMoney(map);
					if(i==1) {
						map.put("orderType", 2);
						map.put("expIncType", 8);
						creadOrder(map);
					}else {
						dataInfo.put("status", "6");//扣费失败
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return dataInfo.toString();
					}
				}*/
				
				ApplyDO apply = new ApplyDO();
				apply.setActId(actId);
				apply.setCreateTime(new Date());
				apply.setStatus(0);
				apply.setUserId(user.getUserId().intValue());
				status = applyService.save(apply);
				num++;
				activityDO.setNumberOfApplicants(num);
				if (status > 0) {
					activityService.update(activityDO);
				}
				dataInfo.put("applyId", apply.getId());//
			}
			ActivityDO activity = activityService.get(actId.intValue());
			dataInfo.put("applyNum", activity.getNumberOfApplicants());//
		} catch (Exception e) {
			status = 5;
			e.printStackTrace();
		}
		dataInfo.put("status", status);//
		return dataInfo.toString();
	}
	
	@ResponseBody
	@RequestMapping("activity/collect")
	public String collect(@RequestParam Map<String,Object> params) {
		JSONObject dataInfo = new JSONObject();
		UserDO user = ShiroUtils.getUser();
		if (user == null) {
			dataInfo.put("code", 3);
			return dataInfo.toString();
		}
		Integer integer = dynamic(params, 5);
		dataInfo.put("code", integer);
		return dataInfo.toString();
	}
	
	@RequestMapping("activity/share/{actType}/{type}/{newsId}")
	public String share(@PathVariable("actType") String actType, @PathVariable("type") String type,
			@PathVariable("newsId") String newsId, Model model) {
		model.addAttribute("actType", actType);
		model.addAttribute("type", type);
		model.addAttribute("newsId", newsId);
		return "pc/share";
	}
	@RequestMapping(value = "/actShare", method = RequestMethod.GET)
	@ResponseBody
	public R shareActLog(@RequestParam Map<String, Object> para) {
		Integer i = dynamic(para, 6);
		R r = new R();
		if (i == 1) {// 不加积分
			r.put("code", 0);
		} else if (i == 2) {// 积分加1
			r.put("code", 2);
			r.put("msg", "积分+1");
		} else if(i == 3){
			r.put("code", 3);
			r.put("msg", "未登录用户分享成功");
		}else {
			return R.error();
		}
		return r;

	}
	
	@ResponseBody
	@RequestMapping("activity/publish")
	public R activiPublish(ActivityDO activityDO) {
		UserDO user = ShiroUtils.getUser();
		VolunteerDO vo = volunteerService.getVo(user.getUserId());
		if(vo==null) {
			return R.error("未找到志愿者信息");
		}
		activityDO.setTeamId(vo.getTeamId());
		activityDO.setMemberId(user.getUserId());
		activityDO.setStatus(1);
		activityDO.setExamineStatus(0);
		activityDO.setNumberOfApplicants(0);
		activityDO.setCreateTime(new Date());
		if (activityService.save(activityDO) > 0) {
			return R.ok();
		}
		return R.error();
	}
	
}
