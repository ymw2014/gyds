package com.fly.helpCenter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.helpCenter.dao.CenterDao;
import com.fly.helpCenter.domain.CenterDO;
import com.fly.helpCenter.service.CenterService;



@Service
public class CenterServiceImpl implements CenterService {
	@Autowired
	private CenterDao centerDao;
	
	@Override
	public CenterDO get(Integer id){
		return centerDao.get(id);
	}
	
	@Override
	public List<CenterDO> list(Map<String, Object> map){
		return centerDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return centerDao.count(map);
	}
	
	@Override
	public int save(CenterDO center){
		return centerDao.save(center);
	}
	
	@Override
	public int update(CenterDO center){
		return centerDao.update(center);
	}
	
	@Override
	public int remove(Integer id){
		return centerDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return centerDao.batchRemove(ids);
	}
	
}
