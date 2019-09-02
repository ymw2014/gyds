package com.fly.verifyName.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.verifyName.dao.NameDao;
import com.fly.verifyName.domain.NameDO;
import com.fly.verifyName.service.NameService;



@Service
public class NameServiceImpl implements NameService {
	@Autowired
	private NameDao nameDao;
	
	@Override
	public NameDO get(Integer id){
		return nameDao.get(id);
	}
	
	@Override
	public List<NameDO> list(Map<String, Object> map){
		return nameDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return nameDao.count(map);
	}
	
	@Override
	public int save(NameDO name){
		return nameDao.save(name);
	}
	
	@Override
	public int update(NameDO name){
		return nameDao.update(name);
	}
	
	@Override
	public int remove(Integer id){
		return nameDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return nameDao.batchRemove(ids);
	}

	@Override
	public List<NameDO> list2(Map<String, Object> map) {
		return nameDao.list2(map);
	}
	
}
