package com.fly.news.service;

import com.fly.news.domain.DynamicDO;

import java.util.List;
import java.util.Map;

/**
 * 咨询动态表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-16 11:50:51
 */
public interface DynamicService {
	
	DynamicDO get(Integer id);
	
	List<DynamicDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DynamicDO dynamic);
	
	int update(DynamicDO dynamic);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
