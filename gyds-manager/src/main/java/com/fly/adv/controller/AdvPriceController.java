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

import com.fly.adv.domain.AdvPriceDO;
import com.fly.adv.service.AdvPriceService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 广告价格配置表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-19 15:27:07
 */
 
@Controller
@RequestMapping("/adv/advPrice")
public class AdvPriceController {
	@Autowired
	private AdvPriceService advPriceService;
	
	@GetMapping()
	@RequiresPermissions("adv:advPrice:advPrice")
	String AdvPrice(){
	    return "adv/advPrice/advPrice";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("adv:advPrice:advPrice")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AdvPriceDO> advPriceList = advPriceService.list(query);
		int total = advPriceService.count(query);
		PageUtils pageUtils = new PageUtils(advPriceList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("adv:advPrice:add")
	String add(){
	    return "adv/advPrice/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("adv:advPrice:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		AdvPriceDO advPrice = advPriceService.get(id);
		model.addAttribute("advPrice", advPrice);
	    return "adv/advPrice/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("adv:advPrice:add")
	public R save( AdvPriceDO advPrice){
		if(advPriceService.save(advPrice)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("adv:advPrice:edit")
	public R update( AdvPriceDO advPrice){
		advPriceService.update(advPrice);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("adv:advPrice:remove")
	public R remove( Integer id){
		if(advPriceService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("adv:advPrice:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		advPriceService.batchRemove(ids);
		return R.ok();
	}
	
}
