package com.fly.activity.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 活动报名表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-08 11:07:53
 */
public class ApplyDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Long id;
	//志愿者编号
	private Integer zyzId;
	//志愿者名称
	private String volunteerName;
	//报名活动编号
	private Long actId;
	//活动名称
	private String actTitle;
	//报名时间
	private Date createTime;
	//审核时间
	private Date examine;
	//审核状态 0：待审核 1：审核通过2.拒绝
	private Integer status;
	//拒绝理由
	private String reasonForRefusal;
	
	private String createTimeStr;
	
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getVolunteerName() {
		return volunteerName;
	}
	public void setVolunteerName(String volunteerName) {
		this.volunteerName = volunteerName;
	}
	public String getActTitle() {
		return actTitle;
	}
	public void setActTitle(String actTitle) {
		this.actTitle = actTitle;
	}
	/**
	 * 设置：自增编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：自增编号
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：志愿者编号
	 */
	public void setZyzId(Integer zyzId) {
		this.zyzId = zyzId;
	}
	/**
	 * 获取：志愿者编号
	 */
	public Integer getZyzId() {
		return zyzId;
	}
	/**
	 * 设置：报名活动编号
	 */
	public void setActId(Long actId) {
		this.actId = actId;
	}
	/**
	 * 获取：报名活动编号
	 */
	public Long getActId() {
		return actId;
	}
	/**
	 * 设置：报名时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：报名时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：审核时间
	 */
	public void setExamine(Date examine) {
		this.examine = examine;
	}
	/**
	 * 获取：审核时间
	 */
	public Date getExamine() {
		return examine;
	}
	/**
	 * 设置：审核状态 0：待审核 1：审核通过2.拒绝
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：审核状态 0：待审核 1：审核通过2.拒绝
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：拒绝理由
	 */
	public void setReasonForRefusal(String reasonForRefusal) {
		this.reasonForRefusal = reasonForRefusal;
	}
	/**
	 * 获取：拒绝理由
	 */
	public String getReasonForRefusal() {
		return reasonForRefusal;
	}
}
