package com.fly.pc.proxybusi.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fly.domain.RegionDO;
import com.fly.domain.UserDO;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.proxybusi.service.ProxybusiService;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.R;

@Controller
@RequestMapping("/pc/proxybusi")
public class PcProxybusiController {

	@Autowired
	private RegionService regionService;
	@Autowired
	private ProxybusiService proxybusiService;
	
	
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
		
		if (CollectionUtils.isEmpty(list)) {//0:申请中 1:通过 2:已拒绝
			return "pc/proxybusi";//3还没申请
		} 
		
		Integer status = list.get(0).getAuditStatus();
		if (status == 0) {
			return "pc/proxybusiMessage";
		}
		
		
		if (status == 1) {
			model.addAttribute("proxybusiDO", list.get(0));
			return "pc/proxybusiPass";
		}
		
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
	@RequestMapping("/save")
	public R save(ProxybusiDO proxybusiDO, String pronvice, String city, String country) {
		proxybusiDO.setCreateTime(new Date());
		proxybusiDO.setAuditStatus(0);
		Integer level = proxybusiDO.getRegionLevel();
		switch (level) {
		case 1:
			proxybusiDO.setProxyRegion(Integer.valueOf(pronvice));
			break;
		case 2:
			proxybusiDO.setProxyRegion(Integer.valueOf(city));
			break;
		case 3:
			proxybusiDO.setProxyRegion(Integer.valueOf(country));
			break;
		default:
			break;
		}
		if (proxybusiDO.getId() == null) {
			if(proxybusiService.save(proxybusiDO)>0){
				return R.ok();
			}
		}
		if(proxybusiService.update(proxybusiDO)>0){
			return R.ok();
		}
		return R.error();
	}
	
}
