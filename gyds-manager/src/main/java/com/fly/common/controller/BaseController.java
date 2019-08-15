package com.fly.common.controller;


import com.fly.activity.dao.ActivityDao;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.news.dao.CommentDao;
import com.fly.news.dao.InfoDao;
import com.fly.news.dao.PacketDao;
import com.fly.news.dao.RedDao;
import com.fly.news.domain.CommentDO;
import com.fly.news.domain.DynamicDO;
import com.fly.news.domain.InfoDO;
import com.fly.news.domain.PacketDO;
import com.fly.news.domain.RedDO;
import com.fly.news.domain.RewardInfoDO;
import com.fly.news.service.DynamicService;
import com.fly.news.service.RewardInfoService;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.points.dao.PointsDao;
import com.fly.points.domain.PointsDO;
import com.fly.sys.dao.SetupDao;
import com.fly.system.dao.UserDao;
import com.fly.system.service.RegionService;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.dao.TeamDao;
import com.fly.team.dao.TeamTypeDao;
import com.fly.team.domain.TypeDO;
import com.fly.utils.DateUtils;
import com.fly.utils.R;
import com.fly.volunteer.dao.VolunteerDao;
import com.fly.volunteer.domain.VolunteerDO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.openxmlformats.schemas.officeDocument.x2006.docPropsVTypes.DecimalDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private DynamicService dynamicService;
	@Autowired
	private InfoDao infoDao;
	@Autowired
	RewardInfoService rewardInfoService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserDao userMapper;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private PointsDao pointsDao;
	@Autowired
	private SetupDao setupDao;
	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private VolunteerDao volunteerDao;
	@Autowired
	private PacketDao packetDao;
	@Autowired
	private RedDao redDao;
	@Autowired
	private TeamTypeDao typeDao;



	/**
	 * 
	 * @param 
	 * @title 		记录+1
	 * @return   0:+1失败          1:+1成功            2:表示+1成功&&积分+1       3:获取不到当前用户
	 * @param   newsId(文章id)  actId(活动id)   teamId(团队Id)   trtype(转发类型)    rewardPrice(打赏金额)  
	 * @param	type:新闻(1 分享 2点赞 3 打赏 4评论) 活动(5 关注 6分享)团队(7关注)
	 * @return
	 */
	public Integer dynamic(Map<String,Object> params,Integer type) {
		Integer i =0;
		UserDO user = null; 
		PointsDO points = new PointsDO();
		user = ShiroUtils.getUser();
		DynamicDO dynamic = new DynamicDO();
		RewardInfoDO rewardInfoDO = new RewardInfoDO();
		switch (type) {
		//文章 分享
		case 1:
			dynamic = new DynamicDO();
			//0:转发 1:点赞 2:收藏
			dynamic.setType(0);
			dynamic.setNewsId(Long.parseLong(params.get("newsId")+"") );
			if(user!=null) {
				dynamic.setMemberId(user.getUserId()); 
			}
			//1:微信 2:QQ空间 3:QQ 4:微博
			dynamic.setTranspondType(Integer.valueOf(params.get("trType")+""));
			dynamic.setCreatTime(new Date());
			dynamic.setActType(1);
			if(dynamicService.save(dynamic)>0){
				//1:分享次数2: 评论次数3:文章点赞次数4:打赏次数
				params.put("numberOfShares",1);
				i=infoDao.updateDynamic(params);
				params.put("sharesNumber", 1);
				params.put("id", user.getUserId());
				volunteerDao.update_count(params);
				if(i>0) {
					if(user!=null) {
						points = new PointsDO();
						points.setMemberId(user.getUserId());
						points.setCreateTime(new Date());
						points.setPointsType(type);
						i=addPoints(points);
					}
				}
			}
			break;
			//文章 点赞
		case 2:
			dynamic = new DynamicDO();
			dynamic.setType(1);
			dynamic.setNewsId(Long.parseLong(params.get("newsId")+""));
			dynamic.setActType(1);
			if(user!=null) {
				dynamic.setMemberId(user.getUserId()); 
			}
			dynamic.setCreatTime(new Date());
			if(dynamicService.save(dynamic)>0){
				params.put("numberOfLikes",3);
				i=infoDao.updateDynamic(params);
				params.put("clickNumber", 3);
				params.put("id", user.getUserId());
				volunteerDao.update_count(params);
				if(i>0) {
					if(user!=null) {
						points = new PointsDO();
						points.setMemberId(user.getUserId());
						points.setCreateTime(new Date());
						points.setPointsType(type);
						i=addPoints(points);
					}
				}
			}
			break;
			//打赏
		case 3:
			rewardInfoDO = new RewardInfoDO();
			rewardInfoDO.setNewsId(Integer.valueOf(params.get("newsId").toString()));

			rewardInfoDO.setRewardPrice(new BigDecimal(params.get("price").toString()));
			dynamic.setActType(1);
			if(user!=null) {
				dynamic.setMemberId(user.getUserId()); 
			}
			dynamic.setCreatTime(new Date());
			if(rewardInfoService.save(rewardInfoDO)>0) {
				params.put("rewardCount",4);
				i=infoDao.updateDynamic(params);
			}
			break;
			//评论
		case 4:
			params.put("criticismOfCount",2);
			i=infoDao.updateDynamic(params);
			params.put("commentNumber", 2);
			params.put("id", user.getUserId());
			volunteerDao.update_count(params);
			if(i>0) {
				if(user!=null) {
					points = new PointsDO();
					points.setMemberId(user.getUserId());
					points.setCreateTime(new Date());
					points.setPointsType(type);
					i=addPoints(points);
				}
			}
			break;
			//活动关注	
		case 5:
			dynamic = new DynamicDO();
			dynamic.setType(2);
			dynamic.setNewsId(Long.parseLong(params.get("actId")+""));
			dynamic.setActType(2);
			if(user==null) {
				return 3;
			}
			dynamic.setMemberId(user.getUserId()); 
			dynamic.setCreatTime(new Date());
			if(dynamicService.save(dynamic)>0){
				//1分享 2关注 3 预览 4报名
				params.put("id", params.get("actId"));
				params.put("numberOfCollection",2);
				i=activityDao.updateActDynamic(params);
			}
			break;

		case 6:	
			dynamic = new DynamicDO();
			//0:转发 1:点赞 2:收藏
			dynamic.setType(0);
			dynamic.setNewsId(Long.parseLong(params.get("actId")+"") );
			if(user!=null) {
				dynamic.setMemberId(user.getUserId()); 
			}
			//1:微信 2:QQ空间 3:QQ 4:微博
			dynamic.setTranspondType(Integer.valueOf(params.get("trType")+""));
			dynamic.setCreatTime(new Date());
			dynamic.setActType(2);
			if(dynamicService.save(dynamic)>0){
				//1:分享次数2: 评论次数3:文章点赞次数4:打赏次数
				params.put("numberOfShares",1);
				i=activityDao.updateActDynamic(params);
				if(i>0) {
					if(user!=null) {
						points = new PointsDO();
						points.setMemberId(user.getUserId());
						points.setCreateTime(new Date());
						points.setPointsType(type);
						i=addPoints(points);
					}
				}
			}
			break;
			//关注团队
		case 7:
			dynamic = new DynamicDO();
			//0:转发 1:点赞 2:收藏
			dynamic.setType(2);
			dynamic.setNewsId(Long.parseLong(params.get("teamId")+"") );
			if(user==null) {
				return 3;
			}
			dynamic.setMemberId(user.getUserId()); 
			dynamic.setCreatTime(new Date());
			dynamic.setActType(3);
			i = dynamicService.save(dynamic);
			break;
		default:
			break;
		}
		return i;
	}
	/**
	 * 
	 * @param 
	 * @title 		记录-1
	 * @return   0:-1失败          1:-1成功      
	 * @param   newsId(文章id)  actId(活动id)      
	 * @param	
	 * @return
	 */
	@Transactional
	public Integer dynamicCall(Map<String,Object> params,Integer type) {
		Integer i =0;
		UserDO user = null; 
		user = ShiroUtils.getUser();
		DynamicDO dynamic = new DynamicDO();
		switch (type) {
		//取消关注	
		case 1:
			dynamic = new DynamicDO();
			if(user==null&&params.get("actId")==null) {
				return i;
			}
			dynamic.setNewsId(Long.parseLong(params.get("actId")+""));
			dynamic.setMemberId(user.getUserId()); 
			if(dynamicService.removeCall(dynamic)>0){
				//1分享 2关注 3 预览 4报名
				params.put("numberOfCollection",2);
				i=activityDao.ActDynamicCall(params);
			}
			break;
		default:
			break;
		}
		return i;
	}

	public Integer upRegCode(Integer code) {
		RegionDO region = regionService.get(code);
		Integer parCode = region.getParentRegionCode();
		return parCode;
	}
	//创建订单
	public Integer creadOrder(Map<String,Object> params) {
		Integer i =-1;
		OrderDO order = new OrderDO();
		String userId =null;
		userId = String.valueOf(params.get("userId"));
		if(userId.equals("null")) {
			userId = ShiroUtils.getUserId() == null ? "" : ShiroUtils.getUserId() + ""; 
		}
		order.setUserId(Long.valueOf(userId)); 
		if(params.get("orderType")!=null) {
			order.setOrderType(Integer.valueOf(params.get("orderType").toString()));
		}if(params.get("expIncType")!=null) {
			order.setExpIncType(Integer.valueOf(params.get("expIncType").toString()));
		}
		if(params.get("price")!=null) {
			order.setPrice(new BigDecimal(params.get("price").toString()));
		}
		if(params.get("examineStatus")!=null) {
			order.setExamineStatus(Integer.valueOf(params.get("examineStatus").toString()));
		}
		if(params.get("cashUpType")!=null) {
			order.setCashUpType(Integer.valueOf(params.get("cashUpType").toString()));
		}
		if(params.get("cashOutType")!=null) {
			order.setCashOutType(Integer.valueOf(params.get("cashOutType").toString()));
		}
		if(params.get("examineUser")!=null) {
			order.setExamineUser(Long.valueOf(params.get("examineUser").toString()));
		}
		order.setIsDel(0);
		order.setOrderNumber(new Date().getTime()+"");
		order.setBusinessTime(new Date());
		order.setExamineStatus(1);
		//产生订单
		if(orderService.save(order)>0){
			i=order.getId();
		}
		return i;
	}
	//是否关注
	public Integer is_attention(Integer id) {
		Map<String, Object> params  = new HashMap<String, Object>();
		Integer i = 0 ;
		UserDO user = null; 
		user = ShiroUtils.getUser();
		if(user!=null) {
			Long userId =  user.getUserId();
			params.put("memberId", userId);
			params.put("type", 2);
			params.put("newsId", id);
			params.put("actType",3);
			List<DynamicDO> dyn = dynamicService.list(params);
			if(dyn.size()>0) {
				//1:已关注
				i=1;
			}else {
				//2:未关注
				i=2;
			}
		}
		//如果返回0表示未登录或无此用户
		return i;

	}
	//是否点赞
	//入参:params id (资讯id)
	public Integer is_likes(Integer id) {
		Map<String, Object> params  = new HashMap<String, Object>();
		Integer i = 0 ;
		UserDO user = null; 
		user = ShiroUtils.getUser();
		if(user!=null) {
			Long userId =  user.getUserId();
			params.put("memberId", userId);
			params.put("type", 1);
			params.put("newsId", id);
			params.put("actType",1);
			List<DynamicDO> dyn = dynamicService.list(params);
			if(dyn.size()>0) {
				//1:已点赞
				i=1;
			}else {
				//2:未点赞
				i=2;
			}
		}
		//如果返回0表示未登录或无此用户
		return i;
	}
	//是否置顶
	//入参:params id (资讯id)
	public Integer is_top(Integer id) {
		Map<String, Object> params  = new HashMap<String, Object>();
		Integer i = 0 ;
			params.put("isTop", 1);
			params.put("id", id);
			List<InfoDO> info = infoDao.list(params);
			if(info.size()>0) {
				//1:已置顶
				i=1;
			}else {
				//2:未置顶
				i=2;
			}
		return i;
	}
	//是否有红包
	//入参:params id (资讯id)
	public Integer is_red(Integer id) {
		Map<String, Object> params  = new HashMap<String, Object>();
		Integer i = 2 ;
		/*
		 * UserDO user = null; user = ShiroUtils.getUser(); if(user!=null) {
		 */
			params.put("isRedPeper", 1);
			params.put("id", id);
			List<InfoDO> info = infoDao.list(params);
			if(info.size()>0) {
				//1:有红包
				i=1;
			}else {
				//2:无红包
				i=2;
			}
	//	}
		//如果返回0表示未登录或无此用户
		return i;
	}
		//是否领红包
		//入参:params id (资讯id)
		public Integer is_get_red(Integer id) {
			Map<String, Object> params  = new HashMap<String, Object>();
			Integer i = 0 ;
			UserDO user = null; 
			user = ShiroUtils.getUser();
			if(user!=null) {
				InfoDO info = infoDao.get(id);
				params.put("id", info.getRedPeperId());
				params.put("getUserId", user.getUserId());
				params.put("is_get", 2);
				List<RedDO> red = redDao.list(params);
				if(red.size()>0) {
					//1:领过红包
					i=1;
				}else {
					//2:没领红包
					i=2;
				}
			}
			//如果返回0表示未登录或无此用户
			return i;
		}
	//return 0:扣款失败 -1表示余额不足 1表示扣款成功 2表示无此用户
	public synchronized Integer deductMoney(Map<String,Object> params) {
		Integer i = 2;
		Long userId = null; 
		UserDO user = null; 
		try {
			userId = ShiroUtils.getUserId();
		} catch (Exception e) {
			return i;
		}
		user = userMapper.get(userId);
		if(user!=null) {
			BigDecimal price = new BigDecimal(params.get("price").toString());;
			BigDecimal account = user.getAccount();
			if(account!=null&&price!=null) {
				//结果 :-1 小于,0 等于,1 大于
				i =account.compareTo(price);
				if(-1==i) {
					return i;
				}else {
					BigDecimal account1 = account.subtract(price);
					UserDO u = new UserDO();
					u.setUserId(user.getUserId());
					u.setAccount(account1);
					return userMapper.update(u);
				}

			}else {
				return i=-1;
			}
		}

		return i;
	}
	//创建评论
	public Integer creadComm(Map<String,Object> params) {
		Integer i = 0;
		CommentDO comment = new CommentDO();
		UserDO user = null; 
		user = ShiroUtils.getUser();
		if(user!=null) {
			comment.setNewsId(Integer.valueOf(params.get("newsId").toString()));
			comment.setMemberId(Integer.valueOf(user.getUserId().toString()));
			String criticismContentparams= params.get("criticismContent").toString();
			comment.setCreateTime(new Date());
			if(criticismContentparams!=null && criticismContentparams!="") {
				comment.setCriticismContent(criticismContentparams.trim());
				if(commentDao.save(comment)>0) {
					return i=1;
				}
			}
		}else {
			return i;
		}
		return i=-1; 

	}
	//加积分
	@Transactional
	public synchronized Integer addPoints(PointsDO Points) {
		Integer i = 1;
		Date startTime = DateUtils.weeHours(new Date(), 0);
		Date endTime = DateUtils.weeHours(new Date(), 1);
		Integer integral = 0;
		Long userId = Points.getMemberId();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("memberId", userId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<PointsDO> userpoints=pointsDao.list(params);
		if(userpoints!=null &&userpoints.size()>0) {
			for(PointsDO pointsDO: userpoints) {
				integral+=pointsDO.getIntegral();
			}
		} 
		if(integral<10) {
			Integer sys_integral = setupDao.get(1).getPunchTheClockIntegral();
			Points.setIntegral(sys_integral);
			if(pointsDao.save(Points)>0) {
				Map<String,Object> userIntegral = new HashMap<String, Object>();
				userIntegral.put("userId", userId);
				userIntegral.put("platformIntegral", sys_integral);
				if(userMapper.updateIntegral(userIntegral)>0) {
					Map<String,Object> volunteer = new HashMap<String, Object>();
					volunteer.put("userId", userId);
					List<VolunteerDO> volunteerList = volunteerDao.list(volunteer);
					if(volunteerList!=null&&volunteerList.size()>0) {
						Map<String,Object> volunteerIntegral = new HashMap<String, Object>();
						volunteerIntegral.put("integral", sys_integral);
						volunteerIntegral.put("id", volunteerList.get(0).getId());
						if(volunteerDao.updateVolunteer(volunteerIntegral)>0) {
							Map<String,Object> teamIntegral = new HashMap<String, Object>();
							teamIntegral.put("integral", sys_integral);
							teamIntegral.put("id", volunteerList.get(0).getTeamId());
							if(teamDao.updateIntegral(teamIntegral)>0) {

							}
						}
					}
					return 2;
				}
			}
		}
		return i ;
	}

	/**
	 * 计算每人获得红包金额;最小每人0.01元
	 * @param mmm 红包总额
	 * @param number 人数
	 * @return
	 */
	public static List<BigDecimal> math(BigDecimal mmm, int number) {
		if (mmm.doubleValue() < number * 0.01) {
			return null;
		}
		Random random = new Random();
		// 金钱，按分计算 10块等于 1000分
		int money = mmm.multiply(BigDecimal.valueOf(100)).intValue();
		// 随机数总额
		double count = 0;
		// 每人获得随机点数
		double[] arrRandom = new double[number];
		// 每人获得钱数
		List<BigDecimal> arrMoney = new ArrayList<BigDecimal>(number);
		// 循环人数 随机点
		for (int i = 0; i < arrRandom.length; i++) {
			int r = random.nextInt((number) * 99) + 1;
			count += r;
			arrRandom[i] = r;
		}
		// 计算每人拆红包获得金额
		int c = 0;
		for (int i = 0; i < arrRandom.length; i++) {
			// 每人获得随机数相加 计算每人占百分比
			Double x = new Double(arrRandom[i] / count);
			// 每人通过百分比获得金额
			int m = (int) Math.floor(x * money);
			// 如果获得 0 金额，则设置最小值 1分钱
			if (m == 0) {
				m = 1;
			}
			// 计算获得总额
			c += m;
			// 如果不是最后一个人则正常计算
			if (i < arrRandom.length - 1) {
				arrMoney.add(new BigDecimal(m).divide(new BigDecimal(100)));
			} else {
				// 如果是最后一个人，则把剩余的钱数给最后一个人
				arrMoney.add(new BigDecimal(money - c + m).divide(new BigDecimal(100)));
			}
		}
		// 随机打乱每人获得金额
		Collections.shuffle(arrMoney);
		return arrMoney;
	}
	@Transactional
	public Integer creatRed(Map<String,Object> params) {
		Integer i = 2;
		Integer redPeperId = null ;
		InfoDO info = new InfoDO();
		RedDO red;
		UserDO user = ShiroUtils.getUser();
		if(user!=null) {
			PacketDO packetDO = new PacketDO();
			packetDO.setCount(Integer.valueOf(params.get("number").toString()));
			packetDO.setMoney(new BigDecimal(params.get("price").toString()));
			packetDO.setUserId(user.getUserId());
			packetDO.setCreatTime(new Date());
			if(packetDao.save(packetDO)>0) {
				redPeperId = packetDO.getId();
				info.setId(Long.valueOf(params.get("newsId").toString()));
				info.setRedPeperId(redPeperId);
				info.setIsRedPeper(1);
				if(infoDao.update(info)>0) {
					List<BigDecimal> moneys = math(new BigDecimal(params.get("price").toString()), Integer.valueOf(params.get("number").toString()));
					if (!moneys.isEmpty()) {
						for (BigDecimal bigDecimal : moneys) {
							red = new RedDO();
							red.setId(redPeperId);
							red.setPrice(bigDecimal);
							red.setIsGet(1);
							i=redDao.save(red);
						}
						return i ;
					}else {
						return 0;
					}
				}
			}

		}  
		return i;
	}
	public R countCost(Integer id) {
		R r = new R();
		TypeDO type = typeDao.get(id);
		if(type!=null) {
			Double price = type.getPrice();
			r.put("code", 0);
			r.put("price",price);
		}else {
			r.error();
		}
		return r;
	}
	//获取红包
	@Transactional
	public  synchronized  BigDecimal getRed(InfoDO info) {
		Map<String,Object> map  = new HashMap<String, Object>();
		BigDecimal price  = new BigDecimal(0);
		UserDO user = ShiroUtils.getUser();
			map.put("id", info.getRedPeperId());
			map.put("isGet","1");
			List<RedDO> list = redDao.list(map);
			if(list.size()>0) {
				RedDO red = list.get(0);
				price  = red.getPrice();
				red.setIsGet(2);
				red.setGetTime(new Date());
				red.setGetUserId(user.getUserId());
				redDao.update(red);
			}
			map.clear();
			map.put("id", info.getRedPeperId());
			map.put("isGet","1");
			list = redDao.list(map);
			if(list.isEmpty()) {
				info.setIsRedPeper(0);
				infoDao.update(info);
			}
		return price;
	}
	//return 0:扣款失败  1表示扣款成功 
		public synchronized Integer addMoney(Map<String,Object> params) {
			Integer i = 0;
			UserDO user = null; 
			user = userMapper.get(Long.valueOf(params.get("userId").toString()));
			if(user!=null) {
				BigDecimal price = new BigDecimal(params.get("price").toString());;
				BigDecimal account = user.getAccount();
						BigDecimal sum = account.add(price);
						UserDO u = new UserDO();
						u.setUserId(user.getUserId());
						u.setAccount(sum);
						return userMapper.update(u);
			}
			return i;
		}
	/**
	 * 获取用户对象
	 * @return
	 */
	public UserDO getUser() {
		UserDO user = userService.get(ShiroUtils.getUserId());
		return user;
	}
	/**
	 * 获取用户编号
	 * @return
	 */
	public Long getUserId() {
		return getUser().getUserId();
	}
	/**
	 * 获取用户名称
	 * @return
	 */
	public String getUsername() {
		return getUser().getUsername();
	}
}