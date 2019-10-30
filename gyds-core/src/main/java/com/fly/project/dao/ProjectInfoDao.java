package com.fly.project.dao;

import com.fly.project.domain.ProjectInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 项目详情
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-09 11:32:41
 */
@Mapper
public interface ProjectInfoDao {

	ProjectInfoDO get(Long id);
	
	List<ProjectInfoDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ProjectInfoDO info);
	
	int update(ProjectInfoDO info);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
	
	List<ProjectInfoDO> proInfoList(Map<String,Object> map);
	
	int updateCount(ProjectInfoDO info);
	
}
