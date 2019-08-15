/*
package com.fly.async.task;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fly.system.dao.RegionDao;
import com.fly.system.service.impl.RegionServiceImpl;
import com.fly.team.dao.TeamDao;
import com.fly.team.domain.TeamDO;

@Service
public class ThreadTaskService extends RegionServiceImpl{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired 
	private RegionDao regionDao;
	@Autowired
	private TeamDao teamDao;
	
		//团队分佣
		@Async("taskExecutor")
		public void sendMessage1(Integer regionCode) throws InterruptedException {
			logger.info("团队分佣开始+"+ regionCode);
			Thread.sleep(5000); // 模拟耗时
			logger.info("发送短信方法---- 1   执行结束");
		}
		@Async("taskExecutor")
		public void roolTeamByElement(Integer regionCode,BigDecimal allPrice) {
			TeamDO team = teamDao.get(regionCode);
			Map<String, Object> map = activeStat(team.getId(),5,10);
			
			
		}


}
*/
