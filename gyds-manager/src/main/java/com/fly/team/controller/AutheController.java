package com.fly.team.controller;

import java.util.Calendar;
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

import com.fly.team.domain.AutheDO;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.AutheService;
import com.fly.team.service.TeamService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 团队认证表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-28 15:21:01
 */
 
@Controller
@RequestMapping("/team/authe")
public class AutheController {
	@Autowired
	private AutheService autheService;
	@Autowired
	private TeamService teamService;
	
	@GetMapping()
	@RequiresPermissions("team:authe:authe")
	String Authe(){
	    return "team/authe/authe";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("team:authe:authe")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AutheDO> autheList = autheService.list(query);
		int total = autheService.count(query);
		PageUtils pageUtils = new PageUtils(autheList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("team:authe:add")
	String add(){
	    return "team/authe/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("team:authe:edit")
	String edit(@PathVariable("id") Long id,Model model){
		AutheDO authe = autheService.get(id);
		model.addAttribute("authe", authe);
	    return "team/authe/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("team:authe:add")
	public R save( AutheDO authe){
		if(autheService.save(authe)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("team:authe:edit")
	public R update( AutheDO authe){
		AutheDO autheDO = autheService.get(authe.getId());
		TeamDO team = teamService.get(autheDO.getTeamId());
		team.setIsAuth(2);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 366);
		team.setAuthEntTime(c.getTime());
		teamService.update(team);
		autheService.update(authe);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("team:authe:remove")
	public R remove( Long id){
		if(autheService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("team:authe:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		autheService.batchRemove(ids);
		return R.ok();
	}
	
}
