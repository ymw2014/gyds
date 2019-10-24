package com.fly.proSetup.service;

import com.fly.proSetup.domain.ProjectSetupDO;

import java.util.List;
import java.util.Map;

/**
 * 项目分佣配置
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-22 15:22:20
 */
public interface ProjectSetupService {
	
	ProjectSetupDO get(Integer id);
	
	List<ProjectSetupDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProjectSetupDO projectSetup);
	
	int update(ProjectSetupDO projectSetup);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
