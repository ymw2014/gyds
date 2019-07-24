package com.fly.photo.service;

import com.fly.photo.domain.PhotoDO;

import java.util.List;
import java.util.Map;

/**
 * 相册表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-24 14:00:11
 */
public interface PhotoService {
	
	PhotoDO get(Integer id);
	
	List<PhotoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PhotoDO photo);
	
	int update(PhotoDO photo);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
