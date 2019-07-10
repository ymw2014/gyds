package com.fly.news.controller;

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
import com.fly.domain.UserDO;
import com.fly.news.domain.InfoDO;
import com.fly.news.service.InfoService;
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

	@GetMapping()
	@RequiresPermissions("news:info:info")
	String Info(){
	    return "news/info/info";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("news:info:info")
	public PageUtils list(@RequestParam Map<String, Object> params){
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
	String audit(@PathVariable("id") Integer id,Model model){
		InfoDO info = infoService.get(id);
		model.addAttribute("info", info);
	    return "news/info/audit";
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