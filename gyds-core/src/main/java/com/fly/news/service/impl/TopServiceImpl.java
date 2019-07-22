package com.fly.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.news.dao.TopDao;
import com.fly.news.domain.TopDO;
import com.fly.news.service.TopService;



@Service
public class TopServiceImpl implements TopService {
	@Autowired
	private TopDao topDao;
	
	@Override
	public TopDO get(Integer id){
		return topDao.get(id);
	}
	
	@Override
	public List<TopDO> list(Map<String, Object> map){
		return topDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return topDao.count(map);
	}
	
	@Override
	public int save(TopDO top){
		return topDao.save(top);
	}
	
	@Override
	public int update(TopDO top){
		return topDao.update(top);
	}
	
	@Override
	public int remove(Integer id){
		return topDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return topDao.batchRemove(ids);
	}
	
}
