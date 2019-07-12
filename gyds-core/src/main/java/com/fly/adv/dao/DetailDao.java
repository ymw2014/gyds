package com.fly.adv.dao;

import com.fly.adv.domain.DetailDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-12 11:20:03
 */
@Mapper
public interface DetailDao {

	DetailDO get(Integer id);
	
	List<DetailDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DetailDO detail);
	
	int update(DetailDO detail);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
