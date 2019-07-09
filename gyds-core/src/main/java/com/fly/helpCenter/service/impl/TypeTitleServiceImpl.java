package com.fly.helpCenter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.helpCenter.dao.TypeTitleDao;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.helpCenter.service.TypeTitleService;



@Service
public class TypeTitleServiceImpl implements TypeTitleService {
	@Autowired
	private TypeTitleDao typeTitleDao;
	
	@Override
	public TypeTitleDO get(Integer id){
		return typeTitleDao.get(id);
	}
	
	@Override
	public List<TypeTitleDO> list(Map<String, Object> map){
		return typeTitleDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return typeTitleDao.count(map);
	}
	
	@Override
	public int save(TypeTitleDO typeTitle){
		return typeTitleDao.save(typeTitle);
	}
	
	@Override
	public int update(TypeTitleDO typeTitle){
		return typeTitleDao.update(typeTitle);
	}
	
	@Override
	public int remove(Integer id){
		return typeTitleDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return typeTitleDao.batchRemove(ids);
	}
	
}
