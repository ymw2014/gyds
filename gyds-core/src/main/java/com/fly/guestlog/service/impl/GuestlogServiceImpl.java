package com.fly.guestlog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.guestlog.dao.GuestlogDao;
import com.fly.guestlog.domain.GuestlogDO;
import com.fly.guestlog.service.GuestlogService;



@Service
public class GuestlogServiceImpl implements GuestlogService {
	@Autowired
	private GuestlogDao guestlogDao;
	
	@Override
	public GuestlogDO get(Long id){
		return guestlogDao.get(id);
	}
	
	@Override
	public List<GuestlogDO> list(Map<String, Object> map){
		return guestlogDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return guestlogDao.count(map);
	}
	
	@Override
	public int save(GuestlogDO guestlog){
		return guestlogDao.save(guestlog);
	}
	
	@Override
	public int update(GuestlogDO guestlog){
		return guestlogDao.update(guestlog);
	}
	
	@Override
	public int remove(Long id){
		return guestlogDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return guestlogDao.batchRemove(ids);
	}
	
}
