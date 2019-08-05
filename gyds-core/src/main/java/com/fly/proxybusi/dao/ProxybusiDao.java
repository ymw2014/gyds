<<<<<<< HEAD
package com.fly.proxybusi.dao;

import com.fly.proxybusi.domain.ProxybusiDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-10 13:57:24
 */
@Mapper
public interface ProxybusiDao {

	ProxybusiDO get(Long id);
	
	List<ProxybusiDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ProxybusiDO proxybusi);
	
	int update(ProxybusiDO proxybusi);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
=======
package com.fly.proxybusi.dao;

import com.fly.proxybusi.domain.ProxybusiDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-10 13:57:24
 */
@Mapper
public interface ProxybusiDao {

	ProxybusiDO get(Long id);
	
	List<ProxybusiDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ProxybusiDO proxybusi);
	
	int update(ProxybusiDO proxybusi);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
>>>>>>> d7599c5df10b932a0c1fc85b5b2e9708ca6a2471
