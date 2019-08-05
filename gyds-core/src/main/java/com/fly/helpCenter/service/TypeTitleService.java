package com.fly.helpCenter.service;

import com.fly.helpCenter.domain.TypeTitleDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Wangyalei
 * @email 15638836857@163.com
 * @date 2019-07-03 14:08:01
 */
public interface TypeTitleService {
	
	TypeTitleDO get(Integer id);
	
	List<TypeTitleDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TypeTitleDO typeTitle);
	
	int update(TypeTitleDO typeTitle);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
