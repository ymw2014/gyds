package com.fly.team.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.team.dao.TeamTypeDao;
import com.fly.team.domain.TypeDO;
import com.fly.team.service.TeamTypeService;


@Service
public class TeamTypeServiceImpl implements TeamTypeService {
	@Autowired
	private TeamTypeDao typeDao;
	
	@Override
	public TypeDO get(Integer id){
		return typeDao.get(id);
	}
	
	@Override
	public List<TypeDO> list(Map<String, Object> map){
		return typeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return typeDao.count(map);
	}
	
	@Override
	public int save(TypeDO type){
		return typeDao.save(type);
	}
	
	@Override
	public int update(TypeDO type){
		return typeDao.update(type);
	}
	
	@Override
	public int remove(Integer id){
		return typeDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return typeDao.batchRemove(ids);
	}
	
}
