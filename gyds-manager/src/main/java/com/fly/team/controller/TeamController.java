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

import com.fly.domain.RegionDO;
import com.fly.system.service.RegionService;
import com.fly.team.dao.TeamTypeDao;
import com.fly.team.domain.TeamDO;
import com.fly.team.domain.TypeDO;
import com.fly.team.service.TeamService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;


/**
 * 团队表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-04 10:09:06
 */
 
@Controller
@RequestMapping("/team/team")
public class TeamController {
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private TeamTypeDao typeDao;
	
	@GetMapping()
	@RequiresPermissions("team:team:team")
	String Team(){
	    return "team/team/team";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("team:team:team")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TeamDO> teamList = teamService.list(query);
		int total = teamService.count(query);
		PageUtils pageUtils = new PageUtils(teamList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("team:team:add")
	String add(){
	    return "team/team/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("team:team:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		TeamDO team = teamService.get(id);
		String imgStr = team.getTeamImg();
		String[] img = imgStr.split(",");
		List<String> imgList = new ArrayList<String>();
		for(int i =0;i<img.length;i++) {
			imgList.add(img[i]);
		}
		
		List<TypeDO> type = typeDao.list1();
		model.addAttribute("type", type);
		model.addAttribute("team", team);
		model.addAttribute("imgList",imgList);
	    return "team/team/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("team:team:add")
	public R save( TeamDO team){
		if(teamService.save(team)>0){
			RegionDO region=new RegionDO();
			region.setRegionCode(team.getTeamCode());
			region.setParentRegionCode(team.getRegCode());
			region.setRegionName(team.getTeamName());
			region.setRegionType(2);
			region.setRegionLevel(4);
			if(regionService.save(region)>0) {
				return R.ok();
			}
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("team:team:edit")
	public R update( TeamDO team){
		teamService.update(team);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("team:team:remove")
	public R remove( Integer id){
		TeamDO team=teamService.get(id);
		if(regionService.checkRegionHasUser(team.getTeamCode())) {
			if(regionService.remove(team.getTeamCode())>0) {
				if(teamService.remove(id)>0){
					return R.ok();
				}
			}
		}else {
			return R.error(1, "请先移除团队管理员");
		}
		
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("team:team:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		teamService.batchRemove(ids);
		return R.ok();
	}
	
}
