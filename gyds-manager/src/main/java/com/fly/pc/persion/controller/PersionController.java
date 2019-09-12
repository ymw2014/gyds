package com.fly.pc.persion.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.bouncycastle.crypto.tls.UserMappingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.activity.domain.ActivityDO;
import com.fly.activity.domain.ApplyDO;
import com.fly.activity.domain.TypeDO;
import com.fly.activity.service.ActivityService;
import com.fly.activity.service.ApplyService;
import com.fly.activity.service.TypeService;
import com.fly.common.controller.BaseController;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.index.utils.OrderType;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.DynamicService;
import com.fly.news.service.InfoService;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.sys.domain.SetupDO;
import com.fly.sys.service.SetupService;
import com.fly.system.dao.UserDao;
import com.fly.system.service.RegionService;
import com.fly.system.service.UserService;
import com.fly.system.utils.MD5Utils;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.dao.TeamDao;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.utils.JSONUtils;
import com.fly.utils.R;
import com.fly.utils.xss.PublicUtils;
import com.fly.verifyName.dao.NameDao;
import com.fly.verifyName.domain.NameDO;
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
	private ApplyService applyService;
	@Autowired
	private VolunteerService voService;
	@Autowired
	private NameDao nameDao;
	@Autowired
	private SetupService setupService;
	@Autowired
	private UserService userService;
	@Autowired
	private TypeService typeService;
	@Autowired
	UserDao userMapper;
	@Autowired
	TeamDao teamDao;
	
	@ResponseBody
	@RequestMapping("/pc/binding")
	public R binding(UserDO user,Model model) {
		
		Map<String, Object> map=new HashMap<>();
		map.put("username", user.getUsername());
		List<UserDO> userList = userService.list(map);
		if(userList!=null&&userList.size()>0) {
			return R.error("该账号已存在");
		}
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		user.setIsBinding(1);
		if (userService.updatePersonal(user)> 0) {
			return R.ok();
		}
		return R.error();
	}
	

	@RequestMapping("/pc/personalCenter")
	public String getPersionCenter (Model model) {
		UserDO user = ShiroUtils.getUser();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", user.getUserId());
		List<TeamDO> TeamDO = teamDao.list(params);
		if(!CollectionUtils.isEmpty(TeamDO)) {
			if(TeamDO.get(0).getIsAuth()==2) {
				model.addAttribute("isAuth", 2);
			}
		}
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
		SetupDO setupDO = setupService.get(1);
		Long userId = ShiroUtils.getUserId();
		UserDO user =  userService.get(userId);
		model.addAttribute("ccount", user.getAccount());//余额
		model.addAttribute("platformIntegral", user.getPlatformIntegral());//平台积分
		model.addAttribute("withdrawalFee", setupDO.getWithdrawalFee());//提现手续费
		Map<String, Object> params=new HashMap<String, Object>(16);
		model.addAttribute("user", user);
		params.put("userId", user.getUserId());
		List<Map<String, Object>> voluntList = voService.voluntList(params);
		if(voluntList.size()==0||voluntList.get(0).get("teamId")==null) {
			model.addAttribute("team",null);	
		}else {
			model.addAttribute("team",teamService.get(Long.parseLong(voluntList.get(0).get("teamId")+"")));
		}
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
		
		model.addAttribute("teamSize", teamList.size());
		model.addAttribute("actLSize", actList.size());
		model.addAttribute("newSize", newList.size());
		model.addAttribute("voSize", voList.size());
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
		Map<String, Object> vo = new HashMap<String, Object>();
		Long userId = user.getUserId();
		boolean flag = voService.isVo(userId);
		if(!flag) {
				model.addAttribute("message", "您还不是志愿者!!!");
				return "pc/message";
		}else {
			 vo = voService.getVoInfo(userId);
			model.addAttribute("vo", vo);
			return "pc/vo_zhengjian";

		}
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
	public String realNameAuthentication(@RequestParam Long teamId,@RequestParam Integer type,Model model) {
		/*
		 * if(user.getIsIdentification()!=null&&user.getIsIdentification()==-1)
		 * {//实名认证已提交 model.addAttribute("model", "实名认证"); model.addAttribute("message",
		 * "您的实名认证已经提交,请等待审核结果......"); return "pc/message"; }
		 * if(user.getIsIdentification()!=null&&user.getIsIdentification()==1) {//已实名认证
		 * model.addAttribute("user",user); return "pc/att_sucess"; }
		 */
		Map<String, Object> map=new HashMap<>(16);
		map.put("parentRegionCode", 0);
		List<RegionDO> areaList = regionService.list(map);
		model.addAttribute("type", type);
		model.addAttribute("teamId", teamId);
		model.addAttribute("areaList", areaList);
		return "pc/attestation";
	}

	/**
	 *	实名认证提交
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/pc/realName")
	@Transactional
	public R realName(NameDO name,Model model) {
		Integer i = 0;
		String flag = "0";
		String errMsg = "账号余额不足，请充值";
		UserDO user = getUser();
		name.setUserId(user.getUserId());
		Map<String, Object> map = JSONUtils.jsonToMap(name.getText());
		if("1".equals(name.getType().toString())) {
			flag="1";
		}
		//1:入团申请2:建团申请3:代理商入驻
		if("2".equals(name.getType().toString())) {
			R r = countCost(Integer.valueOf(map.get("teamType").toString()));
			if(r.get("price")!=null&&r.get("price").toString()!="0") {
				i = deductMoney(r);//return 0:扣款失败 -1表示余额不足 1表示扣款成功 2表示无此用户
				if(i==1) {
					r.put("orderType", OrderType.ZHI_CHU);
					r.put("expIncType", OrderType.JIAN_TUAN);
					i = creadOrder(r);//return 订单号
					if(i>0) {
						flag="1";
						name.setOrderId(i);
					}
				}else {
					return R.error(errMsg);
				}
			}else {
				flag="1";
			}
		}
		//1:入团申请2:建团申请3:代理商入驻
		if("3".equals(name.getType().toString())) {
			SetupDO setupDO = setupService.get(1);
			BigDecimal account = user.getAccount();
			if (account == null) {
				return R.error(errMsg);
			}

			String[] area = null;
			if (map.get("client")!=null) {
				area = (map.get("proxyArea").toString()).split(",");
			}
			Integer level = Integer.valueOf(map.get("regionLevel").toString());
			Map<String,Object> params = new HashMap<String, Object>();
			BigDecimal balance = null;
			switch (level) {
			case 1:
				BigDecimal bail = setupDO.getProvincialBail();
				if (account.compareTo(bail) == -1) {
					return R.error(errMsg);
				}
				params.put("price", bail);
				balance = account.subtract(bail);
				String pron = area != null ? area[0] : map.get("pronvice").toString();
				map.put("proxyRegion",Integer.valueOf(pron));
				break;
			case 2:
				BigDecimal cityBail = setupDO.getCityBail();
				if (account.compareTo(cityBail) == -1) {
					return R.error(errMsg);
				}
				params.put("price", cityBail);
				balance = account.subtract(cityBail);
				String cit = area != null ? area[1] : map.get("city").toString();
				map.put("proxyRegion",Integer.valueOf(cit));
				break;
			case 3:
				BigDecimal areaBail = setupDO.getAreaBail();
				if (account.compareTo(areaBail) == -1) {
					return R.error(errMsg);
				}
				params.put("price", areaBail);
				balance = account.subtract(areaBail);
				String coun = area != null ? area[2] : map.get("country").toString();
				map.put("proxyRegion",Integer.valueOf(coun));
				break;
			case 4:
				BigDecimal agencyBail = setupDO.getAgencyBail();
				if (account.compareTo(agencyBail) == -1) {
					return R.error(errMsg);
				}
				params.put("price", agencyBail);
				balance = account.subtract(agencyBail);
				String street = area != null ? area[3] : map.get("street").toString();
				map.put("proxyRegion",Integer.valueOf(street));
				break;
			default:
				break;
			}
			user.setAccount(balance);
			params.put("orderType", OrderType.ZHI_CHU);
			params.put("expIncType", OrderType.DAI_LI_SHANG);
			params.put("examineStatus", 2);
			params.put("cashUpType", 2);
			params.put("cashOutType", 2);

			map.put("userId",user.getUserId());
			name.setText(JSONUtils.beanToJson(map));
			if(userService.updatePersonal(user)>0) {
				i = creadOrder(params);
				if(i>0) {
					flag="1";
					name.setOrderId(i);
				}

			}
		}
		//1.支付费用成功或无需支付费用 0.失败
		if("1".equals(flag)) {
			try {
				name.setBirth(PublicUtils.IdNOToBirth(name.getCardNo()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			PublicUtils.IdNOToAge(name.getCardNo());
			if(name.getCardFrontImg()==null||name.getCardFrontImg()=="") {
				return R.error("身份证正面图不能为空");
			}
			if(name.getCardBackImg()==null||name.getCardBackImg()=="") {
				return R.error("身份证正面图不能为空");
			}
			name.setCreateTime(new Date());
			name.setStatus(1);
			if(nameDao.save(name)>0) {
				return R.ok();
			}
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
	
	@RequestMapping("/pc/activityAdd")
	public String activityAdd(Model model) {
		UserDO user = ShiroUtils.getUser();
		List<TypeDO> list = typeService.list(null);
		/*
		 * Map<String,Object> params = new HashMap<String, Object>();
		 * params.put("memberId", user.getUserId()); List<ActivityDO> activi =
		 * activityService.list(params); if (!CollectionUtils.isEmpty(activi)) { Integer
		 * examineStatus = activi.get(0).getExamineStatus(); if (examineStatus == 0) {
		 * model.addAttribute("message","活动已提交，请耐心等待后台审核，谢谢配合！"); return "pc/message"; }
		 * else if (examineStatus == 1) {
		 * model.addAttribute("message","恭喜，您发布的活动已审核通过,可以继续发布活动");
		 * model.addAttribute("status","1"); model.addAttribute("activiType",list);
		 * return "pc/activityAdd"; } else { model.addAttribute("activiType",list);
		 * model.addAttribute("message","您发布的活动未通过，请重新发布");
		 * model.addAttribute("status","2"); return "pc/activityAdd"; } }
		 */
		model.addAttribute("activiType",list);
		return "pc/activityAdd";
	}
	/**
	 * 我的参团
	 */
	@RequestMapping("/pc/myJoinTeam")
	public String myJoinTeam(Model model) {
		Long userId = ShiroUtils.getUserId();
		Map<String, Object> params=new HashMap<String, Object>(16);
		params.put("userId", userId);
		//1:未审核 2:审核通过 3:拒绝
		params.put("type", 1);
		List<NameDO> list1= nameDao.queryJoinTeam(params);
		//全部
		model.addAttribute("joinTeam",list1);
		params.clear();
		params.put("userId", userId);
		//1:未审核 2:审核通过 3:拒绝
		params.put("type", 1);
		params.put("status", 1);
		List<NameDO> list2= nameDao.queryJoinTeam(params);
		//未审核
		model.addAttribute("joinTeam1",list2);
		params.clear();
		params.put("userId", userId);
		//1:未审核 2:审核通过 3:拒绝
		params.put("type", 1);
		params.put("status", 2);
		List<NameDO> list3= nameDao.queryJoinTeam(params);
		//审核通过
		model.addAttribute("joinTeam2",list3);
		params.clear();
		params.put("userId", userId);
		//1:未审核 2:审核通过 3:拒绝
		params.put("type", 1);
		params.put("status", 3);
		List<NameDO> list4= nameDao.queryJoinTeam(params);
		//拒绝
		model.addAttribute("joinTeam3",list4);
		return "pc/myJoinTeam";
	}
	
	/*
	 * @RequestMapping(value = "/pc/withdraw/{id}", method = RequestMethod.GET)
	 * public String withdraw(@PathVariable("id") Long id, Model model) { UserDO
	 * user = ShiroUtils.getUser(); if(user!=null) { BigDecimal account =
	 * userService.get(id).getAccount();
	 * 
	 * if(account.compareTo(new BigDecimal(20000))!=-1) { model.addAttribute("max",
	 * "20000"); }else { model.addAttribute("max", account); } //余额
	 * model.addAttribute("account", account); //手续费
	 * model.addAttribute("withdrawalFee", "20"); } return ""; } //查询提现配置 public
	 * Map<String,Object> querySetupWithdraw() { Map<String,Object> listPrice = new
	 * HashMap<String,Object>(); SetupDO setup = setupService.get(1); if
	 * (setup!=null) { } return listPrice; }
	 */
	
	@RequestMapping("/pc/basics")
	public String basics(Model model) {
		UserDO user= ShiroUtils.getUser();
		user = userService.get(user.getUserId());
		model.addAttribute("user", user);
		return "pc/basics";
	}
	
	@ResponseBody
	@RequestMapping("/pc/savabasics")
	public R savaBasics(UserDO user) {
		if (userMapper.update(user)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	
	
}
