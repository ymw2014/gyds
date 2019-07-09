package com.fly.helpCenter.service;

import com.fly.helpCenter.domain.CenterDO;

import java.util.List;
import java.util.Map;

/**
 * 帮助中心信息表
 * 
 * @author Wangyalei
 * @email 15638836857@163.com
 * @date 2019-07-03 13:57:16
 */
public interface CenterService {
	
	CenterDO get(Integer id);
	
	List<CenterDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(CenterDO center);
	
	int update(CenterDO center);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
