package com.fly.utils;


import com.fly.domain.Tree;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class BuildTree {

	public static <T> Tree <T> build(List<Tree<T>> nodes,Long ids) {

		if (nodes == null) {
			return null;
		}
		List<Tree<T>> topNodes = new ArrayList<Tree<T>>();

		for (Tree<T> children : nodes) {

			String pid = children.getParentId();
			if (pid == null || ids.toString().equals(pid)) {
				topNodes.add(children);
				continue;
			}
			

			for (Tree<T> parent : nodes) {
				String id = parent.getId();
				if (id != null && id.equals(pid)) {
					parent.getChildren().add(children);
					children.setHasParent(true);
					parent.setChildren(true);
					continue;
				}
			}

		}

		Tree<T> root = new Tree<T>();
		System.out.println(topNodes.get(0));
		if (topNodes.size() == 1) {
				root = topNodes.get(0);
				root.setChildren(true);
		} else {
			root.setId(ids.toString());
			root.setParentId("");
			root.setHasParent(false);
			root.setChildren(true);
			root.setChecked(true);
			root.setChildren(topNodes);
			root.setText("顶级节点");
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			root.setState(state);
		}

		return root;
	}

	public static <T> List<Tree<T>> buildList(List<Tree<T>> nodes, String idParam) {
		if (nodes == null) {
			return null;
		}
		List<Tree<T>> topNodes = new ArrayList<Tree<T>>();

		for (Tree<T> children : nodes) {

			String pid = children.getParentId();
			if (pid == null || idParam.equals(pid)) {
				topNodes.add(children);

				continue;
			}

			for (Tree<T> parent : nodes) {
				String id = parent.getId();
				if (id != null && id.equals(pid)) {
					parent.getChildren().add(children);
					children.setHasParent(true);
					parent.setChildren(true);

					continue;
				}
			}

		}
		return topNodes;
	}

}