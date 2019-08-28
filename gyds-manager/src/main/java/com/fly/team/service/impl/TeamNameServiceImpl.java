package com.fly.team.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.fly.base.BaseService;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.order.dao.OrderDao;
import com.fly.order.domain.OrderDO;
import com.fly.proxybusi.dao.ProxybusiDao;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.system.dao.RegionDao;
import com.fly.system.dao.UserDao;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.dao.TeamDao;
import com.fly.team.domain.TeamDO;
import com.fly.team.service.TeamNameService;
import com.fly.utils.JSONUtils;
import com.fly.utils.userToObject;
import com.fly.verifyName.dao.NameDao;
import com.fly.verifyName.domain.NameDO;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;

@Service
public class TeamNameServiceImpl extends BaseService implements TeamNameService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserDao userDao;
	@Autowired
	private NameDao nameDao;
	@Autowired
	private RegionDao regionDao;
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private VolunteerService volunteerService;
	
	@Autowired
	private ProxybusiDao proxybusiDao;
	@Autowired
	private OrderDao orderDao;

	@Override
	@Transactional
	public int createTeamExamine(Integer id, Integer status) {
		int count=0;
		NameDO name = nameDao.get(id);
		name.setStatus(status);
		OrderDO order=null;
		if(name.getOrderId()!=null) {
			order = orderDao.get(name.getOrderId());
		}
		boolean isVo =volunteerService.isVo(name.getUserId());
		if(status==2) {//审核通过,保存团队信息
			name.getText();
			TeamDO team=(TeamDO)JSONUtils.jsonToBean(name.getText(), new TeamDO());
			team.setId(randomCode(team.getRegCode()));
			team.setUserId(name.getUserId());
			team.setColonelName(name.getName());
			team.setStatus(1);
			teamDao.save(team);
			RegionDO region=new RegionDO();
			region.setRegionCode(team.getId());
			region.setParentRegionCode(team.getRegCode());
			region.setRegionName(team.getTeamName());
			region.setRegionType(2);
			region.setRegionLevel(5);
			regionDao.save(region);
			UserDO user = userDao.get(name.getUserId());
			if(user.getIsIdentification()==null||user.getIsIdentification()!= 1) {//未实名认证,保存实名认证信息
				user=userToObject.isIdentification(user,name);
				user.setIsManage(1);
				user.setDeptId(team.getId());
				userDao.update(user);
			}
			if(isVo) {//若已经是志愿者,修改志愿者团队编号
				VolunteerDO vo = volunteerService.getVo(user.getUserId());
				vo.setTeamId(team.getId());//将志愿者设置为本团团员
				volunteerService.update(vo);
			}else {//非志愿者重新创建志愿者对象
				VolunteerDO vo=new VolunteerDO();
				vo.setUserId(name.getUserId());
				vo.setTeamId(team.getId());
				vo.setVolunteerNumber(getVoNumber(name.getCardNo()));
				vo.setSharesNumber(0);//转发
				vo.setClickNumber(0);//点击
				vo.setCommentNumber(0);//评论
				vo.setIntegral(0);//积分
				vo.setActNumber(0);
				vo.setCreateTime(new Date());
				volunteerService.save(vo);
			}
			if(order!=null) {
				order.setExamineStatus(2);
				order.setExamineUser(ShiroUtils.getUserId());
				orderDao.update(order);
			}
		}
		if(status==3) {//拒绝申请
			if(name.getOrderId()!=null) {
				boolean flag = increaseMoney(name.getUserId(),order.getPrice());//资金回滚入用户账户
				if(flag) {
					order.setExamineStatus(3);
					order.setExamineUser(ShiroUtils.getUserId());
					orderDao.update(order);
				}else {
					logger.info("团队创建审核失败,失败原因:用户编号:"+name.getUserId()+"资金回滚失败,手动事务处理");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
			}
		}
		count=nameDao.update(name);
		return count;
	}
	

	@Override
	@Transactional
	public int userIntoTeamExamine(Integer id, Integer status) {
		int count=0;
		NameDO name = nameDao.get(id);
		UserDO user = userDao.get(name.getUserId());
		name.setStatus(status);
		boolean flag=volunteerService.isVo(user.getUserId());
		if(status==2) {//审核通过,若用户还不是实名认证,保存实名认证信息
			if(user.getIsIdentification()==null||user.getIsIdentification()!=1) {
				user=userToObject.isIdentification(user,name);
				userDao.update(user);
				if(flag) {//若已经是志愿者,修改志愿者团队编号
					VolunteerDO vo = volunteerService.getVo(user.getUserId());
					vo.setTeamId(name.getTeamId());//将志愿者设置为本团团员
					volunteerService.update(vo);
				}else {//非志愿者重新创建志愿者对象
					VolunteerDO vo=new VolunteerDO();
					vo.setUserId(name.getUserId());
					vo.setTeamId(name.getTeamId());
					vo.setVolunteerNumber(getVoNumber(name.getCardNo()));
					vo.setSharesNumber(0);//转发
					vo.setClickNumber(0);//点击
					vo.setCommentNumber(0);//评论
					vo.setIntegral(0);//积分
					vo.setActNumber(0);
					vo.setCreateTime(new Date());
					volunteerService.save(vo);
				}
			}else {//已经实名认证
				if(flag) {
					VolunteerDO vo = volunteerService.getVo(user.getUserId());
					vo.setTeamId(name.getTeamId());//将志愿者设置为本团团员
					volunteerService.update(vo);
				}else {
					VolunteerDO vo=new VolunteerDO();
					vo.setUserId(name.getUserId());
					vo.setTeamId(name.getTeamId());
					vo.setVolunteerNumber(getVoNumber(name.getCardNo()));
					vo.setSharesNumber(0);//转发
					vo.setClickNumber(0);//点击
					vo.setCommentNumber(0);//评论
					vo.setIntegral(0);//积分
					vo.setActNumber(0);
					vo.setCreateTime(new Date());
					volunteerService.save(vo);
				}
			}
		}
		count=nameDao.update(name);
		return count;
	}
	

	/**
	 * 	代理商入驻审核
	 */
	@Override
	@Transactional
	public int createProxyBus(Integer id, Integer status) {
		int count=0;
		NameDO name = nameDao.get(id);
		UserDO user = userDao.get(name.getUserId());
		name.setStatus(status);
		if(status==2) {//审核通过,若用户还不是实名认证,保存实名认证信息
			user=userToObject.isIdentification(user,name);
			userDao.update(user);
			Map<String, Object> proxyMap = JSONUtils.jsonToMap(name.getText());
			ProxybusiDO proxy=(ProxybusiDO)JSONUtils.jsonToBean(name.getText(), new ProxybusiDO());
			proxy.setCreateTime(new Date());
			proxy.setUserId(user.getUserId());
			proxy.setLevel(Integer.parseInt(proxyMap.get("regionLevel").toString()));
			if(proxyMap.get("regionLevel")!=null&&proxyMap.get("regionLevel").toString().equals("1")) {
				proxy.setProxyRegion(Long.parseLong(proxyMap.get("pronvice").toString()));
			}
			if(proxyMap.get("regionLevel")!=null&&proxyMap.get("regionLevel").toString().equals("2")) {
				proxy.setProxyRegion(Long.parseLong(proxyMap.get("city").toString()));
			}
			if(proxyMap.get("regionLevel")!=null&&proxyMap.get("regionLevel").toString().equals("3")) {
				proxy.setProxyRegion(Long.parseLong(proxyMap.get("country").toString()));
			}
			if(proxyMap.get("regionLevel")!=null&&proxyMap.get("regionLevel").toString().equals("4")) {
				proxy.setProxyRegion(Long.parseLong(proxyMap.get("street").toString()));
			}
			if(user.getIsIdentification()==null||user.getIsIdentification()!=1) {
				user=userToObject.isIdentification(user,name);
			}
			proxybusiDao.save(proxy);
		}
		
		if(status==3) {//如果是收费,驳回需要把资金回滚
			OrderDO order = orderDao.get(name.getOrderId());
			boolean flag = increaseMoney(name.getUserId(),order.getPrice());//资金回滚入用户账户
			if(flag) {
				order.setExamineStatus(3);
				order.setExamineUser(ShiroUtils.getUserId());
				orderDao.update(order);
			}else {
				logger.info("团队创建审核失败,失败原因:用户编号:"+name.getUserId()+"资金回滚失败");
			}
		}
		count=nameDao.update(name);
		return count;
	}
	
	/**
	 * 	生成志愿者编号
	 * @param cardNo
	 * @return
	 */
	public String getVoNumber(String cardNo) {
		int radomInt2 =(int)((Math.random()*9+1)*100000);
		String num = cardNo.substring(0, 8)+radomInt2;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("volunteerNumber",num);
		List<VolunteerDO> voList = volunteerService.list(map);
		if(voList.size()>0) {
			getVoNumber(cardNo);
		}
		return num;
	}
	
	/**
	 * 生成团队编号
	 * @param regCode
	 * @return
	 */
	public Long randomCode(Long regCode) {
		Integer random =(int) (Math.random()*(999-100+1)+100);
		String strCode = regCode.toString().substring(0, regCode.toString().length());
		strCode = strCode+random;
		Long code = Long.parseLong(strCode) ;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",code);
		List<TeamDO> list = teamDao.list(map);
		if(list.size()>0) {
			randomCode(regCode);
		}
		return code;
	}

}
