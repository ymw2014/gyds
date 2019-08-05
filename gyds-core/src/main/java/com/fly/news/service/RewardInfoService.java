package com.fly.news.service;

import com.fly.news.domain.RewardInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-02 14:39:24
 */
public interface RewardInfoService {
	
	RewardInfoDO get(Integer id);
	
	List<RewardInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(RewardInfoDO rewardInfo);
	
	int update(RewardInfoDO rewardInfo);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
