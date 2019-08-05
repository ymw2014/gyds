package com.fly.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fly.activity.dao.ActivityDao;
import com.fly.activity.domain.ActivityDO;
import com.fly.activity.service.ActivityService;
import com.fly.member.dao.MemberDao;
import com.fly.member.domain.MemberDO;



@Service
public class ActivityServiceImpl implements ActivityService {
	@Autowired
	private ActivityDao activityDao;
	
	@Override
	public ActivityDO get(Integer id){
		return activityDao.get(id);
	}
	
	@Override
	public List<ActivityDO> list(Map<String, Object> map){
		return activityDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return activityDao.count(map);
	}
	
	@Override
	public int save(ActivityDO activity){
		return activityDao.save(activity);
	}
	
	@Override
	public int update(ActivityDO activity){
		return activityDao.update(activity);
	}
	
	@Override
	public int remove(Integer id){
		return activityDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return activityDao.batchRemove(ids);
	}
	
}
