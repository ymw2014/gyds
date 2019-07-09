package com.fly.activity.service;

import com.fly.activity.domain.ActivityDO;

import java.util.List;
import java.util.Map;

/**
 * 活动表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-04 14:26:40
 */
public interface ActivityService {
	
	ActivityDO get(Integer id);
	
	List<ActivityDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ActivityDO activity);
	
	int update(ActivityDO activity);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
