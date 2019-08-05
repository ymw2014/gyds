package com.fly.adv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.adv.dao.DetailDao;
import com.fly.adv.domain.DetailDO;
import com.fly.adv.service.DetailService;



@Service
public class DetailServiceImpl implements DetailService {
	@Autowired
	private DetailDao detailDao;
	
	@Override
	public DetailDO get(Integer id){
		return detailDao.get(id);
	}
	
	@Override
	public List<DetailDO> list(Map<String, Object> map){
		return detailDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return detailDao.count(map);
	}
	
	@Override
	public int save(DetailDO detail){
		return detailDao.save(detail);
	}
	
	@Override
	public int update(DetailDO detail){
		return detailDao.update(detail);
	}
	
	@Override
	public int remove(Integer id){
		return detailDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return detailDao.batchRemove(ids);
	}
	
}
