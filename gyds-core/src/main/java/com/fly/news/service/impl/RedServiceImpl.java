package com.fly.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.news.dao.RedDao;
import com.fly.news.domain.RedDO;
import com.fly.news.service.RedService;



@Service
public class RedServiceImpl implements RedService {
	@Autowired
	private RedDao redDao;
	
	@Override
	public RedDO get(Integer id){
		return redDao.get(id);
	}
	
	@Override
	public List<RedDO> list(Map<String, Object> map){
		return redDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return redDao.count(map);
	}
	
	@Override
	public int save(RedDO red){
		return redDao.save(red);
	}
	
	@Override
	public int update(RedDO red){
		return redDao.update(red);
	}
	
	@Override
	public int remove(Integer id){
		return redDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return redDao.batchRemove(ids);
	}
	
}
