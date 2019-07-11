package com.fly.advertisement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.advertisement.dao.AdvertisementDao;
import com.fly.advertisement.domain.AdvertisementDO;
import com.fly.advertisement.service.AdvertisementService;



@Service
public class AdvertisementServiceImpl implements AdvertisementService {
	@Autowired
	private AdvertisementDao advertisementDao;
	
	@Override
	public AdvertisementDO get(Integer id){
		return advertisementDao.get(id);
	}
	
	@Override
	public List<AdvertisementDO> list(Map<String, Object> map){
		return advertisementDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return advertisementDao.count(map);
	}
	
	@Override
	public int save(AdvertisementDO advertisement){
		return advertisementDao.save(advertisement);
	}
	
	@Override
	public int update(AdvertisementDO advertisement){
		return advertisementDao.update(advertisement);
	}
	
	@Override
	public int remove(Integer id){
		return advertisementDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return advertisementDao.batchRemove(ids);
	}
	
}
