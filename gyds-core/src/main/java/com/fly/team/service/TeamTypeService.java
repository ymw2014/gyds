package com.fly.team.service;

import com.fly.team.domain.TypeDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-04 10:09:09
 */
public interface TeamTypeService {
	
	TypeDO get(Integer id);
	
	List<TypeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TypeDO type);
	
	int update(TypeDO type);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
