package com.fly.project.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 项目详情
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-09 11:32:41
 */
public class ProjectInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//项目名称
	private String projectName;
	//项目类型
	private Long projectType;
	//项目LOGO
	private String logo;
	//参加费用
	private BigDecimal tickets;
	//简介
	private String intro;
	//创建时间
	private Date createTime;
	//1:申请中 2:通过 3:拒绝
	private Integer status;
	//订单号
	private Integer order;
	//团队数
	private Integer teamCount;
	//分享次数
	private Integer shareCount;
	
	private String projectTypeName;
	
	private BigDecimal cost;
	
	
	
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getProjectTypeName() {
		return projectTypeName;
	}
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
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
	 * 设置：项目名称
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * 获取：项目名称
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * 设置：项目类型
	 */
	public void setProjectType(Long projectType) {
		this.projectType = projectType;
	}
	/**
	 * 获取：项目类型
	 */
	public Long getProjectType() {
		return projectType;
	}
	/**
	 * 设置：项目LOGO
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取：项目LOGO
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * 设置：参加费用
	 */
	public void setTickets(BigDecimal tickets) {
		this.tickets = tickets;
	}
	/**
	 * 获取：参加费用
	 */
	public BigDecimal getTickets() {
		return tickets;
	}
	/**
	 * 设置：简介
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}
	/**
	 * 获取：简介
	 */
	public String getIntro() {
		return intro;
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
	 * 设置：1:申请中 2:通过 3:拒绝
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：1:申请中 2:通过 3:拒绝
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：订单号
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * 获取：订单号
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * 设置：团队数
	 */
	public void setTeamCount(Integer teamCount) {
		this.teamCount = teamCount;
	}
	/**
	 * 获取：团队数
	 */
	public Integer getTeamCount() {
		return teamCount;
	}
	/**
	 * 设置：分享次数
	 */
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	/**
	 * 获取：分享次数
	 */
	public Integer getShareCount() {
		return shareCount;
	}
}
