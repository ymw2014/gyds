package com.fly.news.dao;

import com.fly.news.domain.PriceDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 区域置顶价格配置表
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-18 16:01:07
 */
@Mapper
public interface PriceDao {

	PriceDO get(Integer id);
	
	List<PriceDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(PriceDO price);
	
	int update(PriceDO price);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
