package com.fly.async.task;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fly.domain.UserDO;
import com.fly.index.utils.OrderType;
import com.fly.order.dao.OrderDao;
import com.fly.order.domain.OrderDO;
import com.fly.proxybusi.dao.ProxybusiDao;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.system.dao.UserDao;
import com.fly.system.service.RegionService;
import com.fly.team.dao.TeamDao;
import com.fly.team.domain.TeamDO;
import com.fly.utils.MathUtils;

@Service
public class ThreadTaskService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private RegionService regionService;
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProxybusiDao proxybusiDao;
	
	@Autowired
	private OrderDao orderDao;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
	
		//团队分佣
		@Async("taskExecutor")
		public void sendMessage1(Integer regionCode) throws InterruptedException {
			logger.info("团队分佣开始+"+ regionCode);
			Thread.sleep(5000); // 模拟耗时
			logger.info("发送短信方法---- 1   执行结束");
		}
		/**
		 * 	团队分佣
		 * @param regionCode
		 * @param allPrice
		 */
		@Async("taskExecutor")
		public void roolTeamByElement(Integer regionCode,Integer orderType,Integer levelType, BigDecimal allPrice) {
			String remake="";
			TeamDO team = teamDao.get(regionCode);
			Map<String, Object> map = regionService.activeStat(team.getId(),5,10);
			BigDecimal teamCount=null;
			BigDecimal count=null;
			BigDecimal result5 = null;
			switch (levelType) {
			case 0://全国
				teamCount = MathUtils.getBigDecimal(map.get("teamCount"));//团队活跃人数
				count = MathUtils.getBigDecimal(map.get("allCount"));//全国活跃人数
				break;
			case 1://省代理
				teamCount = MathUtils.getBigDecimal(map.get("teamCount"));//团队活跃人数
				count = MathUtils.getBigDecimal(map.get("provCount"));//省活跃人数
				break;
			case 2://市代理
				teamCount = MathUtils.getBigDecimal(map.get("teamCount"));//团队活跃人数
				count = MathUtils.getBigDecimal(map.get("cityCount"));//市活跃人数
				break;
			case 3://县代理
				teamCount = MathUtils.getBigDecimal(map.get("teamCount"));//团队活跃人数
				count = MathUtils.getBigDecimal(map.get("distCount"));//县活跃人数
				break;
			case 4://街道办代理
				teamCount = MathUtils.getBigDecimal(map.get("teamCount"));//团队活跃人数
				count = MathUtils.getBigDecimal(map.get("roadCount"));//街道办活跃人数
				break;

			default:
				break;
			}
			result5 = teamCount.divide(count,2,BigDecimal.ROUND_HALF_UP);//本团队在本街道办活跃人数的占比
			BigDecimal fanyong = allPrice.multiply(result5);
			UserDO user = userDao.get(team.getUserId());
			user.setAccount(user.getAccount().add(fanyong));
			userDao.update(user);
			remake=getRemake(team.getUserId(),fanyong,orderType,OrderType.SHOU_RU,OrderType.CommissionType.TUAN_DUI_FEN_YONG);
			createOrder(team.getUserId(), fanyong, orderType, OrderType.SHOU_RU, remake);
			
		}
		
		/**
		 * 	代理返佣
		 * @param regionCode
		 * @param price
		 */
		@Async("taskExecutor")
		public void agencyDomestic(Integer regionCode,Integer type,BigDecimal fanyongPrice) {
			Map<String, Object> params=new HashMap<String, Object>(16);
			params.put("proxyRegion", regionCode);
			String remake="";
			List<ProxybusiDO> proList = proxybusiDao.list(params);
			try {
				if(!CollectionUtils.isEmpty(proList)) {
					increaseMoney(proList.get(0).getUserId(),fanyongPrice);
					remake=getRemake(proList.get(0).getUserId(),fanyongPrice,type,OrderType.SHOU_RU,OrderType.CommissionType.DAI_LI_FEN_YONG);
					createOrder(proList.get(0).getUserId(), fanyongPrice, type, OrderType.SHOU_RU, remake);
				}else {
					logger.info("区域编号"+regionCode+"***************此地区暂无代理商,不生成分佣");
				}
			} catch (Exception e) {
				logger.info("文章资讯县代理分佣失败:用户编号"+proList.get(0).getUserId());
				e.printStackTrace();
			}
		}
		
		
		
		/**
		 * 	
		 * @param regionCode
		 * @param level 代理等级
		 * @param allPrice
		 */
		public void roolAreaByElement(Integer regionCode, Integer level, Integer type,BigDecimal allPrice) {
			Map<String, Object> params=new HashMap<String, Object>(16);
			params.put("proxyRegion", regionCode);
			Map<String, Object> map = regionService.activeStat(regionCode,level,10);
			BigDecimal areaCount=null;
			BigDecimal count=null;
			BigDecimal result5 = null;
			String remake="";
			/*param.put("allCount", allCount);
			param.put("provCount", provCount);
			param.put("cityCount", cityCount);
			param.put("distCount", distCount);
			param.put("roadCount", roadCount);
			param.put("teamCount", teamCount);*/
			switch (level) {
			case 1://省代理
				areaCount = MathUtils.getBigDecimal(map.get("provCount"));//团队活跃人数
				count = MathUtils.getBigDecimal(map.get("allCount"));//全国活跃人数
				break;
			case 2://市代理
				areaCount = MathUtils.getBigDecimal(map.get("cityCount"));//市活跃人数
				count = MathUtils.getBigDecimal(map.get("provCount"));//省活跃人数
				break;
			case 3://县代理
				areaCount = MathUtils.getBigDecimal(map.get("distCount"));//团队活跃人数
				count = MathUtils.getBigDecimal(map.get("cityCount"));//市活跃人数
				break;
			case 4://街道办
				areaCount = MathUtils.getBigDecimal(map.get("roadCount"));//街道办活跃总人数
				count = MathUtils.getBigDecimal(map.get("distCount"));//县活跃总人数
				break;
			default:
				break;
			}
			result5 = areaCount.divide(count,2,BigDecimal.ROUND_HALF_UP);//本团队在本街道办活跃人数的占比
			BigDecimal fanyong = allPrice.multiply(result5);
			List<ProxybusiDO> proList = proxybusiDao.list(params);
			try {
				if(!CollectionUtils.isEmpty(proList)) {
					UserDO user = userDao.get(proList.get(0).getUserId());
					user.setAccount(user.getAccount().add(fanyong));
					userDao.update(user);
					remake=getRemake(proList.get(0).getUserId(),fanyong,type,OrderType.SHOU_RU,OrderType.CommissionType.DAI_LI_FEN_YONG);
					createOrder(proList.get(0).getUserId(), fanyong, type, OrderType.SHOU_RU, remake);
				}else {
					logger.info("区域编号"+regionCode+"***************此地区暂无代理商,不生成分佣");
				}
			} catch (Exception e) {
				logger.info("文章资讯县代理分佣失败:用户编号"+proList.get(0).getUserId());
				e.printStackTrace();
			}
			
		}
		
		
		/**
		 *  	代理商在指定区域分佣方法(按照占比比例分佣)
		 * @param regionCode 区域编号(全国/省/市/县)
		 * @param type 返佣类型
		 * @param levelType	等级,用于判断和在哪个层级的区域获取占比
		 * @param allPrice 可分佣的总金额
		 */
		@Async("taskExecutor")
		public void roolAreaByRegion(Integer regionCode,Integer type,Integer levelType,Integer prentCodeType, BigDecimal allPrice) {
			Map<String, Object> params=new HashMap<String, Object>(16);
			params.put("proxyRegion", regionCode);
			Map<String, Object> map = regionService.activeStat(regionCode,5,10);
			BigDecimal areaCount=null;
			BigDecimal count=null;
			BigDecimal result5 = null;
			String remake="";
			switch (levelType) {
			case 0://全国
				areaCount = MathUtils.getBigDecimal(map.get("roadCount"));//团队活跃人数
				break;
			case 1://省代理
				areaCount = MathUtils.getBigDecimal(map.get("provCount"));//团队活跃人数
				break;
			case 2://市代理
				areaCount = MathUtils.getBigDecimal(map.get("cityCount"));//团队活跃人数
				break;
			case 3://县代理
				areaCount = MathUtils.getBigDecimal(map.get("distCount"));//团队活跃人数
				break;
			case 4://街道办代理
				areaCount = MathUtils.getBigDecimal(map.get("roadCount"));//团队活跃人数
				break;

			default:
				break;
			}
			
			switch (prentCodeType) {
			case 0:
				count = MathUtils.getBigDecimal(map.get("allCount"));//全国活跃人数
				break;
			case 1:
				count = MathUtils.getBigDecimal(map.get("provCount"));//团队活跃人数		
				break;
			case 2:
				count = MathUtils.getBigDecimal(map.get("provCount"));//团队活跃人数
				break;
			case 3:
				count = MathUtils.getBigDecimal(map.get("roadCount"));//团队活跃人数
				break;

			default:
				break;
			}
			List<ProxybusiDO> proList = proxybusiDao.list(params);
			result5 = areaCount.divide(count,2,BigDecimal.ROUND_HALF_UP);//本团队在本街道办活跃人数的占比
			BigDecimal fanyong = allPrice.multiply(result5);
			try {
				if(!CollectionUtils.isEmpty(proList)) {
					UserDO user = userDao.get(proList.get(0).getUserId());
					user.setAccount(user.getAccount().add(fanyong));
					userDao.update(user);
					remake=getRemake(proList.get(0).getUserId(),fanyong,type,OrderType.SHOU_RU,OrderType.CommissionType.DAI_LI_FEN_YONG);
					createOrder(proList.get(0).getUserId(), fanyong, type, OrderType.SHOU_RU, remake);
				}else {
					logger.info("区域编号"+regionCode+"***************此地区暂无代理商,不生成分佣");
				}
			} catch (Exception e) {
				logger.info("文章资讯县代理分佣失败:用户编号"+proList.get(0).getUserId());
				e.printStackTrace();
			}
			
		}
		
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
		public String getRemake(Long userId,BigDecimal price,Integer expIncType,Integer orderType,String infomation) {
			UserDO user = userDao.get(userId);
			StringBuffer remake=new StringBuffer();
			if(orderType.equals(OrderType.SHOU_RU)) {
				remake.append("交易类型：<font style='color:green'>收入【"+infomation+"】</font>\n");
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
			remake.append("本次交易金额<font style='color:red'>"+price+"</font>元\n");
			remake.append("订单创建时间:"+sdf.format(new Date())+"\n");
			return remake.toString();
		}
		

}

