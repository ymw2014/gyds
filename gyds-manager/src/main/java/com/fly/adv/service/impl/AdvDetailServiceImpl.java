package com.fly.adv.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.adv.dao.DetailDao;
import com.fly.adv.domain.DetailDO;
import com.fly.adv.service.AdvDetailService;
import com.fly.adv.service.AdvertisementService;

@Service
public class AdvDetailServiceImpl  implements AdvDetailService{

	@Autowired
	private DetailDao detailDao;
	@Autowired
	private AdvertisementService advertisementService;
	@Override
	public void detailExamine(Integer id,Integer status) {
		DetailDO detail = detailDao.get(id);
		detail.getRegionCode();
		detail.getPositionNum();
		Map<String, Object> params=new HashMap<String, Object>(16);
		
		advertisementService.list(params);
		
		
	}

}
