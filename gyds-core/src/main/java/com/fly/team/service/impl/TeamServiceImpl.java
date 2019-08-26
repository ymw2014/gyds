package com.fly.team.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.team.dao.TeamDao;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamService;



@Service
public class TeamServiceImpl implements TeamService {
	@Autowired
	private TeamDao teamDao;
	
	@Override
	public TeamDO get(Long id){
		return teamDao.get(id);
	}
	
	@Override
	public List<TeamDO> list(Map<String, Object> map){
		return teamDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return teamDao.count(map);
	}
	
	@Override
	public int save(TeamDO team){
		return teamDao.save(team);
	}
	
	@Override
	public int update(TeamDO team){
		return teamDao.update(team);
	}
	
	@Override
	public int remove(Long id){
		return teamDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return teamDao.batchRemove(ids);
	}
	
	@Override
	public boolean checkTeamHasMember(Integer teamId) {
		int result = teamDao.getRegionUserMember(teamId);
		return result==0?true:false;
	}
	
}
