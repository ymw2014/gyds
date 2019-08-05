<<<<<<< HEAD
package com.fly.volunteer.service;

import com.fly.volunteer.domain.VolunteerDO;
import java.util.List;
import java.util.Map;

/**
 * 志愿者表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-09 15:08:21
 */
public interface VolunteerService {
	
	VolunteerDO get(Integer id);
	
	List<VolunteerDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(VolunteerDO volunteer);
	
	int update(VolunteerDO volunteer);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	List<VolunteerDO> isVolllist(Long userId);
	
	/**
	 * 	通过用户编号查询是否为志愿者
	 * @param userId
	 * @return
	 */
	boolean isVo(Long userId);
	
	VolunteerDO getVo(Long userId);
}
=======
package com.fly.volunteer.service;

import com.fly.volunteer.domain.VolunteerDO;
import java.util.List;
import java.util.Map;

/**
 * 志愿者表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-09 15:08:21
 */
public interface VolunteerService {
	
	VolunteerDO get(Integer id);
	
	List<VolunteerDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(VolunteerDO volunteer);
	
	int update(VolunteerDO volunteer);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	List<VolunteerDO> isVolllist(Long userId);
	
	/**
	 * 	通过用户编号查询是否为志愿者
	 * @param userId
	 * @return
	 */
	boolean isVo(Long userId);
	
	VolunteerDO getVo(Long userId);
}
>>>>>>> d7599c5df10b932a0c1fc85b5b2e9708ca6a2471
