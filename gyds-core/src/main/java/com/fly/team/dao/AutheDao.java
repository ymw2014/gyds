package com.fly.team.dao;

import com.fly.team.domain.AutheDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 团队认证表
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-28 15:21:01
 */
@Mapper
public interface AutheDao {

	AutheDO get(Long id);
	
	List<AutheDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(AutheDO authe);
	
	int update(AutheDO authe);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
