package com.fly.adv.controller;

import java.math.BigDecimal;
import java.util.Date;
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
import com.fly.adv.domain.AdvPriceDO;
import com.fly.adv.service.AdvPriceService;
import com.fly.common.controller.BaseController;
import com.fly.domain.RegionDO;
import com.fly.sys.domain.SetupDO;
import com.fly.sys.service.SetupService;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
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
public class AdvPriceController extends BaseController{
	@Autowired
	private AdvPriceService advPriceService;
	@Autowired
	private RegionService regionService;
	@Autowired 
	private SetupService stetupService;
	
	
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
		params.put("regionCode", ShiroUtils.getUser().getDeptId());
        Query query = new Query(params);
		List<AdvPriceDO> advPriceList = advPriceService.list(query);
		if(advPriceList!=null&&advPriceList.size()>0) {
			for (AdvPriceDO advPriceDO : advPriceList) {
				advPriceDO.setRegionName(regionService.get(advPriceDO.getRegionCode()).getRegionName());
			}
		}
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
		advPrice.setCreateTime(new Date());
		RegionDO region = regionService.get(getUser().getDeptId());
		List<SetupDO> setup = stetupService.list(new HashMap<>());
		BigDecimal min = setup.get(0).getAdvertMin();//广告最低价格标准
		BigDecimal max = setup.get(0).getAdvertMax();//广告最高价格标准
		if((advPrice.getPrice().compareTo(max))==1) {
			return R.error("广告价格不能高于"+max+"元/天");
		}
		if((advPrice.getPrice().compareTo(min))==-1) {
			return R.error("广告价格不能低于"+min+"元/天");
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("positionNum", advPrice.getPositionNum());
		params.put("regionCode", region.getRegionCode());
		List<AdvPriceDO> advList = advPriceService.list(params);
		advPrice.setRegionCode(region.getRegionCode());
		R r=new R();
		switch(region.getRegionLevel()){ 
	    case 0:  //超管
	    	r=validateSwitch(advList,advPrice,advPrice.getPositionNum(),6);
	    	break;
	    case 1:  //省管理员
	    	r=validateSwitch(advList,advPrice,advPrice.getPositionNum(),5);
	    	break;
	    case 2:  //市管理员
	    	r=validateSwitch(advList,advPrice,advPrice.getPositionNum(),4);
	    	break;
	    case 3:  //县管理员
	    	r=validateSwitch(advList,advPrice,advPrice.getPositionNum(),3);
	    	break;
	    case 4:  //办事处
	    	r=validateSwitch(advList,advPrice,advPrice.getPositionNum(),2);
	    	break;
	    case 5:  //团管理员
	    	r=validateSwitch(advList,advPrice,advPrice.getPositionNum(),1);
	    	break;
	    default:  
	    	;
		}
		return r;
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
	
public R validateSwitch(List<AdvPriceDO> dataList, AdvPriceDO advPrice, Integer positionNum,Integer size) {
		
		switch(positionNum){ 
	    case 1:  //首页
	    	if(dataList.size()>=size) {
	    		return R.error("最多添加"+size+"张首页广告");
	    	};
	    	break;
	    case 2: 
	    	if(dataList.size()>=1) {
	    		return R.error("资讯列表页只能添加一个广告");
	    	};
	    	break;
	    case 3:  
	    	if(dataList.size()>=1) {
	    		return R.error("资讯详情页只能添加一个广告");
	    	};
	    	break;
	    case 4:  
	    	if(dataList.size()>=1) {
	    		return R.error("活动报名页只能添加一个广告");
	    	};
	    	break;
	    case 5:  
	    	if(dataList.size()>=1) {
	    		return R.error("团队详情页只能添加一个广告");
	    	};
	    	break;
	    case 6:  
	    	if(dataList.size()>=1) {
	    		return R.error("志愿者详情页只能添加一个广告");
	    	};
	    case 7:  
	    	if(dataList.size()>=1) {
	    		return R.error("签到页只能添加一个广告");
	    	};
	    	break;
	    default:  
	    	;
	}
		if(advPriceService.save(advPrice)>0){
			return R.ok();
		}
		return R.error();
		
		
	}
	
}
