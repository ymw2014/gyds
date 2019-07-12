package com.fly.adv.controller;

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

import com.fly.adv.domain.AdvertisementDO;
import com.fly.adv.service.AdvertisementService;
import com.fly.common.controller.BaseController;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.system.service.RegionService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 广告
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-12 11:20:07
 */
 
@Controller
@RequestMapping("/adv/advertisement")
public class AdvertisementController extends BaseController{
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private RegionService regionService;
	@GetMapping()
	@RequiresPermissions("adv:advertisement:advertisement")
	String Advertisement(){
	    return "adv/advertisement/advertisement";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("adv:advertisement:advertisement")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AdvertisementDO> advertisementList = advertisementService.list(query);
		int total = advertisementService.count(query);
		PageUtils pageUtils = new PageUtils(advertisementList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("adv:advertisement:add")
	String add(Model model){
		UserDO user = getUser();
		regionService.get(user.getDeptId());
		model.addAttribute("user", user);
		model.addAttribute("level", regionService.get(user.getDeptId()).getRegionLevel());
	    return "adv/advertisement/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("adv:advertisement:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		AdvertisementDO advertisement = advertisementService.get(id);
		model.addAttribute("advertisement", advertisement);
	    return "adv/advertisement/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("adv:advertisement:add")
	public R save( AdvertisementDO advertisement){
		advertisement.setCreateTime(new Date());
		RegionDO region = regionService.get(getUser().getDeptId());
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("positionNum", advertisement.getPositionNum());
		params.put("regionCode", advertisement.getRegionCode());
		List<AdvertisementDO> dataList = advertisementService.list(params);
		R r=new R();
		switch(region.getRegionLevel()){ 
		    case 0:  //超管
		    	r=validateSwitch(dataList,advertisement,advertisement.getPositionNum(),5);
		    	break;
		    case 1:  //省管理员
		    	r=validateSwitch(dataList,advertisement,advertisement.getPositionNum(),4);
		    	break;
		    case 2:  //市管理员
		    	r=validateSwitch(dataList,advertisement,advertisement.getPositionNum(),3);
		    	break;
		    case 3:  //县管理员
		    	r=validateSwitch(dataList,advertisement,advertisement.getPositionNum(),2);
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
	@RequiresPermissions("adv:advertisement:edit")
	public R update( AdvertisementDO advertisement){
		advertisementService.update(advertisement);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("adv:advertisement:remove")
	public R remove( Integer id){
		if(advertisementService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("adv:advertisement:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		advertisementService.batchRemove(ids);
		return R.ok();
	}
	
	public R validateSwitch(List<AdvertisementDO> dataList, AdvertisementDO advertisement, Integer positionNum,Integer size) {
		
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
		if(advertisementService.save(advertisement)>0){
			return R.ok();
		}
		return R.error();
		
		
	}
	
}
