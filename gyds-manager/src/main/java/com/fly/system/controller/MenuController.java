package com.fly.system.controller;

import java.util.ArrayList;
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
import com.fly.common.annotation.Log;
import com.fly.common.controller.BaseController;
import com.fly.domain.MenuDO;
import com.fly.domain.Tree;
import com.fly.system.service.MenuService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.R;

/**
 *
 */
@RequestMapping("/sys/menu")
@Controller
public class MenuController extends BaseController {
	String prefix = "system/menu";
	@Autowired
	MenuService menuService;

	@RequiresPermissions("sys:menu:menu")
	@GetMapping()
	String menu(Model model) {
		return prefix+"/menu";
	}

	@RequiresPermissions("sys:menu:menu")
	@RequestMapping("/list")
	@ResponseBody
	List<MenuDO> list(@RequestParam Map<String, Object> params) {
		List<MenuDO> menus = menuService.list(params);
		return menus;
	}

	@Log("添加菜单")
	@RequiresPermissions("sys:menu:add")
	@GetMapping("/add/{pId}")
	String add(Model model, @PathVariable("pId") Long pId) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", menuService.get(pId).getName());
		}
		return prefix + "/add";
	}

	@Log("编辑菜单")
	@RequiresPermissions("sys:menu:edit")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		MenuDO mdo = menuService.get(id);
		Long pId = mdo.getParentId();
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", menuService.get(pId).getName());
		}
		model.addAttribute("menu", mdo);
		return prefix+"/edit";
	}

	@Log("保存菜单")
	@RequiresPermissions("sys:menu:add")
	@PostMapping("/save")
	@ResponseBody
	R save(MenuDO menu) {
		if (menuService.save(menu) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}

	@Log("更新菜单")
	@RequiresPermissions("sys:menu:edit")
	@PostMapping("/update")
	@ResponseBody
	R update(MenuDO menu) {
		if (menuService.update(menu) > 0) {
			return R.ok();
		} else {
			return R.error(1, "更新失败");
		}
	}

	@Log("删除菜单")
	@RequiresPermissions("sys:menu:remove")
	@PostMapping("/remove")
	@ResponseBody
	R remove(Long id) {
		if (menuService.remove(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}

	@GetMapping("/tree")
	@ResponseBody
	List<Tree<MenuDO>> tree() {
		List<Tree<MenuDO>> tree = new ArrayList<Tree<MenuDO>>();
		System.out.println(ShiroUtils.getUserId());
		if(ShiroUtils.getUserId()==1) {//超管加载所有权限列表
			tree = menuService.getTree();
		}else {//非超管,只加载自己仅有的权限菜单
			tree = menuService.getRoleTree();
		}
		
		return tree;
	}

	@GetMapping("/tree/{roleId}")
	@ResponseBody
	List<Tree<MenuDO>> tree(@PathVariable("roleId") Long roleId) {
		List<Tree<MenuDO>> tree = menuService.getTree(roleId);
		if(ShiroUtils.getUserId()==1) {//超管加载所有权限列表
			tree = menuService.getTree(roleId);
		}else {//非超管,只加载自己仅有的权限菜单
			tree = menuService.getRoleTree(roleId);
		}
		
		return tree;
	}
}
