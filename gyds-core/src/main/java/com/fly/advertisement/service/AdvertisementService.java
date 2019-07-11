package com.fly.advertisement.service;

import com.fly.advertisement.domain.AdvertisementDO;

import java.util.List;
import java.util.Map;

/**
 * 广告
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-11 14:24:33
 */
public interface AdvertisementService {
	
	AdvertisementDO get(Integer id);
	
	List<AdvertisementDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AdvertisementDO advertisement);
	
	int update(AdvertisementDO advertisement);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
