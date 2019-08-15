package com.fly.volunteer.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.fly.volunteer.domain.VolunteerDO;

/**
 * 志愿者表
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-09 15:08:21
 */
@Mapper
public interface VolunteerDao {

	VolunteerDO get(Integer id);
	Map<String, Object> getVoInfo(Long id);
	
	List<VolunteerDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(VolunteerDO volunteer);
	
	int update(VolunteerDO volunteer);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
	
	List<VolunteerDO> isVolllist(Long userId);
	int updateVolunteer(Map<String,Object> map);
	
	List<VolunteerDO> isVo(Long userId);
	
	List<Map<String,Object>> voluntList(Map<String,Object> map);
	
	List<Map<String,Object>> voluntInfo(Map<String,Object> map);
	
	int voluntInfoCount(Map<String,Object> map);
	
	int update_count(Map<String,Object> map);
}
