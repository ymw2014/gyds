package com.fly.helpCenter.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



/**
 * 
 * 
 * @author Wangyalei
 * @email 15638836857@163.com
 * @date 2019-07-03 14:08:01
 */
public class TypeTitleDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键id
	private Long id;
	//帮助类型
	private String helpTitle;
	//创建时间
	private Date creatTime;
	//0:未删除 1:删除
	private Integer isDel;
	
	private List<CenterDO> center;

	
	public List<CenterDO> getCenter() {
		return center;
	}
	public void setCenter(List<CenterDO> center) {
		this.center = center;
	}
	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setHelpTitle(String helpTitle) {
		this.helpTitle = helpTitle;
	}
	/**
	 * 获取：
	 */
	public String getHelpTitle() {
		return helpTitle;
	}
	/**
	 * 设置：
	 */
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreatTime() {
		return creatTime;
	}
	/**
	 * 设置：
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	/**
	 * 获取：
	 */
	public Integer getIsDel() {
		return isDel;
	}
}
