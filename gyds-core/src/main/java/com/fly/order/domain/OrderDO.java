package com.fly.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 订单表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-12 15:10:18
 */
public class OrderDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Integer id;
	//订单编号
	private String orderNumber;
	//用户编号
	private Long userId;
	//用户名
	private String name;
	//用户昵称
	private String nikeName;
	//团队编号
	private Long teamId;
	//团名称
	private String teamName;
	//下单时间
	private Date businessTime;
	//订单类型1:收入 2：支出 
	private Integer orderType;
	//是否删除0：否1：是
	private Integer isDel;
	//审核人
	private Long examineUser;
	//审核人名称
	private String username;
	//审核状态 1:已完成 2：待审核 3：已拒绝
	private Integer examineStatus;
	//备注信息
	private String remake;
	//等级
	private Integer level;
	//充值类型0:支付宝 1:微信 2:其他
	private Integer cashUpType;
	//提现类型0:支付宝 1:微信 2:其他
	private Integer cashOutType;
	//类型:0:提现 1:充值 2:打赏 3:红包 4:广告费用 5:商城支付
	private Integer expIncType;
	//金额
	private BigDecimal price;
	
	//创建时间
	private Date createTime;
	
	public String getNikeName() {
		return nikeName;
	}
	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
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
	 * 设置：订单编号
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * 获取：订单编号
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * 设置：用户编号
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户编号
	 */
	public Long getUserId() {
		return userId;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	/**
	 * 设置：下单时间
	 */
	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}
	/**
	 * 获取：下单时间
	 */
	public Date getBusinessTime() {
		return businessTime;
	}
	/**
	 * 设置：订单类型1:收入 2：支出 
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	/**
	 * 获取：订单类型1:收入 2：支出 
	 */
	public Integer getOrderType() {
		return orderType;
	}
	/**
	 * 设置：是否删除0：否1：是
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	/**
	 * 获取：是否删除0：否1：是
	 */
	public Integer getIsDel() {
		return isDel;
	}
	/**
	 * 设置：审核人
	 */
	public void setExamineUser(Long examineUser) {
		this.examineUser = examineUser;
	}
	/**
	 * 获取：审核人
	 */
	public Long getExamineUser() {
		return examineUser;
	}
	/**
	 * 设置：审核状态 1:已完成 2：待审核 3：已拒绝
	 */
	public void setExamineStatus(Integer examineStatus) {
		this.examineStatus = examineStatus;
	}
	/**
	 * 获取：审核状态 1:已完成 2：待审核 3：已拒绝
	 */
	public Integer getExamineStatus() {
		return examineStatus;
	}
	/**
	 * 设置：备注信息
	 */
	public void setRemake(String remake) {
		this.remake = remake;
	}
	/**
	 * 获取：备注信息
	 */
	public String getRemake() {
		return remake;
	}
	/**
	 * 设置：等级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：等级
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：充值类型0:支付宝 1:微信 2:其他
	 */
	public void setCashUpType(Integer cashUpType) {
		this.cashUpType = cashUpType;
	}
	/**
	 * 获取：充值类型0:支付宝 1:微信 2:其他
	 */
	public Integer getCashUpType() {
		return cashUpType;
	}
	/**
	 * 设置：提现类型0:支付宝 1:微信 2:其他
	 */
	public void setCashOutType(Integer cashOutType) {
		this.cashOutType = cashOutType;
	}
	/**
	 * 获取：提现类型0:支付宝 1:微信 2:其他
	 */
	public Integer getCashOutType() {
		return cashOutType;
	}
	/**
	 * 设置：类型:0:提现 1:充值 2:打赏 3:红包 4:广告费用 5:商城支付
	 */
	public void setExpIncType(Integer expIncType) {
		this.expIncType = expIncType;
	}
	/**
	 * 获取：类型:0:提现 1:充值 2:打赏 3:红包 4:广告费用 5:商城支付
	 */
	public Integer getExpIncType() {
		return expIncType;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
