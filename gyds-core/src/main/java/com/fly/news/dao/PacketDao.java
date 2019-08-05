package com.fly.news.dao;

import com.fly.news.domain.PacketDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 红包主表
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-05 11:56:18
 */
@Mapper
public interface PacketDao {

	PacketDO get(Integer id);
	
	List<PacketDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(PacketDO packet);
	
	int update(PacketDO packet);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
