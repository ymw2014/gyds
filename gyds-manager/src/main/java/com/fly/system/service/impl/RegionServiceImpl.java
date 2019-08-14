package com.fly.system.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.domain.RegionDO;
import com.fly.domain.Tree;
import com.fly.system.dao.RegionDao;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.BuildTree;



@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionDao regionDao;
	
	@Override
	public RegionDO get(Integer regionCode){
		return regionDao.get(regionCode);
	}
	
	@Override
	public List<RegionDO> list(Map<String, Object> map){
		return regionDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return regionDao.count(map);
	}
	
	@Override
	public int save(RegionDO region){
		return regionDao.save(region);
	}
	
	@Override
	public int update(RegionDO region){
		return regionDao.update(region);
	}
	
	@Override
	public int remove(Integer regionCode){
		return regionDao.remove(regionCode);
	}
	
	@Override
	public int batchRemove(Integer[] regionCodes){
		return regionDao.batchRemove(regionCodes);
	}
	
	@Override
	public Tree<RegionDO> getTree(Map<String,Object> params) {
		List<Tree <RegionDO>> trees = new ArrayList<Tree <RegionDO>>();
		List<RegionDO> RegionDOs = regionDao.list(params);
		for (RegionDO sysRegion : RegionDOs) {
			Tree<RegionDO> tree = new Tree <RegionDO>();
			tree.setId(sysRegion.getRegionCode().toString());
			tree.setParentId(sysRegion.getParentRegionCode().toString());
			tree.setChildren(true);
			tree.setText(sysRegion.getRegionName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", false);
			state.put("children", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree <RegionDO> t = BuildTree.build(trees,-1);
		return t;
	}
	
	@Override
	public Tree<RegionDO> getRegionTree(Map<String,Object> params) {
		List<Tree <RegionDO>> trees = new ArrayList<Tree <RegionDO>>();
		List<RegionDO> RegionDOs = regionDao.regionIdByList(params);
		RegionDO regionDO = regionDao.get(Integer.parseInt(params.get("pids").toString()));
		RegionDOs.add(regionDO);
		for (RegionDO sysRegion : RegionDOs) {
			Tree<RegionDO> tree = new Tree <RegionDO>();
			tree.setId(sysRegion.getRegionCode().toString());
			tree.setParentId(sysRegion.getParentRegionCode().toString());
			tree.setText(sysRegion.getRegionName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", false);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree <RegionDO> t = BuildTree.build(trees,regionDO.getParentRegionCode());
		return t;
	}

	@Override
	public boolean checkRegionHasUser(Integer regionCode) {
		int result = regionDao.getRegionUserNumber(regionCode);
		return result==0?true:false;
	}

	@Override
	public List<Integer> getAllTeamByUserRole(Map<String,Object> params) {
		List<RegionDO> RegionDOs =new ArrayList<>();
		String pids = String.valueOf(params.get("pids"));
		if(Integer.valueOf(pids) == 0) {
			RegionDOs = regionDao.list(null);
		}else {
			params.put("pids", params.get("pids"));
			RegionDOs = regionDao.regionIdByList(params);
			
			  /*RegionDO regionDO = regionDao.get(ShiroUtils.getUser().getDeptId());
			  RegionDOs.add(regionDO);*/
			 
		}
		List<Integer> list=new ArrayList<>();
		for (RegionDO sysRegion : RegionDOs) {
			if(sysRegion.getRegionType()==2) {
				list.add(sysRegion.getRegionCode());
			}
		}
		return list;
	}
	
	@Override
	public List<Integer> getTeamAndAreaByUserRole(Map<String,Object> params) {
		List<RegionDO> RegionDOs =new ArrayList<>();
		if(params.get("pids").toString().equals("0")) {
			RegionDOs = regionDao.list(new HashMap<String,Object>(16));
		}else {
			params.put("pids", ShiroUtils.getUser().getDeptId());
			RegionDOs = regionDao.regionIdByList(params);
			RegionDO regionDO = regionDao.get(ShiroUtils.getUser().getDeptId());
			RegionDOs.add(regionDO);
		}
		List<Integer> list=new ArrayList<>();
		for (RegionDO sysRegion : RegionDOs) {
			list.add(sysRegion.getRegionCode());
		}
		return list;
	}

	@Override
	public Map<String, Object> activeStat(Integer region, Integer level, Integer day) {
		Map<String,Object> param = new HashMap<String, Object>();
		int teamCount = 0;//团队
		int roadCount = 0;//街道
		int distCount = 0;//区县
		int cityCount = 0;//市
		int provCount = 0;//省
		Integer parentRegionCode = null;
		switch (level) {
			case 5:
				teamCount = activeCount(5, region, day);
				parentRegionCode = getParentCode(region);
			case 4:
				roadCount = activeCount(null, chooseCode(parentRegionCode, region), day);
				parentRegionCode = getParentCode(region);
			case 3:
				distCount = activeCount(null, chooseCode(parentRegionCode, region), day);
				parentRegionCode = getParentCode(region);
			case 2:
				cityCount = activeCount(null, chooseCode(parentRegionCode, region), day);
				parentRegionCode = getParentCode(region);
			case 1:
				provCount = activeCount(null, chooseCode(parentRegionCode, region), day);
			case 0:
				int allCount = activeCount(null, 0, day);
				param.clear();
				param.put("allCount", allCount);
				param.put("provCount", provCount);
				param.put("cityCount", cityCount);
				param.put("distCount", distCount);
				param.put("roadCount", roadCount);
				param.put("teamCount", teamCount);
				break;
		}
		return param;
	}
	
	public int activeCount(Integer level, Integer region, Integer day) {
		Map<String,Object> param = new HashMap<String, Object>();
		if (level==5) {
			param.put("regionLevel", level);
			param.put("regionCode", region);
		} else {
			if (region!=0) {
				param.put("parentRegionCode", region);
			}
		}
		param.put("regionType", 2);//团队
		LocalDate now = LocalDate.now();
		LocalDate dayAgo = LocalDate.now().minusDays(Long.valueOf(day));
		List<Integer>  teamId = regionDao.list(param).stream().map(bean -> bean.getRegionCode()).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(teamId)) {
			return 0;
		}
		int allCount = regionDao.activeCount(teamId, dayAgo.toString(), now.toString());
		return allCount;
	}
	
	public Integer  getParentCode(Integer region) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("regionCode", region);
		Integer parentRegionCode = regionDao.list(param).get(0).getParentRegionCode();
		return parentRegionCode;
	}
	
	
	public Integer chooseCode(Integer parentRegionCode, Integer region) {
		return parentRegionCode == null ? region : parentRegionCode;
	}
	
}
