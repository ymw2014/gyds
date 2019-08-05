package com.fly.points.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.domain.UserDO;
import com.fly.points.dao.PointsDao;
import com.fly.points.domain.PointsDO;
import com.fly.points.service.PointsService;



@Service
public class PointsServiceImpl implements PointsService {
	@Autowired
	private PointsDao pointsDao;
	
	@Override
	public PointsDO get(Long id){
		return pointsDao.get(id);
	}
	
	@Override
	public List<PointsDO> list(Map<String, Object> map){
		return pointsDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return pointsDao.count(map);
	}
	
	@Override
	public int save(PointsDO points){
		return pointsDao.save(points);
	}
	
	@Override
	public int update(PointsDO points){
		return pointsDao.update(points);
	}
	
	@Override
	public int remove(Long id){
		return pointsDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return pointsDao.batchRemove(ids);
	}
	
}
