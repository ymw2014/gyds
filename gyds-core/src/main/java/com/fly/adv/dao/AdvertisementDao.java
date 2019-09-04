package com.fly.adv.dao;

import com.fly.adv.domain.AdvertisementDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 广告
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-12 11:20:07
 */
@Mapper
public interface AdvertisementDao {

	AdvertisementDO get(Integer id);
	
	List<AdvertisementDO> list(Map<String,Object> map);
	
	List<AdvertisementDO> listEndShow(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(AdvertisementDO advertisement);
	
	int update(AdvertisementDO advertisement);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
