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

import com.fly.news.domain.RewardInfoDO;
import com.fly.news.service.RewardInfoService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-02 14:39:24
 */
 
@Controller
@RequestMapping("/news/rewardInfo")
public class RewardInfoController {
	@Autowired
	private RewardInfoService rewardInfoService;
	
	@GetMapping()
	@RequiresPermissions("news:rewardInfo:rewardInfo")
	String RewardInfo(){
	    return "news/rewardInfo/rewardInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("news:rewardInfo:rewardInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<RewardInfoDO> rewardInfoList = rewardInfoService.list(query);
		int total = rewardInfoService.count(query);
		PageUtils pageUtils = new PageUtils(rewardInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("news:rewardInfo:add")
	String add(){
	    return "news/rewardInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("news:rewardInfo:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		RewardInfoDO rewardInfo = rewardInfoService.get(id);
		model.addAttribute("rewardInfo", rewardInfo);
	    return "news/rewardInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("news:rewardInfo:add")
	public R save( RewardInfoDO rewardInfo){
		if(rewardInfoService.save(rewardInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("news:rewardInfo:edit")
	public R update( RewardInfoDO rewardInfo){
		rewardInfoService.update(rewardInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("news:rewardInfo:remove")
	public R remove( Integer id){
		if(rewardInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("news:rewardInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		rewardInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
