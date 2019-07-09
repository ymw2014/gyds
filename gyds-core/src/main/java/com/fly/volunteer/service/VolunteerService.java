package com.fly.volunteer.service;

import com.fly.volunteer.domain.VolunteerDO;

import java.util.List;
import java.util.Map;

/**
 * 志愿者表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-09 13:42:20
 */
public interface VolunteerService {
	
	VolunteerDO get(Long id);
	
	List<VolunteerDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(VolunteerDO volunteer);
	
	int update(VolunteerDO volunteer);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
