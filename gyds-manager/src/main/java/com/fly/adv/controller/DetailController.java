package com.fly.adv.controller;

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

import com.fly.adv.domain.DetailDO;
import com.fly.adv.service.DetailService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;


/**
 * 广告记录
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-19 15:16:53
 */
 
@Controller
@RequestMapping("/adv/detail")
public class DetailController {
	@Autowired
	private DetailService detailService;
	
	@GetMapping()
	@RequiresPermissions("adv:detail:detail")
	String Detail(){
	    return "adv/detail/detail";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("adv:detail:detail")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DetailDO> detailList = detailService.list(query);
		int total = detailService.count(query);
		PageUtils pageUtils = new PageUtils(detailList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("adv:detail:add")
	String add(){
	    return "adv/detail/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("adv:detail:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		DetailDO detail = detailService.get(id);
		model.addAttribute("detail", detail);
	    return "adv/detail/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("adv:detail:add")
	public R save( DetailDO detail){
		if(detailService.save(detail)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("adv:detail:edit")
	public R update( DetailDO detail){
		detailService.update(detail);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("adv:detail:remove")
	public R remove( Integer id){
		if(detailService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("adv:detail:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		detailService.batchRemove(ids);
		return R.ok();
	}
	
}
