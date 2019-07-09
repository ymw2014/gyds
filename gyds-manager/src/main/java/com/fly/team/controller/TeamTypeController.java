package com.fly.team.controller;

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

import com.fly.team.domain.TypeDO;
import com.fly.team.service.TeamTypeService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;


/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-04 10:09:09
 */
@Controller
@RequestMapping("/team/type")
public class TeamTypeController {
	@Autowired
	private TeamTypeService teamTypeService;
	
	@GetMapping()
	@RequiresPermissions("team:type:type")
	String Type(){
	    return "team/type/type";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("team:type:type")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TypeDO> typeList = teamTypeService.list(query);
		int total = teamTypeService.count(query);
		PageUtils pageUtils = new PageUtils(typeList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("team:type:add")
	String add(){
	    return "team/type/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("team:type:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		TypeDO type = teamTypeService.get(id);
		model.addAttribute("type", type);
	    return "team/type/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("team:type:add")
	public R save( TypeDO type){
		type.setCreateTime(new Date());
		if(teamTypeService.save(type)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("team:type:edit")
	public R update( TypeDO type){
		teamTypeService.update(type);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("team:type:remove")
	public R remove( Integer id){
		if(teamTypeService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("team:type:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		teamTypeService.batchRemove(ids);
		return R.ok();
	}
	
}
