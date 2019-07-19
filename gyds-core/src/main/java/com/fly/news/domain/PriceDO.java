package com.fly.news.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 区域置顶价格配置表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-18 16:01:07
 */
public class PriceDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Integer id;
	//置顶区域
	private Integer regionCode;
	//价格
	private BigDecimal priceOfDay;

	/**
	 * 设置：自增编号
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：自增编号
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：置顶区域
	 */
	public void setRegionCode(Integer regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * 获取：置顶区域
	 */
	public Integer getRegionCode() {
		return regionCode;
	}
	/**
	 * 设置：价格
	 */
	public void setPriceOfDay(BigDecimal priceOfDay) {
		this.priceOfDay = priceOfDay;
	}
	/**
	 * 获取：价格
	 */
	public BigDecimal getPriceOfDay() {
		return priceOfDay;
	}
}
