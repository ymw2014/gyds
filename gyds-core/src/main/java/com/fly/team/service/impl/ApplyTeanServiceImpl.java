package com.fly.team.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.team.dao.ApplyTeamDao;
import com.fly.team.domain.ApplyTeamDO;
import com.fly.team.service.ApplyTeamService;



@Service
public class ApplyTeanServiceImpl implements ApplyTeamService {
	@Autowired
	private ApplyTeamDao applyDao;
	
	@Override
	public ApplyTeamDO get(Integer id){
		return applyDao.get(id);
	}
	
	@Override
	public List<ApplyTeamDO> list(Map<String, Object> map){
		return applyDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return applyDao.count(map);
	}
	
	@Override
	public int save(ApplyTeamDO apply){
		return applyDao.save(apply);
	}
	
	@Override
	public int update(ApplyTeamDO apply){
		return applyDao.update(apply);
	}
	
	@Override
	public int remove(Integer id){
		return applyDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return applyDao.batchRemove(ids);
	}

	@Override
	public Integer teamApplyStatus(Map<String, Object> map) {
		return applyDao.teamApplyStatus(map);
	}
	
}
