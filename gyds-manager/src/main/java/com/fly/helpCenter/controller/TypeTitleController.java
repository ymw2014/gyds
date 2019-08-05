package com.fly.helpCenter.controller;

import java.util.Date;
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

import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.helpCenter.service.TypeTitleService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 
 * 
 * @author Wangyalei
 * @email 15638836857@163.com
 * @date 2019-07-03 14:08:01
 */
 
@Controller
@RequestMapping("/helpCenter/typeTitle")
public class TypeTitleController {
	@Autowired
	private TypeTitleService typeTitleService;
	
	@GetMapping()
	@RequiresPermissions("helpCenter:typeTitle:typeTitle")
	String TypeTitle(){
	    return "helpCenter/typeTitle/typeTitle";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("helpCenter:typeTitle:typeTitle")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TypeTitleDO> typeTitleList = typeTitleService.list(query);
		int total = typeTitleService.count(query);
		PageUtils pageUtils = new PageUtils(typeTitleList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("helpCenter:typeTitle:add")
	String add(){
	    return "helpCenter/typeTitle/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("helpCenter:typeTitle:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		TypeTitleDO typeTitle = typeTitleService.get(id);
		model.addAttribute("typeTitle", typeTitle);
	    return "helpCenter/typeTitle/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("helpCenter:typeTitle:add")
	public R save( TypeTitleDO typeTitle){
		typeTitle.setCreatTime(new Date());
		if(typeTitleService.save(typeTitle)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("helpCenter:typeTitle:edit")
	public R update( TypeTitleDO typeTitle){
		typeTitleService.update(typeTitle);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("helpCenter:typeTitle:remove")
	public R remove( Integer id){
		if(typeTitleService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("helpCenter:typeTitle:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		typeTitleService.batchRemove(ids);
		return R.ok();
	}
	
}
