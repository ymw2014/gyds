package com.fly.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.news.dao.InfoDao;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.InfoService;



@Service
public class InfoServiceImpl implements InfoService {
	@Autowired
	private InfoDao infoDao;
	
	@Override
	public InfoDO get(Integer id){
		return infoDao.get(id);
	}
	
	@Override
	public List<InfoDO> list(Map<String, Object> map){
		return infoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return infoDao.count(map);
	}
	
	@Override
	public int save(InfoDO info){
		return infoDao.save(info);
	}
	
	@Override
	public int update(InfoDO info){
		return infoDao.update(info);
	}
	
	@Override
	public int remove(Integer id){
		return infoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return infoDao.batchRemove(ids);
	}
	
}
