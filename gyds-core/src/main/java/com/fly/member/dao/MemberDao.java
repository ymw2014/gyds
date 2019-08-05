package com.fly.member.dao;

import com.fly.member.domain.MemberDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-08 15:52:57
 */
@Mapper
public interface MemberDao {

	MemberDO get(Long id);
	
	List<MemberDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(MemberDO member);
	
	int update(MemberDO member);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
