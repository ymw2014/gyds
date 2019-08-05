package com.fly.points.service;

import com.fly.points.domain.PointsDO;

import java.util.List;
import java.util.Map;

/**
 * 积分记录
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-11 14:25:54
 */
public interface PointsService {
	
	PointsDO get(Long id);
	
	List<PointsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PointsDO points);
	
	int update(PointsDO points);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}

