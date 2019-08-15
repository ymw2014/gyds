package com.fly.news.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 红包附表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-05 11:55:52
 */
public class RedDO implements Serializable {
	private static final long serialVersionUID = 1L;
	//
	private Long redId;
	//红包表关联id
	private Integer id;
	//金额
	private BigDecimal price;
	//1:未领取 2:领取
	private Integer isGet;
	//领取人id
	private Long getUserId;
	//
	private Date getTime;
	
	public Long getRedId() {
		return redId;
	}
	public void setRedId(Long redId) {
		this.redId = redId;
	}
	/**
	 * 设置：红包表关联id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：红包表关联id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：金额
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：金额
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：1:未领取 2:领取
	 */
	public void setIsGet(Integer isGet) {
		this.isGet = isGet;
	}
	/**
	 * 获取：1:未领取 2:领取
	 */
	public Integer getIsGet() {
		return isGet;
	}
	/**
	 * 设置：领取人id
	 */
	public void setGetUserId(Long getUserId) {
		this.getUserId = getUserId;
	}
	/**
	 * 获取：领取人id
	 */
	public Long getGetUserId() {
		return getUserId;
	}
	/**
	 * 设置：
	 */
	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}
	/**
	 * 获取：
	 */
	public Date getGetTime() {
		return getTime;
	}
}
