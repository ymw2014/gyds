package com.fly.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
			tree.setText(sysRegion.getRegionName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", false);
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
			/*
			 * RegionDO regionDO = regionDao.get(ShiroUtils.getUser().getDeptId());
			 * RegionDOs.add(regionDO);
			 */
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
	public List<Integer> getAllCode(Map<String,Object> params) {
		List<RegionDO> RegionDOs =new ArrayList<>();
		String pids = String.valueOf(params.get("pids"));
		if(Integer.valueOf(pids) == 0) {
			RegionDOs = regionDao.list(null);
		}else {
			params.put("pids", params.get("pids"));
			RegionDOs = regionDao.regionIdByList(params);
			/*
			 * RegionDO regionDO = regionDao.get(ShiroUtils.getUser().getDeptId());
			 * RegionDOs.add(regionDO);
			 */
		}
		List<Integer> list=new ArrayList<>();
		for (RegionDO sysRegion : RegionDOs) {
			if(sysRegion.getRegionType()==1) {
				list.add(sysRegion.getRegionCode());
			}
		}
		return list;
	}
	
}
