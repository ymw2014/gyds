package com.fly.guestlog.service;

import com.fly.guestlog.domain.GuestlogDO;

import java.util.List;
import java.util.Map;

/**
 * 访客记录表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-23 17:07:37
 */
public interface GuestlogService {
	
	GuestlogDO get(Long id);
	
	List<GuestlogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GuestlogDO guestlog);
	
	int update(GuestlogDO guestlog);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
