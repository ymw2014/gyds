package com.fly.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.project.dao.ProjectDao;
import com.fly.project.domain.ProjectDO;
import com.fly.project.service.ProjectService;



@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public ProjectDO get(Long id){
		return projectDao.get(id);
	}
	
	@Override
	public List<ProjectDO> list(Map<String, Object> map){
		return projectDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return projectDao.count(map);
	}
	
	@Override
	public int save(ProjectDO project){
		return projectDao.save(project);
	}
	
	@Override
	public int update(ProjectDO project){
		return projectDao.update(project);
	}
	
	@Override
	public int remove(Long id){
		return projectDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return projectDao.batchRemove(ids);
	}
	
}
