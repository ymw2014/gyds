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
		params.put("positionNum", positionNum);
		if(areaId!=null) {
			RegionDO region = regionDao.get(areaId);
			params.put("positionNum", 1);
			params.put("regionCode", region.getRegionCode());//所选择区域首页广告
    		List<AdvertisementDO> allList = advertisementDao.list(params);
    		Integer size=allList.size();
			switch(region.getRegionLevel()){  
		    case 0:  //全国
	    		if(allList.size()>=5) {
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (5-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				allList.add(adv);//广告位不足五个添加广告展示位
					}
	    			dataList=allList;
	    		}
		    	break;
		    case 1:  //省级
	    		if(allList.size()>=4) {//省级代理四个广告位
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (4-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				allList.add(adv);//广告位不足四个添加广告展示位
					}
	    		}
	    		params.put("regionCode", region.getParentRegionCode());//所选择区域首页广告
	    		allList.add(advertisementDao.list(params).size()==0?new AdvertisementDO():advertisementDao.list(params).get(0));
	    		dataList=allList;
		    	break;
		    case 2:  //市级
		    	if(allList.size()>=3) {//市级代理三个广告位
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (3-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				allList.add(adv);//广告位不足三个添加广告展示位
					}
	    		}
		    	
	    		RegionDO proRegion = regionDao.get(region.getParentRegionCode());
	    		params.put("regionCode", proRegion.getRegionCode());//所选择区域首页广告
	    		allList.add(advertisementDao.list(params).size()==0?new AdvertisementDO():advertisementDao.list(params).get(0));
	    		params.put("regionCode", proRegion.getParentRegionCode());//所选择区域首页广告
	    		allList.add(advertisementDao.list(params).size()==0?new AdvertisementDO():advertisementDao.list(params).get(0));
	    		dataList=allList;
		    	break;
		    case 3:  //县级
		    	if(allList.size()>=2) {//县级代理两个广告位
	    			dataList=allList;
	    		}else {
	    			for (int i = 0; i < (2-size); i++) {
	    				AdvertisementDO adv=new AdvertisementDO();
	    				allList.add(adv);//广告位不足两个添加广告展示位
					}
	    		}
	    		RegionDO cityRegion = regionDao.get(region.getParentRegionCode());
	    		params.put("regionCode", cityRegion.getRegionCode());//市级代理首页广告
	    		AdvertisementDO advs = advertisementDao.list(params).size()==0?new AdvertisementDO():advertisementDao.list(params).get(0);
	    		allList.add(advs);
	    		params.put("regionCode", cityRegion.getParentRegionCode());//省级代理首页广告
	    		allList.add(advertisementDao.list(params).size()==0?new AdvertisementDO():advertisementDao.list(params).get(0));
	    		RegionDO pubRegion = regionDao.get(cityRegion.getParentRegionCode());
	    		params.put("regionCode", pubRegion.getParentRegionCode());//平台首页广告
	    		allList.add(advertisementDao.list(params).size()==0?new AdvertisementDO():advertisementDao.list(params).get(0));
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
		dataList.add(teamAdvList.size()>0?teamAdvList.get(0):new AdvertisementDO());//添加团队广告
		RegionDO area = regionDao.get(regionCode);//获取县/区
		params.put("regionCode", area.getRegionCode());
		List<AdvertisementDO> areaAdvList = advertisementDao.list(params);//获取县广告
		dataList.add(areaAdvList.size()>0?areaAdvList.get(0):new AdvertisementDO());//添加县广告
		RegionDO city = regionDao.get(area.getParentRegionCode());//获取市
		params.put("regionCode", city.getRegionCode());
		List<AdvertisementDO> cityAdvList = advertisementDao.list(params);//获取市广告
		dataList.add(cityAdvList.size()>0?cityAdvList.get(0):new AdvertisementDO());//添加市广告
		RegionDO province = regionDao.get(area.getParentRegionCode());//获取省
		params.put("regionCode", province.getRegionCode());
		List<AdvertisementDO> provinceAdvList = advertisementDao.list(params);//获取省广告
		dataList.add(provinceAdvList.size()>0?provinceAdvList.get(0):new AdvertisementDO());//添加省广告
		params.put("regionCode", 0);
		List<AdvertisementDO> AdvList = advertisementDao.list(params);//获取平台广告
		dataList.add(AdvList.size()>0?AdvList.get(0):new AdvertisementDO());//添加平台广告
		return dataList;
	}


}
