package com.fly.pc.volunteer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.domain.RegionDO;
import com.fly.news.domain.CommentDO;
import com.fly.news.service.CommentService;
import com.fly.system.service.RegionService;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;


@Controller
@RequestMapping("/pc/")
public class PcVolunteerController {
	
	@Autowired
	private RegionService regionService;
	@Autowired
	private VolunteerService volunteerService;
	@Autowired
	private CommentService commentService;

	@RequestMapping("volunteerList")
	public String volunteer(@RequestParam Map<String,Object> params, HttpServletRequest request, 
			Model model,String sort, String order, HttpServletResponse response) throws IOException {
		params.clear();
		String areaId = request.getParameter("regionCode");
		String sex = request.getParameter("sex");
		params.put("parentRegionCode", 0);
		params.put("regionType",1);
		List<RegionDO> areaList = regionService.list(params);
		params.clear();
		if (!StringUtils.isEmpty(areaId)) {
			params.put("parentRegionCode", areaId);
			params.put("regionType",1);
			List<RegionDO> region = regionService.list(params);
			params.clear();
			List<RegionDO> regin = new ArrayList<RegionDO>();
			regin.addAll(region);
			if (!CollectionUtils.isEmpty(region)) {
				for (RegionDO bean : region) {
					params.put("parentRegionCode", bean.getParentRegionCode());
					params.put("regionType",1);
					List<RegionDO> reg = regionService.list(params);
					regin.addAll(reg);
				}
			}
			List<Integer> codeS = regin.stream().map(RegionDO :: getRegionCode).collect(Collectors.toList());
			params.put("regionCodeS", codeS);
		} 
		
		params.put("auditStatus",1);//
		params.put("sex",sex);
		params.put("sort", sort);
		params.put("order", order);
		List<VolunteerDO> voluntList = volunteerService.list(params);
		model.addAttribute("voluntList", voluntList);
		model.addAttribute("areaList", areaList);//全国包含的省
		return "pc/volunteerList";
	}
	
	@RequestMapping("volunteerDetail")
	public String volunteerDetail(@RequestParam Map<String,Object> params, long id, Model model) {
		VolunteerDO volunteerDO = volunteerService.get(id);
		String region = volunteerDO.getProvince() + " " + volunteerDO.getCity();
		volunteerDO.setProvince(region);
		params.clear();
		params.put("memberId",id);
		List<CommentDO> comment = commentService.list(params);
		model.addAttribute("volunteer",volunteerDO);
		model.addAttribute("commentList",comment);
		return "pc/volunteerDetail";
	}
}
