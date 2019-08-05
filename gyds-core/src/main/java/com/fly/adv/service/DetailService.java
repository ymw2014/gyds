package com.fly.adv.service;

import com.fly.adv.domain.DetailDO;

import java.util.List;
import java.util.Map;

/**
 * 广告记录
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-19 15:16:53
 */
public interface DetailService {
	
	DetailDO get(Integer id);
	
	List<DetailDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DetailDO detail);
	
	int update(DetailDO detail);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
