package com.fly.common.service;

import java.util.List;
import java.util.Map;

import com.fly.domain.FileDO;

/**
 * 文件上传
 * 
 * @aut
 * @email
 * @date
 */
public interface FileService {
	
	FileDO get(Long id);
	
	List<FileDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(FileDO sysFile);

	int update(FileDO sysFile);

	int remove(Long id);

	/**
	 * 根据文件名称删除
	 * @param fileName
	 * @return
	 */
	List<FileDO> getByFileNames(String[] fileName);
	
	int batchRemove(Long[] ids);

	/**
	 * 判断一个文件是否存在
	 * @param url FileDO中存的路径
	 * @return
	 */
    Boolean isExist(String url);
}
