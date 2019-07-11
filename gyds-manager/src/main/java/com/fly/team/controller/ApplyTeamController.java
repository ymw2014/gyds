package com.fly.team.controller;

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

import com.fly.team.domain.ApplyTeamDO;
import com.fly.team.service.ApplyTeamService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 入团申请表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-11 15:31:51
 */
 
@Controller
@RequestMapping("/team/apply")
public class ApplyTeamController {
	@Autowired
	private ApplyTeamService applyService;
	
	@GetMapping()
	@RequiresPermissions("team:apply:apply")
	String Apply(){
	    return "team/apply/apply";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("team:apply:apply")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ApplyTeamDO> applyList = applyService.list(query);
		int total = applyService.count(query);
		PageUtils pageUtils = new PageUtils(applyList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("team:apply:add")
	String add(){
	    return "team/apply/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("team:apply:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ApplyTeamDO apply = applyService.get(id);
		model.addAttribute("apply", apply);
	    return "team/apply/edit";
	}
	@GetMapping("/applyMember/{id}")
	@RequiresPermissions("team:apply:applyMember")
	String applyMember(@PathVariable("id") Integer id,Model model){
		model.addAttribute("teamId", id);
	    return "team/apply/apply";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("team:apply:add")
	public R save( ApplyTeamDO apply){
		if(applyService.save(apply)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("team:apply:edit")
	public R update( ApplyTeamDO apply){
		applyService.update(apply);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("team:apply:remove")
	public R remove( Integer id){
		if(applyService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("team:apply:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		applyService.batchRemove(ids);
		return R.ok();
	}
	
}
