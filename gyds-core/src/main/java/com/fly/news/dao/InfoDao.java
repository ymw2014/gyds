package com.fly.news.dao;

import com.fly.news.domain.InfoDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-02 14:35:59
 */
@Mapper
public interface InfoDao {

	InfoDO get(Integer id);
	
	List<InfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(InfoDO info);
	
	int update(InfoDO info);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	int updateDynamic(Map<String, Object> map);
}
