package com.fly.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fly.news.dao.PacketDao;
import com.fly.news.domain.PacketDO;
import com.fly.news.service.PacketService;



@Service
public class PacketServiceImpl implements PacketService {
	@Autowired
	private PacketDao packetDao;
	
	@Override
	public PacketDO get(Integer id){
		return packetDao.get(id);
	}
	
	@Override
	public List<PacketDO> list(Map<String, Object> map){
		return packetDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return packetDao.count(map);
	}
	
	@Override
	public int save(PacketDO packet){
		return packetDao.save(packet);
	}
	
	@Override
	public int update(PacketDO packet){
		return packetDao.update(packet);
	}
	
	@Override
	public int remove(Integer id){
		return packetDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return packetDao.batchRemove(ids);
	}
	
}
