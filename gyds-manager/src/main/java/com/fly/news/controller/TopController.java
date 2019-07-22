package com.fly.news.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.news.domain.TopDO;
import com.fly.news.service.TopService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 置顶申请列表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-22 15:14:59
 */
 
@Controller
@RequestMapping("/news/top")
public class TopController {
	@Autowired
	private TopService topService;
	
	@GetMapping()
	@RequiresPermissions("news:top:top")
	String Top(){
	    return "news/top/top";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("news:top:top")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TopDO> topList = topService.list(query);
		int total = topService.count(query);
		PageUtils pageUtils = new PageUtils(topList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("news:top:add")
	String add(){
	    return "news/top/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("news:top:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		TopDO top = topService.get(id);
		model.addAttribute("top", top);
	    return "news/top/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("news:top:add")
	public R save( TopDO top){
		if(topService.save(top)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("news:top:edit")
	public R update( TopDO top){
		topService.update(top);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("news:top:remove")
	public R remove( Integer id){
		if(topService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("news:top:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		topService.batchRemove(ids);
		return R.ok();
	}
	
}
