package com.fly.level.service;

import com.fly.level.domain.LevelDO;

import java.util.List;
import java.util.Map;

/**
 * 积分区间表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-09-20 10:20:09
 */
public interface LevelService {
	
	LevelDO get(Long id);
	
	List<LevelDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(LevelDO level);
	
	int update(LevelDO level);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
