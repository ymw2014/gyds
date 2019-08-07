package com.fly.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.activity.dao.ApplyDao;
import com.fly.activity.domain.ApplyDO;
import com.fly.activity.service.ApplyService;

import java.util.List;
import java.util.Map;





@Service
public class ApplyServiceImpl implements ApplyService {
	@Autowired
	private ApplyDao applyDao;
	
	@Override
	public ApplyDO get(Long id){
		return applyDao.get(id);
	}
	
	@Override
	public List<ApplyDO> list(Map<String, Object> map){
		return applyDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return applyDao.count(map);
	}
	
	@Override
	public int save(ApplyDO apply){
		return applyDao.save(apply);
	}
	
	@Override
	public int update(ApplyDO apply){
		return applyDao.update(apply);
	}
	
	@Override
	public int remove(Long id){
		return applyDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return applyDao.batchRemove(ids);
	}

	@Override
	public ApplyDO getApply(Long userId,Integer actId) {
		if(applyDao.isApply(userId,actId)!=null&&applyDao.isApply(userId,actId).size()>0) {
			return applyDao.isApply(userId,actId).get(0);
		}
		return  null;
	}
	
	

	
}
