package com.fly.pc.news.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.fly.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.domain.UserDO;
import com.fly.news.domain.DynamicDO;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.DynamicService;
import com.fly.news.service.InfoService;
import com.fly.system.utils.ShiroUtils;

@Controller
@RequestMapping("/pc/news/")
public class NewsInfoController {
	@Autowired
	private InfoService infoService;
	@Autowired
	private DynamicService dynamicService;
	
	
	@RequestMapping("/info/{id}")
	public String newInfo(@PathVariable("id") Integer id,Model model) {
		InfoDO info = infoService.get(id);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",10);
		//发布时间排序
		params.put("sort","n.public_time desc");
		List<InfoDO> infoList = infoService.list(params);
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",10);
		//点赞人数排序
		params.put("sort","n.number_of_likes desc");
		List<InfoDO> newsLike = infoService.list(params);
		model.addAttribute("newsList", infoList);
		model.addAttribute("newsLike",newsLike);
		model.addAttribute("info", info);
		return "/pc/newInfo";
	}
	@RequestMapping(value="/share",method=RequestMethod.GET)
	@ResponseBody
	public R shareNewInfoLog(DynamicDO dynamic) {
		UserDO user = null; 
		 dynamic.setCreatTime(new Date());
		 dynamic.setType(0);
		 user = ShiroUtils.getUser();
		 if(user!=null) {
			 dynamic.setMemberId(user.getUserId()); 
		 }
		 if(dynamicService.save(dynamic)>0){
				return R.ok();
			}
			return R.error();
			
	}
	
	@RequestMapping(value="/likes",method=RequestMethod.GET)
	@ResponseBody
	public R likes(DynamicDO dynamic) {
		UserDO user = null; 
		 dynamic.setType(1);
		 user = ShiroUtils.getUser();
		 if(user!=null) {
			 dynamic.setMemberId(user.getUserId()); 
		 }
		 if(dynamicService.save(dynamic)>0){
				return R.ok();
			}
			return R.error();
			
	}
}
