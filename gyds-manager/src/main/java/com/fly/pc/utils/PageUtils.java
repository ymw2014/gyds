package com.fly.pc.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageUtils extends LinkedHashMap<String, Object>{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		// 
		private int page;
		// 每页条数
		private int limit = 10;
	public PageUtils(Map<String, Object> params) {
		this.putAll(params);
		// 分页参数
		this.page = Integer.parseInt(params.get("page").toString());
		this.put("offset", (page-1)*limit);
		this.put("page", page / limit + 1);
		this.put("limit", limit);
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
