package com.fly.pc.persion.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.activity.domain.ApplyDO;
import com.fly.activity.service.ApplyService;
import com.fly.common.controller.BaseController;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.index.utils.OrderType;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.DynamicService;
import com.fly.news.service.InfoService;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.system.service.RegionService;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.service.TeamService;
import com.fly.utils.R;
import com.fly.utils.xss.PublicUtils;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;


@Controller
public class PersionController extends BaseController{
	@Autowired
	private TeamService teamService;
	@Autowired
	private DynamicService dynamicService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private UserService userService;
	@Autowired
	private ApplyService applyService;
	@Autowired
	private VolunteerService voService;

	
	@RequestMapping("/pc/personalCenter")
	public String getPersionCenter(Model model) {
		UserDO user = ShiroUtils.getUser();
		if(user==null) {
			return "redirect:/admin";
		}
		return "pc/persion_center";
	}
	
	/**
	 * 个人中心首页
	 * @param model
	 * @return
	 */
	@GetMapping("/pc/persion_main")
	String main(Model model) {
		UserDO user = ShiroUtils.getUser();
		model.addAttribute("ccount", user.getAccount());//余额
		model.addAttribute("platformIntegral", user.getPlatformIntegral());//平台积分
		Map<String, Object> params=new HashMap<String, Object>(16);
		model.addAttribute("user", user);
		params.put("userId", user.getUserId());
		List<Map<String, Object>> voluntList = voService.voluntList(params);
		model.addAttribute("team",voluntList.size()==0?null: teamService.get(Integer.parseInt(voluntList.get(0).get("teamId")+"")));
		return "pc/persion_main";
	}
	
	/**
	 *	 我的关注
	 * @param model
	 * @return
	 */
	@RequestMapping("/pc/collect")
	public String getCollect(Model model) {
		Map<String, Object> params=new HashMap<>(16);
		params.put("userId", getUserId());
		List<Map<String, Object>> teamList = dynamicService.dyTeamList(params);//关注的团队
		List<Map<String, Object>> actList = dynamicService.dyActList(params);//关注的活动
		VolunteerDO vo =voService.getVo(getUserId());
		for (Map<String, Object> map : actList) {//查找该用户是否已经报名该活动
			if(vo!=null) {
				ApplyDO apply = applyService.getApply(getUserId(), Integer.parseInt(map.get("news_id").toString()));
				if(apply!=null) {
					if(apply.getStatus()==0) {
						map.put("app_status",0);
						map.put("appId", apply.getId());
					}
					if(apply.getStatus()==1) {
						map.put("app_status",1);
						map.put("appId", apply.getId());
					}
				}else {
					map.put("app_status",-1);
					map.put("appId",-1);
				}
			}else {
				map.put("app_status",-1);
				map.put("appId", -1);
			}
		}
		List<Map<String, Object>> newList = dynamicService.dyNewList(params);//关注的新闻
		List<Map<String, Object>> voList = dynamicService.dyVoluList(params);//关注的志愿者
		model.addAttribute("teamList", teamList);
		model.addAttribute("actList", actList);
		model.addAttribute("newList", newList);
		model.addAttribute("voList", voList);
		return "pc/collect";
	}
	
	/**
	 * 财务明细
	 * @return 
	 */
	@RequestMapping("/pc/getFinancial")
	public String getFinancialDetails(Model model) {//类型:0:提现 1:充值 2:打赏 3:红包 4:广告费用 5:置顶
		Map<String, Object> params=new HashMap<>(16);
		params.put("userId", getUserId());
		List<OrderDO> allList = orderService.list(params);
		params.put("expIncType", OrderType.CHONG_ZHI);
		List<OrderDO> czList = orderService.list(params);
		params.put("expIncType", OrderType.TI_XIAN);
		List<OrderDO> txList = orderService.list(params);
		//params.put("expIncType", OrderType.SHOU_RU);
		//List<OrderDO> dsList = orderService.list(params);
		params.clear();
		params.put("userId", getUserId());
		params.put("orderType", OrderType.SHOU_RU);
		List<OrderDO> srList = orderService.list(params);
		params.clear();
		params.put("userId", getUserId());
		params.put("orderType",OrderType.ZHI_CHU );
		List<OrderDO> zcList = orderService.list(params);
		/*params.put("expIncType", OrderType.ZHI_DING);
		List<OrderDO> zdList = orderService.list(params);*/
		model.addAttribute("allList", allList);
		model.addAttribute("czList", czList);
		model.addAttribute("txList", txList);
		//model.addAttribute("dsList", dsList);
		model.addAttribute("srList", srList);
		model.addAttribute("zcList", zcList);
		//model.addAttribute("zdList", zdList);
		return "pc/caiwu_details";
	}
	
	/**
	 * 志愿者申请
	 * @return 
	 */
	@RequestMapping("/pc/voApply")
	public String voApply(Model model) {
		UserDO user = getUser();
		if(user.getIsIdentification()==null||user.getIsIdentification()!=1) {//未实名认证
			model.addAttribute("message", "您还未进行实名认证!请先进行实名认证,感谢您的参与!");
			return "pc/message";
		}
		Long userId = user.getUserId();
		List<VolunteerDO> volist = voService.isVolllist(userId);//查询是否已经是志愿者
		if(volist!=null&&volist.size()>0) {
			VolunteerDO vo = volist.get(0);
			if(vo.getAuditStatus()==0) {
				model.addAttribute("model", "志愿者申请");
				model.addAttribute("message", "您的志愿者申请正在审核!!");
				return "pc/message";
			}
			if(vo.getAuditStatus()==1) {//志愿者证件
				model.addAttribute("vo", vo);
				return "pc/vo_zhengjian";
			}
		}
		model.addAttribute("user", user);
		return "pc/vo_apply";
	}
	
	
	/**
	 *	志愿者申请提交
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/pc/saveVoApply")
	public R saveVoApply(VolunteerDO vo) {
		UserDO user = getUser();
		int radomInt2 =(int)((Math.random()*9+1)*100000);
		vo.setVolunteerNumber(user.getCardNo().substring(0, 8)+radomInt2);
		vo.setCity(user.getCity());
		vo.setUserId(user.getUserId());
		vo.setCounty(user.getDistrict());
		vo.setTelephone(user.getMobile());
		vo.setAge(PublicUtils.IdNOToAge(user.getCardNo()));
		vo.setAddress(user.getLiveAddress());
		vo.setProvince(user.getProvince());
		vo.setVolunteerName(user.getName());
		vo.setAuditStatus(0);
		vo.setCreateTime(new Date());
		vo.setHeadImg(user.getHeadImg());
		vo.setSex(user.getSex());
		if(voService.save(vo)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	/**
	 * 实名认证
	 * @return 
	 */
	@RequestMapping("/pc/attestation")
	public String realNameAuthentication(Model model) {
		UserDO user = getUser();
		Map<String, Object> map=new HashMap<>(16);
		if(user.getIsIdentification()!=null&&user.getIsIdentification()==-1) {//实名认证已提交
			model.addAttribute("model", "实名认证");
			model.addAttribute("message", "您的实名认证已经提交,请等待审核结果......");
			return "pc/message";
		}
		if(user.getIsIdentification()!=null&&user.getIsIdentification()==1) {//已实名认证
			model.addAttribute("user",user);
			return "pc/att_sucess";
		}
		map.put("parentRegionCode", 0);
		List<RegionDO> areaList = regionService.list(map);
		model.addAttribute("areaList", areaList);
		model.addAttribute("user", user);
		return "pc/attestation";
	}
	
	/**
	 *	实名认证提交
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/pc/realName")
	public R realName(UserDO user,Model model) {
		try {
			user.setBirth(PublicUtils.IdNOToBirth(user.getCardNo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PublicUtils.IdNOToAge(user.getCardNo());
		if(user.getCardFrontImg()==null||user.getCardFrontImg()=="") {
			return R.error("身份证正面图不能为空");
		}
		if(user.getCardBackImg()==null||user.getCardBackImg()=="") {
			return R.error("身份证正面图不能为空");
		}
		user.setIsIdentification(-1);
		if(userService.updatePersonal(user)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	@RequestMapping("/pc/newAdd")
	public String newsAdd(Model model) {
		return "pc/news_add";
	}
	@RequestMapping("/pc/new/message")
	public String message(Model model) {
		model.addAttribute("message",  "您已提交成功!");
		return "pc/message";
	}
	
	@ResponseBody
	@RequestMapping("/pc/newSave")
	public R newSave(InfoDO newInfo,Model model) {
		UserDO user = getUser();
		Map<String, Object> map=new HashMap<>();
		map.put("userId", user.getUserId());
		List<VolunteerDO> voList = voService.list(map);
		if(voList==null||voList.size()==0) {
			return R.error("未找到志愿者信息");
		}
		newInfo.setIsDel(0);
		newInfo.setIsTop(0);
		newInfo.setNumberOfShares(0);
		newInfo.setCriticismOfCount(0);
		newInfo.setNumberOfLikes(0);
		newInfo.setRewardCount(0);
		newInfo.setStatus(0);
		newInfo.setCreateTime(new Date());
		newInfo.setTeamId(voList.get(0).getTeamId());
		if(infoService.save(newInfo)>0) {
			R r = new R();
			r.put("code", 0);
			r.put("url", "/pc/new/message");
			return r;
		}
		return R.error();
		
	}
	
	
}
