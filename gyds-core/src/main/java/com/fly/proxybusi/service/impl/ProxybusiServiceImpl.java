<<<<<<< HEAD
package com.fly.proxybusi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.proxybusi.dao.ProxybusiDao;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.proxybusi.service.ProxybusiService;



@Service
public class ProxybusiServiceImpl implements ProxybusiService {
	@Autowired
	private ProxybusiDao proxybusiDao;
	
	@Override
	public ProxybusiDO get(Long id){
		return proxybusiDao.get(id);
	}
	
	@Override
	public List<ProxybusiDO> list(Map<String, Object> map){
		return proxybusiDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return proxybusiDao.count(map);
	}
	
	@Override
	public int save(ProxybusiDO proxybusi){
		return proxybusiDao.save(proxybusi);
	}
	
	@Override
	public int update(ProxybusiDO proxybusi){
		return proxybusiDao.update(proxybusi);
	}
	
	@Override
	public int remove(Long id){
		return proxybusiDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return proxybusiDao.batchRemove(ids);
	}
	
}
=======
package com.fly.proxybusi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.proxybusi.dao.ProxybusiDao;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.proxybusi.service.ProxybusiService;



@Service
public class ProxybusiServiceImpl implements ProxybusiService {
	@Autowired
	private ProxybusiDao proxybusiDao;
	
	@Override
	public ProxybusiDO get(Long id){
		return proxybusiDao.get(id);
	}
	
	@Override
	public List<ProxybusiDO> list(Map<String, Object> map){
		return proxybusiDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return proxybusiDao.count(map);
	}
	
	@Override
	public int save(ProxybusiDO proxybusi){
		return proxybusiDao.save(proxybusi);
	}
	
	@Override
	public int update(ProxybusiDO proxybusi){
		return proxybusiDao.update(proxybusi);
	}
	
	@Override
	public int remove(Long id){
		return proxybusiDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return proxybusiDao.batchRemove(ids);
	}
	
}
>>>>>>> d7599c5df10b932a0c1fc85b5b2e9708ca6a2471
