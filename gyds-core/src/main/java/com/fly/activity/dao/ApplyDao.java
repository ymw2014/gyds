package com.fly.activity.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.fly.activity.domain.ApplyDO;

/**
 * 活动报名表
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-08 11:07:53
 */
@Mapper
public interface ApplyDao {

	ApplyDO get(Long id);
	
	List<ApplyDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ApplyDO apply);
	
	int update(ApplyDO apply);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	void isApply(Integer zyzId,Integer actId);
}
