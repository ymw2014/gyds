package com.fly.activity.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.activity.dao.TypeDao;
import com.fly.activity.domain.ActivityDO;
import com.fly.activity.domain.TypeDO;
import com.fly.activity.service.ActivityService;
import com.fly.system.service.RegionService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;



/**
 * 活动表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-04 14:26:40
 */
 
@Controller
@RequestMapping("/activity/activity")
public class ActivityController {
	@Autowired
	private ActivityService activityService;
	@Autowired
	private TypeDao typeDao;
	@Autowired
	private RegionService regionService;
	@GetMapping()
	@RequiresPermissions("activity:activity:activity")
	String Activity(){
	    return "activity/activity/activity";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("activity:activity:activity")
	
	public PageUtils list(@RequestParam Map<String, Object> params){
		params.put("pids", ShiroUtils.getUser().getDeptId());
		String ids = regionService.getTeamAndAreaByUserRole(ShiroUtils.getUser().getDeptId());
		params.put("ids", ids);
		//查询列表数据
        Query query = new Query(params);
		List<ActivityDO> activityList = activityService.list(query);
		int total = activityService.count(query);
		PageUtils pageUtils = new PageUtils(activityList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("activity:activity:add")
	String add(Model model){
		List<TypeDO> typeList = typeDao.list1();
		model.addAttribute("typeList",typeList);
	    return "activity/activity/add";
	}

	@GetMapping("/audit/{id}")
	@RequiresPermissions("activity:activity:audit")
	String audit(@PathVariable("id") Integer id,Model model){
		ActivityDO activity = activityService.get(id);
		model.addAttribute("activity", activity);
	    return "activity/activity/audit";
	}
	@GetMapping("/edit/{id}")
	@RequiresPermissions("activity:activity:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ActivityDO activity = activityService.get(id);
		List<TypeDO> typeList = typeDao.list1();
		model.addAttribute("typeList",typeList);
		model.addAttribute("activity", activity);
	    return "activity/activity/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("activity:activity:add")
	public R save( ActivityDO activity){
		Long userId = ShiroUtils.getUserId();
		activity.setMemberId(userId);
		activity.setCreateTime(new Date());
		activity.setStatus(0);
		if(activityService.save(activity)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("activity:activity:edit")
	public R update( ActivityDO activity){
		activityService.update(activity);
		return R.ok();
	}
	@ResponseBody
	@RequestMapping("/upStatus")
	public R upStatus(ActivityDO activity){
		if(activityService.update(activity)>0){
			return R.ok();
			}
			return R.error();
		
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("activity:activity:remove")
	public R remove( Integer id){
		if(activityService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("activity:activity:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		activityService.batchRemove(ids);
		return R.ok();
	}
	
	
	/**
	 * 审核
	 */
	@PostMapping( "/examine")
	@ResponseBody
	@RequiresPermissions("activity:activity:examine")
	public R examine(Integer id,Integer status){
		ActivityDO activity = activityService.get(id);
		activity.setExamineStatus(status);
		if(status==1) {
			activity.setNumberOfCollection(0);//收藏次数
			activity.setNumberOfShares(0);//分享次数
			activity.setNumberOfPreviews(0);//预览次数
			activity.setNumberOfApplicants(0);//报名人数
		}
		if(activityService.update(activity)>0) {
			return R.ok();
		}
		return R.error();
	}
	
}
