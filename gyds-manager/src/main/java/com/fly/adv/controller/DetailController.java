package com.fly.adv.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.adv.domain.AdvertisementDO;
import com.fly.adv.domain.DetailDO;
import com.fly.adv.service.AdvDetailService;
import com.fly.adv.service.AdvertisementService;
import com.fly.adv.service.DetailService;
import com.fly.base.BaseService;
import com.fly.domain.RegionDO;
import com.fly.index.utils.OrderType;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
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
	@Autowired
	private RegionService regionService;
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private OrderService orderService;
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
		params.put("regionCode", ShiroUtils.getUser().getDeptId());
        Query query = new Query(params);
		List<DetailDO> detailList = detailService.list(query);
		for (DetailDO detailDO : detailList) {
			detailDO.setRegionName(regionService.get(detailDO.getRegionCode()).getRegionName());
		}
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
	@Transactional
	@ResponseBody
	@PostMapping( "/examine")
	public R examine(Integer id,Integer status) {
		DetailDO detail = detailService.get(id);
		OrderDO order = orderService.get(detail.getOrderId());
		Map<String, Object> params=new HashMap<String, Object>(16);
		params.put("regionCode", detail.getRegionCode());
		params.put("positionNum", detail.getPositionNum());
	/*	List<AdvertisementDO> list = advertisementService.list(params);
		if(list.size()>0) {
			return R.error("改广告位已被购买,审核失败");

		}*/
		
		RegionDO region = regionService.get(detail.getRegionCode());
		switch (region.getRegionLevel()) {
		case 0:
			if(detail.getPositionNum()==1) {
				
			}
			break;
		case 1:
					
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		case 5:
			
			break;

		default:
			break;
		}
		detail.setStatus(status);
		if(status==1) {//审核通过
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, detail.getShowDay());
			AdvertisementDO advertisement=new AdvertisementDO();
			advertisement.setAdvImg(detail.getAdvImg());
			advertisement.setCreateTime(new Date());
			advertisement.setPositionNum(detail.getPositionNum());
			advertisement.setRegionCode(detail.getRegionCode());
			advertisement.setShowStartTime(new Date());
			advertisement.setShowEndTime(c.getTime());
			advertisement.setUrl(detail.getAdvUrl());
			//baseService.distributionOfDomesticTop(detail.getPrice(),  OrderType.GUANG_GAO_FAN_YONG, null, detail.getRegionCode());
			if(advertisementService.save(advertisement)>0) {
				order.setExamineStatus(2);
				order.setExamineUser(ShiroUtils.getUserId());
				orderService.update(order);
				detailService.update(detail);
				return R.ok();
			}
		}
		
		if(status==2) {//审核拒绝
			boolean flag = baseService.increaseMoney(detail.getUserId(), order.getPrice());
			if(flag) {
				order.setExamineStatus(3);
				order.setExamineUser(ShiroUtils.getUserId());
				orderService.update(order);
				detailService.update(detail);
				return R.ok();
			}
		}
		
		
		return R.error();

	}
	
}
