package com.fly.proSetup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.proSetup.dao.ProjectSetupDao;
import com.fly.proSetup.domain.ProjectSetupDO;
import com.fly.proSetup.service.ProjectSetupService;



@Service
public class ProjectSetupServiceImpl implements ProjectSetupService {
	@Autowired
	private ProjectSetupDao projectSetupDao;
	
	@Override
	public ProjectSetupDO get(Integer id){
		return projectSetupDao.get(id);
	}
	
	@Override
	public List<ProjectSetupDO> list(Map<String, Object> map){
		return projectSetupDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return projectSetupDao.count(map);
	}
	
	@Override
	public int save(ProjectSetupDO projectSetup){
		return projectSetupDao.save(projectSetup);
	}
	
	@Override
	public int update(ProjectSetupDO projectSetup){
		return projectSetupDao.update(projectSetup);
	}
	
	@Override
	public int remove(Integer id){
		return projectSetupDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return projectSetupDao.batchRemove(ids);
	}
	
}
