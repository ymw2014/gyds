package com.fly.project.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 申请参加项目
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-09 16:35:53
 */
public class ProjectDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//项目id
	private Long projectId;
	//团队id
	private Long teamId;
	//1:申请中 2:通过 3:拒绝
	private Integer status;
	//订单
	private Integer order;
	//
	private Date createTime;
	
	private String teamName;
	
	private String projectName;
	
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	 * 设置：项目id
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目id
	 */
	public Long getProjectId() {
		return projectId;
	}
	/**
	 * 设置：团队id
	 */
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	/**
	 * 获取：团队id
	 */
	public Long getTeamId() {
		return teamId;
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
	 * 设置：订单
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * 获取：订单
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
