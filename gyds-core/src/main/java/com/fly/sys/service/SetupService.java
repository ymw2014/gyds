package com.fly.sys.service;

import com.fly.sys.domain.SetupDO;

import java.util.List;
import java.util.Map;

/**
 * 配置表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-11 16:11:17
 */
public interface SetupService {
	
	SetupDO get(Integer id);
	
	List<SetupDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SetupDO setup);
	
	int update(SetupDO setup);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
