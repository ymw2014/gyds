package com.fly.team.controller;

import java.util.ArrayList;
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

import com.fly.system.service.UserService;
import com.fly.team.service.TeamNameService;
import com.fly.utils.BeanUtil;
import com.fly.utils.Dictionary;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;
import com.fly.verifyName.domain.NameDO;
import com.fly.verifyName.service.NameService;

/**
 * 入团申请表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-11 15:31:51
 */
 
@Controller
@RequestMapping("/team/apply")
public class ApplyTeamController {
	@Autowired
	private NameService nameService;
	@Autowired
	private TeamNameService  teamNameService;
	@Autowired
	private UserService userService;
	
	@GetMapping()
	@RequiresPermissions("team:apply:apply")
	String Apply(){
	    return "team/apply/apply";
	}
	
	
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		params.put("type", Dictionary.RU_TUAN_SHEN_QING);
        Query query = new Query(params);
        List<NameDO> applyList = nameService.list(query);
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for (NameDO nameDO : applyList) {
        	Map<String, Object> map = BeanUtil.transBean2Map(nameDO);
        	map.put("headImg", userService.get(nameDO.getUserId()).getHeadImg());
        	list.add(map);
		}
		int total = nameService.count(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}
	

	@GetMapping("/edit/{id}")
	@RequiresPermissions("team:apply:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		NameDO apply = nameService.get(id);
		model.addAttribute("apply", apply);
	    return "team/apply/edit";
	}
	@GetMapping("/applyMember/{id}")
	@RequiresPermissions("team:apply:applyMember")
	String applyMember(@PathVariable("id") Long id,Model model){
		model.addAttribute("teamId", id);
	    return "team/apply/name";
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("team:apply:edit")
	public R update( NameDO name){
		nameService.update(name);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("team:apply:remove")
	public R remove( Integer id){
		if(nameService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("team:apply:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		nameService.batchRemove(ids);
		return R.ok();
	}
	/***
	 * 	入团审核
	 * @param id
	 * @param status
	 * @return
	 */
	@PostMapping( "/examine")
	@ResponseBody
	@RequiresPermissions("team:apply:examine")
	public R examine(Integer id,Integer status){
		if(teamNameService.userIntoTeamExamine(id,status)>0) {
			return R.ok();
		}
		return R.error();
	}
	
}
