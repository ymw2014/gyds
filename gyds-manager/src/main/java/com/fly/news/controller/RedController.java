package com.fly.news.controller;

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

import com.fly.news.domain.RedDO;
import com.fly.news.service.RedService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 红包附表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-05 11:55:52
 */
 
@Controller
@RequestMapping("/news/red")
public class RedController {
	@Autowired
	private RedService redService;
	
	@GetMapping()
	@RequiresPermissions("news:red:red")
	String Red(){
	    return "news/red/red";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("news:red:red")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<RedDO> redList = redService.list(query);
		int total = redService.count(query);
		PageUtils pageUtils = new PageUtils(redList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("news:red:add")
	String add(){
	    return "news/red/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("news:red:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		RedDO red = redService.get(id);
		model.addAttribute("red", red);
	    return "news/red/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("news:red:add")
	public R save( RedDO red){
		if(redService.save(red)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("news:red:edit")
	public R update( RedDO red){
		redService.update(red);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("news:red:remove")
	public R remove( Integer id){
		if(redService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("news:red:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		redService.batchRemove(ids);
		return R.ok();
	}
	
}
