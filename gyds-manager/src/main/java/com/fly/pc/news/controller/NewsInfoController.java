package com.fly.pc.news.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.formula.functions.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.adv.domain.AdvertisementDO;
import com.fly.base.BaseService;
import com.fly.common.controller.BaseController;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.index.service.IndexService;
import com.fly.index.utils.OrderType;
import com.fly.news.dao.CommentDao;
import com.fly.news.dao.PacketDao;
import com.fly.news.dao.PriceDao;
import com.fly.news.dao.RedDao;
import com.fly.news.dao.RewardInfoDao;
import com.fly.news.dao.TopDao;
import com.fly.news.domain.CommentDO;
import com.fly.news.domain.InfoDO;
import com.fly.news.domain.PacketDO;
import com.fly.news.domain.PriceDO;
import com.fly.news.domain.RewardInfoDO;
import com.fly.news.domain.TopDO;
import com.fly.news.service.InfoService;
import com.fly.pc.utils.PageUtils;
import com.fly.sys.domain.SetupDO;
import com.fly.sys.service.SetupService;
import com.fly.system.dao.UserDao;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.utils.Dictionary;
import com.fly.utils.R;

import me.chanjar.weixin.mp.api.WxMpService;

@Controller
@RequestMapping("/pc/news/")
public class NewsInfoController extends BaseController {
	@Autowired
	private InfoService infoService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private PriceDao priceDao;
	@Autowired
	private RewardInfoDao rewardInfoDao;
	@Autowired
	private UserDao userMapper;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private TopDao topDao;
	@Autowired
	private RegionService regionService;
	@Autowired
	private SetupService setupService;
	@Autowired
	private PacketDao packetDao;
	@Autowired
	private RedDao redDao;
	@Autowired
	private BaseService baseService;
	@Autowired
	private IndexService indexService;

	@RequestMapping("/info")
	public String newInfo(@RequestParam Integer id, @RequestParam Long areaId, Long teamId,Model model) {
		InfoDO info = infoService.get(id);
		Map<String, Object> params = new HashMap<String, Object>();
		
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		if(teamId!=null) {
			areaId=teamId;
		}
		params.put("parentRegionCode", areaId);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);
		
		// 团队置顶排序
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("teamId", info.getTeamId());
		params.put("sort", "n.is_top desc,n.public_time desc");
		List<InfoDO> infoList = infoService.list(params);
		// 发布时间排序
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("sort", "n.is_top desc,n.public_time desc");
		List<InfoDO> newsLike = infoService.list(params);
		// 获取团队信息
		TeamDO teamDO = teamService.get(info.getTeamId());
		// 街道级置顶3条
		Long code = upRegCode(info.getTeamId());
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit", 3);
		params.put("topRegion", code);
		params.put("sort", "n.is_top desc,n.public_time desc");
		List<InfoDO> newsTop = infoService.list(params);
		// 县级置顶3条
		code = upRegCode(code);
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit", 3);
		params.put("topRegion", code);
		params.put("sort", "n.is_top desc,n.public_time desc");
		newsTop.addAll(infoService.list(params));
		// 市级置顶3条
		code = upRegCode(code);
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit", 3);
		params.put("topRegion", code);
		params.put("sort", "n.is_top desc,n.public_time desc");
		newsTop.addAll(infoService.list(params));
		// 省置顶3条
		code = upRegCode(code);
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit", 3);
		params.put("topRegion", code + "");
		params.put("sort", "n.is_top desc,n.public_time desc");
		newsTop.addAll(infoService.list(params));
		// 总平台置顶3条
		code = upRegCode(code);
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit", 3);
		params.put("topRegion", code + "");
		params.put("sort", "n.is_top desc,n.public_time desc");
		newsTop.addAll(infoService.list(params));
		// 是否关注团队
		Integer a = is_attention(info.getTeamId());
		model.addAttribute("is_attention", a);
		// 是否点过赞
		Integer l = is_likes(id);
		// 0:未登录 1:点赞 2: 未点赞
		model.addAttribute("isLike", l);
		// 是否置过顶
		Integer t = is_top(id);
		// 0:未登录 1:置顶 2: 未置顶
		model.addAttribute("isTop", t);
		// 是否有红包
		Integer r = is_red(id);
		// 0:未登录 1:有红包 2: 无红包
		model.addAttribute("isRed", r);
		// 是否领红包
		Integer gr = is_get_red(id);
		// 0:未登录 1:领过红包 2: 没领过红包
		model.addAttribute("isGetRed", gr);
		// 打赏人数
		params.clear();
		params.put("newsId", id);
		params.put("sort", "create_time desc");
		params.put("group", "member_id");
		List<RewardInfoDO> re = rewardInfoDao.list1(params);
		List<Long> userIds = new ArrayList<Long>();
		for (RewardInfoDO user : re) {
			userIds.add(user.getMemberId());
		}
		params.clear();
		List<AdvertisementDO> advList = indexService.getCenterAdvList(info.getTeamId(), Dictionary.AdvPosition.NEW_INFO);
		model.addAttribute("advList", advList);
		List<UserDO> user = new ArrayList<UserDO>();
		params.put("userIds", userIds);
		params.put("offset", 0);
		params.put("limit", 15);
		if(userIds.size()>0) {
			 user = userMapper.list(params);
		}

		// 评论 默认时间顺序
		params.clear();
		params.put("newsId", id);
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("sort", "create_time desc");
		List<CommentDO> comm = commentDao.list(params);
		
		
		// 评论人数
		model.addAttribute("commCount", comm.size());
		// 评论默认时间排序
		model.addAttribute("comm", comm);
		// 打赏人数
		model.addAttribute("reCountUser", re.size());
		// 打赏用户
		model.addAttribute("user", user);
		// 置顶排序
		model.addAttribute("newsTop", newsTop);
		// 团队信息
		model.addAttribute("team", teamDO);
		// 团队咨询列表
		model.addAttribute("newsList", infoList);
		// 点赞排行
		model.addAttribute("newsLike", newsLike);
		// 新闻详情
		model.addAttribute("info", info);
		//区域
		model.addAttribute("areaId", areaId);
		model.addAttribute("title",setupService.get(1).getTitle());
		return "pc/newInfo";
	}
	
	@RequestMapping("/queryCommList")
	@ResponseBody
	public List<CommentDO> queryCommList(@RequestParam Map<String, Object> para, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		para.put("sort", "create_time desc");
		map = new PageUtils(para);
		List<CommentDO> comm = commentDao.list(map);
		return comm;
		
	}
	// 文章分享
	@RequestMapping(value = "/share", method = RequestMethod.GET)
	@ResponseBody
	public R shareNewInfoLog(@RequestParam Map<String, Object> para) {
		Integer i = dynamic(para, 1);
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

	// 文章点赞
	@RequestMapping(value = "/likes", method = RequestMethod.GET)
	@ResponseBody
	public R likes(@RequestParam Map<String, Object> params) {
		Integer i = dynamic(params, 2);
		R r = new R();
		if (i == 1) {
			r.put("code", 0);
		} else if (i == 2) {// 积分加1
			r.put("code", 2);
			r.put("msg", "积分+1");
		} else {
			return R.error();
		}
		return r;
	}
	
	// 打赏
			@RequestMapping(value="/rewardList", method = RequestMethod.POST)
			@ResponseBody
			public R rewardList(@RequestParam Map<String, Object> params) {
				R r = new R();
				UserDO user = null; 
				user = ShiroUtils.getUser();
				if(user==null) {
					r.put("code", 1);
					r.put("url", "/admin");
					return r;
				}
				user = userMapper.get(user.getUserId());
				List<Map<String,Object>> listPrice = querySetupReward();
				r.put("code", 0);
				r.put("listPrice", listPrice);
				r.put("newsId", params);
				r.put("user", user);
				return r;
			}
			
			//查询配置置顶额度
			public List<Map<String,Object>> querySetupReward() {
				List<Map<String,Object>> listPrice = new ArrayList<Map<String,Object>>();
				SetupDO setup = setupService.get(1);
				String[] split = null;
				if (setup!=null) {
					split = setup.getRewardPriceSetup().split(",");
					for(String s : split) {
						Map<String,Object> data = new HashMap<String, Object>();
						data.put("price", s);
						listPrice.add(data);
					}
				}
				return listPrice;
			}
	
	// 打赏
	// return 0:扣款失败 -1表示余额不足 1表示扣款成功 2表示无此用户
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/reward", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public R reward(@RequestParam Map<String, Object> params) {
		R r = new R();
		Integer i = null;
		Boolean flag =false;
		i = deductMoney(params);
		if (i == 1) {
			// 产生订单
			if (creadOrder(params) > 0) {
				// 记录+1
				if (dynamic(params, 3) == 1) {
					flag = true;
				}
			}
		}
		if(flag) {
			BigDecimal price = new BigDecimal(params.get("price").toString());
			baseService.distributionOfDomestic(OrderType.DA_SHANG_FAN_YONG, price, Integer.parseInt(params.get("newsId").toString()));
			return r.ok();
		}else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return r.error(i+"");
		}
	}

	// 红包
	// return 0:扣款失败 -1表示余额不足 1表示扣款成功 2表示无此用户
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/redPacket", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public R redPacket(@RequestParam Map<String, Object> params) {
		SetupDO setup = setupService.list(new HashMap<String, Object>(16)).get(0);
		R r = new R();
		Integer i = null;
		Boolean flag =false;
		if (params.get("price") != null && params.get("price") != "" && params.get("number") != null
				&& params.get("number") != "") {
			BigDecimal number = new BigDecimal(params.get("number").toString());
			BigDecimal price = new BigDecimal(params.get("price").toString());
			//红包最大倍数
			BigDecimal priceMax = price.multiply(new BigDecimal(2));
			//结果 :-1 小于,0 等于,1 大于
			if(number.compareTo(priceMax)==1) {
				return r.error("3");
			}
			BigDecimal rate = setup.getRedPacketExtract();
			params.put("price", rate.multiply(price).add(price));
			i = deductMoney(params);
			if (i == 1) {
				// 产生订单
				if(creadOrder(params)>0) {
					params.put("price", price);
					//创建红包
					if (creatRed(params) > 0) {
						flag = true;
					}
				}
			}
			//获取需进行分佣的金额
			BigDecimal commissionPrice = price.multiply(setup.getRedPacketExtract());
			baseService.distributionOfDomestic(OrderType.HONG_BAO_FAN_YONG, commissionPrice, Integer.parseInt(params.get("newsId").toString()));
		}
		if(flag) {
			return r.ok();
		}else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return r.error(i+"");
		}
	}
	
	// 红包
		@RequestMapping(value="/red", method = RequestMethod.POST)
		@ResponseBody
		public R red(@RequestParam Map<String, Object> params) {
			R r = new R();
			UserDO user = null; 
			user = ShiroUtils.getUser();
			if(user==null) {
				r.put("code", 1);
				r.put("url", "/admin");
				return r;
			}
			user = userMapper.get(user.getUserId());
			List<Map<String,Object>> listPrice = querySetupPrice();
			SetupDO setup = setupService.get(1);
			BigDecimal rate = setup.getRedPacketExtract();
			r.put("code", 0);
			r.put("listPrice", listPrice);
			r.put("newsId", params);
			r.put("user", user);
			r.put("rate", rate);
			return r;
		}
		
		//查询配置红包额度
		public List<Map<String,Object>> querySetupPrice() {
			List<Map<String,Object>> listPrice = new ArrayList<Map<String,Object>>();
			SetupDO setup = setupService.get(1);
			String[] split = null;
			BigDecimal rate = null;
			if (setup!=null) {
				split = setup.getRedPriceSetup().split(",");
				rate = setup.getRedPacketExtract();
				for(String s : split) {
					Map<String,Object> data = new HashMap<String, Object>();
					data.put("price", s);
					data.put("rate", rate.multiply(new BigDecimal(s)));
					listPrice.add(data);
				}
			}
			return listPrice;
		}
		
		@ResponseBody
		@PostMapping("/queryRed")
		public Map<String, Object> queryRed(@RequestParam Map<String, Object> params){
			Map<String, Object> outMap = new HashMap<String, Object>();
			UserDO user = null;
			Long userId = null;
			List<Map<String,Object>> userRed = null ;
			user = ShiroUtils.getUser();
			if(user==null) {
				userId = (long) 0;
			}else{
				userId = user.getUserId();
			}
			InfoDO info = infoService.get(Integer.valueOf(params.get("newsId").toString()));
			//红包详情
			PacketDO Packet = packetDao.get(info.getRedPeperId());
			params.put("id", info.getRedPeperId());
			params.put("isGet", "2");
			//已抢红包详情
			List<Map<String,Object>> list = redDao.redListUser(params);
			params.clear();
			params.put("id", info.getRedPeperId());
			params.put("isGet", "2");
			params.put("getUserId", userId);
			//当前登录用户抢红包金额
			userRed = redDao.redListUser(params);
			//当前登录用户抢红包金额
			if(!userRed.isEmpty()) {
				outMap.put("userRed", userRed.get(0));
			}else {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("head_img", "/pc/images/touxiang5.png");
				map.put("price","0");
				outMap.put("userRed",map);
			}
			//红包详情
			outMap.put("Packet", Packet);
			//已抢红包详情
			outMap.put("listRed", list);
			//已抢红包数量
			outMap.put("redSize", list.size());
			outMap.put("code", 0);
			return outMap;
		}
		
		@SuppressWarnings("static-access")
		@RequestMapping(value = "/vieRed", method = RequestMethod.POST)
		@ResponseBody
		@Transactional
		public R vieRed(@RequestParam Map<String, Object> params) {
			R r = new R();
			Boolean flag = false;
			Long userId = null;
			try {
				 userId = ShiroUtils.getUserId();
			} catch (Exception e) {
				//3:获取不到用户
				 return r.error("3");
			}
			Integer gr = is_get_red(Integer.valueOf(params.get("newsId").toString()));
			if(gr==1) {
				//4:领取过红包
				 return r.error("4");
			}
			InfoDO info = infoService.get(Integer.valueOf(params.get("newsId").toString()));
			if(info.getIsRedPeper()==1) {
				BigDecimal price  = getRed(info);
				if(price.compareTo(new BigDecimal(0))==0) {
					//红包抢完啦!
					return r.error("1");
				}
				params.put("price", price);
				params.put("userId", userId);
				Integer i = creadOrder(params);
				if(i>0) {
					i = addMoney(params);
					if(i==1) {
						flag=true;
					}
				}
			}else {
				//红包抢完啦!
				return r.error("1");
			}
			if(flag) {
				//成功
				return r.ok();
			}else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				//抢红包失败
				return r.error("2");
			}
		}
	// 置顶
	@RequestMapping(value = "/top/{id}", method = RequestMethod.GET)
	public String top(@PathVariable("id") Integer id, Model model) {
		List<Map<String, Object>> regin = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		UserDO user = null; 
		user = ShiroUtils.getUser();
		if(user==null) {
			return "redirect:/login";
		}
		InfoDO info = infoService.get(id);
		Long code = info.getTeamId();
		map.put("region", code);
		map.put("name", "本团");
		regin.add(map);
		map = new HashMap<String, Object>();
		code = upRegCode(code);
		map.put("region", code);
		map.put("name", "本街道办");
		regin.add(map);
		map = new HashMap<String, Object>();
		code = upRegCode(code);
		map.put("region", code);
		map.put("name", "本县(区)");
		regin.add(map);
		map = new HashMap<String, Object>();
		code = upRegCode(code);
		map.put("region", code);
		map.put("name", "本市");
		regin.add(map);
		map = new HashMap<String, Object>();
		code = upRegCode(code);
		map.put("region", code);
		map.put("name", "本省");
		regin.add(map);
		map = new HashMap<String, Object>();
		code = upRegCode(code);
		map.put("region", code);
		map.put("name", "本平台");
		regin.add(map);
		model.addAttribute("region", regin);
		model.addAttribute("newsId", id);
		model.addAttribute("topCount", topDays());
		return "pc/top";
	}

	// 置顶提交
	@Transactional
	@ResponseBody
	@PostMapping("/topInfo")
	public R comTopInfo(@RequestParam Map<String, Object> params) {
		TopDO top = new TopDO();
		UserDO user = null;
		Map<String,Object> count = new HashMap<String,Object>();
		count = count(params.get("topCount").toString(),params.get("topReg").toString());
		count.put("price", count.get("count"));
		Integer i = deductMoney(count);
		if(i==1) {
			params.put("orderType", 2);
			params.put("examineStatus", 2);
			params.put("expIncType", 5);
			params.put("price", count.get("price"));
			Integer orderNuber = creadOrder(params);
			if (orderNuber > 0) {
				top.setOrdernumber(orderNuber);
				top.setNewsId(Integer.parseInt(params.get("newsId").toString()));
				top.setStatus(3);
				top.setTopPrice(new BigDecimal(count.get("price").toString()));
				top.setRegionCode(Long.valueOf(params.get("topReg").toString()));
				top.setTopDay(Integer.valueOf(params.get("topCount").toString()));
				user = ShiroUtils.getUser();
				if (user != null) {
					top.setUserId(Long.parseLong(user.getUserId().toString()));
				}
				if (topDao.save(top) > 0) {
					return R.ok();
				}
			}
		}
		
		if (i == -1) {
			return R.error("-1");
		}
		return R.error();
	}

	@RequestMapping(value = "/count", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map<String, Object> count(String topDays, String regionCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("regionCode", regionCode);
		List<PriceDO> pri = priceDao.list(params);
		if (CollectionUtils.isEmpty(pri)) {
			map.put("count", -1);
			return map;
		}
		if (pri.size() == 3) {
			map.put("count", -2);
			return map;
		}
		BigDecimal mon = pri.get(0).getPriceOfDay();
		BigDecimal day = new BigDecimal(topDays);
		map.put("count", mon.multiply(day));
		return map;
	}

	

	// 评论
	@ResponseBody
	@PostMapping("/comment")
	@Transactional
	public R comment(@RequestParam Map<String, Object> params) {
		Integer i = creadComm(params);
		R r = new R();
		if (i == 1) {
			Integer c = dynamic(params, 4);
			if (c == 1) {
				r.put("code", 0);
			} else if (c == 2) {
				r.put("code", 2);
				r.put("msg", "积分+1");
			}
		} else if (i == 0) {
			return R.error("0");
		}
		return r;
	}

	@RequestMapping("/infoList")
	public String newInfoList(@RequestParam Long areaId, @RequestParam Integer flag,Long teamId, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		List<TypeTitleDO> list2 = indexService.getFooterCenter();
		model.addAttribute("centerList", list2);
		
		if(teamId!=null) {
			areaId=teamId;
		}
		
		params.put("parentRegionCode", areaId);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);
		
		// 查询列表数据
		params.put("pids", areaId);
		String ids = regionService.getTeamAndAreaByUserRole(Long.valueOf(areaId));
		params.put("ids", ids);
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit", 10);
		if (flag == 0) {
			params.put("sort", "n.is_top desc,n.public_time desc");
		} else if (flag == 1) {
			params.put("sort", "n.is_red_peper desc,n.public_time desc");
		} else if (flag == 2) {
			params.put("sort", "n.number_of_likes desc,n.public_time desc");
		}
		// 查询列表数据
		List<InfoDO> infoList = infoService.list(params);
		model.addAttribute("newsList", infoList);
		model.addAttribute("areaId", areaId);
		model.addAttribute("flag", flag);
		params.clear();
		List<AdvertisementDO> dataList = indexService.getIndexAdvList(areaId,Dictionary.AdvPosition.NEWS_LIST,params);//获取首页广告
		model.addAttribute("advList", dataList);
		model.addAttribute("title",setupService.get(1).getTitle());
		return "pc/newsList";
	}
	
	@RequestMapping("/queryInfoList")
	@ResponseBody
	public List<InfoDO> queryInfoList(@RequestParam Integer areaId, @RequestParam Integer flag,@RequestParam Map<String, Object> para, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 查询列表数据
		params.put("pids", areaId);
		String ids = regionService.getTeamAndAreaByUserRole(Long.valueOf(areaId));
		PageUtils page = new PageUtils(para);
		page.put("ids", ids);
		page.put("status", 1);
		page.put("isDel", 0);
		if (flag == 0) {
			params.put("sort", "n.is_top desc,n.public_time desc");
		} else if (flag == 1) {
			params.put("sort", "n.is_red_peper desc,n.public_time desc");
		} else if (flag == 2) {
			params.put("sort", "n.number_of_likes desc,n.public_time desc");
		}
		// 查询列表数据
		List<InfoDO> infoList = infoService.list(page);
		model.addAttribute("title",setupService.get(1).getTitle());
		return infoList;
	}

	// 关注团队
	@ResponseBody
	@PostMapping("/attention")
	@Transactional
	public R attention(@RequestParam Map<String, Object> params) {
		Integer i = null;
		// 0:+1失败 1:+1成功 2:表示+1成功&&积分+1 3:获取不到当前用户
		i = dynamic(params, 7);
		R r = new R();
		if (i == 1) {
			r.put("code", 0);
		} else if (i == 3) {
			r.put("code", 1);
		} else {
			return R.error("2");
		}
		return r;
	}
	
	public List<Map<String,Object>> topDays() {
		SetupDO setup;
		List<SetupDO> list = setupService.list(new HashMap<String,Object>(16));
		if (list!=null) {
			setup = list.get(0);
		}else {
			setup=new SetupDO();
		}
		List<Map<String,Object>> listTop = new ArrayList<Map<String,Object>>();
		String[] split = setup.getTopCount().split(",");
		for(String s : split) {
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("count", s);
			listTop.add(data);
		}
		
		return listTop;
	}
	
	@ResponseBody
	@RequestMapping("/shareCode")
	public R getQrCode(@RequestParam Map<String,Object> params) {
		R r=new R();
		String url = baseService.createQrcode(params.get("webUrl").toString());
		if(url!=null) {
			r.put("code", 0);
			r.put("url", url);
			return r;
		}else {
			return r.error();
		}

	}
	
	
	
}
