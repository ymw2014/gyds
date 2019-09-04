package com.fly.activity.dao;

import com.fly.activity.domain.ActivityDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 活动表
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-04 14:26:40
 */
@Mapper
public interface ActivityDao {

	ActivityDO get(Integer id);
	
	List<ActivityDO> list(Map<String,Object> map);
	
	List<ActivityDO> ActivityTime(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ActivityDO activity);
	
	int update(ActivityDO activity);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	int updateActDynamic(Map<String,Object> map);
	
	int ActDynamicCall(Map<String,Object> map);
}
