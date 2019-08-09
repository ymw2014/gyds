package com.fly.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.fly.domain.RegionDO;

/**
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-03 15:01:06
 */
@Mapper
public interface RegionDao {

	RegionDO get(Integer regionCode);
	
	List<RegionDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(RegionDO region);
	
	int update(RegionDO region);
	
	int remove(Integer region_code);
	
	int batchRemove(Integer[] regionCodes);

	int getRegionUserNumber(Integer regionCode);

	List<RegionDO> regionIdByList(Map<String, Object> params);
	
	int activeCount(List<Integer> ids, String startTime, String endTime);
}
