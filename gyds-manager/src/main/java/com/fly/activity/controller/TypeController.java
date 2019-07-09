package com.fly.activity.controller;

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

import com.fly.activity.dao.TypeDao;
import com.fly.activity.domain.TypeDO;
import com.fly.activity.service.TypeService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;


/**
 * 活动类型表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-04 14:40:10
 */
 
@Controller
@RequestMapping("/activity/type")
public class TypeController {
	@Autowired
	private TypeService typeService;
	
	@GetMapping()
	@RequiresPermissions("activity:type:type")
	String Type(){
	    return "activity/type/type";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("activity:type:type")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TypeDO> typeList = typeService.list(query);
		int total = typeService.count(query);
		PageUtils pageUtils = new PageUtils(typeList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("activity:type:add")
	String add(){
	    return "activity/type/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("activity:type:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		TypeDO type = typeService.get(id);
		model.addAttribute("type", type);
	    return "activity/type/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("activity:type:add")
	public R save( TypeDO type){
		type.setCreateTime(new Date());
		if(typeService.save(type)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("activity:type:edit")
	public R update( TypeDO type){
		typeService.update(type);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("activity:type:remove")
	public R remove( Integer id){
		if(typeService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("activity:type:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		typeService.batchRemove(ids);
		return R.ok();
	}
	
}
