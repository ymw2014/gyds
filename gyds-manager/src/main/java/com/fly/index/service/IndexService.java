package com.fly.index.service;

import java.util.List;
import java.util.Map;

import com.fly.adv.domain.AdvertisementDO;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.utils.R;

/**
 * 
 * @author 王彦克
 *
 */
public interface IndexService {
	
	/**
	 * 通过区域编号获取所选区域五个广告位信息(用于首页和资讯列表页)
	 * @param regionCode 区域编号
	 * @param position 页面
	 * @param params 
	 * @return
	 */
	List<AdvertisementDO>  getIndexAdvList(Long regionCode,Integer position,Map<String,Object> params);
	
	/**
	 * 获取网站底部帮助中心内容
	 * @return
	 */
	List<TypeTitleDO> getFooterCenter();
	
	/**
	 *	 获取非首页广告展示信息
	 * @param teamId 团队编号
	 * @param positionNum  加载内容广告的页面(3:资讯详情页4:活动报名页5:签到页6:志愿者详情页7:团队详情页)
	 * @return
	 */
	List<AdvertisementDO> getCenterAdvList(Long teamId,Integer positionNum); 
	
	

}
