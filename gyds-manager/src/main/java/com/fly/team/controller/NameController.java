package com.fly.team.controller;

import java.util.ArrayList;
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

import com.fly.domain.RegionDO;
import com.fly.system.service.RegionService;
import com.fly.team.domain.TeamDO;
import com.fly.team.domain.TypeDO;
import com.fly.team.service.TeamService;
import com.fly.team.service.TeamTypeService;
import com.fly.utils.BeanUtil;
import com.fly.utils.Dictionary;
import com.fly.utils.JSONUtils;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;
import com.fly.verifyName.domain.NameDO;
import com.fly.verifyName.service.NameService;

/**
 * 实名认证申请表/入团/建团/代理商入驻
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-07 14:06:00
 */
 
@Controller
@RequestMapping("/verifyName/name")
public class NameController {
	@Autowired
	private NameService nameService;
	@Autowired
	private TeamTypeService teamTypeService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private RegionService regionService;
	
	@GetMapping()
	@RequiresPermissions("verifyName:name:name")
	String Name(){
	    return "team/verifyName/name";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("verifyName:name:name")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		params.put("type", Dictionary.JIAN_TUAN_SHEN_QING);
        Query query = new Query(params);
		List<NameDO> nameList = nameService.list(query);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for (NameDO nameDO : nameList) {
			Map<String, Object> teamMap = JSONUtils.jsonToMap(nameDO.getText());
			Map<String, Object> map = BeanUtil.transBean2Map(nameDO);
			map.putAll(teamMap);
			TypeDO type = teamTypeService.get(Integer.parseInt(teamMap.get("teamType").toString()));
			map.put("teamType", type.getTypeName());
			list.add(map);
		}
		int total = nameService.count(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("verifyName:name:add")
	String add(){
	    return "team/verifyName/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("verifyName:name:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		NameDO name = nameService.get(id);
		model.addAttribute("name", name);
	    return "team/verifyName/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("verifyName:name:add")
	public R save( NameDO name){
		if(nameService.save(name)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("verifyName:name:edit")
	public R update( NameDO name){
		nameService.update(name);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("verifyName:name:remove")
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
	@RequiresPermissions("verifyName:name:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		nameService.batchRemove(ids);
		return R.ok();
	}
	
	/**
	 *	建团信息审核
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/examine")
	@RequiresPermissions("member:member:shenhe")
	@Transactional
	public R examine(Integer id,Integer status) {
		NameDO name = nameService.get(id);
		name.setStatus(status);
		if(status==1) {//审核通过,保存团队信息
			name.getText();
			TeamDO team=(TeamDO)JSONUtils.jsonToBean(name.getText(), new TeamDO());
			team.setId(randomCode(team.getRegCode()));
			teamService.save(team);
			RegionDO region=new RegionDO();
			region.setRegionCode(team.getId());
			region.setParentRegionCode(team.getRegCode());
			region.setRegionName(team.getTeamName());
			region.setRegionType(2);
			region.setRegionLevel(5);
			regionService.save(region);
		}
		if(nameService.update(name)>0){
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 生成团队编号
	 * @param regCode
	 * @return
	 */
	public Integer randomCode(Integer regCode) {
		Integer random =(int) (Math.random()*(999-100+1)+100);
		String strCode = regCode.toString().substring(2, regCode.toString().length());
		strCode = strCode+random;
		Integer code = Integer.valueOf(strCode);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",code);
		List<TeamDO> list = teamService.list(map);
		if(list.size()>0) {
			randomCode(regCode);
		}
		return code;
	}
	
}
