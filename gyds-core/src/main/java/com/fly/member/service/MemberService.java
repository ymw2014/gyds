package com.fly.member.service;

import com.fly.member.domain.MemberDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-08 15:52:57
 */
public interface MemberService {
	
	MemberDO get(Long id);
	
	List<MemberDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MemberDO member);
	
	int update(MemberDO member);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
