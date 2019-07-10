package com.fly.volunteer.dao;

import com.fly.volunteer.domain.VolunteerDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 志愿者表
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-09 15:08:21
 */
@Mapper
public interface VolunteerDao {

	VolunteerDO get(Long id);
	
	List<VolunteerDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(VolunteerDO volunteer);
	
	int update(VolunteerDO volunteer);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
