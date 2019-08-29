package com.fly.team.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.team.dao.AutheDao;
import com.fly.team.domain.AutheDO;
import com.fly.team.service.AutheService;



@Service
public class AutheServiceImpl implements AutheService {
	@Autowired
	private AutheDao autheDao;
	
	@Override
	public AutheDO get(Long id){
		return autheDao.get(id);
	}
	
	@Override
	public List<AutheDO> list(Map<String, Object> map){
		return autheDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return autheDao.count(map);
	}
	
	@Override
	public int save(AutheDO authe){
		return autheDao.save(authe);
	}
	
	@Override
	public int update(AutheDO authe){
		return autheDao.update(authe);
	}
	
	@Override
	public int remove(Long id){
		return autheDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return autheDao.batchRemove(ids);
	}
	
}
