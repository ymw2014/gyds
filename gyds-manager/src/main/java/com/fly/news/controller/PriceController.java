package com.fly.news.controller;

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
import com.fly.news.domain.PriceDO;
import com.fly.news.service.PriceService;
import com.fly.sys.domain.SetupDO;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;


/**
 * 区域置顶价格配置表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-18 16:01:07
 */
 
@Controller
@RequestMapping("/news/price")
public class PriceController {
	@Autowired
	private PriceService priceService;
	
	@GetMapping()
	@RequiresPermissions("news:price:price")
	String Price(Model model){
		PriceDO priceDo;
		Map<String, Object> params=new HashMap<>(16);
		params.put("regionCode", ShiroUtils.getUser().getDeptId());
		List<PriceDO> list = priceService.list(params);
		priceDo=list.size()>0?list.get(0):new PriceDO();
		model.addAttribute("price", priceDo);
	    return "news/price/edit";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("news:price:price")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PriceDO> priceList = priceService.list(query);
		int total = priceService.count(query);
		PageUtils pageUtils = new PageUtils(priceList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("news:price:add")
	String add(){
	    return "news/price/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("news:price:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		PriceDO price = priceService.get(id);
		model.addAttribute("price", price);
	    return "news/price/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("news:price:add")
	public R save( PriceDO price){
		if(priceService.save(price)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("news:price:edit")
	public R update( PriceDO price){
		if(price.getId()==null) {
			price.setRegionCode(ShiroUtils.getUser().getDeptId());
			priceService.save(price);
		}else {
			priceService.update(price);
		}
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("news:price:remove")
	public R remove( Integer id){
		if(priceService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("news:price:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		priceService.batchRemove(ids);
		return R.ok();
	}
	
}
