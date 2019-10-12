package com.fly.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.project.dao.ProjectTypeDao;
import com.fly.project.domain.ProjectTypeDO;
import com.fly.project.service.ProjectTypeService;



@Service
public class ProjectTypeServiceImpl implements ProjectTypeService {
	@Autowired
	private ProjectTypeDao projectTypeDao;
	
	@Override
	public ProjectTypeDO get(Long id){
		return projectTypeDao.get(id);
	}
	
	@Override
	public List<ProjectTypeDO> list(Map<String, Object> map){
		return projectTypeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return projectTypeDao.count(map);
	}
	
	@Override
	public int save(ProjectTypeDO type){
		return projectTypeDao.save(type);
	}
	
	@Override
	public int update(ProjectTypeDO type){
		return projectTypeDao.update(type);
	}
	
	@Override
	public int remove(Long id){
		return projectTypeDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return projectTypeDao.batchRemove(ids);
	}
	
}
