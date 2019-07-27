package com.fly.pc.persion.controller;

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
import com.fly.system.utils.ShiroUtils;
import com.fly.team.service.TeamService;
import com.fly.utils.R;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;


@Controller
public class PersionController extends BaseController{
	@Autowired
	private TeamService teamService;
	@Autowired
	private VolunteerService volunteerService;
	@Autowired
	private DynamicService dynamicService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private InfoService infoService;
	
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
		List<VolunteerDO> voluntList = volunteerService.list(params);
		model.addAttribute("team",voluntList.size()==0?null: teamService.get(voluntList.get(0).getTeamId()));
		return "pc/persion_main";
	}
	
	/**
	 * 我的关注
	 * @param model
	 * @return
	 */
	@RequestMapping("/pc/collect")
	public String getCollect(Model model) {
		Map<String, Object> params=new HashMap<>(16);
		params.put("userId", getUserId());
		List<Map<String, Object>> teamList = dynamicService.dyTeamList(params);//关注的团队
		List<Map<String, Object>> actList = dynamicService.dyActList(params);//关注的活动
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
		params.put("expIncType", OrderType.DA_SHANG);
		List<OrderDO> dsList = orderService.list(params);
		params.put("expIncType", OrderType.HONG_BAO);
		List<OrderDO> hbList = orderService.list(params);
		params.put("expIncType", OrderType.GUANG_GAO);
		List<OrderDO> ggList = orderService.list(params);
		params.put("expIncType", OrderType.ZHI_DING);
		List<OrderDO> zdList = orderService.list(params);
		model.addAttribute("allList", allList);
		model.addAttribute("czList", czList);
		model.addAttribute("txList", txList);
		model.addAttribute("dsList", dsList);
		model.addAttribute("hbList", hbList);
		model.addAttribute("ggList", ggList);
		model.addAttribute("zdList", zdList);
		return "pc/caiwu_details";
	}
	
	/**
	 * 志愿者申请
	 * @return 
	 */
	@RequestMapping("/pc/voApply")
	private String voApply(Model model) {
		UserDO user = getUser();
		if(user.getIsIdentification()==0) {//未实名认证
			model.addAttribute("message", "您还未进行实名认证!请先进行实名认证,感谢您的参与!");
			return "pc/message";
		}
		model.addAttribute("user", user);
		return "pc/vo_apply";

	}
	
	/**
	 * 实名认证
	 * @return 
	 */
	@RequestMapping("/pc/attestation")
	public String realNameAuthentication(Model model) {
		UserDO user = getUser();
		Map<String, Object> map=new HashMap<>(16);
		map.put("parentRegionCode", 0);
		List<RegionDO> areaList = regionService.list(map);
		model.addAttribute("areaList", areaList);
		model.addAttribute("user", user);
		return "pc/attestation";
	}
	
	
	@RequestMapping("/pc/newAdd")
	public String newsAdd(Model model) {
		return "pc/news_add";
	}
	
	
	@ResponseBody
	@RequestMapping("/pc/newSave")
	public R newSave(InfoDO newInfo) {
		UserDO user = getUser();
		Map<String, Object> map=new HashMap<>();
		map.put("userId", user.getUserId());
		List<VolunteerDO> voList = volunteerService.list(map);
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
			return R.ok();
		}
		return R.error();
		
	}
	
	
}
