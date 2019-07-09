package com.fly.actApply.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.actApply.dao.ApplyDao;
import com.fly.actApply.domain.ApplyDO;
import com.fly.actApply.service.ApplyService;



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
	
}
