package com.fly.actApply.controller;

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

import com.fly.activity.domain.ApplyDO;
import com.fly.activity.service.ApplyService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;


/**
 * 活动报名表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-08 11:07:53
 */
 
@Controller
@RequestMapping("/actApply/apply")
public class ApplyController {
	@Autowired
	private ApplyService applyService;
	
	@GetMapping()
	@RequiresPermissions("actApply:apply:apply")
	String Apply(){
	    return "actApply/apply/apply";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("actApply:apply:apply")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ApplyDO> applyList = applyService.list(query);
		int total = applyService.count(query);
		PageUtils pageUtils = new PageUtils(applyList, total);
		return pageUtils;
	}
	@GetMapping("/auditList/{id}")
	@RequiresPermissions("actApply:apply:audit")
	String auditList(@PathVariable("id") Long id,Model model){
		model.addAttribute("id", id);
	    return "actApply/apply/apply";
	}
	@GetMapping("/add")
	@RequiresPermissions("actApply:apply:add")
	String add(){
	    return "actApply/apply/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("actApply:apply:edit")
	String edit(@PathVariable("id") Long id,Model model){
		ApplyDO apply = applyService.get(id);
		model.addAttribute("apply", apply);
	    return "actApply/apply/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("actApply:apply:add")
	public R save( ApplyDO apply){
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
	@RequiresPermissions("actApply:apply:edit")
	public R update( ApplyDO apply){
		apply.setExamine(new Date());
		applyService.update(apply);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("actApply:apply:remove")
	public R remove( Long id){
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
	@RequiresPermissions("actApply:apply:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		applyService.batchRemove(ids);
		return R.ok();
	}
	
}
