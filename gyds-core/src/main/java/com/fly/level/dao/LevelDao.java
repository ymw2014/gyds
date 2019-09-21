package com.fly.level.dao;

import com.fly.level.domain.LevelDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 积分区间表
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-09-20 10:20:09
 */
@Mapper
public interface LevelDao {

	LevelDO get(Long id);
	
	List<LevelDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(LevelDO level);
	
	int update(LevelDO level);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
	
	List<LevelDO> queryIntegral(Map<String,Object> map);
}
