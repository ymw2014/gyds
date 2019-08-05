package com.fly.volunteer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.volunteer.dao.VolunteerDao;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;



@Service
public class VolunteerServiceImpl implements VolunteerService {
	@Autowired
	private VolunteerDao volunteerDao;
	
	@Override
	public VolunteerDO get(Integer id){
		return volunteerDao.get(id);
	}
	
	@Override
	public List<VolunteerDO> list(Map<String, Object> map){
		return volunteerDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return volunteerDao.count(map);
	}
	
	@Override
	public int save(VolunteerDO volunteer){
		return volunteerDao.save(volunteer);
	}
	
	@Override
	public int update(VolunteerDO volunteer){
		return volunteerDao.update(volunteer);
	}
	
	@Override
	public int remove(Long id){
		return volunteerDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return volunteerDao.batchRemove(ids);
	}

	@Override
	public List<VolunteerDO> isVolllist(Long userId) {
		return volunteerDao.isVolllist(userId);
	}
	
	@Override
	public boolean isVo(Long userId) {
		boolean flag=false;
		flag=volunteerDao.isVo(userId).size()>0;
		return flag;
	}

	@Override
	public VolunteerDO getVo(Long userId) {
		if(volunteerDao.isVo(userId).size()>0) {
			return volunteerDao.isVo(userId).get(0);
		}
		return null;
	}
	
}
