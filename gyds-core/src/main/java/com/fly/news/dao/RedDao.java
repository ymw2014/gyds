package com.fly.news.dao;

import com.fly.news.domain.RedDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 红包附表
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-05 11:55:52
 */
@Mapper
public interface RedDao {

	RedDO get(Integer id);
	
	List<RedDO> list(Map<String,Object> map);
	
	List<Map<String,Object>> redListUser(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(RedDO red);
	
	int update(RedDO red);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
