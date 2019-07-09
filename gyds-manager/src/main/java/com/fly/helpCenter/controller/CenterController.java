package com.fly.helpCenter.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.fly.domain.UserDO;
import com.fly.helpCenter.dao.TypeTitleDao;
import com.fly.helpCenter.domain.CenterDO;
import com.fly.helpCenter.domain.TypeTitleDO;
import com.fly.helpCenter.service.CenterService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 帮助中心信息表
 * 
 * @author Wangyalei
 * @email 15638836857@163.com
 * @date 2019-07-03 13:57:16
 */
 
@Controller
@RequestMapping("/helpCenter/center")
public class CenterController {
	@Autowired
	private CenterService centerService;
	@Autowired
	private TypeTitleDao typeTitleDao;
	@GetMapping()
	@RequiresPermissions("helpCenter:center:center")
	String Center(){
	    return "helpCenter/center/center";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("helpCenter:center:center")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CenterDO> centerList = centerService.list(query);
		int total = centerService.count(query);
		PageUtils pageUtils = new PageUtils(centerList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("helpCenter:center:add")
	String add(HttpSession session){
		List<TypeTitleDO> typeTitleList = typeTitleDao.list1();
		session.setAttribute("list",typeTitleList);
	    return "helpCenter/center/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("helpCenter:center:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		CenterDO center = centerService.get(id);
		List<TypeTitleDO> typeTitleList = typeTitleDao.list1();
		model.addAttribute("center", center);
		model.addAttribute("list",typeTitleList);
	    return "helpCenter/center/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("helpCenter:center:add")
	public R save( CenterDO center){
		Long user = ShiroUtils.getUserId();
		center.setMemberId(user);
		center.setCreatTime(new Date());
		if("0".equals(center.getStatus())) {
			center.setPublicTime(new Date());
		}
		if(centerService.save(center)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("helpCenter:center:edit")
	public R update( CenterDO center){
		center.setUpdateTime(new Date());
		centerService.update(center);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("helpCenter:center:remove")
	public R remove( Integer id){
		if(centerService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("helpCenter:center:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		centerService.batchRemove(ids);
		return R.ok();
	}
	
}
