package com.fly.system.service;

import com.fly.domain.MenuDO;
import com.fly.domain.Tree;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface MenuService {
	Tree<MenuDO> getSysMenuTree(Long id);

	List<Tree <MenuDO>> listMenuTree(Long id);

	List<Tree<MenuDO>> getTree();

	List<Tree<MenuDO>> getTree(Long id);

	List<MenuDO> list(Map <String, Object> params);

	int remove(Long id);

	int save(MenuDO menu);

	int update(MenuDO menu);

	MenuDO get(Long id);

	Set<String> listPerms(Long userId);

	List<Tree<MenuDO>> getRoleTree();
	List<Tree<MenuDO>> getRoleTree(Long userId);
}
