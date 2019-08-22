package com.fly.index.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.adv.dao.AdvertisementDao;
import com.fly.adv.domain.AdvertisementDO;
import com.fly.domain.RegionDO;
import com.fly.helpCenter.dao.CenterDao;
import com.fly.helpCenter.dao.TypeTitleDao;
import com.fly.helpCenter.domain.CenterDO;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.index.service.IndexService;
import com.fly.system.dao.RegionDao;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.Dictionary;

@Service
public class IndexServiceImpl implements IndexService{
	
	@Autowired
	private AdvertisementDao advertisementDao;
	@Autowired
	private RegionDao regionDao;
	@Autowired
	private TypeTitleDao typeTitleDao;
	@Autowired
	private CenterDao centerDao;

	@Override
	public List<AdvertisementDO> getIndexAdvList(Integer areaId,Integer positionNum,Map<String,Object> params) {
		List<AdvertisementDO> dataList=new ArrayList<AdvertisementDO>();
		List<AdvertisementDO> list=new ArrayList<AdvertisementDO>();
		params.put("positionNum", positionNum);
		if(areaId!=null) {
			RegionDO region = regionDao.get(areaId);
			params.put("positionNum", positionNum);
			params.put("regionCode", region.getRegionCode());//所选择区域首页广告
    		List<AdvertisementDO> allList = advertisementDao.list(params);
    		Integer size=allList.size();
			switch(region.getRegionLevel()){  
		    case 0:  //全国
	    		if(allList.size()>=6) {
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (6-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				adv.setUrl("/pc/advShow?regionCode="+region.getRegionCode()+"&sort="+(size+i)+"&position="+Dictionary.AdvPosition.SHOUYE);
	    				allList.add(adv);//广告位不足五个添加广告展示位
					}
	    			dataList=allList;
	    		}
		    	break;
		    case 1:  //省级
	    		if(allList.size()>=5) {//省级代理四个广告位
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (5-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				adv.setUrl("/pc/advShow?regionCode="+region.getRegionCode()+"&sort="+(size+i)+"&position="+Dictionary.AdvPosition.SHOUYE);
	    				allList.add(adv);//广告位不足四个添加广告展示位
					}
	    		}
	    		params.put("regionCode", region.getParentRegionCode());//所选择区域首页广告
	    		list = advertisementDao.list(params);
	    		if(list!=null&&list.size()>0) {
	    			allList.add(list.get(0));
	    		}else {
	    			AdvertisementDO adv = new AdvertisementDO();
	    			adv.setUrl("/pc/advShow?regionCode="+region.getParentRegionCode()+"&sort="+0+"&position="+Dictionary.AdvPosition.SHOUYE);
	    			allList.add(adv);
	    		}
	    		dataList=allList;
		    	break;
		    case 2:  //市级
		    	if(allList.size()>=4) {//市级代理三个广告位
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (4-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				adv.setUrl("/pc/advShow?regionCode="+region+"&sort="+(size+i)+"&position="+Dictionary.AdvPosition.SHOUYE);
	    				allList.add(adv);//广告位不足三个添加广告展示位
					}
	    		}
		    	
	    		RegionDO proRegion = regionDao.get(region.getParentRegionCode());
	    		params.put("regionCode", proRegion.getRegionCode());
	    		list=advertisementDao.list(params);
	    		if(list!=null&&list.size()>0) {//所选择区域首页省级广告
	    			allList.add(list.get(0));
	    		}else {
	    			AdvertisementDO adv = new AdvertisementDO();
	    			adv.setUrl("/pc/advShow?regionCode="+proRegion.getRegionCode()+"&sort="+0+"&position="+Dictionary.AdvPosition.SHOUYE);
	    			allList.add(adv);
	    		}
	    		params.put("regionCode", proRegion.getParentRegionCode());//所选择区域首页广告
	    		list=advertisementDao.list(params);
	    		if(list!=null&&list.size()>0) {//所选择区域首页省级广告
	    			allList.add(list.get(0));
	    		}else {
	    			AdvertisementDO adv = new AdvertisementDO();
	    			adv.setUrl("/pc/advShow?regionCode="+proRegion.getParentRegionCode()+"&sort="+0+"&position="+Dictionary.AdvPosition.SHOUYE);
	    			allList.add(adv);
	    		}
		    	break;
		    case 3:  //县级
		    	if(allList.size()>=3) {//县级代理两个广告位
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (3-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				adv.setUrl("/pc/advShow?regionCode="+region+"&sort="+(size+i)+"&position="+Dictionary.AdvPosition.SHOUYE);
	    				allList.add(adv);//广告位不足两个添加广告展示位
					}
	    		}
	    		RegionDO cityRegion = regionDao.get(region.getParentRegionCode());
	    		params.put("regionCode", cityRegion.getRegionCode());//市级代理首页广告
	    		list=advertisementDao.list(params);
	    		if(list!=null&&list.size()>0) {//所选择区域首页省级广告
	    			allList.add(list.get(0));
	    		}else {
	    			AdvertisementDO adv = new AdvertisementDO();
	    			adv.setUrl("/pc/advShow?regionCode="+cityRegion.getRegionCode()+"&sort="+0+"&position="+Dictionary.AdvPosition.SHOUYE);
	    			allList.add(adv);
	    		}
	    		params.put("regionCode", cityRegion.getParentRegionCode());//省级代理首页广告
	    		list=advertisementDao.list(params);
	    		if(list!=null&&list.size()>0) {//所选择区域首页省级广告
	    			allList.add(list.get(0));
	    		}else {
	    			AdvertisementDO adv = new AdvertisementDO();
	    			adv.setUrl("/pc/advShow?regionCode="+cityRegion.getParentRegionCode()+"&sort="+0+"&position="+Dictionary.AdvPosition.SHOUYE);
	    			allList.add(adv);
	    		}
	    		RegionDO pubRegion = regionDao.get(cityRegion.getParentRegionCode());
	    		params.put("regionCode", pubRegion.getParentRegionCode());//平台首页广告
	    		list=advertisementDao.list(params);
	    		if(list!=null&&list.size()>0) {//所选择区域首页省级广告
	    			allList.add(list.get(0));
	    		}else {
	    			AdvertisementDO adv = new AdvertisementDO();
	    			adv.setUrl("/pc/advShow?regionCode="+pubRegion.getParentRegionCode()+"&sort="+0+"&position="+Dictionary.AdvPosition.SHOUYE);
	    			allList.add(adv);
	    		}
	    		dataList=allList;
		    	break;
		    case 4:  //街道办
		    	if(allList.size()>=2) {//街道办代理两个广告位
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (2-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				adv.setUrl("/pc/advShow?regionCode="+region+"&sort="+(size+i)+"&position="+Dictionary.AdvPosition.SHOUYE);
	    				allList.add(adv);//广告位不足两个添加广告展示位
					}
	    		}
	    		RegionDO areaRegion = regionDao.get(region.getParentRegionCode());
	    		params.put("regionCode", areaRegion.getRegionCode());//市级代理首页广告
	    		list=advertisementDao.list(params);
	    		if(list!=null&&list.size()>0) {//所选择区域首页县级广告
	    			allList.add(list.get(0));
	    		}else {
	    			AdvertisementDO adv = new AdvertisementDO();
	    			adv.setUrl("/pc/advShow?regionCode="+areaRegion.getRegionCode()+"&sort="+0+"&position="+Dictionary.AdvPosition.SHOUYE);
	    			allList.add(adv);
	    		}
	    		RegionDO city = regionDao.get(areaRegion.getParentRegionCode());
	    		params.put("regionCode", city.getRegionCode());//市代理首页广告
	    		list=advertisementDao.list(params);
	    		if(list!=null&&list.size()>0) {//所选择区域首页市级广告
	    			allList.add(list.get(0));
	    		}else {
	    			AdvertisementDO adv = new AdvertisementDO();
	    			
	    			adv.setUrl("/pc/advShow?regionCode="+city.getRegionCode()+"&sort="+0+"&position="+Dictionary.AdvPosition.SHOUYE);
	    			allList.add(adv);
	    		}
	    		RegionDO provi = regionDao.get(city.getParentRegionCode());
	    		params.put("regionCode", provi.getRegionCode());//平台首页广告
	    		list=advertisementDao.list(params);
	    		if(list!=null&&list.size()>0) {//所选择区域首页省级广告
	    			allList.add(list.get(0));
	    		}else {
	    			AdvertisementDO adv = new AdvertisementDO();
	    			adv.setUrl("/pc/advShow?regionCode="+provi.getRegionCode()+"&sort="+0+"&position="+Dictionary.AdvPosition.SHOUYE);
	    			allList.add(adv);
	    		}
	    		RegionDO head = regionDao.get(provi.getParentRegionCode());
	    		params.put("regionCode", head.getRegionCode());//平台首页广告
	    		list=advertisementDao.list(params);
	    		if(list!=null&&list.size()>0) {//所选择区域首页省级广告
	    			allList.add(list.get(0));
	    		}else {
	    			AdvertisementDO adv = new AdvertisementDO();
	    			adv.setUrl("/pc/advShow?regionCode="+head.getRegionCode()+"&sort="+0+"&position="+Dictionary.AdvPosition.SHOUYE);
	    			allList.add(adv);
	    		}
	    		dataList=allList;
		    	break;
		    default:  
		    	;
			}
		}
		return dataList;
	}

	@Override
	public List<TypeTitleDO> getFooterCenter() {
		Map<String,Object> params=new HashMap<>(16);
		List<TypeTitleDO> list2 = typeTitleDao.list(params);
		for (TypeTitleDO type : list2) {
			params.put("helpTypeId", type.getId());
			List<CenterDO> list = centerDao.list(params);
			type.setCenter(list);
		}
		return list2;
	}

	@Override
	public List<AdvertisementDO> getCenterAdvList(Integer regionCode, Integer positionNum) {
		List<AdvertisementDO> dataList=new ArrayList<AdvertisementDO>();
		Map<String,Object> params=new HashMap<>(16);
		params.put("positionNum", positionNum);
		params.put("regionCode", regionCode);//加载内容广告的页面(3:资讯详情页4:活动报名页5:签到页6:志愿者详情页7:团队详情页)
		List<AdvertisementDO> teamAdvList = advertisementDao.list(params);
		if(teamAdvList!=null&&teamAdvList.size()>0) {//所选择区域首页省级广告
			dataList.add(teamAdvList.get(0));
		}else {
			AdvertisementDO adv = new AdvertisementDO();
			adv.setUrl("/pc/advShow?regionCode="+regionCode+"&sort="+0+"&position="+positionNum);
			dataList.add(adv);
		}
		
		RegionDO jeidaoban = regionDao.get(regionCode);//获取街道办
		params.put("regionCode", jeidaoban.getRegionCode());
		List<AdvertisementDO> jdbAdvList = advertisementDao.list(params);//获取街道办广告
		if(teamAdvList!=null&&teamAdvList.size()>0) {//所选择区域首页省级广告
			dataList.add(jdbAdvList.get(0));
		}else {
			AdvertisementDO adv = new AdvertisementDO();
			adv.setUrl("/pc/advShow?regionCode="+regionCode+"&sort="+0+"&position="+positionNum);
			dataList.add(adv);
		}
		RegionDO area = regionDao.get(jeidaoban.getParentRegionCode());//获取县/区
		params.put("regionCode", area.getRegionCode());
		List<AdvertisementDO> areaAdvList = advertisementDao.list(params);//获取县广告
		if(areaAdvList!=null&&areaAdvList.size()>0) {//所选择区域首页省级广告
			dataList.add(areaAdvList.get(0));
		}else {
			AdvertisementDO adv = new AdvertisementDO();
			adv.setUrl("/pc/advShow?regionCode="+area.getRegionCode()+"&sort="+0+"&position="+positionNum);
			dataList.add(adv);
		}
		
		RegionDO city = regionDao.get(area.getParentRegionCode());//获取市
		params.put("regionCode", city.getRegionCode());
		List<AdvertisementDO> cityAdvList = advertisementDao.list(params);//获取市广告
		if(cityAdvList!=null&&cityAdvList.size()>0) {//所选择区域首页省级广告
			dataList.add(cityAdvList.get(0));
		}else {
			AdvertisementDO adv = new AdvertisementDO();
			adv.setUrl("/pc/advShow?regionCode="+city.getRegionCode()+"&sort="+0+"&position="+positionNum);
			dataList.add(adv);
		}
		
		RegionDO province = regionDao.get(area.getParentRegionCode());//获取省
		params.put("regionCode", province.getRegionCode());
		List<AdvertisementDO> provinceAdvList = advertisementDao.list(params);//获取省广告
		
		if(provinceAdvList!=null&&provinceAdvList.size()>0) {//所选择区域首页省级广告
			dataList.add(provinceAdvList.get(0));
		}else {
			AdvertisementDO adv = new AdvertisementDO();
			adv.setUrl("/pc/advShow?regionCode="+province.getRegionCode()+"&sort="+0+"&position="+positionNum);
			dataList.add(adv);
		}
		
		params.put("regionCode", 0);
		List<AdvertisementDO> AdvList = advertisementDao.list(params);//获取平台广告
		if(AdvList!=null&&AdvList.size()>0) {//所选择区域首页省级广告
			dataList.add(AdvList.get(0));
		}else {
			AdvertisementDO adv = new AdvertisementDO();
			adv.setUrl("/pc/advShow?regionCode="+0+"&sort="+0+"&position="+positionNum);
			dataList.add(adv);
		}
		return dataList;
	}


}
