package com.fly.system.service.impl;

import com.fly.domain.MenuDO;
import com.fly.domain.Tree;
import com.fly.system.dao.MenuDao;
import com.fly.system.dao.RoleDao;
import com.fly.system.dao.RoleMenuDao;
import com.fly.system.dao.UserRoleDao;
import com.fly.system.service.MenuService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.BuildTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {
	@Autowired
	MenuDao menuMapper;
	@Autowired
	RoleMenuDao roleMenuMapper;
	@Autowired
	UserRoleDao userRoleMapper;

	/**
	 * @param
	 * @return 树形菜单
	 */
	@Cacheable
	@Override
	public Tree<MenuDO> getSysMenuTree(Long id) {
		List<Tree <MenuDO>> trees = new ArrayList<Tree <MenuDO>>();
		List<MenuDO> menuDOs = menuMapper.listMenuByUserId(id);
		for (MenuDO sysMenuDO : menuDOs) {
			Tree <MenuDO> tree = new Tree <MenuDO>();
			tree.setId(sysMenuDO.getMenuId().toString());
			tree.setParentId(sysMenuDO.getParentId().toString());
			tree.setText(sysMenuDO.getName());
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("url", sysMenuDO.getUrl());
			attributes.put("icon", sysMenuDO.getIcon());
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree <MenuDO> t = BuildTree.build(trees,0L);
		return t;
	}

	@Override
	public List<MenuDO> list(Map<String, Object> params) {
		List<MenuDO> menus = menuMapper.list(params);
		return menus;
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int remove(Long id) {
		int result = menuMapper.remove(id);
		return result;
	}
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int save(MenuDO menu) {
		int r = menuMapper.save(menu);
		return r;
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int update(MenuDO menu) {
		int r = menuMapper.update(menu);
		return r;
	}

	@Override
	public MenuDO get(Long id) {
		MenuDO menuDO = menuMapper.get(id);
		return menuDO;
	}

	@Override
	public List<Tree<MenuDO>> getTree() {
		List<Tree <MenuDO>> trees = new ArrayList<Tree <MenuDO>>();
		System.out.println(ShiroUtils.getUser().getRoleIds());
		List<MenuDO> menuDOs = menuMapper.list(new HashMap<>(16));
		for (MenuDO sysMenuDO : menuDOs) {
			Tree <MenuDO> tree = new Tree <MenuDO>();
			tree.setId(sysMenuDO.getMenuId().toString());
			tree.setParentId(sysMenuDO.getParentId().toString());
			tree.setText(sysMenuDO.getName());
			Map<String, Object> state = new HashMap<>(16);
			//state.put("disabled", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<MenuDO>> t = BuildTree.buildList(trees, "0");
		return t;
	}

	@Override
	public List<Tree<MenuDO>> getTree(Long id) {
		// 根据roleId查询权限
		List<MenuDO> menus = menuMapper.list(new HashMap<String, Object>(16));
		List<Long> menuIds = roleMenuMapper.listMenuIdByRoleId(id);
		List<Long> temp = menuIds;
		for (MenuDO menu : menus) {
			if (temp.contains(menu.getParentId())) {
				menuIds.remove(menu.getParentId());
			}
		}
		List<Tree <MenuDO>> trees = new ArrayList<Tree <MenuDO>>();
		List<MenuDO> menuDOs = menuMapper.list(new HashMap<String, Object>(16));
		for (MenuDO sysMenuDO : menuDOs) {
			Tree <MenuDO> tree = new Tree <MenuDO>();
			tree.setId(sysMenuDO.getMenuId().toString());
			tree.setParentId(sysMenuDO.getParentId().toString());
			tree.setText(sysMenuDO.getName());
			Map<String, Object> state = new HashMap<>(16);
			Long menuId = sysMenuDO.getMenuId();
			if (menuIds.contains(menuId)) {
				state.put("selected", true);
			} else {
				state.put("selected", false);
			}
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		 List<Tree<MenuDO>> t = BuildTree.buildList(trees, "0");
		return t;
	}

	@Override
	public Set<String> listPerms(Long userId) {
		List<String> perms = menuMapper.listUserPerms(userId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (StringUtils.isNotBlank(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet;
	}

	@Override
	public List<Tree <MenuDO>> listMenuTree(Long id) {
		List<Tree <MenuDO>> trees = new ArrayList<Tree <MenuDO>>();
		List<MenuDO> menuDOs = menuMapper.listMenuByUserId(id);
		for (MenuDO sysMenuDO : menuDOs) {
			Tree <MenuDO> tree = new Tree <MenuDO>();
			tree.setId(sysMenuDO.getMenuId().toString());
			tree.setParentId(sysMenuDO.getParentId().toString());
			tree.setText(sysMenuDO.getName());
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("url", sysMenuDO.getUrl());
			attributes.put("icon", sysMenuDO.getIcon());
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree <MenuDO>> list = BuildTree.buildList(trees, "0");
		return list;
	}

	@Override
	public List<Tree<MenuDO>> getRoleTree() {
		List<MenuDO> menus = menuMapper.list(new HashMap<String, Object>(16));//获取所有的菜单
		List<Long> roles = userRoleMapper.listRoleId(ShiroUtils.getUserId());//获取用户所有权限ID
		List<Long> menuIds=new ArrayList<>();
		for (Long role :roles) {//将用户所拥有所有角色的菜单编号管理
			menuIds.addAll(roleMenuMapper.listMenuIdByRoleId(role));
		}
		List<Tree <MenuDO>> trees = new ArrayList<Tree <MenuDO>>();
		for (MenuDO menu : menus) {
			if (menuIds.contains(menu.getMenuId())) {
				Tree <MenuDO> tree = new Tree <MenuDO>();
				tree.setId(menu.getMenuId().toString());
				tree.setParentId(menu.getParentId().toString());
				tree.setText(menu.getName());
				Map<String, Object> state = new HashMap<>(16);
				//state.put("disabled", true);
				tree.setState(state);
				trees.add(tree);
			}
		}
		 List<Tree<MenuDO>> t = BuildTree.buildList(trees, "0");
		return t;
	}

	@Override
	public List<Tree<MenuDO>> getRoleTree(Long id) {
		List<Long> roles = userRoleMapper.listRoleId(ShiroUtils.getUserId());//获取用户所有权限ID
		List<Long> menuIds=new ArrayList<>();
		List<Long> ruloMenus = roleMenuMapper.listMenuIdByRoleId(id);
		for (Long role :roles) {//将用户所拥有所有角色的菜单编号管理
			menuIds.addAll(roleMenuMapper.listMenuIdByRoleId(role));
		}
		List<Tree <MenuDO>> trees = new ArrayList<Tree <MenuDO>>();
		List<MenuDO> menuDOs = menuMapper.list(new HashMap<String, Object>(16));
		for (MenuDO sysMenuDO : menuDOs) {
			if (menuIds.contains(sysMenuDO.getMenuId())) {
				Tree <MenuDO> tree = new Tree <MenuDO>();
				tree.setId(sysMenuDO.getMenuId().toString());
				tree.setParentId(sysMenuDO.getParentId().toString());
				tree.setText(sysMenuDO.getName());
				Map<String, Object> state = new HashMap<>(16);
				Long menuId = sysMenuDO.getMenuId();
				if (ruloMenus.contains(menuId)) {
					state.put("selected", true);
				} else {
					state.put("selected", false);
				}
				tree.setState(state);
				trees.add(tree);
			}
			
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		 List<Tree<MenuDO>> t = BuildTree.buildList(trees, "0");
		return t;
	}

}
