package com.fly.activity.service;


import java.util.List;
import java.util.Map;

import com.fly.activity.domain.ApplyDO;

/**
 * 活动报名表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-08 11:07:53
 */
public interface ApplyService {
	
	ApplyDO get(Long id);
	
	List<ApplyDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ApplyDO apply);
	
	int update(ApplyDO apply);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
	
	ApplyDO getApply(Long userId,Integer actId);
	
}
