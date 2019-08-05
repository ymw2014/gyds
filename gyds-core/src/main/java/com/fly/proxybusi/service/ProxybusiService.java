<<<<<<< HEAD
package com.fly.proxybusi.service;

import com.fly.proxybusi.domain.ProxybusiDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-10 13:57:24
 */
public interface ProxybusiService {
	
	ProxybusiDO get(Long id);
	
	List<ProxybusiDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProxybusiDO proxybusi);
	
	int update(ProxybusiDO proxybusi);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
=======
package com.fly.proxybusi.service;

import com.fly.proxybusi.domain.ProxybusiDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-10 13:57:24
 */
public interface ProxybusiService {
	
	ProxybusiDO get(Long id);
	
	List<ProxybusiDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProxybusiDO proxybusi);
	
	int update(ProxybusiDO proxybusi);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
>>>>>>> d7599c5df10b932a0c1fc85b5b2e9708ca6a2471
