package com.fly.pc.news.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fly.activity.dao.ActivityDao;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.news.dao.CommentDao;
import com.fly.news.dao.InfoDao;
import com.fly.news.domain.CommentDO;
import com.fly.news.domain.DynamicDO;
import com.fly.news.domain.InfoDO;
import com.fly.news.domain.RewardInfoDO;
import com.fly.news.service.DynamicService;
import com.fly.news.service.RewardInfoService;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.points.dao.PointsDao;
import com.fly.points.domain.PointsDO;
import com.fly.points.service.PointsService;
import com.fly.sys.dao.SetupDao;
import com.fly.system.dao.UserDao;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.DateUtils;
import com.fly.utils.R;


@Controller
public class BaseDynamicController {

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
	
/**
 * 
 * @param 
 * @title 		记录+1
 * @return   0:+1失败          1:+1成功            2:表示+1成功&&积分+1       3:获取不到当前用户
 * @param   newsId(文章id)  actId(活动id)   trtype(转发类型)    rewardPrice(打赏金额)  
 * @param	type:新闻(1 分享 2点赞 3 打赏 4评论) 活动(5 关注 6分享)
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
			rewardInfoDO.setRewardPrice(BigDecimal.valueOf(Long.parseLong(params.get("price").toString())));
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
		UserDO user = null; 
		user = ShiroUtils.getUser();
		if(user!=null) {
			order.setUserId(Integer.valueOf(user.getUserId().toString())); 
		}
		if(params.get("orderType")!=null) {
			order.setOrderType(Integer.valueOf(params.get("orderType").toString()));
		}if(params.get("expIncType")!=null) {
			order.setExpIncType(Integer.valueOf(params.get("expIncType").toString()));
		}
		if(params.get("price")!=null) {
			order.setPrice(BigDecimal.valueOf(Long.parseLong(params.get("price").toString())));
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
		order.setIsDel(0);
		order.setOrderNumber(new Date().getTime()+"");
		order.setBusinessTime(new Date());
		//产生订单
		if(orderService.save(order)>0){
			i=order.getId();
		}
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
		//如果返回0表示未登录或无此用户
		return i;
	}
	//return 0:扣款失败 -1表示余额不足 1表示扣款成功 2表示无此用户
	public synchronized Integer deductMoney(Map<String,Object> params) {
		Integer i = 2;
		Long userId = null; 
		UserDO user = null; 
		userId = ShiroUtils.getUserId();
		user = userMapper.get(userId);
		if(user!=null) {
			BigDecimal price = BigDecimal.valueOf(Long.parseLong(params.get("price").toString()));
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
	public Integer addPoints(PointsDO Points) {
		Integer i = 1;
		Date startTime = DateUtils.weeHours(new Date(), 0);
		Date endTime = DateUtils.weeHours(new Date(), 1);
		Integer integral = 0;
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("memberId", Points.getMemberId());
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<PointsDO> userpoints=pointsDao.list(params);
		if(userpoints!=null &&userpoints.size()>0) {
			for(PointsDO pointsDO: userpoints) {
				integral+=pointsDO.getIntegral();
			}
		} 
		if(integral<10) {
			Points.setIntegral(setupDao.get(1).getPunchTheClockIntegral());
			if(pointsDao.save(Points)>0) {
				return i=2;
			}
		}
		return i ;
	}
}
