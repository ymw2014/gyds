package com.fly.project.controller;

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

import com.fly.project.domain.ProjectTypeDO;
import com.fly.project.service.ProjectTypeService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 项目类型
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-09 10:57:06
 */
 
@Controller
@RequestMapping("/project/type")
public class ProjectTypeController {
	@Autowired
	private ProjectTypeService projectTypeService;
	
	@GetMapping()
	@RequiresPermissions("project:type:type")
	String Type(){
	    return "project/type/type";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("project:type:type")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ProjectTypeDO> typeList = projectTypeService.list(query);
		int total = projectTypeService.count(query);
		PageUtils pageUtils = new PageUtils(typeList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("project:type:add")
	String add(){
	    return "project/type/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("project:type:edit")
	String edit(@PathVariable("id") Long id,Model model){
		ProjectTypeDO type = projectTypeService.get(id);
		model.addAttribute("type", type);
	    return "project/type/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("project:type:add")
	public R save( ProjectTypeDO type){
		type.setCreateTime(new Date());
		if(projectTypeService.save(type)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("project:type:edit")
	public R update( ProjectTypeDO type){
		projectTypeService.update(type);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("project:type:remove")
	public R remove( Long id){
		if(projectTypeService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("project:type:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		projectTypeService.batchRemove(ids);
		return R.ok();
	}
	
	@PostMapping( "/queryCost")
	@ResponseBody
	public R queryCost(Long id){
		R r = new R();
		ProjectTypeDO type = projectTypeService.get(id);
		r.put("cost", type.getCost());
		return r;
	}
	
}
