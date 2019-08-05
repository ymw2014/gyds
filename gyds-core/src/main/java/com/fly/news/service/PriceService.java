package com.fly.news.service;

import com.fly.news.domain.PriceDO;

import java.util.List;
import java.util.Map;

/**
 * 区域置顶价格配置表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-18 16:01:07
 */
public interface PriceService {
	
	PriceDO get(Integer id);
	
	List<PriceDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PriceDO price);
	
	int update(PriceDO price);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
