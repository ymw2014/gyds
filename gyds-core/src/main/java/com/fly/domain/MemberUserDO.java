package com.fly.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 
 * @author mars
 * @email 15638836857@163.com
 * @date 2019-02-13 13:38:37
 */
public class MemberUserDO implements Serializable {
	private static final long serialVersionUID = 1L;


	//主键
	private Integer id;
	//
	private String openid;
	//微信昵称
	private String nickname;
	//性别描述
	private String sexdesc;
	//性别 1男 0女
	private Integer sex;
	//语言
	private String language;
	//城市
	private String city;
	//
	private String province;
	//手机号
	private String phone;
	//
	private String country;
	//
	private String headimgurl;
	//
	private String privileges;
	//邮箱
	private String email;
	//
	private Integer integral;

	private String receiveAddress;

	private BigDecimal balance;

	private BigDecimal totalCommission;

	private BigDecimal refundCommission;

	private String sellerName;

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public BigDecimal getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(BigDecimal totalCommission) {
		this.totalCommission = totalCommission;
	}

	public BigDecimal getRefundCommission() {
		return refundCommission;
	}

	public void setRefundCommission(BigDecimal refundCommission) {
		this.refundCommission = refundCommission;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public MemberUserDO(){

	}


	/**
	 * 设置：主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * 设置：微信昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：微信昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置：性别描述
	 */
	public void setSexdesc(String sexdesc) {
		this.sexdesc = sexdesc;
	}
	/**
	 * 获取：性别描述
	 */
	public String getSexdesc() {
		return sexdesc;
	}
	/**
	 * 设置：性别 1男 0女
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别 1男 0女
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 设置：语言
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * 获取：语言
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * 设置：城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：城市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * 获取：
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * 设置：
	 */
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	/**
	 * 获取：
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}
	/**
	 * 设置：
	 */
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
	/**
	 * 获取：
	 */
	public String getPrivileges() {
		return privileges;
	}
	/**
	 * 设置：邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：
	 */
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	/**
	 * 获取：
	 */
	public Integer getIntegral() {
		return integral;
	}
}
