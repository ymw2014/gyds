package com.fly.volunteer.controller;

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

import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;
import com.fly.volunteer.domain.VolunteerDO;
import com.fly.volunteer.service.VolunteerService;

/**
 * 志愿者表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-09 13:42:20
 */
 
@Controller
@RequestMapping("/volunteer/volunteer")
public class VolunteerController {
	@Autowired
	private VolunteerService volunteerService;
	
	@GetMapping()
	@RequiresPermissions("volunteer:volunteer:volunteer")
	String Volunteer(){
	    return "volunteer/volunteer/volunteer";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("volunteer:volunteer:volunteer")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<VolunteerDO> volunteerList = volunteerService.list(query);
		int total = volunteerService.count(query);
		PageUtils pageUtils = new PageUtils(volunteerList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("volunteer:volunteer:add")
	String add(){
	    return "volunteer/volunteer/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("volunteer:volunteer:edit")
	String edit(@PathVariable("id") Long id,Model model){
		VolunteerDO volunteer = volunteerService.get(id);
		model.addAttribute("volunteer", volunteer);
	    return "volunteer/volunteer/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("volunteer:volunteer:add")
	public R save( VolunteerDO volunteer){
		if(volunteerService.save(volunteer)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("volunteer:volunteer:edit")
	public R update( VolunteerDO volunteer){
		volunteerService.update(volunteer);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("volunteer:volunteer:remove")
	public R remove( Long id){
		if(volunteerService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("volunteer:volunteer:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		volunteerService.batchRemove(ids);
		return R.ok();
	}
	
}
