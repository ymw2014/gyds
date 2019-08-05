package com.fly.team.dao;

import com.fly.team.domain.ApplyTeamDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 入团申请表
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-11 15:31:51
 */
@Mapper
public interface ApplyTeamDao {

	ApplyTeamDO get(Integer id);
	
	List<ApplyTeamDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ApplyTeamDO apply);
	
	int update(ApplyTeamDO apply);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
