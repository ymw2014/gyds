<<<<<<< HEAD
package com.fly.proxybusi.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-10 13:57:24
 */
public class ProxybusiDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Long id;
	//代理商名称
	private String name;
	//状态
	private Integer status;
	//商家等级
	private Integer level;
	//审核时间
	private Date auditTime;
	//入团时间
	private Date createTime;
	//审核转态
	private Integer auditStatus;
	//代理级别（省，市，县）
	private Integer regionLevel;
	//
	private Long userId;
	//会员年龄
	private int age;
	//会员所在城市
	private String city;
	//会员所在县
	private String county;
	//会员姓名
	private String memberName;
	//会员省
	private String province;
	//会员的电话
	private String telephone;

	private int auditResult;
	
	private String auditOpinion;
	
	private String proxyAddr;
	
	private String proxyTelephone;
	
	private int  proxyRegion;
	
	
	public String getProxyTelephone() {
		return proxyTelephone;
	}
	public void setProxyTelephone(String proxyTelephone) {
		this.proxyTelephone = proxyTelephone;
	}
	public int getProxyRegion() {
		return proxyRegion;
	}
	public void setProxyRegion(int proxyRegion) {
		this.proxyRegion = proxyRegion;
	}
	public String getProxyAddr() {
		return proxyAddr;
	}
	public void setProxyAddr(String proxyAddr) {
		this.proxyAddr = proxyAddr;
	}
	public int getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(int auditResult) {
		this.auditResult = auditResult;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * 设置：自增编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：自增编号
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：代理商名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：代理商名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：商家等级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：商家等级
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：审核时间
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	/**
	 * 获取：审核时间
	 */
	public Date getAuditTime() {
		return auditTime;
	}
	/**
	 * 设置：入团时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：入团时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：审核转态
	 */
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	/**
	 * 获取：审核转态
	 */
	public Integer getAuditStatus() {
		return auditStatus;
	}
	/**
	 * 设置：代理级别（省，市，县）
	 */
	public void setRegionLevel(Integer regionLevel) {
		this.regionLevel = regionLevel;
	}
	/**
	 * 获取：代理级别（省，市，县）
	 */
	public Integer getRegionLevel() {
		return regionLevel;
	}
	/**
	 * 设置：
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public Long getUserId() {
		return userId;
	}
}
=======
package com.fly.proxybusi.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-10 13:57:24
 */
public class ProxybusiDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Long id;
	//代理商名称
	private String name;
	//状态
	private Integer status;
	//商家等级
	private Integer level;
	//审核时间
	private Date auditTime;
	//入团时间
	private Date createTime;
	//审核转态
	private Integer auditStatus;
	//代理级别（省，市，县）
	private Integer regionLevel;
	//
	private Long userId;
	//会员年龄
	private int age;
	//会员所在城市
	private String city;
	//会员所在县
	private String county;
	//会员姓名
	private String memberName;
	//会员省
	private String province;
	//会员的电话
	private String telephone;

	private int auditResult;
	
	private String auditOpinion;
	
	private String proxyAddr;
	
	private String proxyTelephone;
	
	private int  proxyRegion;
	
	
	public String getProxyTelephone() {
		return proxyTelephone;
	}
	public void setProxyTelephone(String proxyTelephone) {
		this.proxyTelephone = proxyTelephone;
	}
	public int getProxyRegion() {
		return proxyRegion;
	}
	public void setProxyRegion(int proxyRegion) {
		this.proxyRegion = proxyRegion;
	}
	public String getProxyAddr() {
		return proxyAddr;
	}
	public void setProxyAddr(String proxyAddr) {
		this.proxyAddr = proxyAddr;
	}
	public int getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(int auditResult) {
		this.auditResult = auditResult;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * 设置：自增编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：自增编号
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：代理商名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：代理商名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：商家等级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：商家等级
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：审核时间
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	/**
	 * 获取：审核时间
	 */
	public Date getAuditTime() {
		return auditTime;
	}
	/**
	 * 设置：入团时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：入团时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：审核转态
	 */
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	/**
	 * 获取：审核转态
	 */
	public Integer getAuditStatus() {
		return auditStatus;
	}
	/**
	 * 设置：代理级别（省，市，县）
	 */
	public void setRegionLevel(Integer regionLevel) {
		this.regionLevel = regionLevel;
	}
	/**
	 * 获取：代理级别（省，市，县）
	 */
	public Integer getRegionLevel() {
		return regionLevel;
	}
	/**
	 * 设置：
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public Long getUserId() {
		return userId;
	}
}
>>>>>>> d7599c5df10b932a0c1fc85b5b2e9708ca6a2471
