package com.fly.team.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 入团申请表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-11 15:31:51
 */
public class ApplyTeamDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增主键
	private Integer id;
	//申请团id
	private Integer applyTeamId;
	//申请团名称
	private String teamName;
	//志愿者姓名
	private String volunteerName;
	//志愿者id
	private Integer zyzId;
	//状态 0:申请中 1:申请成功 2 :已拒绝
	private Integer status;
	//
	private Date applyTeamTime;
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getVolunteerName() {
		return volunteerName;
	}
	public void setVolunteerName(String volunteerName) {
		this.volunteerName = volunteerName;
	}
	/**
	 * 设置：自增主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：自增主键
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：申请团id
	 */
	public void setApplyTeamId(Integer applyTeamId) {
		this.applyTeamId = applyTeamId;
	}
	/**
	 * 获取：申请团id
	 */
	public Integer getApplyTeamId() {
		return applyTeamId;
	}
	/**
	 * 设置：志愿者id
	 */
	public void setZyzId(Integer zyzId) {
		this.zyzId = zyzId;
	}
	/**
	 * 获取：志愿者id
	 */
	public Integer getZyzId() {
		return zyzId;
	}
	/**
	 * 设置：状态 0:申请中 1:申请成功 2 :已拒绝
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态 0:申请中 1:申请成功 2 :已拒绝
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setApplyTeamTime(Date applyTeamTime) {
		this.applyTeamTime = applyTeamTime;
	}
	/**
	 * 获取：
	 */
	public Date getApplyTeamTime() {
		return applyTeamTime;
	}
}
