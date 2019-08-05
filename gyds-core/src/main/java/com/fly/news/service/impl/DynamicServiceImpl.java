<<<<<<< HEAD
package com.fly.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.news.dao.DynamicDao;
import com.fly.news.domain.DynamicDO;
import com.fly.news.service.DynamicService;



@Service
public class DynamicServiceImpl implements DynamicService {
	@Autowired
	private DynamicDao dynamicDao;
	
	@Override
	public DynamicDO get(Integer id){
		return dynamicDao.get(id);
	}
	
	@Override
	public List<DynamicDO> list(Map<String, Object> map){
		return dynamicDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dynamicDao.count(map);
	}
	
	@Override
	public int save(DynamicDO dynamic){
		return dynamicDao.save(dynamic);
	}
	
	@Override
	public int update(DynamicDO dynamic){
		return dynamicDao.update(dynamic);
	}
	
	@Override
	public int remove(Integer id){
		return dynamicDao.remove(id);
	}
	@Override
	public int removeCall(DynamicDO dynamic){
		return dynamicDao.removeCall(dynamic);
	}
	@Override
	public int batchRemove(Integer[] ids){
		return dynamicDao.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> dyTeamList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dynamicDao.dyTeamList(params);
	}

	@Override
	public List<Map<String, Object>> dyActList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dynamicDao.dyActList(params);
	}

	@Override
	public List<Map<String, Object>> dyNewList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dynamicDao.dyNewList(params);
	}

	@Override
	public List<Map<String, Object>> dyVoluList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dynamicDao.dyVoluList(params);
	}
	
}
=======
package com.fly.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.news.dao.DynamicDao;
import com.fly.news.domain.DynamicDO;
import com.fly.news.service.DynamicService;



@Service
public class DynamicServiceImpl implements DynamicService {
	@Autowired
	private DynamicDao dynamicDao;
	
	@Override
	public DynamicDO get(Integer id){
		return dynamicDao.get(id);
	}
	
	@Override
	public List<DynamicDO> list(Map<String, Object> map){
		return dynamicDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return dynamicDao.count(map);
	}
	
	@Override
	public int save(DynamicDO dynamic){
		return dynamicDao.save(dynamic);
	}
	
	@Override
	public int update(DynamicDO dynamic){
		return dynamicDao.update(dynamic);
	}
	
	@Override
	public int remove(Integer id){
		return dynamicDao.remove(id);
	}
	@Override
	public int removeCall(DynamicDO dynamic){
		return dynamicDao.removeCall(dynamic);
	}
	@Override
	public int batchRemove(Integer[] ids){
		return dynamicDao.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> dyTeamList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dynamicDao.dyTeamList(params);
	}

	@Override
	public List<Map<String, Object>> dyActList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dynamicDao.dyActList(params);
	}

	@Override
	public List<Map<String, Object>> dyNewList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dynamicDao.dyNewList(params);
	}

	@Override
	public List<Map<String, Object>> dyVoluList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dynamicDao.dyVoluList(params);
	}
	
}
>>>>>>> d7599c5df10b932a0c1fc85b5b2e9708ca6a2471
