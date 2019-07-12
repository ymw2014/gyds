package com.fly.adv.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-12 11:20:03
 */
public class DetailDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private Integer memeberId;
	//
	private Integer positionNum;
	//
	private Integer regionCode;
	//
	private String advUrl;
	//
	private Date createTime;
	//图片
	private String advImg;
	//
	private Date showStartTime;
	//
	private Date showEndTime;
	//处理状态 0:未处理 1:已处理
	private Integer status;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setMemeberId(Integer memeberId) {
		this.memeberId = memeberId;
	}
	/**
	 * 获取：
	 */
	public Integer getMemeberId() {
		return memeberId;
	}
	/**
	 * 设置：
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}
	/**
	 * 获取：
	 */
	public Integer getPositionNum() {
		return positionNum;
	}
	/**
	 * 设置：
	 */
	public void setRegionCode(Integer regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * 获取：
	 */
	public Integer getRegionCode() {
		return regionCode;
	}
	/**
	 * 设置：
	 */
	public void setAdvUrl(String advUrl) {
		this.advUrl = advUrl;
	}
	/**
	 * 获取：
	 */
	public String getAdvUrl() {
		return advUrl;
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
	/**
	 * 设置：图片
	 */
	public void setAdvImg(String advImg) {
		this.advImg = advImg;
	}
	/**
	 * 获取：图片
	 */
	public String getAdvImg() {
		return advImg;
	}
	/**
	 * 设置：
	 */
	public void setShowStartTime(Date showStartTime) {
		this.showStartTime = showStartTime;
	}
	/**
	 * 获取：
	 */
	public Date getShowStartTime() {
		return showStartTime;
	}
	/**
	 * 设置：
	 */
	public void setShowEndTime(Date showEndTime) {
		this.showEndTime = showEndTime;
	}
	/**
	 * 获取：
	 */
	public Date getShowEndTime() {
		return showEndTime;
	}
	/**
	 * 设置：处理状态 0:未处理 1:已处理
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：处理状态 0:未处理 1:已处理
	 */
	public Integer getStatus() {
		return status;
	}
}
