package com.fly.news.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.fly.news.domain.DynamicDO;

/**
 * 咨询动态表
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-16 11:50:51
 */
@Mapper
public interface DynamicDao {

	DynamicDO get(Integer id);
	
	List<DynamicDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DynamicDO dynamic);
	
	int update(DynamicDO dynamic);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	List<Map<String, Object>>dyTeamList(Map<String,Object> params);
	
	List<Map<String, Object>>dyActList(Map<String,Object> params);
	
	List<Map<String, Object>>dyNewList(Map<String,Object> params);
	
	List<Map<String, Object>>dyVoluList(Map<String,Object> params);
}
