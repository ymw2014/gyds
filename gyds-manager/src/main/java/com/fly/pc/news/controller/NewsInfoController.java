package com.fly.pc.news.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.news.domain.InfoDO;
import com.fly.news.service.InfoService;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.utils.R;

@Controller
@RequestMapping("/pc/news/")
public class NewsInfoController extends BaseDynamicController{
	@Autowired
	private InfoService infoService;
	@Autowired
	private TeamService teamService;
	
	@RequestMapping("/info")
	public String newInfo(@RequestParam Integer id,Model model) {
		InfoDO info = infoService.get(id);
		Map<String, Object> params = new HashMap<String, Object>();
		//团队置顶排序
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",10);
		params.put("teamId", info.getTeamId());
		params.put("sort","n.is_top desc,n.public_time desc");
		List<InfoDO> infoList = infoService.list(params);
		//发布时间排序
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",10);
		params.put("sort","n.is_top desc,n.public_time desc");
		List<InfoDO> newsLike = infoService.list(params);
		//获取团队信息
		TeamDO teamDO = teamService.get(info.getTeamId());
		//县级置顶3条
		Integer code = upRegCode(info.getTeamId());
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",3);
		params.put("topRegion", code);
		params.put("sort","n.is_top desc,n.public_time desc");
		List<InfoDO> newsTop = infoService.list(params);
		//市级置顶3条
		code = upRegCode(code);
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",3);
		params.put("topRegion", code);
		params.put("sort","n.is_top desc,n.public_time desc");
		newsTop.addAll(infoService.list(params));
		//省级置顶3条
		code = upRegCode(code);
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",3);
		params.put("topRegion", code);
		params.put("sort","n.is_top desc,n.public_time desc");
		newsTop.addAll(infoService.list(params));
		//总平台置顶3条
		code = upRegCode(code);
		params.clear();
		params.put("status", 1);
		params.put("isDel", 0);
		params.put("offset", 0);
		params.put("limit",3);
		params.put("topRegion", code+"");
		params.put("sort","n.is_top desc,n.public_time desc");
		newsTop.addAll(infoService.list(params));
		//是否点赞
		Integer l = is_likes(id);
		//0:未登录 1:点赞 2: 未点赞
		model.addAttribute("isLike", l);
		//是否置顶
		Integer t = is_likes(id);
		//0:未登录 1:置顶 2: 未置顶
		model.addAttribute("isTop", t);
		
		model.addAttribute("newsTop", newsTop);
		model.addAttribute("team", teamDO);
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
	public R likes(@RequestParam Map<String,Object> params) {
		if(dynamic(params,2)==1){
				return R.ok();
			}
			return R.error();
	}
	//打赏
		@RequestMapping(value="/reward",method=RequestMethod.POST)
		@ResponseBody
		public R reward(@RequestParam Map<String,Object> params) {
			//产生订单
			if(creadOrder(params)>0){
			//记录+1
			if(dynamic(params,3)==1){
					return R.ok();
				}
			}
				return R.error();
		}
		//置顶
				@RequestMapping(value="/top/{id}",method=RequestMethod.GET)
				public String top(@PathVariable("id") Integer id,Model model) {
					InfoDO info = infoService.get(id);
					Integer code = upRegCode(info.getTeamId());
					model.addAttribute("teamRegion", code);
					code = upRegCode(code);
					model.addAttribute("areaRegion", code);
					code = upRegCode(code);
					model.addAttribute("cityRegion", code);
					code = upRegCode(code);
					model.addAttribute("proRegion", code);
					code = upRegCode(code);
					model.addAttribute("region", code);
					return "/pc/top";
				}
				
	@RequestMapping(value="/topInfo",method=RequestMethod.POST)
	@ResponseBody
	public R comTopInfo(@RequestParam Map<String,Object> params) {
		if(1>0){
			return R.ok();
		}
		return R.error();
	}
}


