package com.fly.adv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 广告价格配置表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-19 15:27:07
 */
public class AdvPriceDO implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private Integer id;
	//区域编号
	private Long regionCode;
	//区域编号
	private String regionName;
	//展现位置
	private Integer positionNum;
	//序号
	private Integer sort;
	//价格/天
	private BigDecimal price;
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
	 * 设置：区域编号
	 */
	public void setRegionCode(Long regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * 获取：区域编号
	 */
	public Long getRegionCode() {
		return regionCode;
	}
	/**
	 * 设置：展现位置
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}
	/**
	 * 获取：展现位置
	 */
	public Integer getPositionNum() {
		return positionNum;
	}
	/**
	 * 设置：序号
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：序号
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * 设置：价格/天
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：价格/天
	 */
	public BigDecimal getPrice() {
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
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
}
