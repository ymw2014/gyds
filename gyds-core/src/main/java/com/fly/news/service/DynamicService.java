<<<<<<< HEAD
package com.fly.news.service;

import com.fly.news.domain.DynamicDO;

import java.util.List;
import java.util.Map;

/**
 * 咨询动态表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-16 11:50:51
 */
public interface DynamicService {
	
	DynamicDO get(Integer id);
	
	List<DynamicDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DynamicDO dynamic);
	
	int update(DynamicDO dynamic);
	
	int remove(Integer id);
	
	int removeCall(DynamicDO dynamic);
	
	int batchRemove(Integer[] ids);
	/**
	 * 	获取关注团队的关联信息
	 * @param params
	 * @return
	 */
	List<Map<String, Object>>dyTeamList(Map<String,Object> params);
	
	/**
	 * 	获取关注活动的关联信息
	 * @param params
	 * @return
	 */
	List<Map<String, Object>>dyActList(Map<String,Object> params);
	/**
	 * 	获取关注新闻的关联信息
	 * @param params
	 * @return
	 */
	List<Map<String, Object>>dyNewList(Map<String,Object> params);
	/**
	 * 	获取关注志愿者的关联信息
	 * @param params
	 * @return
	 */
	List<Map<String, Object>>dyVoluList(Map<String,Object> params);
}
=======
package com.fly.news.service;

import com.fly.news.domain.DynamicDO;

import java.util.List;
import java.util.Map;

/**
 * 咨询动态表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-16 11:50:51
 */
public interface DynamicService {
	
	DynamicDO get(Integer id);
	
	List<DynamicDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DynamicDO dynamic);
	
	int update(DynamicDO dynamic);
	
	int remove(Integer id);
	
	int removeCall(DynamicDO dynamic);
	
	int batchRemove(Integer[] ids);
	/**
	 * 	获取关注团队的关联信息
	 * @param params
	 * @return
	 */
	List<Map<String, Object>>dyTeamList(Map<String,Object> params);
	
	/**
	 * 	获取关注活动的关联信息
	 * @param params
	 * @return
	 */
	List<Map<String, Object>>dyActList(Map<String,Object> params);
	/**
	 * 	获取关注新闻的关联信息
	 * @param params
	 * @return
	 */
	List<Map<String, Object>>dyNewList(Map<String,Object> params);
	/**
	 * 	获取关注志愿者的关联信息
	 * @param params
	 * @return
	 */
	List<Map<String, Object>>dyVoluList(Map<String,Object> params);
}
>>>>>>> d7599c5df10b932a0c1fc85b5b2e9708ca6a2471
