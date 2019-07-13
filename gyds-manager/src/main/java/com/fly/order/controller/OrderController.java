package com.fly.order.controller;

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

import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 订单表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-12 14:37:27
 */
 
@Controller
@RequestMapping("/order/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	//充值
	@GetMapping("/CashUp")
	@RequiresPermissions("order:order:orderCashUp")
	String listCashUp(){
	    return "order/order/orderCashUp";
	}
	//提现
	@GetMapping("/CashOut")
	@RequiresPermissions("order:order:orderCashOut")
	String listCashOut(){
	    return "order/order/orderCashOut";
	}
	//打赏
	@GetMapping("/Reward")
	@RequiresPermissions("order:order:orderReward")
	String listReward(){
	    return "order/order/orderReward";
	}
	//红包
	@GetMapping("/RedPacket")
	@RequiresPermissions("order:order:orderRedPacket")
	String listRedPacket(){
	    return "order/order/orderRedPacket";
	}
	//广告
	@GetMapping("/Adver")
	@RequiresPermissions("order:order:orderAdver")
	String listAdver(){
	    return "order/order/orderAdver";
	}
	//广告记录
	@ResponseBody
	@GetMapping("/listAdver")
	@RequiresPermissions("order:order:orderAdver")
	public PageUtils listAdver(@RequestParam Map<String, Object> params){
		params.put("expIncType", 4);
		//查询列表数据
        Query query = new Query(params);
		List<OrderDO> orderList = orderService.list(query);
		int total = orderService.count(query);
		PageUtils pageUtils = new PageUtils(orderList, total);
		return pageUtils;
	}
	//提现
	@ResponseBody
	@GetMapping("/listCashOut")
	@RequiresPermissions("order:order:orderCashOut")
	public PageUtils listCashOut(@RequestParam Map<String, Object> params){
		params.put("expIncType", 0);
		//查询列表数据
        Query query = new Query(params);
		List<OrderDO> orderList = orderService.list(query);
		int total = orderService.count(query);
		PageUtils pageUtils = new PageUtils(orderList, total);
		return pageUtils;
	}
	//充值
	@ResponseBody
	@GetMapping("/listCashUp")
	@RequiresPermissions("order:order:orderCashUp")
	public PageUtils listCashUp(@RequestParam Map<String, Object> params){
		params.put("expIncType", 1);
        Query query = new Query(params);
		List<OrderDO> orderList = orderService.list(query);
		int total = orderService.count(query);
		PageUtils pageUtils = new PageUtils(orderList, total);
		return pageUtils;
	}
	//红包
	@ResponseBody
	@GetMapping("/listRedPacket")
	@RequiresPermissions("order:order:orderRedPacket")
	public PageUtils listRedPacket(@RequestParam Map<String, Object> params){
		params.put("expIncType", 3);
		//查询列表数据
        Query query = new Query(params);
		List<OrderDO> orderList = orderService.list(query);
		int total = orderService.count(query);
		PageUtils pageUtils = new PageUtils(orderList, total);
		return pageUtils;
	}
	//打赏
	@ResponseBody
	@GetMapping("/listReward")
	@RequiresPermissions("order:order:orderReward")
	public PageUtils listReward(@RequestParam Map<String, Object> params){
		params.put("expIncType", 2);
		//查询列表数据
        Query query = new Query(params);
		List<OrderDO> orderList = orderService.list(query);
		int total = orderService.count(query);
		PageUtils pageUtils = new PageUtils(orderList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("order:order:add")
	String add(){
	    return "order/order/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("order:order:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		OrderDO order = orderService.get(id);
		model.addAttribute("order", order);
	    return "order/order/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("order:order:add")
	public R save( OrderDO order){
		if(orderService.save(order)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("order:order:audit")
	public R update( OrderDO order){
		orderService.update(order);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("order:order:remove")
	public R remove( Integer id){
		if(orderService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("order:order:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		orderService.batchRemove(ids);
		return R.ok();
	}
	
}
