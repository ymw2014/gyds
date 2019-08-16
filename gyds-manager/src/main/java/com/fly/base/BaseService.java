package com.fly.base;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//import com.fly.async.task.ThreadTaskService;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.index.utils.OrderType;
import com.fly.news.dao.InfoDao;
import com.fly.news.domain.InfoDO;
import com.fly.order.dao.OrderDao;
import com.fly.order.domain.OrderDO;
import com.fly.proxybusi.dao.ProxybusiDao;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.sys.dao.SetupDao;
import com.fly.sys.domain.SetupDO;
import com.fly.system.dao.UserDao;
import com.fly.system.service.RegionService;
import com.fly.team.dao.TeamDao;
import com.fly.team.domain.TeamDO;

public class BaseService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired 
	private UserDao userDao;
	@Autowired 
	private InfoDao infoDao;
	@Autowired
	private SetupDao setupDao;
	@Autowired
	private ProxybusiDao proxybusiDao;
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired 
	private RegionService regionService;
	//@Autowired
	//private ThreadTaskService threadTaskService;
	
	/**
	   *       更新账户余额
	 * @param userId 用户编号
	 * @param price  账户需增加的金额
	 * @return 
	 */
	public synchronized boolean increaseMoney(Long userId,BigDecimal price) {
		UserDO user = userDao.get(userId);
		user.setAccount(user.getAccount().add(price));
		if(userDao.update(user)>0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 	文章资讯红包分佣调用
	 * 	文章资讯打赏金额分佣调用
	 * 	团队创建分佣调用
	 * @param type 分佣类型（红包,打赏）
	 * @param price 参与分佣的金额
	 * @param newId 资讯ID
	 */
	public void DistributionOfDomestic(Integer type,BigDecimal price,Integer newId) {
		logger.info("文章资讯生成分佣开始****************************************************************************");
		Map<String, Object> params=new HashMap<>(16);
		InfoDO info = infoDao.get(newId);
		SetupDO setup = setupDao.list(new HashMap<String, Object>(16)).get(0);
		setup.getCityExtract();//市代理分佣比例
		setup.getAreaExtract();//县代理分佣比例
		setup.getHeadExtract();//平台分佣比例
		setup.getProvinceExtract();//省分佣比例
		setup.getTeamExtract();//团队分佣比例
		setup.getAgencyExtract();//街道办分佣比例
		String remake="";
		TeamDO team = teamDao.get(info.getTeamId());
		if(team!=null&&!type.equals(OrderType.JIAN_TUAN_FAN_YONG)) {//团队分佣(创建团队团队不做分佣)
			try {
				BigDecimal fanyong=price.multiply(setup.getTeamExtract());
				increaseMoney(team.getUserId(),fanyong);
				remake=getRemake(team.getUserId(),fanyong,type,OrderType.SHOU_RU);
				createOrder(team.getUserId(), price, type, OrderType.SHOU_RU, remake);
			} catch (Exception e) {
				logger.info("文章资讯团队分佣失败:用户编号"+team.getUserId());
				e.printStackTrace();
			}
			
		}
		RegionDO agencyRegion = regionService.get(team.getId());//街道办
		params.put("proxyRegion", agencyRegion.getRegionCode());
		List<ProxybusiDO> proList = proxybusiDao.list(params);
		if(!CollectionUtils.isEmpty(proList)) {//街道办分佣
			try {
				BigDecimal fanyong=price.multiply(setup.getAgencyExtract());
				increaseMoney(proList.get(0).getUserId(),fanyong);
				remake=getRemake(proList.get(0).getUserId(),fanyong,type,OrderType.SHOU_RU);
				createOrder(proList.get(0).getUserId(), price, type, OrderType.SHOU_RU, remake);
			} catch (Exception e) {
				logger.info("文章资讯街道办分佣失败:用户编号"+proList.get(0).getUserId());
				e.printStackTrace();
			}
			
		}
		//获取街道办上级
		RegionDO areaRegion = regionService.get(agencyRegion.getParentRegionCode());//县级代理
		params.put("proxyRegion", areaRegion.getRegionCode());
		proList = proxybusiDao.list(params);
		if(!CollectionUtils.isEmpty(proList)) {//县级分佣
			try {
				BigDecimal fanyong=price.multiply(setup.getAreaExtract());
				increaseMoney(proList.get(0).getUserId(),fanyong);
				remake=getRemake(proList.get(0).getUserId(),fanyong,type,OrderType.SHOU_RU);
				createOrder(proList.get(0).getUserId(), price, type, OrderType.SHOU_RU, remake);
			} catch (Exception e) {
				logger.info("文章资讯县代理分佣失败:用户编号"+proList.get(0).getUserId());
				e.printStackTrace();
			}
			
		}
		//获取县代理上级
		RegionDO cityRegion = regionService.get(areaRegion.getParentRegionCode());//市级代理
		params.put("proxyRegion", areaRegion.getRegionCode());
		proList = proxybusiDao.list(params);
		if(!CollectionUtils.isEmpty(proList)) {//市级分佣
			try {
				BigDecimal fanyong=price.multiply(setup.getCityExtract());
				increaseMoney(proList.get(0).getUserId(),fanyong);
				remake=getRemake(proList.get(0).getUserId(),fanyong,type,OrderType.SHOU_RU);
				createOrder(proList.get(0).getUserId(), price, type, OrderType.SHOU_RU, remake);
			} catch (Exception e) {
				logger.info("文章资讯市代理分佣失败:用户编号"+proList.get(0).getUserId());
				e.printStackTrace();
			}
			
		}
		//获取市代理上级
		RegionDO ProvincRegion = regionService.get(cityRegion.getParentRegionCode());//省级代理
		params.put("proxyRegion", ProvincRegion.getRegionCode());
		proList = proxybusiDao.list(params);
		if(!CollectionUtils.isEmpty(proList)) {//省级分佣
			try {
				BigDecimal fanyong=price.multiply(setup.getProvinceExtract());
				increaseMoney(proList.get(0).getUserId(),fanyong);
				remake=getRemake(proList.get(0).getUserId(),fanyong,type,OrderType.SHOU_RU);
				createOrder(proList.get(0).getUserId(), price, type, OrderType.SHOU_RU, remake);
			} catch (Exception e) {
				logger.info("文章资讯省代理分佣失败:用户编号"+proList.get(0).getUserId());
				e.printStackTrace();
			}
		}
		
		/**
		 * 平台分佣
		 */
		logger.info("文章资讯生成分佣结束****************************************************************************");
	}
	
	/**
	 * 	文章置顶金额分佣
	 * @param price
	 * @param newId
	 * @param topPos
	 */
	public void DistributionOfDomesticTop(BigDecimal price,Integer newId,Integer regionCode) {
		SetupDO setup = setupDao.list(new HashMap<String, Object>(16)).get(0);
		RegionDO region = regionService.get(regionCode);
		switch (region.getRegionLevel()) {
		case 0://置顶全国(向下所有节点分佣)
			
			break;
		case 1://置顶本省(向上正常分佣,向下各市代理分佣,各县代理分佣,各街道办分佣,各团分佣)
			
			break;
		case 2://置顶本市(向上正常分佣,向下各县代理分佣,各街道办分佣,各团分佣)
			
			break;
		case 3://置顶本县(向上正常分佣,向下各街道办分佣,各团分佣)
			
			break;
		case 4://置顶街道办(向上正常分佣,向下各团分佣)
			BigDecimal allPrice=price.multiply(setup.getTeamExtract());
			agencyFindTeamDomestic(regionCode,allPrice);
			
			
			break;
		case 5://置顶本团(向上正常分佣)
			DistributionOfDomestic(OrderType.ZHI_DING_FAN_YONG,price,newId);
			break;	
			

		default:
			break;
		}

	}
	
	public void agencyFindTeamDomestic(Integer regionCode,BigDecimal allPrice) {
		Map<String, Object> params=new HashMap<>();
		params.put("parentRegionCode", regionCode);
		List<RegionDO> teamList = regionService.list(params);
		if(!CollectionUtils.isEmpty(teamList)) {
			for (RegionDO regionDO : teamList) {
				TeamDO team = teamDao.get(regionDO.getRegionCode());
				regionService.activeStat(team.getId(), 5, 10);
				//threadTaskService.roolTeamByElement(regionDO.getRegionCode(),allPrice);
			}
		}

	}
	
	
	/**
	 * 	创建订单 
	 * @param userId 用户编号
	 * @param price 订单金额
	 * @param expIncType 订单类型
	 * @param orderType	交易类型
	 * @param remake	订单描述
	 * @return 
	 */
	public boolean createOrder(Long userId,BigDecimal price,Integer expIncType,Integer orderType,String remake){
		OrderDO order=new OrderDO();
		order.setOrderNumber(new Date().getTime()+"");
		order.setUserId(userId);
		order.setBusinessTime(new Date());
		if(orderType.equals(OrderType.SHOU_RU)) {
			order.setOrderType(OrderType.SHOU_RU);
		}
		if(orderType.equals(OrderType.ZHI_CHU)) {
			order.setOrderType(OrderType.ZHI_CHU);
		}
		order.setIsDel(0);
		order.setExamineUser(0L);
		order.setExamineStatus(1);
		order.setRemake(remake);
		order.setLevel(1);
		switch (expIncType) {
		case 8:
			order.setExpIncType(OrderType.HONG_BAO_FAN_YONG);
			break;
		case 9:
			order.setExpIncType(OrderType.DA_SHANG_FAN_YONG);
			break;
		case 10:
			order.setExpIncType(OrderType.ZHI_DING_FAN_YONG);
			break;
		case 11:
			order.setExpIncType(OrderType.GUANG_GAO_FAN_YONG);
			break;	
		case 12:
			order.setExpIncType(OrderType.JIAN_TUAN_FAN_YONG);
			break;	
		default:
			break;
		}
		order.setPrice(price);
		order.setCreateTime(new Date());
		if(orderDao.save(order)>0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 	创建订单描述
	 * @param userId 用户编号
	 * @param price 订单金额
	 * @param expIncType 订单类型
	 * @param orderType 交易类型
	 * @return
	 */
	public String getRemake(Long userId,BigDecimal price,Integer expIncType,Integer orderType) {
		UserDO user = userDao.get(userId);
		StringBuffer remake=new StringBuffer();
		if(orderType.equals(OrderType.SHOU_RU)) {
			remake.append("交易类型：<font style='color:green'>收入</font>\n");
		}
		if(orderType.equals(OrderType.ZHI_CHU)) {
			remake.append("交易类型：<font style='color:red'>支出</font>\n");
		}
		switch (expIncType) {
		case 0:
			remake.append("本次操作：<font style='color:green'>体现</font>\n");
			break;
		case 1:
			remake.append("本次操作：<font style='color:green'>账户充值</font>\n");
			break;
		case 2:
			remake.append("本次操作：<font style='color:green'>新闻打赏</font>\n");
			break;
		case 3:
			remake.append("本次操作：<font style='color:green'>红包</font>\n");
			break;
		case 4:
			remake.append("本次操作：<font style='color:green'>广告位购买</font>\n");
			break;
		case 5:
			remake.append("本次操作：<font style='color:green'>文章置顶</font>\n");
			break;
		case 6:
			remake.append("本次操作：<font style='color:green'>代理商入驻</font>\n");
			break;
		case 7:
			remake.append("本次操作：<font style='color:green'>团队创建</font>\n");
			break;
		case 8:
			remake.append("本次操作：<font style='color:green'>红包返佣</font>\n");
			break;
		case 9:
			remake.append("本次操作：<font style='color:green'>打赏返佣</font>\n");
			break;	
		case 10:
			remake.append("本次操作：<font style='color:green'>置顶返佣</font>\n");
			break;
		case 11:
			remake.append("本次操作：<font style='color:green'>广告返佣</font>\n");
			break;
		case 12:
			remake.append("本次操作：<font style='color:green'>建团返佣</font>\n");
			break;
		default:
			break;
		}
		remake.append("用户编号【"+user.getUserId()+"】用户名称【"+user.getName()+"】\n");
		remake.append("用户账户余额:<font style='color:green'>"+user.getAccount()+"</font>元\n");
		remake.append("本次交易金额<font style='color:red'>"+price+"</font>元");
		remake.append("订单创建时间:"+new Date()+"\n");
		return remake.toString();
	}

}
