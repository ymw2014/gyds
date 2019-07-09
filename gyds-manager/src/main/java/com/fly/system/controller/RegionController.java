package com.fly.system.controller;

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

import com.fly.common.controller.BaseController;
import com.fly.domain.RegionDO;
import com.fly.domain.Tree;
import com.fly.domain.UserDO;
import com.fly.system.service.RegionService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;


/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-03 15:01:06
 */
 
@Controller
@RequestMapping("/system/region")
public class RegionController extends BaseController {
	@Autowired
	private RegionService regionService;
	
	@GetMapping()
	@RequiresPermissions("system:region:region")
	String Region(){
	    return "system/region/region";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:region:region")
	public List<RegionDO> list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Map<String, Object> query = new HashMap<>(16);
		List<RegionDO> regionList = regionService.list(query);
		return regionList;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:region:add")
	String add(){
	    return "system/region/add";
	}

	@GetMapping("/edit/{regionCode}")
	@RequiresPermissions("system:region:edit")
	String edit(@PathVariable("regionCode") Integer regionCode,Model model){
		RegionDO region = regionService.get(regionCode);
		model.addAttribute("region", region);
	    return "system/region/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:region:add")
	public R save( RegionDO region){
		if(regionService.save(region)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:region:edit")
	public R update( RegionDO region){
		regionService.update(region);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:region:remove")
	public R remove( Integer regionCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentRegionCode", regionCode);
		if(regionService.count(map)>0) {
			return R.error(1, "包含下级,不允许删除");
		}
		if(regionService.checkRegionHasUser(regionCode)) {
			if(regionService.remove(regionCode)>0){
				return R.ok();
			}
		}else {
			return R.error(1, "包含用户,不允许修改");
		}
		
		return R.error();
		
		
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:region:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] regionCodes){
		regionService.batchRemove(regionCodes);
		return R.ok();
	}
	
	@GetMapping("/tree")
	@ResponseBody
	public Tree<RegionDO> tree() {
		UserDO user = getUser();
		Tree<RegionDO> tree = new Tree <RegionDO>();
		Map<String,Object> params=new HashMap<>(16);
		if(user.getDeptId()==0) {//超管
			tree = regionService.getTree(params);
		}else {
			params.put("pids", user.getDeptId());
			tree=regionService.getRegionTree(params);
		}
		System.out.println(regionService.getAllTeamByUserRole());
		return tree;
	}
	
	@GetMapping("/treeView")
	String treeView() {
		return  "system/region/regionTree";
	}
	
}
