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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.domain.UserDO;
import com.fly.news.domain.DynamicDO;
import com.fly.news.domain.InfoDO;
import com.fly.news.domain.MapVoDo;
import com.fly.news.service.DynamicService;
import com.fly.news.service.InfoService;
import com.fly.system.utils.ShiroUtils;

@Controller
@RequestMapping("/pc/news/")
public class NewsInfoController extends BaseDynamicController{
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
	//文章分享
	@RequestMapping(value="/share",method=RequestMethod.GET)
	@ResponseBody
	public R shareNewInfoLog(@RequestParam Map<String,Object> para) {
		if(dynamic(para,1)==1){
				return R.ok();
			}
			return R.error();
			
	}
	//文章点赞
	@RequestMapping(value="/likes",method=RequestMethod.GET)
	@ResponseBody
	public R likes(Map<String,Object> params) {
		if(dynamic(params,1)==1){
				return R.ok();
			}
			return R.error();
	}
}


