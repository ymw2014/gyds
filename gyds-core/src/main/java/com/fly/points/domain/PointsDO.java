package com.fly.points.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 积分记录
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-11 14:25:54
 */
public class PointsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//订单号
	private String orderId;
	//支付类型
	private Integer payType;
	//订单状态
	private Integer orderStatus;
	//提交时间
	private Date createTime;
	//积分类型
	private Integer pointsType;
	//金额
	private BigDecimal money;
	//会员id
	private Long memberId;

	//会员姓名
	private String name;
	//会员电话
	private String telephone;
	//会员地址
	private String address;
	//积分
	private Integer integral;
	
	
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	 * 设置：订单号
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单号
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置：支付类型
	 */
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	/**
	 * 获取：支付类型
	 */
	public Integer getPayType() {
		return payType;
	}
	/**
	 * 设置：订单状态
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取：订单状态
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置：提交时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：提交时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：积分类型
	 */
	public void setPointsType(Integer pointsType) {
		this.pointsType = pointsType;
	}
	/**
	 * 获取：积分类型
	 */
	public Integer getPointsType() {
		return pointsType;
	}
	/**
	 * 设置：金额
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	/**
	 * 获取：金额
	 */
	public BigDecimal getMoney() {
		return money;
	}
	/**
	 * 设置：会员id
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	/**
	 * 获取：会员id
	 */
	public Long getMemberId() {
		return memberId;
	}
}
