package com.fly.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.news.dao.CommentDao;
import com.fly.news.domain.CommentDO;
import com.fly.news.service.CommentService;



@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
	
	@Override
	public CommentDO get(Integer id){
		return commentDao.get(id);
	}
	
	@Override
	public List<CommentDO> list(Map<String, Object> map){
		return commentDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return commentDao.count(map);
	}
	
	@Override
	public int save(CommentDO comment){
		return commentDao.save(comment);
	}
	
	@Override
	public int update(CommentDO comment){
		return commentDao.update(comment);
	}
	
	@Override
	public int remove(Integer id){
		return commentDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return commentDao.batchRemove(ids);
	}
	
}
