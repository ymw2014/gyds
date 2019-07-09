package com.fly.common.dao;

import com.fly.domain.FileDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 文件上传
 */
@Mapper
public interface FileDao {

	FileDO get(Long id);
	
	List<FileDO> list(Map <String, Object> map);

	int count(Map <String, Object> map);
	
	int save(FileDO file);
	
	int update(FileDO file);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    List<FileDO> getByFileNames(String[] fileName);
}
