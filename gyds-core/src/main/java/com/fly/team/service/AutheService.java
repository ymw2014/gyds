package com.fly.team.service;

import com.fly.team.domain.AutheDO;

import java.util.List;
import java.util.Map;

/**
 * 团队认证表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-28 15:21:01
 */
public interface AutheService {
	
	AutheDO get(Long id);
	
	List<AutheDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AutheDO authe);
	
	int update(AutheDO authe);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
