package com.fly.proxybusi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.fly.proxybusi.dao.ProxybusiDao;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.proxybusi.service.ProxybusiService;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.service.TeamNameService;
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
@RequestMapping("/proxybusi/apply")
public class ProxyApplyController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private NameService nameService;
	@Autowired
	private ProxybusiService proxybusiService;
	@Autowired
	private TeamNameService teamNameService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private ProxybusiDao proxybusiDao;

	
	@GetMapping()
	@RequiresPermissions("proxybusi:apply:apply")
	String Name(){
	    return "proxybusi/apply/name";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("proxybusi:apply:apply")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Long userId = ShiroUtils.getUserId();
		String reg = "\"proxyRegion\":";
		Long areaId = 0l; 
		if(userId!=null&&userId!=1) {
			ProxybusiDO proxybusi = proxybusiDao.getByUserId(userId);
			areaId = proxybusi.getProxyRegion();
		}
		  String ids = regionService.getTeamAndAreaByUserRole(areaId);
		params.put("type", Dictionary.DAI_LI_SHANG_SHEN_QING);
        Query query = new Query(params);
        if(ids!=null) {
        	query.put("ids", reg+ids);
        }
		List<NameDO> nameList = nameService.list(query);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for (NameDO nameDO : nameList) {
			Map<String, Object> proxyMap = JSONUtils.jsonToMap(nameDO.getText());
			proxyMap.put("id", nameDO.getId());
			Map<String, Object> map = BeanUtil.transBean2Map(nameDO);//
			
			if(proxyMap.get("regionLevel")!=null&&proxyMap.get("regionLevel").toString().equals("1")) {
				String pronvice=regionService.get(Long.parseLong(proxyMap.get("proxyRegion").toString())).getRegionName();
				map.put("daili", pronvice);
			}
			if(proxyMap.get("regionLevel")!=null&&proxyMap.get("regionLevel").toString().equals("2")) {
				RegionDO cityRetion = regionService.get(Long.parseLong(proxyMap.get("proxyRegion").toString()));
				RegionDO proRetion = regionService.get(cityRetion.getParentRegionCode());
				map.put("daili", proRetion.getRegionName()+"&nbsp;"+cityRetion.getRegionName());
			}
			if(proxyMap.get("regionLevel")!=null&&proxyMap.get("regionLevel").toString().equals("3")) {
				RegionDO areaRetion = regionService.get(Long.parseLong(proxyMap.get("proxyRegion").toString()));
				RegionDO cityRetion = regionService.get(areaRetion.getParentRegionCode());
				RegionDO proRetion = regionService.get(cityRetion.getParentRegionCode());
				map.put("daili", proRetion.getRegionName()+"&nbsp;"+cityRetion.getRegionName()+"&nbsp;"+areaRetion.getRegionName());
			}
			if(proxyMap.get("regionLevel")!=null&&proxyMap.get("regionLevel").toString().equals("4")) {
				RegionDO jdbRetion = regionService.get(Long.parseLong(proxyMap.get("proxyRegion").toString()));
				RegionDO areaRetion = regionService.get(jdbRetion.getParentRegionCode());
				RegionDO cityRetion = regionService.get(areaRetion.getParentRegionCode());
				RegionDO proRetion = regionService.get(cityRetion.getParentRegionCode());
				map.put("daili", proRetion.getRegionName()+"&nbsp;"+cityRetion.getRegionName()+"&nbsp;"+areaRetion.getRegionName()+"&nbsp;"+jdbRetion.getRegionName());
			}
			map.putAll(proxyMap);
			list.add(map);
		}
		int total = nameService.count(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("proxybusi:apply:add")
	String add(){
	    return "proxybusi/apply/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("proxybusi:apply:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		NameDO name = nameService.get(id);
		model.addAttribute("name", name);
	    return "proxybusi/apply/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("proxybusi:apply:add")
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
	@RequiresPermissions("proxybusi:apply:edit")
	public R update( NameDO name){
		nameService.update(name);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("proxybusi:apply:remove")
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
	@RequiresPermissions("proxybusi:apply:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		nameService.batchRemove(ids);
		return R.ok();
	}
	
	/**
	 *	代理商入驻审核
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/examine")
	@RequiresPermissions("proxybusi:apply:examine")
	public R examine(Integer id,Integer status) {
		
		
		Map<String, Object> paramas=new HashMap<>(16);
		NameDO name = nameService.get(id);
		paramas.put("userId", name.getUserId());
		List<ProxybusiDO> list = proxybusiService.list(paramas);
		if(list.size()>0) {
			return R.error("此用户已经是代理商");
		}
		paramas.clear();
		String text = name.getText().toString();
		Map<String, Object> jsonToMap = JSONUtils.jsonToMap(text);
		paramas.put("proxyRegion", jsonToMap.get("proxyRegion"));
		int count = proxybusiService.count(paramas);
		if(count > 0) {
			return R.error("该用户申请的代理区域已被代理，请重新申请");
		}
		if(teamNameService.createProxyBus(id, status)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	
}
