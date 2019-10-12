package com.fly.project.service;

import com.fly.project.domain.ProjectTypeDO;

import java.util.List;
import java.util.Map;

/**
 * 项目类型
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-09 10:57:06
 */
public interface ProjectTypeService {
	
	ProjectTypeDO get(Long id);
	
	List<ProjectTypeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProjectTypeDO type);
	
	int update(ProjectTypeDO type);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
