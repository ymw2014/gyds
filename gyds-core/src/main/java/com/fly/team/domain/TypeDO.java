package com.fly.team.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-04 10:09:09
 */
public class TypeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String typeName;
	//是否收费0：否 1：是
	private Integer isCharge;
	//
	private Double price;
	//创建时间
	private Date createTime;

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
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取：
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置：是否收费0：否 1：是
	 */
	public void setIsCharge(Integer isCharge) {
		this.isCharge = isCharge;
	}
	/**
	 * 获取：是否收费0：否 1：是
	 */
	public Integer getIsCharge() {
		return isCharge;
	}
	/**
	 * 设置：
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * 获取：
	 */
	public Double getPrice() {
		return price;
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
}
