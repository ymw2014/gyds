package com.fly.points.controller;

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

import com.fly.points.domain.PointsDO;
import com.fly.points.service.PointsService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 积分记录
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-11 14:25:54
 */
 
@Controller
@RequestMapping("/points/points")
public class PointsController {
	@Autowired
	private PointsService pointsService;
	
	@GetMapping()
	@RequiresPermissions("points:points:points")
	String Points(){
	    return "points/points/points";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("points:points:points")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PointsDO> pointsList = pointsService.list(query);
		int total = pointsService.count(query);
		PageUtils pageUtils = new PageUtils(pointsList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("points:points:add")
	String add(){
	    return "points/points/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("points:points:edit")
	String edit(@PathVariable("id") Long id,Model model){
		PointsDO points = pointsService.get(id);
		model.addAttribute("points", points);
	    return "points/points/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("points:points:add")
	public R save( PointsDO points){
		if(pointsService.save(points)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("points:points:edit")
	public R update( PointsDO points){
		pointsService.update(points);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("points:points:remove")
	public R remove( Long id){
		if(pointsService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("points:points:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		pointsService.batchRemove(ids);
		return R.ok();
	}
	
}
