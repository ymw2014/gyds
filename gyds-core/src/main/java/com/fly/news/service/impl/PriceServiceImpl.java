package com.fly.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.news.dao.PriceDao;
import com.fly.news.domain.PriceDO;
import com.fly.news.service.PriceService;



@Service
public class PriceServiceImpl implements PriceService {
	@Autowired
	private PriceDao priceDao;
	
	@Override
	public PriceDO get(Integer id){
		return priceDao.get(id);
	}
	
	@Override
	public List<PriceDO> list(Map<String, Object> map){
		return priceDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return priceDao.count(map);
	}
	
	@Override
	public int save(PriceDO price){
		return priceDao.save(price);
	}
	
	@Override
	public int update(PriceDO price){
		return priceDao.update(price);
	}
	
	@Override
	public int remove(Integer id){
		return priceDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return priceDao.batchRemove(ids);
	}
	
}
