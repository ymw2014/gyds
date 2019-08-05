package com.fly.helpCenter.dao;

import com.fly.helpCenter.domain.TypeTitleDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author Wangyalei
 * @email 15638836857@163.com
 * @date 2019-07-03 14:08:01
 */
@Mapper
public interface TypeTitleDao {

	TypeTitleDO get(Integer id);
	
	List<TypeTitleDO> list(Map<String,Object> map);
	List<TypeTitleDO> list1();
	
	int count(Map<String,Object> map);
	
	int save(TypeTitleDO typeTitle);
	
	int update(TypeTitleDO typeTitle);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
