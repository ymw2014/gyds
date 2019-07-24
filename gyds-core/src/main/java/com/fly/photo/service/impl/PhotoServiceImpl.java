package com.fly.photo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.photo.dao.PhotoDao;
import com.fly.photo.domain.PhotoDO;
import com.fly.photo.service.PhotoService;



@Service
public class PhotoServiceImpl implements PhotoService {
	@Autowired
	private PhotoDao photoDao;
	
	@Override
	public PhotoDO get(Integer id){
		return photoDao.get(id);
	}
	
	@Override
	public List<PhotoDO> list(Map<String, Object> map){
		return photoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return photoDao.count(map);
	}
	
	@Override
	public int save(PhotoDO photo){
		return photoDao.save(photo);
	}
	
	@Override
	public int update(PhotoDO photo){
		return photoDao.update(photo);
	}
	
	@Override
	public int remove(Integer id){
		return photoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return photoDao.batchRemove(ids);
	}
	
}
