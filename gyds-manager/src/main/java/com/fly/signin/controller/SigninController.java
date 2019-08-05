package com.fly.signin.controller;

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

import com.fly.signin.domain.SigninDO;
import com.fly.signin.service.SigninService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 签到表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-29 11:28:41
 */
 
@Controller
@RequestMapping("/signin/signin")
public class SigninController {
	@Autowired
	private SigninService signinService;
	
	@GetMapping()
	@RequiresPermissions("signin:signin:signin")
	String Signin(){
	    return "signin/signin/signin";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("signin:signin:signin")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SigninDO> signinList = signinService.list(query);
		int total = signinService.count(query);
		PageUtils pageUtils = new PageUtils(signinList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("signin:signin:add")
	String add(){
	    return "signin/signin/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("signin:signin:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		SigninDO signin = signinService.get(id);
		model.addAttribute("signin", signin);
	    return "signin/signin/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("signin:signin:add")
	public R save( SigninDO signin){
		if(signinService.save(signin)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("signin:signin:edit")
	public R update( SigninDO signin){
		signinService.update(signin);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("signin:signin:remove")
	public R remove( Integer id){
		if(signinService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("signin:signin:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		signinService.batchRemove(ids);
		return R.ok();
	}
	
}
