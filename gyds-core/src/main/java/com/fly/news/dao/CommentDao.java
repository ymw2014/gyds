package com.fly.news.dao;

import com.fly.news.domain.CommentDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 咨询评论表
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-09 17:41:16
 */
@Mapper
public interface CommentDao {

	CommentDO get(Integer id);
	
	List<CommentDO> list(Map<String,Object> map);
	List<CommentDO> list1(Map<String,Object> map);
	int count(Map<String,Object> map);
	
	int save(CommentDO comment);
	
	int update(CommentDO comment);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
