package com.fly.team.dao;

import com.fly.team.domain.TeamDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 团队表
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-04 10:09:06
 */
@Mapper
public interface TeamDao {

	TeamDO get(Integer id);
	
	TeamDO getByTeamCode(Integer teamCode);

	List<TeamDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(TeamDO team);
	
	int update(TeamDO team);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	int getDeptUserNumber(Long teamId);

	int getRegionUserMember(Integer teamId);
	
	List<TeamDO> is_apply(Map<String,Object> map);
	int updateIntegral(Map<String,Object> map);
}
