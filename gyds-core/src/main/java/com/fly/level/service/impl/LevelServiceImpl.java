package com.fly.level.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.level.dao.LevelDao;
import com.fly.level.domain.LevelDO;
import com.fly.level.service.LevelService;



@Service
public class LevelServiceImpl implements LevelService {
	@Autowired
	private LevelDao levelDao;
	
	@Override
	public LevelDO get(Long id){
		return levelDao.get(id);
	}
	
	@Override
	public List<LevelDO> list(Map<String, Object> map){
		return levelDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return levelDao.count(map);
	}
	
	@Override
	public int save(LevelDO level){
		return levelDao.save(level);
	}
	
	@Override
	public int update(LevelDO level){
		return levelDao.update(level);
	}
	
	@Override
	public int remove(Long id){
		return levelDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return levelDao.batchRemove(ids);
	}
	
}
