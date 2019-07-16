package com.fly.pc.news.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fly.domain.UserDO;
import com.fly.news.domain.DynamicDO;
import com.fly.news.service.DynamicService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.R;

public class Dynamic_Controller {
	
	@Autowired
	private DynamicService dynamicService;
	
	public R Dynamic(Map<String,Object> params,Integer type) {
		UserDO user = null; 
		user = ShiroUtils.getUser();
		switch (type) {
		//微信分享
		case 1:
			DynamicDO dynamic = new DynamicDO();
			dynamic.setType(0);
			if(user!=null) {
				 dynamic.setMemberId(user.getUserId()); 
			 }
			dynamic.setTranspondType((Integer) params.get("reType"));
			if(dynamicService.save(dynamic)>0){
				
			}
			break;
		case 2:
			
			break;
		case 3:
					
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
			return R.error();
			
	}

}
