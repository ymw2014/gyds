package com.fly.news.service;

import com.fly.news.domain.InfoDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-02 14:35:59
 */
public interface InfoService {
	
	InfoDO get(Integer id);
	
	List<InfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(InfoDO info);
	
	int update(InfoDO info);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	List<Map<String,Object>> auditData(Map<String, Object> map);
	
	int auditDataCount(Map<String, Object> map);
}
