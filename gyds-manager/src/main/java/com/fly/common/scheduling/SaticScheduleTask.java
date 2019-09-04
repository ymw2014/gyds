package com.fly.common.scheduling;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fly.activity.dao.ActivityDao;
import com.fly.activity.domain.ActivityDO;
import com.fly.adv.dao.AdvertisementDao;
import com.fly.adv.domain.AdvertisementDO;
import com.fly.news.dao.InfoDao;
import com.fly.news.domain.InfoDO;
import com.fly.team.dao.TeamDao;
import com.fly.team.domain.TeamDO;
import com.fly.utils.DateUtils;
import com.mysql.fabric.xmlrpc.base.Data;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class SaticScheduleTask {
	@Autowired
	private InfoDao infoDao;
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private AdvertisementDao advertisementDao;
	@Autowired
	private ActivityDao activityDao;
	
	 //3.添加定时任务
    @Scheduled(cron = "0 0 * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("isTop", "1");
        params.put("isDel", "0");
        params.put("status", "1");
        params.put("endTime", DateUtils.format(new Date(), "yyyy-MM-dd"));
        List<InfoDO> info = infoDao.listEndTop(params);
        for (InfoDO infoDO : info) {
        	infoDO.setIsTop(0);
        	infoDao.update(infoDO);
        	System.out.println("执行定时任务查询置顶到期时间");
		}
        params.clear();
        params.put("is_auth", "1");
        params.put("endTime", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        List<TeamDO> team = teamDao.listEndAuthe(params);
        for (TeamDO teamDO : team) {
        	teamDO.setIsAuth(1);
        	teamDao.update(teamDO);
        	System.out.println("执行定时任务查询团队认证到期时间");
		}
        params.clear();
        params.put("endTime", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        List<AdvertisementDO> AdvertisementDO = advertisementDao.listEndShow(params);
        for (AdvertisementDO advertisementDO2 : AdvertisementDO) {
        	advertisementDao.remove(advertisementDO2.getId());
        	System.out.println("执行定时任务查询广告到期时间");
		}
        params.clear();
        params.put("activiStartTime", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        params.put("examineStatus", "1");
        params.put("status", "1");
        List<ActivityDO> Activity = activityDao.ActivityTime(params);
        for (ActivityDO activityDO : Activity) {
        	activityDO.setStatus(2);
        	activityDao.update(activityDO);
        	System.out.println("执行定时任务查询活动时间");
		}
        params.clear();
        params.put("activiEndTime", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        params.put("examineStatus", "1");
        params.put("status", "2");
        Activity = activityDao.ActivityTime(params);
        for (ActivityDO activityDO : Activity) {
        	activityDO.setStatus(3);
        	activityDao.update(activityDO);
        	System.out.println("执行定时任务查询活动时间");
		}
    }
}
