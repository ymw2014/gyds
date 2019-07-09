package com.fly.member.domain;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-08 15:52:57
 */
public class MemberDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Long id;
	//用户名称
	private String name;
	//性别
	private Integer sex;
	//用户头像
	private String headImg;
	//年龄
	private Integer age;
	//家庭住址
	private String address;
	//电话
	private String telephone;
	//登录账号
	private String loginNo;
	//登录密码
	private String password;
	//所属团队编号
	private Integer teamId;
	//账户余额
	private BigDecimal account;
	//红包收益
	private BigDecimal redEnvelopeIncome;
	//平台积分
	private Integer platformIntegral;
	//县
	private String county;
	//市
	private String city;
	//所属区域编号
	private Integer regioncode;
	//省
	private String province;
	//是否实名认证0：否1：是
	private Integer isIdentification;

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
	 * 设置：用户名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：用户名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：性别
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 设置：用户头像
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	/**
	 * 获取：用户头像
	 */
	public String getHeadImg() {
		return headImg;
	}
	/**
	 * 设置：年龄
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * 获取：年龄
	 */
	public Integer getAge() {
		return age;
	}
	/**
	 * 设置：家庭住址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：家庭住址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：电话
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * 获取：电话
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * 设置：登录账号
	 */
	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}
	/**
	 * 获取：登录账号
	 */
	public String getLoginNo() {
		return loginNo;
	}
	/**
	 * 设置：登录密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：登录密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：所属团队编号
	 */
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	/**
	 * 获取：所属团队编号
	 */
	public Integer getTeamId() {
		return teamId;
	}
	/**
	 * 设置：账户余额
	 */
	public void setAccount(BigDecimal account) {
		this.account = account;
	}
	/**
	 * 获取：账户余额
	 */
	public BigDecimal getAccount() {
		return account;
	}
	/**
	 * 设置：红包收益
	 */
	public void setRedEnvelopeIncome(BigDecimal redEnvelopeIncome) {
		this.redEnvelopeIncome = redEnvelopeIncome;
	}
	/**
	 * 获取：红包收益
	 */
	public BigDecimal getRedEnvelopeIncome() {
		return redEnvelopeIncome;
	}
	/**
	 * 设置：平台积分
	 */
	public void setPlatformIntegral(Integer platformIntegral) {
		this.platformIntegral = platformIntegral;
	}
	/**
	 * 获取：平台积分
	 */
	public Integer getPlatformIntegral() {
		return platformIntegral;
	}
	/**
	 * 设置：县
	 */
	public void setCounty(String county) {
		this.county = county;
	}
	/**
	 * 获取：县
	 */
	public String getCounty() {
		return county;
	}
	/**
	 * 设置：市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：所属区域编号
	 */
	public void setRegioncode(Integer regioncode) {
		this.regioncode = regioncode;
	}
	/**
	 * 获取：所属区域编号
	 */
	public Integer getRegioncode() {
		return regioncode;
	}
	/**
	 * 设置：省
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：省
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：是否实名认证0：否1：是
	 */
	public void setIsIdentification(Integer isIdentification) {
		this.isIdentification = isIdentification;
	}
	/**
	 * 获取：是否实名认证0：否1：是
	 */
	public Integer getIsIdentification() {
		return isIdentification;
	}
}
