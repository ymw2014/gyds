package com.fly.adv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.adv.dao.AdvPriceDao;
import com.fly.adv.domain.AdvPriceDO;
import com.fly.adv.service.AdvPriceService;



@Service
public class AdvPriceServiceImpl implements AdvPriceService {
	@Autowired
	private AdvPriceDao advPriceDao;
	
	@Override
	public AdvPriceDO get(Integer id){
		return advPriceDao.get(id);
	}
	
	@Override
	public List<AdvPriceDO> list(Map<String, Object> map){
		return advPriceDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return advPriceDao.count(map);
	}
	
	@Override
	public int save(AdvPriceDO advPrice){
		return advPriceDao.save(advPrice);
	}
	
	@Override
	public int update(AdvPriceDO advPrice){
		return advPriceDao.update(advPrice);
	}
	
	@Override
	public int remove(Integer id){
		return advPriceDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return advPriceDao.batchRemove(ids);
	}
	
}
