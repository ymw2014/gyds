package com.fly.pc.adv.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.adv.domain.AdvPriceDO;
import com.fly.adv.domain.DetailDO;
import com.fly.adv.service.AdvPriceService;
import com.fly.adv.service.DetailService;
import com.fly.async.task.ThreadTaskService;
import com.fly.base.BaseService;
import com.fly.common.controller.BaseController;
import com.fly.domain.RegionDO;
import com.fly.index.utils.OrderType;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.R;

/**
 * 	广告位购买
 * @author Administrator
 *
 */

@Controller
public class AdvController extends BaseController{
	@Autowired
	private RegionService regionService;
	@Autowired
	private AdvPriceService advPriceService;
	@Autowired 
	private ThreadTaskService threadTaskService;
	@Autowired
	private DetailService detailService;
	
	@ResponseBody
	@RequestMapping("/pc/advShow")
	public R advShow(Integer regionCode,Integer sort,Integer position){
		R r=new R();
		if(ShiroUtils.getUser()==null) {
			r.put("code", -1);
			r.put("url", "/login");
			return r;
		}
		RegionDO region = regionService.get(regionCode);
		r.put("regionName", region.getRegionName());
		r.put("positionNum",position);
		r.put("regionCode",regionCode);
		r.put("sort",sort);
		switch (position) {
		case 1:
			r.put("positionName", "首页/咨询列表页");
			break;
		case 3:
			r.put("positionName", "咨询详情页");
			break;
		case 4:
			r.put("positionName", "活动报名页");
			break;
		case 5:
			r.put("positionName", "签到页");
			break;
		case 6:
			r.put("positionName", "志愿者详情页");		
			break;
		case 7:
			r.put("positionName", "团队详情页");		
			break;
		default:
			break;
		}
		Map<String, Object> params=new HashMap<String, Object>(16);
		params.put("regionCode", regionCode);
		params.put("positionNum", position);
		List<AdvPriceDO> advList = advPriceService.list(params);
		if(!CollectionUtils.isEmpty(advList)) {
			r.put("price",advList.get(sort).getPrice());
		}else {
			return r.error("此广告位暂未开放");
		}
		r.put("code", 0);
		return r;
	}
	
	/**
	 * 广告位购买
	 * @return 
	 */
	@Transactional
	@ResponseBody
	@RequestMapping("/pc/advSubmit")
	public R advSubmit(DetailDO detail) {
		Map<String, Object> params=new HashMap<String, Object>(16);
		if(detail.getAdvImg()==null) {
			return R.error("广告图不能为空");
		}
		params.put("regionCode", detail.getRegionCode());
		params.put("positionNum", detail.getPositionNum());
		List<AdvPriceDO> advList = advPriceService.list(params);
		if(!CollectionUtils.isEmpty(advList)) {
			BigDecimal dayPrice = advList.get(detail.getSort()).getPrice();
			BigDecimal allPrice = dayPrice.multiply(new BigDecimal(detail.getShowDay()));
			params.put("price", allPrice);
			Integer sourcce=deductMoney(params);
			if(sourcce==0) {//0:扣款失败 -1表示余额不足 1表示扣款成功 2表示无此用户
				return R.error("扣款失败");
			}
			if(sourcce== -1) {
				return R.error("账户余额不足");
			}
			if(sourcce==2) {
				return R.error("获取账户信息失败");
			}
			detail.setPrice(allPrice);
			detail.setUserId(ShiroUtils.getUserId());
			detail.setStatus(0);
			detail.setCreateTime(new Date());
			String remake = threadTaskService.getRemake(ShiroUtils.getUserId(), allPrice, OrderType.GUANG_GAO_GOU_MAI,OrderType.ZHI_CHU, "广告购买");
			Integer orderId = threadTaskService.createOrder(ShiroUtils.getUserId(), allPrice, OrderType.GUANG_GAO_GOU_MAI, OrderType.ZHI_CHU, remake);
			detail.setOrderId(orderId);
			if(detailService.save(detail)>0) {
				return R.ok();
			}else {
				return R.error("操作失败");
			}
		}else {
			return R.error("广告价格计算失败");
		}
		

	}

}
