<<<<<<< HEAD
package com.fly.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.member.dao.MemberDao;
import com.fly.member.domain.MemberDO;
import com.fly.member.service.MemberService;



@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public MemberDO get(Long id){
		return memberDao.get(id);
	}
	
	@Override
	public List<MemberDO> list(Map<String, Object> map){
		return memberDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return memberDao.count(map);
	}
	
	@Override
	public int save(MemberDO member){
		return memberDao.save(member);
	}
	
	@Override
	public int update(MemberDO member){
		return memberDao.update(member);
	}
	
	@Override
	public int remove(Long id){
		return memberDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return memberDao.batchRemove(ids);
	}
	
}
=======
package com.fly.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.member.dao.MemberDao;
import com.fly.member.domain.MemberDO;
import com.fly.member.service.MemberService;



@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public MemberDO get(Long id){
		return memberDao.get(id);
	}
	
	@Override
	public List<MemberDO> list(Map<String, Object> map){
		return memberDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return memberDao.count(map);
	}
	
	@Override
	public int save(MemberDO member){
		return memberDao.save(member);
	}
	
	@Override
	public int update(MemberDO member){
		return memberDao.update(member);
	}
	
	@Override
	public int remove(Long id){
		return memberDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return memberDao.batchRemove(ids);
	}
	
}
>>>>>>> d7599c5df10b932a0c1fc85b5b2e9708ca6a2471
