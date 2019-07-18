package com.fly.pc.news.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fly.domain.UserDO;
import com.fly.news.dao.InfoDao;
import com.fly.news.domain.DynamicDO;
import com.fly.news.domain.RewardInfoDO;
import com.fly.news.service.DynamicService;
import com.fly.news.service.RewardInfoService;
import com.fly.system.utils.ShiroUtils;


@Controller
public class BaseDynamicController {
	
	@Autowired
	private DynamicService dynamicService;
	@Autowired
	private InfoDao infoDao;
	@Autowired
	RewardInfoService rewardInfoService;
	
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
				dynamic.setNewsId((Long) params.get("newsId"));
			if(user!=null) {
			 dynamic.setMemberId(user.getUserId()); 
		 }
		if(dynamicService.save(dynamic)>0){
			params.put("numberOfShares",3);
			infoDao.updateDynamic(params);
			i=1;
		}
			break;
		//打赏
		case 3:
			rewardInfoDO = new RewardInfoDO();
			rewardInfoDO.setNewsId((Integer) params.get("newsId"));
			rewardInfoDO.setRewardPrice((BigDecimal) params.get("rewardPrice"));
			if(user!=null) {
				 dynamic.setMemberId(user.getUserId()); 
			 }
			if(rewardInfoService.save(rewardInfoDO)>0) {
				params.put("numberOfShares",4);
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

}
