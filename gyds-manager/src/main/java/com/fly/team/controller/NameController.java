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

import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;
import com.fly.verifyName.domain.NameDO;
import com.fly.verifyName.service.NameService;

/**
 * 实名认证申请表/入团/建团/代理商入驻
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-07 14:06:00
 */
 
@Controller
@RequestMapping("/verifyName/name")
public class NameController {
	@Autowired
	private NameService nameService;
	
	@GetMapping()
	@RequiresPermissions("verifyName:name:name")
	String Name(){
	    return "team/verifyName/name";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("verifyName:name:name")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<NameDO> nameList = nameService.list(query);
		int total = nameService.count(query);
		PageUtils pageUtils = new PageUtils(nameList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("verifyName:name:add")
	String add(){
	    return "team/verifyName/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("verifyName:name:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		NameDO name = nameService.get(id);
		model.addAttribute("name", name);
	    return "team/verifyName/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("verifyName:name:add")
	public R save( NameDO name){
		if(nameService.save(name)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("verifyName:name:edit")
	public R update( NameDO name){
		nameService.update(name);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("verifyName:name:remove")
	public R remove( Integer id){
		if(nameService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("verifyName:name:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		nameService.batchRemove(ids);
		return R.ok();
	}
	
}
