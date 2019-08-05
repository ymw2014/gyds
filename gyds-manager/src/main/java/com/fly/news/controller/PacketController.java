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

import com.fly.news.domain.PacketDO;
import com.fly.news.service.PacketService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 红包主表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-05 11:56:18
 */
 
@Controller
@RequestMapping("/news/packet")
public class PacketController {
	@Autowired
	private PacketService packetService;
	
	@GetMapping()
	@RequiresPermissions("news:packet:packet")
	String Packet(){
	    return "news/packet/packet";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("news:packet:packet")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PacketDO> packetList = packetService.list(query);
		int total = packetService.count(query);
		PageUtils pageUtils = new PageUtils(packetList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("news:packet:add")
	String add(){
	    return "news/packet/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("news:packet:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		PacketDO packet = packetService.get(id);
		model.addAttribute("packet", packet);
	    return "news/packet/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("news:packet:add")
	public R save( PacketDO packet){
		if(packetService.save(packet)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("news:packet:edit")
	public R update( PacketDO packet){
		packetService.update(packet);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("news:packet:remove")
	public R remove( Integer id){
		if(packetService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("news:packet:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		packetService.batchRemove(ids);
		return R.ok();
	}
	
}
