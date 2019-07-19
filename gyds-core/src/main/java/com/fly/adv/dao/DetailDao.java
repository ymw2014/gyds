package com.fly.adv.dao;

import com.fly.adv.domain.DetailDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 广告记录
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-19 15:16:53
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
