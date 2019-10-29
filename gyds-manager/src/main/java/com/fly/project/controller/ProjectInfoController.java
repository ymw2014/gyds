package com.fly.project.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
import com.fly.project.domain.ProjectInfoDO;
import com.fly.project.domain.ProjectTypeDO;
import com.fly.project.service.ProjectInfoService;
import com.fly.project.service.ProjectTypeService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;
import com.fly.verifyName.domain.NameDO;

/**
 * 项目详情
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-09 11:32:41
 */
 
@Controller
@RequestMapping("/project/info")
public class ProjectInfoController {
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private ProjectTypeService ProjectTypeService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private OrderService orderService;
	@Autowired 
	private TeamService teamService;
	
	@GetMapping()
	@RequiresPermissions("project:info:info")
	String Info(){
	    return "project/info/info";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("project:info:info")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ProjectInfoDO> infoList = projectInfoService.list(query);
		int total = projectInfoService.count(query);
		PageUtils pageUtils = new PageUtils(infoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("project:info:add")
	String add(Model model){
		Map<String, Object> params=new HashMap<>();
		params.put("userId", ShiroUtils.getUserId());
		List<TeamDO> list = teamService.list(params);
		if(!list.isEmpty()) {
			TeamDO team = list.get(0);
			model.addAttribute("teamId", team.getId());
		}else {
			model.addAttribute("teamId", -1);
		}
		List<ProjectTypeDO> type = ProjectTypeService.list(null);
		model.addAttribute("type", type);
	    return "project/info/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("project:info:edit")
	String edit(@PathVariable("id") Long id,Model model){
		ProjectInfoDO info = projectInfoService.get(id);
		List<ProjectTypeDO> type = ProjectTypeService.list(null);
		model.addAttribute("info", info);
		model.addAttribute("type", type);
	    return "project/info/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("project:info:add")
	public R save(ProjectInfoDO info){
		info.setCreateTime(new Date());
		info.setStatus(1);
		//BigDecimal cost = info.getCost();
		//int i = cost.compareTo(new BigDecimal(0));
		if(info.getTeamId()==-1) {
			return R.error("您不是团长,不能发起项目");
		}
		if(projectInfoService.save(info)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("project:info:edit")
	public R update( ProjectInfoDO info){
		projectInfoService.update(info);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("project:info:remove")
	public R remove( Long id){
		if(projectInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("project:info:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		projectInfoService.batchRemove(ids);
		return R.ok();
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping("/examine")
	@RequiresPermissions("project:info:auth")
	public R examine(Long id,Integer status) {
		ProjectInfoDO info = projectInfoService.get(id);
		info.setStatus(status);
		if(projectInfoService.update(info)>0) {
			OrderDO order = orderService.get(info.getOrder());
			if(order!=null) {
				baseService.productOfDomestic(order.getExpIncType(), order.getPrice(),info.getId());
			}
			return R.ok();
		}
		return R.error();
	}
	
}
