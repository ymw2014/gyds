package com.fly.pc.proxybusi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fly.domain.RegionDO;
import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.system.service.RegionService;

@Controller
@RequestMapping("/pc/proxybusi")
public class PcProxybusiController {

	@Autowired
	private RegionService regionService;
	
	
	@RequestMapping("/show")
	public String index(@RequestParam Map<String,Object> params, HttpServletRequest request,Model model) {
		String areaId = request.getParameter("areaId");
		if (StringUtils.isEmpty(areaId)) {
			params.put("parentRegionCode",0);
		} else {
			params.put("parentRegionCode",areaId);
		}
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		model.addAttribute("areaList", areaList);
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
	public String save(ProxybusiDO proxybusiDO) {
		return "";
	}
}
