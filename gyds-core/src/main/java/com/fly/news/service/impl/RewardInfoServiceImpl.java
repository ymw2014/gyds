package com.fly.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.news.dao.RewardInfoDao;
import com.fly.news.domain.RewardInfoDO;
import com.fly.news.service.RewardInfoService;



@Service
public class RewardInfoServiceImpl implements RewardInfoService {
	@Autowired
	private RewardInfoDao rewardInfoDao;
	
	@Override
	public RewardInfoDO get(Integer id){
		return rewardInfoDao.get(id);
	}
	
	@Override
	public List<RewardInfoDO> list(Map<String, Object> map){
		return rewardInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return rewardInfoDao.count(map);
	}
	
	@Override
	public int save(RewardInfoDO rewardInfo){
		return rewardInfoDao.save(rewardInfo);
	}
	
	@Override
	public int update(RewardInfoDO rewardInfo){
		return rewardInfoDao.update(rewardInfo);
	}
	
	@Override
	public int remove(Integer id){
		return rewardInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return rewardInfoDao.batchRemove(ids);
	}
	
}
