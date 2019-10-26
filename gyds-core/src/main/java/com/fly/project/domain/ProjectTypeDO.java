package com.fly.project.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 项目类型
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-09 10:57:06
 */
public class ProjectTypeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//项目类型名称
	private String projectTypeName;
	//创建时间
	private Date createTime;
	//1:是 2:否
	private Integer isPay;
	//费用
	private BigDecimal cost;
	//url
	private String url;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	 * 设置：项目类型名称
	 */
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}
	/**
	 * 获取：项目类型名称
	 */
	public String getProjectTypeName() {
		return projectTypeName;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：1:是 2:否
	 */
	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}
	/**
	 * 获取：1:是 2:否
	 */
	public Integer getIsPay() {
		return isPay;
	}
	/**
	 * 设置：费用
	 */
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	/**
	 * 获取：费用
	 */
	public BigDecimal getCost() {
		return cost;
	}
}
