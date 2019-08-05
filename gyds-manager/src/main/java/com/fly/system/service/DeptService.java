package com.fly.system.service;


import com.fly.domain.DeptDO;
import com.fly.domain.Tree;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author

 */
public interface DeptService {
	
	DeptDO get(Long deptId);
	
	List<DeptDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(DeptDO sysDept);
	
	int update(DeptDO sysDept);
	
	int remove(Long deptId);
	
	int batchRemove(Long[] deptIds);

	Tree<DeptDO> getTree();
	
	boolean checkDeptHasUser(Long deptId);
}
