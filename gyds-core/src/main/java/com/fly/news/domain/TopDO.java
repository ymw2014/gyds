package com.fly.news.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 置顶申请列表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-22 15:14:59
 */
public class TopDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增id
	private Integer id;
	//咨询id
	private Integer newsId;
	//用户id
	private Long userId;
	//置顶开始时间
	private Date topStartTime;
	//置顶结束时间
	private Date topEndTime;
	//置顶费用
	private BigDecimal topPrice;
	//置顶状态1:通过 2:拒绝
	private Integer status;
	//订单号
	private Integer ordernumber;
	//制定天数
	private Integer topDay;
	//置顶区域
	private Long regionCode;

	public Long getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(Long regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * 设置：自增id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：自增id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：咨询id
	 */
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	/**
	 * 获取：咨询id
	 */
	public Integer getNewsId() {
		return newsId;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：置顶开始时间
	 */
	public void setTopStartTime(Date topStartTime) {
		this.topStartTime = topStartTime;
	}
	/**
	 * 获取：置顶开始时间
	 */
	public Date getTopStartTime() {
		return topStartTime;
	}
	/**
	 * 设置：置顶结束时间
	 */
	public void setTopEndTime(Date topEndTime) {
		this.topEndTime = topEndTime;
	}
	/**
	 * 获取：置顶结束时间
	 */
	public Date getTopEndTime() {
		return topEndTime;
	}
	/**
	 * 设置：置顶费用
	 */
	public void setTopPrice(BigDecimal topPrice) {
		this.topPrice = topPrice;
	}
	/**
	 * 获取：置顶费用
	 */
	public BigDecimal getTopPrice() {
		return topPrice;
	}
	/**
	 * 设置：置顶状态1:通过 2:拒绝
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：置顶状态1:通过 2:拒绝
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：订单号
	 */
	public void setOrdernumber(Integer ordernumber) {
		this.ordernumber = ordernumber;
	}
	/**
	 * 获取：订单号
	 */
	public Integer getOrdernumber() {
		return ordernumber;
	}
	/**
	 * 设置：制定天数
	 */
	public void setTopDay(Integer topDay) {
		this.topDay = topDay;
	}
	/**
	 * 获取：制定天数
	 */
	public Integer getTopDay() {
		return topDay;
	}
}
