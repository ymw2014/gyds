package com.fly.guestlog.controller;

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

import com.fly.guestlog.domain.GuestlogDO;
import com.fly.guestlog.service.GuestlogService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 访客记录表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-23 17:07:37
 */
 
@Controller
@RequestMapping("/guestlog/guestlog")
public class GuestlogController {
	@Autowired
	private GuestlogService guestlogService;
	
	@GetMapping()
	@RequiresPermissions("guestlog:guestlog:guestlog")
	String Guestlog(){
	    return "guestlog/guestlog/guestlog";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("guestlog:guestlog:guestlog")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<GuestlogDO> guestlogList = guestlogService.list(query);
		int total = guestlogService.count(query);
		PageUtils pageUtils = new PageUtils(guestlogList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("guestlog:guestlog:add")
	String add(){
	    return "guestlog/guestlog/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("guestlog:guestlog:edit")
	String edit(@PathVariable("id") Long id,Model model){
		GuestlogDO guestlog = guestlogService.get(id);
		model.addAttribute("guestlog", guestlog);
	    return "guestlog/guestlog/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("guestlog:guestlog:add")
	public R save( GuestlogDO guestlog){
		if(guestlogService.save(guestlog)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("guestlog:guestlog:edit")
	public R update( GuestlogDO guestlog){
		guestlogService.update(guestlog);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("guestlog:guestlog:remove")
	public R remove( Long id){
		if(guestlogService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("guestlog:guestlog:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		guestlogService.batchRemove(ids);
		return R.ok();
	}
	
}
