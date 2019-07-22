package com.fly.news.dao;

import com.fly.news.domain.TopDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 置顶申请列表
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-22 15:14:59
 */
@Mapper
public interface TopDao {

	TopDO get(Integer id);
	
	List<TopDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(TopDO top);
	
	int update(TopDO top);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
