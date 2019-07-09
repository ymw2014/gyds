package com.fly.activity.service;

import com.fly.activity.domain.TypeDO;

import java.util.List;
import java.util.Map;

/**
 * 活动类型表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-04 14:40:10
 */
public interface TypeService {
	
	TypeDO get(Integer id);
	
	List<TypeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TypeDO type);
	
	int update(TypeDO type);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
