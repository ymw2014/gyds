package com.fly.project.dao;

import com.fly.project.domain.ProjectDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 申请参加项目
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-09 16:35:53
 */
@Mapper
public interface ProjectDao {

	ProjectDO get(Long id);
	
	List<ProjectDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ProjectDO project);
	
	int update(ProjectDO project);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
	
	List<ProjectDO> endTimePro(Map<String,Object> map);
}
