package com.fly.project.service;

import com.fly.project.domain.ProjectInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 项目详情
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-09 11:32:41
 */
public interface ProjectInfoService {
	
	ProjectInfoDO get(Long id);
	
	List<ProjectInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProjectInfoDO info);
	
	int update(ProjectInfoDO info);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
