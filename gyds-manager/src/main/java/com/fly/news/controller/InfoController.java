package com.fly.news.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.base.BaseService;
import com.fly.domain.UserDO;
import com.fly.index.utils.OrderType;
import com.fly.news.dao.TopDao;
import com.fly.news.domain.InfoDO;
import com.fly.news.domain.TopDO;
import com.fly.news.service.InfoService;
import com.fly.news.service.TopService;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.system.service.RegionService;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;
import com.fly.team.dao.TeamDao;
import com.fly.team.domain.TeamDO;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-02 14:35:59
 */
 
@Controller
@RequestMapping("/news/info")
public class InfoController {
	@Autowired
	private InfoService infoService;
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private TopDao topDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private TopService topService;
	@Autowired
	private BaseService baseService;


	@GetMapping()
	@RequiresPermissions("news:info:info")
	String Info(){
	    return "news/info/info";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("news:info:info")
	public PageUtils list(@RequestParam Map<String, Object> params){
		// 查询列表数据
		params.put("pids", ShiroUtils.getUser().getDeptId());
		List<Integer> ids = regionService.getTeamAndAreaByUserRole(params);
		params.put("ids", ids);
		//查询列表数据
        Query query = new Query(params);
		List<InfoDO> infoList = infoService.list(query);
		int total = infoService.count(query);
		PageUtils pageUtils = new PageUtils(infoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("news:info:add")

	String add(Model model){
		UserDO user = ShiroUtils.getUser();
		TeamDO teamDO = teamDao.getByTeamCode(user.getDeptId());
		model.addAttribute("team", teamDO);
	    return "news/info/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("news:info:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		InfoDO info = infoService.get(id);
		model.addAttribute("info", info);
	    return "news/info/edit";
	}

	@GetMapping("/audit/{id}")
	@RequiresPermissions("news:info:audit")
	public String audit(@PathVariable("id") Long id,Model model){
		model.addAttribute("newsId", id);
	    return "news/info/newsAudit";
	}
	
	
	@ResponseBody
	@GetMapping("/auditData/{id}")
	@RequiresPermissions("news:info:audit")
	public PageUtils auditDate(@PathVariable("id") Long id,String limit,String offset){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newsId", id);
		params.put("limit", limit);
		params.put("offset", offset);
		Query query = new Query(params);
		List<Map<String,Object>> auditData = infoService.auditData(query);
		int dataCount = infoService.auditDataCount(query);
		PageUtils pageUtils = new PageUtils(auditData, dataCount);
	    return pageUtils;
	}

	@Transactional
	@ResponseBody
	@RequestMapping("/auditUpdate")
	@RequiresPermissions("news:info:audit")
	public R auditUpdate(TopDO apply){
		TopDO exentApply = topService.get(apply.getId());
		OrderDO order = orderService.get(exentApply.getOrdernumber());
		if(order==null) {
			return R.error("订单信息查询失败");
		}
		 if (apply.getStatus() == 2) {
			BigDecimal price = order.getPrice();
			UserDO userDO = userService.getUser(exentApply.getUserId());
			BigDecimal account = userDO.getAccount();
			BigDecimal blance = price.add(account);
			userDO.setAccount(blance);
			userService.updatePersonal(userDO);
			exentApply.setStatus(2);
			topDao.update(exentApply);
			order.setExamineStatus(3);
			order.setExamineUser(ShiroUtils.getUserId());
			orderService.update(order);
		 }
		 if (apply.getStatus() == 1) {
			 InfoDO newInfo = infoService.get(exentApply.getNewsId());
			 Calendar c = Calendar.getInstance();
			 c.add(Calendar.DAY_OF_MONTH, apply.getTopDay());
			 exentApply.setTopStartTime(new Date());
			 exentApply.setTopEndTime(c.getTime());
			 newInfo.setPymentEndTime(c.getTime());
			 newInfo.setPymentStartTime(new Date());
			 newInfo.setTopRegion(exentApply.getRegionCode());
			 infoService.update(newInfo);
			 exentApply.setStatus(1);
			 topDao.update(exentApply);
			 order.setExamineStatus(3);
			 order.setExamineUser(ShiroUtils.getUserId());
			 orderService.update(order);
			 newInfo.setIsTop(1);
			 infoService.update(newInfo);
			 try {
				 baseService.DistributionOfDomesticTop(order.getPrice(), OrderType.GUANG_GAO_FAN_YONG, apply.getNewsId(), exentApply.getRegionCode());
			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			}
			
		 }
		return R.ok();
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("news:info:add")
	public R save( InfoDO info){
		info.setCreateTime(new Date());
		info.setStatus(0);
		info.setIsTop(0);
		if(infoService.save(info)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("news:info:edit")
	public R update( InfoDO info){
		if(info.getStatus()!=null&&1==info.getStatus()) {
			info.setPublicTime(new Date());
		}
		infoService.update(info);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("news:info:remove")
	public R remove( Integer id){
		if(infoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("news:info:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		infoService.batchRemove(ids);
		return R.ok();
	}
	
}
