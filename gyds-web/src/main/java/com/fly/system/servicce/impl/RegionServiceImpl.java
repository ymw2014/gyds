package com.fly.system.servicce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fly.domain.RegionDO;
import com.fly.domain.Tree;
import com.fly.system.dao.RegionDao;
import com.fly.system.servicce.RegionService;
import com.fly.utils.BuildTree;



@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionDao regionDao;
	
	@Override
	public RegionDO get(Long regionCode){
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
	public int remove(Long regionCode){
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
		Tree <RegionDO> t = BuildTree.build(trees,-1L);
		return t;
	}
	
	@Override
	public Tree<RegionDO> getRegionTree(Map<String,Object> params) {
		List<Tree <RegionDO>> trees = new ArrayList<Tree <RegionDO>>();
		List<RegionDO> RegionDOs = regionDao.regionIdByList(params);
		RegionDO regionDO = regionDao.get(Long.parseLong(params.get("pids").toString()));
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

	
}
