package com.fly.index.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fly.adv.domain.AdvPriceDO;
import com.fly.adv.domain.DetailDO;
import com.fly.adv.service.AdvPriceService;
import com.fly.adv.service.DetailService;
import com.fly.common.controller.BaseController;
import com.fly.index.utils.OrderType;
import com.fly.pc.news.controller.BaseDynamicController;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.DateUtils;
import com.fly.utils.R;
/**
 * web端可共用接口
 * @author Wang Yanke
 * 
 */
@Controller
public class WebBaseController extends BaseController{
	@Autowired
	private AdvPriceService advPriceService;
	@Autowired
	private DetailService detailService;
	private Logger logger = LoggerFactory.getLogger(WebBaseController.class);
	
	
	/**
	 *	web端广告位购买
	 * @param params
	 * @param regionCode
	 * @param positionNum
	 * @param sort
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/web/advSubmit")
	@Transactional(rollbackFor= {Exception.class})
	public R submitAdv(@RequestParam Map<String,Object> params) {
		logger.debug("购买广告 method:submitAdv"
				+ "传入参数"+params.toString());
		ShiroUtils.getUserId();
		if(params.get("regionCode")==null) {
			logger.debug("组织编号region :"+params.get("regionCode")+"");
			return R.error("广告位购买失败");
		}
		if(params.get("positionNum")==null) {
			logger.debug("购买广告位置页面 :"+params.get("positionNum")+"");
			return R.error("缺少参数");
		}
		if(params.get("sort")==null) {
			logger.debug("广告所在页面序号 :"+params.get("sort")+"");
			return R.error("缺少参数");
		}
		Integer regionCode=Integer.parseInt(params.get("regionCode").toString());
		Integer positionNum=Integer.parseInt(params.get("positionNum").toString());
		Integer sort=Integer.parseInt(params.get("sort").toString());
		DetailDO advDetail=new DetailDO ();
		advDetail.setCreateTime(new Date());
		Map<String, Object> map=new HashMap<>(16);
		map.put("regionCode", regionCode);
		map.put("positionNum", positionNum);
		map.put("sort", sort);
		List<AdvPriceDO> setupAdvList = advPriceService.list(map);//获取广告位价格配置信息
		if(setupAdvList==null||setupAdvList.size()==0) {
			return R.error("该广告位无效，请联系管理员");
		}
		AdvPriceDO setupPrice = setupAdvList.get(0);
		advDetail.setAdvUrl(params.get("advUrl").toString());
		advDetail.setAdvImg(params.get("advImg").toString());
		advDetail.setPositionNum(positionNum);
		advDetail.setRegionCode(regionCode);
		advDetail.setShowStartTime(DateUtils.parse(params.get("showStartTime").toString()));
		advDetail.setShowEndTime(DateUtils.parse(params.get("showEndTime").toString()));
		Integer day = 0;
		try {
			day=DateUtils.longOfTwoDate(DateUtils.parse(params.get("showStartTime").toString()), DateUtils.parse(params.get("showEndTime").toString()));
		} catch (ParseException e) {
			logger.debug("广告开始时间计算失败");
			e.printStackTrace();
			return R.error("提交失败");
		}
		BigDecimal num2 = new BigDecimal(day);
		advDetail.setStatus(0);
		advDetail.setPrice(setupPrice.getPrice().multiply(num2));
		advDetail.setUserId(ShiroUtils.getUserId());
		try {
			detailService.save(advDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> orderParams=new HashMap<>(16);
		params.get("orderType");
		orderParams.put("orderType", 2);
		orderParams.put("price", advDetail.getPrice());
		orderParams.put("expIncType", OrderType.GUANG_GAO);
		try {
			Integer i=creadOrder(orderParams);
			if(i>0) {
				return R.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("操作失败");
		}
		return R.error();
		
	}

}
