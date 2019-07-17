package com.fly.pc.news.controller;

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

import com.fly.news.domain.DynamicDO;
import com.fly.news.service.DynamicService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;


/**
 * 咨询动态表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-16 11:50:51
 */
 
@Controller
@RequestMapping("/pc/news/dynamic")
public class DynamicController {
	@Autowired
	private DynamicService dynamicService;
	
	@GetMapping()
	@RequiresPermissions("news:dynamic:dynamic")
	String Dynamic(){
	    return "news/dynamic/dynamic";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("news:dynamic:dynamic")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DynamicDO> dynamicList = dynamicService.list(query);
		int total = dynamicService.count(query);
		PageUtils pageUtils = new PageUtils(dynamicList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("news:dynamic:add")
	String add(){
	    return "news/dynamic/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("news:dynamic:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		DynamicDO dynamic = dynamicService.get(id);
		model.addAttribute("dynamic", dynamic);
	    return "news/dynamic/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("news:dynamic:add")
	public R save( DynamicDO dynamic){
		if(dynamicService.save(dynamic)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("news:dynamic:edit")
	public R update( DynamicDO dynamic){
		dynamicService.update(dynamic);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("news:dynamic:remove")
	public R remove( Integer id){
		if(dynamicService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("news:dynamic:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		dynamicService.batchRemove(ids);
		return R.ok();
	}
	
}
