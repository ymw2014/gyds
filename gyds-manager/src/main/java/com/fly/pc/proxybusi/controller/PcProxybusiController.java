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
import com.fly.common.controller.BaseController;
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
import com.fly.team.dao.TeamDao;
import com.fly.team.domain.TeamDO;
import com.fly.team.domain.TypeDO;
import com.fly.utils.JSONUtils;
import com.fly.utils.R;
import com.fly.utils.userToObject;
import com.fly.verifyName.dao.NameDao;
import com.fly.verifyName.domain.NameDO;
import com.fly.volunteer.service.VolunteerService;

@Controller
@RequestMapping("/pc/proxybusi")
public class PcProxybusiController extends BaseController{

	@Autowired
	private RegionService regionService;
	@Autowired
	private ProxybusiService proxybusiService;
	@Autowired
	private SetupService setupService;
	@Autowired
	private UserService userService;
	@Autowired
	private NameDao nameDao;
	@Autowired
	private TeamDao teamDao;
	/***
	 * 	进入代理商申请页面
	 * @param params
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/show")
	public String index(@RequestParam Map<String,Object> params, HttpServletRequest request,Model model) {
		String areaId = request.getParameter("areaId");
		UserDO user = ShiroUtils.getUser();
		if(user==null) {//未登录返回登录页
			return "redirect:/login";
		}
		Map<String,Object> param = new HashMap<String,Object>();
		
		params.put("userId", user.getUserId());
		List<TeamDO> TeamDO = teamDao.list(params);
		if(!CollectionUtils.isEmpty(TeamDO)) {
			model.addAttribute("message", "您已经是团长，团长不能申请代理商，感谢您的参与" );
			return "pc/message";
		}
		param.put("type",3);
		param.put("status", 1);
		param.put("userId", user.getUserId());
		NameDO name = nameDao.applyStatus(param);
		if (name != null) {
			if(name.getStatus()==1) {
				model.addAttribute("message", "如果通过审核，我们将在3个工作内通过电话与您沟通，请保持手机畅通，谢谢！");
				return "pc/message";
			} 
		}
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
		//申请保证金查询
		SetupDO setupDO = setupService.get(1);
		model.addAttribute("setupDO", setupDO);
		if (user.getIsIdentification()==null||user.getIsIdentification()!=1) {
				model.addAttribute("type", 3);
				return "pc/attestationProxy";
		}
		String cardNo = user.getCardNo();
		StringBuilder sb = new StringBuilder(cardNo);
		sb.replace(6, 14, "********");
		user.setCardNo(sb.toString());
		model.addAttribute("user", user);
		
		if (CollectionUtils.isEmpty(list)) {//还没申请
			return "pc/proxybusi";
		}else {
			model.addAttribute("proxybusiDO", list.get(0));
			return "pc/proxybusiPass";
		}
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
	public synchronized R save(ProxybusiDO proxybusiDO, String pronvice, String city, String country, String street,String client, String proxyArea) {
		if(proxybusiDO.getRegImg()==null||proxybusiDO.getRegImg()=="") {
			return R.error("区域图片不能为空");
		}
		Integer i = 0;
		SetupDO setupDO = setupService.get(1);
		String errMsg = "账号余额不足，请充值";
		UserDO user = ShiroUtils.getUser();
		user = userService.get(user.getUserId());
		BigDecimal account = user.getAccount();
		if (account == null) {
			return R.error(errMsg);
		}
		
		String[] area = null;
		if (!StringUtils.isEmpty(client)) {
			area = proxyArea.split(",");
		}
		proxybusiDO.setCreateTime(new Date());
		Integer level = proxybusiDO.getRegionLevel();
		Map<String,Object> params = new HashMap<String, Object>();
		BigDecimal balance = null;
		switch (level) {
		case 1:
			BigDecimal bail = setupDO.getProvincialBail();
			if (account.compareTo(bail) == -1) {
				return R.error(errMsg);
			}
			params.put("price", bail);
			balance = account.subtract(bail);
			String pron = area != null ? area[0] : pronvice;
			proxybusiDO.setProxyRegion(Long.valueOf(pron));
			break;
		case 2:
			BigDecimal cityBail = setupDO.getCityBail();
			if (account.compareTo(cityBail) == -1) {
				return R.error(errMsg);
			}
			params.put("price", cityBail);
			balance = account.subtract(cityBail);
			String cit = area != null ? area[1] : city;
			proxybusiDO.setProxyRegion(Long.valueOf(cit));
			break;
		case 3:
			BigDecimal areaBail = setupDO.getAreaBail();
			if (account.compareTo(areaBail) == -1) {
				return R.error(errMsg);
			}
			params.put("price", areaBail);
			balance = account.subtract(areaBail);
			String coun = area != null ? area[2] : country;
			proxybusiDO.setProxyRegion(Long.valueOf(coun));
			break;
		case 4:
			BigDecimal agencyBail = setupDO.getAgencyBail();
			if (account.compareTo(agencyBail) == -1) {
				return R.error(errMsg);
			}
			params.put("price", agencyBail);
			balance = account.subtract(agencyBail);
			String stree = area != null ? area[3] : street;
			proxybusiDO.setProxyRegion(Long.valueOf(stree));
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
			
		NameDO name = userToObject.userToverify(user, null);
		proxybusiDO.setUserId(user.getUserId());
		name.setType(3);
		name.setText(JSONUtils.beanToJson(proxybusiDO));
		 i = creadOrder(params);
		 if(i>0) {
			 name.setOrderId(i);
			 if(nameDao.save(name)>0){
					userService.updatePersonal(user);
					return R.ok();
				}
		 }
			
		return R.error();
	}
	
}
