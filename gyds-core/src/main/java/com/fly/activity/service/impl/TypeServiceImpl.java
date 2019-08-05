package com.fly.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.activity.dao.TypeDao;
import com.fly.activity.domain.TypeDO;
import com.fly.activity.service.TypeService;



@Service
public class TypeServiceImpl implements TypeService {
	@Autowired
	private TypeDao typeDao;
	
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
