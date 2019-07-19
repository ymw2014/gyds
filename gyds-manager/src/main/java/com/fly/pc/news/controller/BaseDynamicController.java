package com.fly.pc.news.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.news.dao.InfoDao;
import com.fly.news.domain.DynamicDO;
import com.fly.news.domain.InfoDO;
import com.fly.news.domain.RewardInfoDO;
import com.fly.news.service.DynamicService;
import com.fly.news.service.RewardInfoService;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;


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
	//入参 params:newsId(文章id)retype(转发类型)rewardPrice(打赏金额)
	public Integer dynamic(Map<String,Object> params,Integer type) {
		Integer i =0;
		UserDO user = null; 
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
			if(dynamicService.save(dynamic)>0){
				//1:分享次数2: 评论次数3:文章点赞次数4:打赏次数
				params.put("numberOfShares",1);
				infoDao.updateDynamic(params);
				i=1;
			}
			break;
		//文章 点赞
		case 2:
			 	dynamic = new DynamicDO();
				dynamic.setType(1);
				dynamic.setNewsId(Long.parseLong(params.get("newsId")+""));
			if(user!=null) {
			 dynamic.setMemberId(user.getUserId()); 
		 }
		if(dynamicService.save(dynamic)>0){
			params.put("numberOfLikes",3);
			infoDao.updateDynamic(params);
			i=1;
		}
			break;
		//打赏
		case 3:
			rewardInfoDO = new RewardInfoDO();
			rewardInfoDO.setNewsId(Integer.valueOf(params.get("newsId").toString()));
			rewardInfoDO.setRewardPrice(BigDecimal.valueOf(Long.parseLong(params.get("price").toString())));
			if(user!=null) {
				 dynamic.setMemberId(user.getUserId()); 
			 }
			if(rewardInfoService.save(rewardInfoDO)>0) {
				params.put("rewardCount",4);
				infoDao.updateDynamic(params);
				i=1;
			}
			break;
		case 4:
			
			break;
		case 5:
			
			break;
		case 6:
	
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
	
	public Integer creadOrder(Map<String,Object> params) {
		Integer i =0;
		OrderDO order = new OrderDO();
		UserDO user = null; 
		user = ShiroUtils.getUser();
		if(user!=null) {
			order.setUserId(Integer.valueOf(user.getUserId().toString())); 
		 }
		order.setOrderType(Integer.valueOf(params.get("orderType").toString()));
		order.setOrderNumber(new Date().getTime()+"");
		order.setExpIncType(Integer.valueOf(params.get("expIncType").toString()));
		order.setPrice(BigDecimal.valueOf(Long.parseLong(params.get("price").toString())));
		order.setIsDel(0);
		//产生订单
		if(orderService.save(order)>0){
			return i=1;
		}
		return i;
	}
	//入参:params id (资讯id)
	public Integer is_likes(Integer id) {
		Map<String, Object> params  = new HashMap<String, Object>();
		Integer i = 0 ;
		UserDO user = null; 
		user = ShiroUtils.getUser();
		if(user!=null) {
			Long userId =  user.getUserId();
			params.put("memberId", user);
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
	//入参:params id (资讯id)
		public Integer is_top(Integer id) {
			Map<String, Object> params  = new HashMap<String, Object>();
			Integer i = 0 ;
			UserDO user = null; 
			user = ShiroUtils.getUser();
			if(user!=null) {
				Long userId =  user.getUserId();
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
			 }
			//如果返回0表示未登录或无此用户
			return i;
		}
}
