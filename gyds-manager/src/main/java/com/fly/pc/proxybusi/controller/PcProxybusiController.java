package com.fly.pc.proxybusi.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.index.utils.OrderType;
import com.fly.pc.news.controller.BaseDynamicController;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.proxybusi.service.ProxybusiService;
import com.fly.sys.domain.SetupDO;
import com.fly.sys.service.SetupService;
import com.fly.system.service.RegionService;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.R;

@Controller
@RequestMapping("/pc/proxybusi")
public class PcProxybusiController extends BaseDynamicController{

	@Autowired
	private RegionService regionService;
	@Autowired
	private ProxybusiService proxybusiService;
	@Autowired
	private SetupService setupService;
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/show")
	public String index(@RequestParam Map<String,Object> params, HttpServletRequest request,Model model) {
		String areaId = request.getParameter("areaId");
		UserDO user = ShiroUtils.getUser();
		if (StringUtils.isEmpty(areaId)) {
			params.put("parentRegionCode",0);
		} else {
			params.put("parentRegionCode",areaId);
		}
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);
		
		params.clear();
		params.put("userId", user.getUserId());
		List<ProxybusiDO> list = proxybusiService.list(params);
		String cardNo = user.getCardNo();
		StringBuilder sb = new StringBuilder(cardNo);
		sb.replace(6, 14, "********");
		user.setCardNo(sb.toString());
		model.addAttribute("user", user);
		//申请保证金查询
		SetupDO setupDO = setupService.get(1);
		model.addAttribute("setupDO", setupDO);
		
		if (CollectionUtils.isEmpty(list)) {//还没申请
			model.addAttribute("proxybusiDO", new ProxybusiDO());
			return "pc/proxybusi";
		} 
		
		Integer status = list.get(0).getAuditStatus();
		if (status == 0) {//0:申请中
			return "pc/proxybusiMessage";
		}
		
		
		if (status == 1) {//1:通过
			model.addAttribute("proxybusiDO", list.get(0));
			return "pc/proxybusiPass";
		}
		//2:已拒绝
		model.addAttribute("proxybusiDO", list.get(0));
		return "pc/proxybusi";
	}
	
	
	@ResponseBody
	@RequestMapping("/city")
	public String city(@RequestParam Map<String,Object> params, HttpServletRequest request) {
		JSONObject dataInfo = new JSONObject();
		String areaId = request.getParameter("areaId");
		if (StringUtils.isEmpty(areaId)) {
			params.put("parentRegionCode",0);
		} else {
			params.put("parentRegionCode",areaId);
		}
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		dataInfo.put("areaList", areaList);
		return dataInfo.toString();
	}
	
	@ResponseBody
	@Transactional
	@RequestMapping("/save")
	public synchronized R save(ProxybusiDO proxybusiDO, String pronvice, String city, String country,String aaa, String bbb) {
		SetupDO setupDO = setupService.get(1);
		UserDO user = ShiroUtils.getUser();
		BigDecimal account = user.getAccount();
		if (account == null) {
			return R.error("账号余额不足，请充值");
		}
		proxybusiDO.setCreateTime(new Date());
		proxybusiDO.setAuditStatus(0);
		Integer level = proxybusiDO.getRegionLevel();
		Map<String,Object> params = new HashMap<String, Object>();
		BigDecimal balance = null;
		switch (level) {
		case 1:
			BigDecimal bail = setupDO.getProvincialBail();
			if (account.compareTo(bail) == -1) {
				return R.error("账号余额不足，请充值");
			}
			params.put("price", bail);
			balance = account.subtract(bail);
			proxybusiDO.setProxyRegion(Integer.valueOf(pronvice));
			break;
		case 2:
			BigDecimal cityBail = setupDO.getCityBail();
			if (account.compareTo(cityBail) == -1) {
				return R.error("账号余额不足，请充值");
			}
			params.put("price", cityBail);
			balance = account.subtract(cityBail);
			proxybusiDO.setProxyRegion(Integer.valueOf(city));
			break;
		case 3:
			BigDecimal areaBail = setupDO.getAreaBail();
			if (account.compareTo(areaBail) == -1) {
				return R.error("账号余额不足，请充值");
			}
			params.put("price", areaBail);
			balance = account.subtract(areaBail);
			proxybusiDO.setProxyRegion(Integer.valueOf(country));
			break;
		default:
			break;
		}
		user.setAccount(balance);
		params.put("orderType", 2);
		params.put("expIncType", OrderType.DAI_LI_SHANG);
		params.put("examineStatus", 2);
		params.put("cashUpType", 2);
		params.put("cashOutType", 2);
		
		if (proxybusiDO.getId() == null) {
			if(proxybusiService.save(proxybusiDO)>0){
				userService.updatePersonal(user);
				creadOrder(params);
				return R.ok();
			}
		}
		if(proxybusiService.update(proxybusiDO)>0){
			userService.updatePersonal(user);
			creadOrder(params);
			return R.ok();
		}
		return R.error();
	}
	
}
