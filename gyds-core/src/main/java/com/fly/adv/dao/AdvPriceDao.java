package com.fly.adv.dao;

import com.fly.adv.domain.AdvPriceDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 广告价格配置表
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-19 15:27:07
 */
@Mapper
public interface AdvPriceDao {

	AdvPriceDO get(Integer id);
	
	List<AdvPriceDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(AdvPriceDO advPrice);
	
	int update(AdvPriceDO advPrice);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
