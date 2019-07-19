package com.fly.adv.service;

import com.fly.adv.domain.AdvPriceDO;

import java.util.List;
import java.util.Map;

/**
 * 广告价格配置表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-19 15:27:07
 */
public interface AdvPriceService {
	
	AdvPriceDO get(Integer id);
	
	List<AdvPriceDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AdvPriceDO advPrice);
	
	int update(AdvPriceDO advPrice);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
