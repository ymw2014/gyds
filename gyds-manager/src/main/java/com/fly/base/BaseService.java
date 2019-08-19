package com.fly.base;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fly.async.task.ThreadTaskService;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.index.utils.OrderType;
import com.fly.news.dao.InfoDao;
import com.fly.news.domain.InfoDO;
import com.fly.sys.dao.SetupDao;
import com.fly.sys.domain.SetupDO;
import com.fly.system.dao.UserDao;
import com.fly.system.service.RegionService;
import com.fly.team.dao.TeamDao;
import com.fly.team.domain.TeamDO;
import com.fly.utils.Dictionary;

public class BaseService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired 
	private UserDao userDao;
	@Autowired 
	private InfoDao infoDao;
	@Autowired
	private SetupDao setupDao;
	@Autowired
	private TeamDao teamDao;
	@Autowired 
	private RegionService regionService;
	@Autowired
	private ThreadTaskService threadTaskService;
	
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
	public void distributionOfDomestic(Integer expIncType,BigDecimal price,Integer newId) {
		logger.info("文章资讯生成分佣开始****************************************************************************");
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
		if(team!=null&&!expIncType.equals(OrderType.JIAN_TUAN_FAN_YONG)) {//团队分佣(创建团队团队不做分佣)
			try {
				BigDecimal fanyong=price.multiply(setup.getTeamExtract());
				increaseMoney(team.getUserId(),fanyong);
				remake=threadTaskService.getRemake(team.getUserId(),fanyong,expIncType,OrderType.SHOU_RU,OrderType.CommissionType.TUAN_DUI_FEN_YONG);
				threadTaskService.createOrder(team.getUserId(), fanyong, expIncType, OrderType.SHOU_RU, remake);
			} catch (Exception e) {
				logger.info("文章资讯团队分佣失败:用户编号"+team.getUserId());
				e.printStackTrace();
			}
			
		}
		RegionDO teamRegion = regionService.get(team.getId());//团队
		RegionDO agencyRegion = regionService.get(teamRegion.getParentRegionCode());//街道办
		BigDecimal agencyFanyong=price.multiply(setup.getAgencyExtract());
		threadTaskService.agencyDomestic(agencyRegion.getRegionCode(),expIncType, agencyFanyong);
		//获取街道办上级
		RegionDO areaRegion = regionService.get(agencyRegion.getParentRegionCode());//县级代理
		BigDecimal areaFanyong=price.multiply(setup.getAreaExtract());
		threadTaskService.agencyDomestic(areaRegion.getRegionCode(),expIncType, areaFanyong);
		//获取县代理上级
		RegionDO cityRegion = regionService.get(areaRegion.getParentRegionCode());//市级代理
		BigDecimal cityFanyong=price.multiply(setup.getCityExtract());
		threadTaskService.agencyDomestic(cityRegion.getRegionCode(),expIncType, cityFanyong);
		//获取市代理上级(省代理)
		RegionDO ProvincRegion = regionService.get(cityRegion.getParentRegionCode());//省级代理
		BigDecimal provincFanyong=price.multiply(setup.getProvinceExtract());
		threadTaskService.agencyDomestic(ProvincRegion.getRegionCode(),expIncType, provincFanyong);
				
		
		/**
		 * 平台分佣
		 */
		logger.info("文章资讯生成分佣结束****************************************************************************");
	}
	
	/**
	 * 	文章置顶金额分佣(广告分佣)
	 * @param price
	 * @param type 置顶分佣  广告分佣 
	 * @param newId
	 * @param topPos
	 */
	public void distributionOfDomesticTop(BigDecimal price,Integer type,Integer newId,Integer regionCode) {
		SetupDO setup = setupDao.list(new HashMap<String, Object>(16)).get(0);
		Map<String, Object> params=new HashMap<>(16);
		RegionDO region = regionService.get(regionCode);
		switch (region.getRegionLevel()) {
		case 0://置顶全国(向下所有节点分佣)
			//团队分佣
			params.put("pids", regionCode);
			List<Integer> teamList4 = regionService.getAllTeamByUserRole(params);
			BigDecimal allPrice4=price.multiply(setup.getTeamExtract());
			if(!CollectionUtils.isEmpty(teamList4)) {
				for (Integer teamId : teamList4) {
					//多线程分佣
					threadTaskService.roolTeamByElement(teamId,type,Dictionary.ZONG_PING_TAI,allPrice4);
				}
			}
			//街道办分佣
			BigDecimal agencFanyong4=price.multiply(setup.getAgencyExtract());//获取街道办代理可分佣总金额
			params.put("pids", regionCode);
			params.put("level", Dictionary.JIE_DAO_BAN);
			List<Integer> jiedaobanList4 = regionService.getAllReginByLevel(params);
			for (Integer regCode : jiedaobanList4) {
				threadTaskService.roolAreaByRegion(regCode,type,Dictionary.JIE_DAO_BAN,Dictionary.ZONG_PING_TAI,agencFanyong4);
			}
			//县分佣
			BigDecimal areaFenPrice=price.multiply(setup.getAreaExtract());//获取街道办代理可分佣总金额
			params.put("pids", regionCode);
			params.put("level", Dictionary.XIAN);
			List<Integer> xianList4 = regionService.getAllReginByLevel(params);
			for (Integer regCode : xianList4) {
				threadTaskService.roolAreaByRegion(regCode,type,Dictionary.XIAN,Dictionary.ZONG_PING_TAI,areaFenPrice);
			}
			
			//市分佣
			BigDecimal cityFenPrice=price.multiply(setup.getCityExtract());//获取街道办代理可分佣总金额
			params.put("pids", regionCode);
			params.put("level", Dictionary.SHI);
			List<Integer> shiList1 = regionService.getAllReginByLevel(params);
			for (Integer regCode : shiList1) {
				threadTaskService.roolAreaByRegion(regCode,type,Dictionary.SHI,Dictionary.ZONG_PING_TAI,cityFenPrice);
			}
			
			//省分佣
			BigDecimal proPriceAll=price.multiply(setup.getProvinceExtract());//获取街道办代理可分佣总金额
			params.put("pids", regionCode);
			params.put("level", Dictionary.SHI);
			List<Integer> proList = regionService.getAllReginByLevel(params);
			for (Integer regCode : proList) {
				threadTaskService.roolAreaByRegion(regCode,type,Dictionary.SHENG,Dictionary.ZONG_PING_TAI,proPriceAll);
			}
			break;
		case 1://置顶本省(向上正常分佣,向下各市代理分佣,各县代理分佣,各街道办分佣,各团分佣)
			//团队分佣
			params.put("pids", regionCode);
			List<Integer> teamList3 = regionService.getAllTeamByUserRole(params);
			BigDecimal allPrice3=price.multiply(setup.getTeamExtract());
			if(!CollectionUtils.isEmpty(teamList3)) {
				for (Integer teamId : teamList3) {
					//多线程分佣
					threadTaskService.roolTeamByElement(teamId,type,Dictionary.SHENG,allPrice3);
				}
			}
			//街道办分佣
			BigDecimal agencFanyong3=price.multiply(setup.getAgencyExtract());//获取街道办代理可分佣总金额
			params.put("pids", regionCode);
			params.put("level", Dictionary.JIE_DAO_BAN);
			List<Integer> jiedaobanList1 = regionService.getAllReginByLevel(params);
			for (Integer regCode : jiedaobanList1) {
				threadTaskService.roolAreaByRegion(regCode,type,Dictionary.JIE_DAO_BAN,Dictionary.SHENG,agencFanyong3);
			}
			
			//县分佣
			BigDecimal areaFen=price.multiply(setup.getAreaExtract());//获取街道办代理可分佣总金额
			params.put("pids", regionCode);
			params.put("level", Dictionary.XIAN);
			List<Integer> jiedaobanList3 = regionService.getAllReginByLevel(params);
			for (Integer regCode : jiedaobanList3) {
				threadTaskService.roolAreaByRegion(regCode,type,Dictionary.XIAN,Dictionary.SHENG,areaFen);
			}
			
			//市分佣
			BigDecimal cityFen=price.multiply(setup.getCityExtract());//获取街道办代理可分佣总金额
			params.put("pids", regionCode);
			params.put("level", Dictionary.SHI);
			List<Integer> shiList = regionService.getAllReginByLevel(params);
			for (Integer regCode : shiList) {
				threadTaskService.roolAreaByRegion(regCode,type,Dictionary.SHI,Dictionary.SHENG,cityFen);
			}
			//省代理返佣
			RegionDO ProvincRegion3 = regionService.get(regionCode);//省级代理
			BigDecimal provincFanyong3=price.multiply(setup.getProvinceExtract());
			threadTaskService.agencyDomestic(ProvincRegion3.getRegionCode(),type, provincFanyong3);
			
			
			break;
		case 2://置顶本市(向上正常分佣,向下各县代理分佣,各街道办分佣,各团分佣)
			//市下所有团队分佣
			params.put("pids", regionCode);
			List<Integer> teamList1 = regionService.getAllTeamByUserRole(params);
			BigDecimal allPrice1=price.multiply(setup.getTeamExtract());
			if(!CollectionUtils.isEmpty(teamList1)) {
				for (Integer teamId : teamList1) {
					//多线程分佣
					threadTaskService.roolTeamByElement(teamId,type,Dictionary.SHI,allPrice1);
				}
			}
			//街道办分佣
			BigDecimal agencFanyong2=price.multiply(setup.getAgencyExtract());//获取街道办代理可分佣总金额
			params.put("pids", regionCode);
			params.put("level", Dictionary.JIE_DAO_BAN);
			List<Integer> jiedaobanList = regionService.getAllReginByLevel(params);
			for (Integer regCode : jiedaobanList) {
				threadTaskService.roolAreaByRegion(regCode,type,Dictionary.JIE_DAO_BAN,Dictionary.SHI,agencFanyong2);
			}
			
			//获取该市代理下所有县代理进行返佣
			BigDecimal xianFanyong=price.multiply(setup.getAreaExtract());//获取县代理可分佣总金额
			findChildenRegion(regionCode,Dictionary.XIAN,xianFanyong);
			//市代理返佣
			RegionDO cityRegion2 = regionService.get(regionCode);//市级代理
			BigDecimal cityFanyong2=price.multiply(setup.getCityExtract());
			threadTaskService.agencyDomestic(cityRegion2.getRegionCode(),type, cityFanyong2);
			//省代理返佣
			RegionDO ProvincRegion2 = regionService.get(cityRegion2.getParentRegionCode());//省级代理
			BigDecimal provincFanyong2=price.multiply(setup.getProvinceExtract());
			threadTaskService.agencyDomestic(ProvincRegion2.getRegionCode(),type, provincFanyong2);
			
			break;
		case 3://置顶本县(向上正常分佣,向下各街道办分佣,各团分佣)
			//获取该县代理下所有团队进行返佣
			params.put("pids", regionCode);
			List<Integer> teamList = regionService.getAllTeamByUserRole(params);
			BigDecimal allPrice=price.multiply(setup.getTeamExtract());
			if(!CollectionUtils.isEmpty(teamList)) {
				for (Integer teamId : teamList) {
					//多线程分佣
					threadTaskService.roolTeamByElement(teamId,type,Dictionary.XIAN,allPrice);
				}
			}
			
			//获取该县代理下所有街道办代理进行返佣
			BigDecimal agencFanyong=price.multiply(setup.getAgencyExtract());//获取街道办代理可分佣总金额
			findChildenRegion(regionCode,Dictionary.JIE_DAO_BAN,agencFanyong);
			//县代理商分佣
			RegionDO areaRegion1 = regionService.get(regionCode);
			BigDecimal areaFanyong1=price.multiply(setup.getAreaExtract());
			threadTaskService.agencyDomestic(areaRegion1.getRegionCode(),OrderType.ZHI_DING_FAN_YONG, areaFanyong1);
			//市代理返佣
			RegionDO cityRegion1 = regionService.get(areaRegion1.getParentRegionCode());//市级代理
			BigDecimal cityFanyong1=price.multiply(setup.getCityExtract());
			threadTaskService.agencyDomestic(cityRegion1.getRegionCode(),OrderType.ZHI_DING_FAN_YONG, cityFanyong1);
			//省代理返佣
			RegionDO ProvincRegion1 = regionService.get(cityRegion1.getParentRegionCode());//省级代理
			BigDecimal provincFanyong1=price.multiply(setup.getProvinceExtract());
			threadTaskService.agencyDomestic(ProvincRegion1.getRegionCode(),OrderType.ZHI_DING_FAN_YONG, provincFanyong1);
			
			break;
		case 4://置顶街道办(向上正常分佣,向下各团分佣)
			BigDecimal teamAllPrice=price.multiply(setup.getTeamExtract());
			//向下各团队分佣
			agencyFindTeamDomestic(regionCode,teamAllPrice,type);
			//街道办分佣
			BigDecimal agencyFanyong=price.multiply(setup.getAgencyExtract());
			threadTaskService.agencyDomestic(regionCode,OrderType.ZHI_DING_FAN_YONG, agencyFanyong);
			//县代理分佣
			RegionDO areaRegion = regionService.get(region.getParentRegionCode());
			BigDecimal areaFanyong=price.multiply(setup.getAreaExtract());
			threadTaskService.agencyDomestic(areaRegion.getRegionCode(),OrderType.ZHI_DING_FAN_YONG, areaFanyong);
			
			//市代理返佣
			RegionDO cityRegion = regionService.get(areaRegion.getParentRegionCode());//市级代理
			BigDecimal cityFanyong=price.multiply(setup.getCityExtract());
			threadTaskService.agencyDomestic(cityRegion.getRegionCode(),OrderType.ZHI_DING_FAN_YONG, cityFanyong);
			//省代理返佣
			RegionDO ProvincRegion = regionService.get(cityRegion.getParentRegionCode());//省级代理
			BigDecimal provincFanyong=price.multiply(setup.getProvinceExtract());
			threadTaskService.agencyDomestic(ProvincRegion.getRegionCode(),OrderType.ZHI_DING_FAN_YONG, provincFanyong);
			//平台返佣
			
			
			break;
		case 5://置顶本团(向上正常分佣)
			distributionOfDomestic(OrderType.ZHI_DING_FAN_YONG,price,newId);
			break;	
			

		default:
			break;
		}

	}
	
	/**
	 * 	通过区域编号获取下级代理并返佣
	 * @param regionCode 区域编号
	 * @param allPrice 需要瓜分的返佣金额
	 */
	public void findChildenRegion(Integer regionCode,Integer level,BigDecimal allPrice) {
		Map<String, Object> params=new HashMap<>();
		params.put("parentRegionCode", regionCode);
		List<RegionDO> regionList = regionService.list(params);
		if(!CollectionUtils.isEmpty(regionList)) {
			for (RegionDO regionDO : regionList) {
				//多线程分佣
				threadTaskService.roolAreaByElement(regionDO.getRegionCode(),level,OrderType.ZHI_DING_FAN_YONG,allPrice);
			}
		}
		
	}
	
	
	/**
	 * 	获取本街道办下所有团队
	 * @param regionCode
	 * @param allPrice
	 */
	public void agencyFindTeamDomestic(Integer regionCode,BigDecimal allPrice,Integer orderType) {
		Map<String, Object> params=new HashMap<>();
		params.put("parentRegionCode", regionCode);
		List<RegionDO> teamList = regionService.list(params);
		if(!CollectionUtils.isEmpty(teamList)) {
			for (RegionDO regionDO : teamList) {
				TeamDO team = teamDao.get(regionDO.getRegionCode());
				regionService.activeStat(team.getId(), 5, 10);
				//多线程分佣
				threadTaskService.roolTeamByElement(regionDO.getRegionCode(),orderType,Dictionary.JIE_DAO_BAN,allPrice);

			}
		}
	}
	
	
	

}
