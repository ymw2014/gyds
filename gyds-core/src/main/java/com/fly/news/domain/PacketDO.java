package com.fly.news.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 红包主表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-05 11:56:18
 */
public class PacketDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//总金额
	private BigDecimal money;
	//红包总数量
	private Integer count;
	//咨询id
	private Long newId;
	//发红包用户
	private Long userId;
	//创建时间
	private Date creatTime;

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
	 * 设置：总金额
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	/**
	 * 获取：总金额
	 */
	public BigDecimal getMoney() {
		return money;
	}
	/**
	 * 设置：红包总数量
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * 获取：红包总数量
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置：咨询id
	 */
	public void setNewId(Long newId) {
		this.newId = newId;
	}
	/**
	 * 获取：咨询id
	 */
	public Long getNewId() {
		return newId;
	}
	/**
	 * 设置：发红包用户
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：发红包用户
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreatTime() {
		return creatTime;
	}
}
