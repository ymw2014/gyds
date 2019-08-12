package com.fly.verifyName.service;

import com.fly.verifyName.domain.NameDO;

import java.util.List;
import java.util.Map;

/**
 * 实名认证申请表/入团/建团/代理商入驻
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-07 14:06:00
 */
public interface NameService {
	
	NameDO get(Integer id);
	
	List<NameDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(NameDO name);
	
	int update(NameDO name);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
}
