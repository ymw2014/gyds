package com.fly.helpCenter.dao;

import com.fly.helpCenter.domain.CenterDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 帮助中心信息表
 * @author Wangyalei
 * @email 15638836857@163.com
 * @date 2019-07-03 13:57:16
 */
@Mapper
public interface CenterDao {

	CenterDO get(Integer id);
	
	List<CenterDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(CenterDO center);
	
	int update(CenterDO center);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
