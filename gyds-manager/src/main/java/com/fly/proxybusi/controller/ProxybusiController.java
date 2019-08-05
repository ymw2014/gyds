package com.fly.proxybusi.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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

import com.fly.domain.UserDO;
import com.fly.index.utils.OrderType;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.pc.news.controller.BaseDynamicController;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.proxybusi.service.ProxybusiService;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-10 13:57:24
 */
 
@Controller
@RequestMapping("/proxybusi/proxybusi")
public class ProxybusiController extends BaseDynamicController{
	@Autowired
	private ProxybusiService proxybusiService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
	@GetMapping()
	@RequiresPermissions("proxybusi:proxybusi:proxybusi")
	String Proxybusi(){
	    return "proxybusi/proxybusi/proxybusi";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("proxybusi:proxybusi:proxybusi")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ProxybusiDO> proxybusiList = proxybusiService.list(query);
		int total = proxybusiService.count(query);
		PageUtils pageUtils = new PageUtils(proxybusiList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("proxybusi:proxybusi:add")
	String add(){
	    return "proxybusi/proxybusi/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("proxybusi:proxybusi:edit")
	String edit(@PathVariable("id") Long id,Model model){
		ProxybusiDO proxybusi = proxybusiService.get(id);
		model.addAttribute("proxybusi", proxybusi);
	    return "proxybusi/proxybusi/edit";
	}
	
	@GetMapping("/audit/{id}")
	@RequiresPermissions("proxybusi:proxybusi:audit")
	String audit(@PathVariable("id") Long id,Model model){
		ProxybusiDO proxybusi = proxybusiService.get(id);
		model.addAttribute("proxybusi", proxybusi);
	    return "proxybusi/proxybusi/audit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("proxybusi:proxybusi:add")
	public R save( ProxybusiDO proxybusi){
		if(proxybusiService.save(proxybusi)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@Transactional
	@RequestMapping("/update")
	@RequiresPermissions("proxybusi:proxybusi:edit")
	public synchronized R update( ProxybusiDO proxybusi){
		Long userId = proxybusi.getUserId();
		Integer auditStatus = proxybusi.getAuditStatus();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("orderType", 2);
		params.put("expIncType", OrderType.DAI_LI_SHANG);
		params.put("examineStatus", 2);
		params.put("cashUpType", 2);
		params.put("cashOutType", 2);
		List<OrderDO> order = orderService.list(params);
		if (auditStatus != null) {
			proxybusi.setAuditTime(new Date(System.currentTimeMillis()));
			if (auditStatus == 2) {//拒绝，
				if (!CollectionUtils.isEmpty(order)) {
					//入团保证金退还账户
					BigDecimal price = order.get(0).getPrice();
					UserDO userDO = userService.get(userId);
					userDO.setAccount(userDO.getAccount().add(price));
					userService.update(userDO);
					//新增拒绝订单
					params.clear();
					params.put("orderType", 1);
					params.put("expIncType", OrderType.DAI_LI_SHANG);
					params.put("examineStatus", 3);
					params.put("cashUpType", 2);
					params.put("cashOutType", 2);
					params.put("examineUser", ShiroUtils.getUserId());
					params.put("price", price);
					params.put("userId", userId);
					creadOrder(params);
				}
			}
			if (auditStatus == 1) {//审核通过，更新订单状态
				if (!CollectionUtils.isEmpty(order)) {
					 OrderDO orderDO = order.get(0);
					 orderDO.setExamineStatus(1);
					 orderDO.setExamineUser(ShiroUtils.getUserId().intValue());
					 orderService.update(orderDO);
				}
			}
		}
		proxybusiService.update(proxybusi);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("proxybusi:proxybusi:remove")
	public R remove( Long id){
		if(proxybusiService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("proxybusi:proxybusi:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		proxybusiService.batchRemove(ids);
		return R.ok();
	}
	
}
