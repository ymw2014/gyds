package com.fly.system.controller;

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

import com.fly.sys.domain.SetupDO;
import com.fly.sys.service.SetupService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;


/**
 * 配置表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-11 16:11:17
 */
 
@Controller
@RequestMapping("/sys/setup")
public class SetupController {
	@Autowired
	private SetupService setupService;
	
	@GetMapping()
	@RequiresPermissions("sys:setup:setup")
	String Setup(Model model){
		SetupDO setup;
		List<SetupDO> list = setupService.list(new HashMap<String,Object>(16));
		if (list!=null) {
			setup = list.get(0);
		}else {
			setup=new SetupDO();
		}
		model.addAttribute("setup", setup);
	    return "system/setup/edit";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("sys:setup:setup")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SetupDO> setupList = setupService.list(query);
		int total = setupService.count(query);
		PageUtils pageUtils = new PageUtils(setupList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("sys:setup:add")
	String add(){
	    return "sys/setup/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("sys:setup:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		SetupDO setup = setupService.get(id);
		model.addAttribute("setup", setup);
	    return "sys/setup/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("sys:setup:add")
	public R save( SetupDO setup){
		if(setupService.save(setup)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("sys:setup:edit")
	public R update( SetupDO setup){
		setupService.update(setup);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("sys:setup:remove")
	public R remove( Integer id){
		if(setupService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("sys:setup:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		setupService.batchRemove(ids);
		return R.ok();
	}
	
}
