package com.fly.team.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 团队认证表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-28 15:21:01
 */
public class AutheDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增id
	private Long id;
	//负责人电话
	private String tel;
	//负责人姓名
	private String name;
	//父级部门
	private String parentDept;
	//公函
	private String official;
	//价格
	private BigDecimal price;
	//团队id
	private Long teamId;
	//创建时间
	private Date createTime;
	//状态 1: 未审核 2:已审核 3:拒绝
	private Integer status;
	//订单
	private Long order;
	//团队名称
	private String teamName;
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	/**
	 * 设置：自增id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：自增id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：负责人电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * 获取：负责人电话
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * 设置：负责人姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：负责人姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：父级部门
	 */
	public void setParentDept(String parentDept) {
		this.parentDept = parentDept;
	}
	/**
	 * 获取：父级部门
	 */
	public String getParentDept() {
		return parentDept;
	}
	/**
	 * 设置：公函
	 */
	public void setOfficial(String official) {
		this.official = official;
	}
	/**
	 * 获取：公函
	 */
	public String getOfficial() {
		return official;
	}
	/**
	 * 设置：价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：价格
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：团队id
	 */
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	/**
	 * 获取：团队id
	 */
	public Long getTeamId() {
		return teamId;
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
	/**
	 * 设置：状态 1: 未审核 2:已审核 3:拒绝
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态 1: 未审核 2:已审核 3:拒绝
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：订单
	 */
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	
	
}
