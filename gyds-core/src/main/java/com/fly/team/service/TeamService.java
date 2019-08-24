package com.fly.team.service;

import com.fly.team.domain.TeamDO;

import java.util.List;
import java.util.Map;

/**
 * 团队表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-04 10:09:06
 */
public interface TeamService {
	
	TeamDO get(Long id);
	
	List<TeamDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TeamDO team);
	
	int update(TeamDO team);
	
	int remove(Long id);
	
	int batchRemove(Integer[] ids);

	boolean checkTeamHasMember(Integer teamId);
}
