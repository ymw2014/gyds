package com.fly.points.dao;

import com.fly.points.domain.PointsDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 积分记录
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-11 14:25:54
 */
@Mapper
public interface PointsDao {

	PointsDO get(Long id);
	
	List<PointsDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(PointsDO points);
	
	int update(PointsDO points);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
	
	List<PointsDO>  list1(Map<String,Object> map);
}

