package com.fly.signin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.signin.dao.SigninDao;
import com.fly.signin.domain.SigninDO;
import com.fly.signin.service.SigninService;



@Service
public class SigninServiceImpl implements SigninService {
	@Autowired
	private SigninDao signinDao;
	
	@Override
	public SigninDO get(Integer id){
		return signinDao.get(id);
	}
	
	@Override
	public List<SigninDO> list(Map<String, Object> map){
		return signinDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return signinDao.count(map);
	}
	
	@Override
	public int save(SigninDO signin){
		return signinDao.save(signin);
	}
	
	@Override
	public int update(SigninDO signin){
		return signinDao.update(signin);
	}
	
	@Override
	public int remove(Integer id){
		return signinDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return signinDao.batchRemove(ids);
	}
	
}
