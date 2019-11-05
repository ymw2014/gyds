package com.fly.project.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.project.dao.ProjectInfoDao;
import com.fly.project.domain.ProjectInfoDO;
import com.fly.project.service.ProjectInfoService;



@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
	@Autowired
	private ProjectInfoDao projectInfoDao;
	
	@Override
	public ProjectInfoDO get(Long id){
		return projectInfoDao.get(id);
	}
	
	@Override
	public List<ProjectInfoDO> list(Map<String, Object> map){
		return projectInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return projectInfoDao.count(map);
	}
	
	@Override
	public int save(ProjectInfoDO info){
		return projectInfoDao.save(info);
	}
	
	@Override
	public int update(ProjectInfoDO info){
		return projectInfoDao.update(info);
	}
	
	@Override
	public int remove(Long id){
		return projectInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return projectInfoDao.batchRemove(ids);
	}

	@Override
	public List<ProjectInfoDO> proInfoList(Map<String, Object> map) {
		return projectInfoDao.proInfoList(map);
	}

	@Override
	public int updateCount(ProjectInfoDO info) {
		return projectInfoDao.updateCount(info);
	}

	@Override
	public List<ProjectInfoDO> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return projectInfoDao.queryList(map);
	}

	
}
