package com.fly.project.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.base.BaseService;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.project.domain.ProjectDO;
import com.fly.project.domain.ProjectInfoDO;
import com.fly.project.service.ProjectService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 申请参加项目
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-09 16:35:53
 */
 
@Controller
@RequestMapping("/project/project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private BaseService baseService;
	@Autowired
	private OrderService orderService;
	
	@GetMapping()
	@RequiresPermissions("project:project:project")
	String Project(Long id,Integer status,Model model){
			model.addAttribute("type", id);
			model.addAttribute("status", status);
	    return "project/project/project";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("project:project:project")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ProjectDO> projectList = projectService.list(query);
		int total = projectService.count(query);
		PageUtils pageUtils = new PageUtils(projectList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("project:project:add")
	String add(){
	    return "project/project/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("project:project:edit")
	String edit(@PathVariable("id") Long id,Model model){
		ProjectDO project = projectService.get(id);
		model.addAttribute("project", project);
	    return "project/project/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("project:project:add")
	public R save( ProjectDO project){
		project.setCreateTime(new Date());
		project.setStatus(1);
		if(projectService.save(project)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("project:project:edit")
	public R update( ProjectDO project){
		projectService.update(project);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("project:project:remove")
	public R remove( Long id){
		if(projectService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("project:project:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		projectService.batchRemove(ids);
		return R.ok();
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping("/examine")
	@RequiresPermissions("project:info:auth")
	public R examine(Long id,Integer status) {
		ProjectDO project = projectService.get(id);
		project.setStatus(status);
		Integer flag = project.getDuration();
		if(flag==1) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, 366);
			project.setEndTime(c.getTime());
		}
		
		if(projectService.update(project)>0) {
			OrderDO order = orderService.get(project.getOrder());
			if(order!=null) {
				baseService.productOfDomestic(order.getExpIncType(), order.getPrice(), project.getProjectId());
			}
			return R.ok();
		}
		return R.error();
	}
	
}
