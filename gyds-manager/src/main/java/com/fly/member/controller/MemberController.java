package com.fly.member.controller;

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

import com.fly.activity.domain.ActivityDO;
import com.fly.domain.UserDO;
import com.fly.system.service.UserService;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;

/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-08 15:52:57
 */
 
@Controller
@RequestMapping("/member/member")
public class MemberController {
	@Autowired
	private UserService userService;
	
	@GetMapping()
	@RequiresPermissions("member:member:member")
	String Member(){
	    return "member/member/member";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("member:member:member")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserDO> memberList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtils = new PageUtils(memberList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("member:member:add")
	String add(){
	    return "member/member/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("member:member:edit")
	String edit(@PathVariable("id") Long id,Model model){
		UserDO member = userService.get(id);
		model.addAttribute("member", member);
	    return "member/member/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("member:member:add")
	public R save( UserDO member){
		if(userService.save(member)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("member:member:edit")
	public R update( UserDO member){
		userService.updatePersonal(member);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("member:member:remove")
	public R remove( Long id){
		if(userService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 *	 实名认证审核
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/examine")
	@RequiresPermissions("member:member:shenhe")
	public R examine(Long id,Integer status) {
		UserDO user = userService.getUser(id);
		user.setIsIdentification(status);
		if(userService.updatePersonal(user)>0){
			return R.ok();
		}
		return R.error();
	}
	
	
}
