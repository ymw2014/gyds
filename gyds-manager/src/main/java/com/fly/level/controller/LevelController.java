package com.fly.level.controller;

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

import com.fly.level.domain.LevelDO;
import com.fly.level.service.LevelService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 积分区间表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-09-20 10:20:09
 */
 
@Controller
@RequestMapping("/level/level")
public class LevelController {
	@Autowired
	private LevelService levelService;
	
	@GetMapping()
	@RequiresPermissions("level:level:level")
	String Level(){
	    return "level/level/level";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("level:level:level")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<LevelDO> levelList = levelService.list(query);
		int total = levelService.count(query);
		PageUtils pageUtils = new PageUtils(levelList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("level:level:add")
	String add(){
	    return "level/level/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("level:level:edit")
	String edit(@PathVariable("id") Long id,Model model){
		LevelDO level = levelService.get(id);
		model.addAttribute("level", level);
	    return "level/level/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("level:level:add")
	public R save( LevelDO level){
		level.setType(1);
		level.setCrateTime(new Date());
		if(levelService.save(level)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("level:level:edit")
	public R update( LevelDO level){
		levelService.update(level);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("level:level:remove")
	public R remove( Long id){
		if(levelService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("level:level:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		levelService.batchRemove(ids);
		return R.ok();
	}
	
}
