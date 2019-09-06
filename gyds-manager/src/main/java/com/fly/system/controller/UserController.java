package com.fly.system.controller;


import com.fly.common.annotation.Log;
import com.fly.common.controller.BaseController;
import com.fly.domain.*;
import com.fly.system.service.RegionService;
import com.fly.system.service.RoleService;
import com.fly.system.service.UserService;
import com.fly.system.utils.MD5Utils;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.Constant;
import com.fly.utils.PageUtils;
import com.fly.utils.Query;
import com.fly.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制器
 */
@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {
	private String prefix="system/user"  ;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	private RegionService regionService;
	@RequiresPermissions("sys:user:user")
	@GetMapping("")
	String user(Model model) {
		return prefix + "/user";
	}

	@GetMapping("/list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		params.put("pids", ShiroUtils.getUser().getDeptId());
		String ids = regionService.getTeamAndAreaByUserRole(ShiroUtils.getUser().getDeptId());
		params.put("ids", ids);
		params.put("isManage", 1);
		Query query = new Query(params);
		List<UserDO> sysUserList = userService.list(query);
		for (UserDO userDO : sysUserList) {
			RegionDO region = regionService.get(userDO.getDeptId());
			switch (region.getRegionLevel()) {
			case 0:
				String admin=regionService.get(userDO.getDeptId()).getRegionName();
				userDO.setDeptName(admin+"(全国)");
				break;
			case 1:
				String pronvice=regionService.get(userDO.getDeptId()).getRegionName();
				userDO.setDeptName(pronvice);
				break;
			case 2:
				RegionDO cityRetion = regionService.get(userDO.getDeptId());
				RegionDO proRetion = regionService.get(cityRetion.getParentRegionCode());
				userDO.setDeptName(proRetion.getRegionName()+"&nbsp;"+cityRetion.getRegionName());
				break;
			case 3:
				RegionDO areaRetion = regionService.get(userDO.getDeptId());
				RegionDO cityRetion1 = regionService.get(areaRetion.getParentRegionCode());
				RegionDO proRetion1 = regionService.get(cityRetion1.getParentRegionCode());
				userDO.setDeptName(proRetion1.getRegionName()+"&nbsp;"+cityRetion1.getRegionName()+"&nbsp;"+areaRetion.getRegionName());
				break;
			case 4:
				RegionDO jdbRetion = regionService.get(userDO.getDeptId());
				RegionDO areaRetion2 = regionService.get(jdbRetion.getParentRegionCode());
				RegionDO cityRetion2 = regionService.get(areaRetion2.getParentRegionCode());
				RegionDO proRetion2 = regionService.get(cityRetion2.getParentRegionCode());
				userDO.setDeptName(proRetion2.getRegionName()+"&nbsp;"+cityRetion2.getRegionName()+"&nbsp;"+areaRetion2.getRegionName()+"&nbsp;"+jdbRetion.getRegionName());
				break;
			case 5:
				RegionDO teamRegion = regionService.get(userDO.getDeptId());
				RegionDO jdbRetion5 = regionService.get(teamRegion.getParentRegionCode());
				RegionDO areaRetion5 = regionService.get(jdbRetion5.getParentRegionCode());
				RegionDO cityRetion5 = regionService.get(areaRetion5.getParentRegionCode());
				RegionDO proRetion5 = regionService.get(cityRetion5.getParentRegionCode());
				userDO.setDeptName(proRetion5.getRegionName()+"&nbsp;"+cityRetion5.getRegionName()+"&nbsp;"+areaRetion5.getRegionName()+"&nbsp;"+jdbRetion5.getRegionName()+"&nbsp;"+teamRegion.getRegionName());
				break;	

			default:
				break;
			}
		}
		int total = userService.count(query);
		PageUtils pageUtil = new PageUtils(sysUserList, total);
		return pageUtil;
	}

	@RequiresPermissions("sys:user:add")
	@Log("添加用户")
	@GetMapping("/add")
	String add(Model model) {
		Map<String,Object> params=new HashMap<String,Object>(16);
		params.put("userIdCreate", ShiroUtils.getUserId());
		List<RoleDO> roles = roleService.list(params);
		model.addAttribute("roles", roles);
		return prefix + "/add";
	}

	@RequiresPermissions("sys:user:edit")
	@Log("编辑用户")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		UserDO userDO = userService.get(id);
		model.addAttribute("user", userDO);
		List<RoleDO> roles = roleService.list(id);
		model.addAttribute("roles", roles);
		return prefix+"/edit";
	}

	@RequiresPermissions("sys:user:add")
	@Log("保存用户")
	@PostMapping("/save")
	@ResponseBody
	R save(UserDO user) {
		/*if (Constant.ADMIN.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}*/
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		user.setIsManage(1);
		if (userService.save(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:edit")
	@Log("更新用户")
	@PostMapping("/update")
	@ResponseBody
	R update(UserDO user) {
		if (userService.update(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("sys:user:edit")
	@Log("更新用户")
	@PostMapping("/updatePeronal")
	@ResponseBody
	R updatePeronal(UserDO user) {
		if (userService.updatePersonal(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("sys:user:remove")
	@Log("删除管理员")
	@PostMapping("/remove")
	@ResponseBody
	R remove(Long id) {
		UserDO user = userService.get(id);
		if (Constant.ADMIN.equals(user.getUsername())) {
			return R.error(1, "超管用户不允许删除");
		}
		user.setIsManage(0);
		if (userService.updatePersonal(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:batchRemove")
	@Log("批量删除用户")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] userIds) {
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exit(params);
	}

	@RequiresPermissions("sys:user:resetPwd")
	@Log("请求更改用户密码")
	@GetMapping("/resetPwd/{id}")
	String resetPwd(@PathVariable("id") Long userId, Model model) {

		UserDO userDO = new UserDO();
		userDO.setUserId(userId);
		model.addAttribute("user", userDO);
		return prefix + "/reset_pwd";
	}

	@Log("提交更改用户密码")
	@PostMapping("/resetPwd")
	@ResponseBody
	R resetPwd(UserVO userVO) {
		try{
			userService.resetPwd(userVO,getUser());
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@RequiresPermissions("sys:user:resetPwd")
	@Log("admin提交更改用户密码")
	@PostMapping("/adminResetPwd")
	@ResponseBody
	R adminResetPwd(UserVO userVO) {
		try{
			userService.adminResetPwd(userVO);
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = userService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/userTree";
	}

	@GetMapping("/personal")
	String personal(Model model) {
		UserDO userDO  = userService.get(getUserId());
		model.addAttribute("user",userDO);
		return prefix + "/personal";
	}
	@ResponseBody
	@PostMapping("/uploadImg")
	R uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		Map<String, Object> result = new HashMap<>();
		try {
			result = userService.updatePersonalImg(file, avatar_data, getUserId());
		} catch (Exception e) {
			return R.error("更新图像失败！");
		}
		if(result!=null && result.size()>0){
			return R.ok(result);
		}else {
			return R.error("更新图像失败！");
		}
	}
}
