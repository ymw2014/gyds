package com.fly.proSetup.controller;

import java.util.HashMap;
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

import com.fly.proSetup.domain.ProjectSetupDO;
import com.fly.proSetup.service.ProjectSetupService;
import com.fly.sys.domain.SetupDO;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 项目分佣配置
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-22 15:22:20
 */
 
@Controller
@RequestMapping("/proSetup/projectSetup")
public class ProjectSetupController {
	@Autowired
	private ProjectSetupService projectSetupService;
	
	@GetMapping()
	@RequiresPermissions("proSetup:projectSetup:projectSetup")
	String ProjectSetup(Model model){
		ProjectSetupDO projectSetup1 = projectSetupService.get(2);
		ProjectSetupDO projectSetup2 = projectSetupService.get(1);
		model.addAttribute("projectSetup1", projectSetup1);
		model.addAttribute("projectSetup2", projectSetup2);
	    return "proSetup/projectSetup/edit";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("proSetup:projectSetup:projectSetup")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ProjectSetupDO> projectSetupList = projectSetupService.list(query);
		int total = projectSetupService.count(query);
		PageUtils pageUtils = new PageUtils(projectSetupList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("proSetup:projectSetup:add")
	String add(){
	    return "proSetup/projectSetup/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("proSetup:projectSetup:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ProjectSetupDO projectSetup1 = projectSetupService.get(1);
		ProjectSetupDO projectSetup2 = projectSetupService.get(2);
		model.addAttribute("projectSetup1", projectSetup1);
		model.addAttribute("projectSetup2", projectSetup2);
	    return "proSetup/projectSetup/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("proSetup:projectSetup:add")
	public R save( ProjectSetupDO projectSetup){
		if(projectSetupService.save(projectSetup)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("proSetup:projectSetup:edit")
	public R update( ProjectSetupDO projectSetup){
		projectSetupService.update(projectSetup);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("proSetup:projectSetup:remove")
	public R remove( Integer id){
		if(projectSetupService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("proSetup:projectSetup:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		projectSetupService.batchRemove(ids);
		return R.ok();
	}
	
}
