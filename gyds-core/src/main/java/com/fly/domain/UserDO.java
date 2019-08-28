package com.fly.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;



/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-17 11:59:54
 */
public class UserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long userId;
	//用户名
	private String username;
	//姓名
	private String name;
	//密码
	private String password;
	//昵称
	private String nikeName;
	//是否绑定账号
	private Integer isBinding;
	//组织ID
	private Long deptId;
	private String deptName;
	//用户头像
	private String headImg;
	//账户余额
	private BigDecimal account;
	//平台积分
	private Integer platformIntegral;
	//邮箱
	private String email;
	//手机号
	private String mobile;
	//状态 0:禁用，1:正常
	private Integer status;
	//创建用户id
	private Long userIdCreate;
	//创建时间
	private Date gmtCreate;
	//修改时间
	private Date gmtModified;
	//角色
    private List<Long> roleIds;
	//性别
	private Integer sex;
	//出身日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth;
	//
	private Long picId;
	//现居住地
	private String liveAddress;
	//爱好
	private String hobby;
	//省份
	private String province;
	//所在城市
	private String city;
	//所在地区
	private String district;
	//所在街道
	private String street;
	//是否实名认证0否1:是
	private Integer isIdentification;
	//身份证号
	private String cardNo;
	//身份证正面照
	private String cardFrontImg;
	//身份证背面照
	private String cardBackImg;
	//民族
	private String nation;
	//区域编号
	private Integer regionCode;
	//政治面貌
	private String politicalStatus;
	//是否为管理员0:否1:是
	private Integer isManage;
	
	private String openId;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
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
	/**
	 * 设置：用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：昵称
	 */
	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
	/**
	 * 获取：昵称
	 */
	public String getNikeName() {
		return nikeName;
	}
	/**
	 * 设置：组织ID
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：组织ID
	 */
	public Long getDeptId() {
		return deptId;
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
	 * 设置：手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：状态 0:禁用，1:正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态 0:禁用，1:正常
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：创建用户id
	 */
	public void setUserIdCreate(Long userIdCreate) {
		this.userIdCreate = userIdCreate;
	}
	/**
	 * 获取：创建用户id
	 */
	public Long getUserIdCreate() {
		return userIdCreate;
	}
	/**
	 * 设置：创建时间
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}
	/**
	 * 设置：修改时间
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getGmtModified() {
		return gmtModified;
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
	 * 设置：出身日期
	 */
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	/**
	 * 获取：出身日期
	 */
	public Date getBirth() {
		return birth;
	}
	/**
	 * 设置：
	 */
	public void setPicId(Long picId) {
		this.picId = picId;
	}
	/**
	 * 获取：
	 */
	public Long getPicId() {
		return picId;
	}
	/**
	 * 设置：现居住地
	 */
	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}
	/**
	 * 获取：现居住地
	 */
	public String getLiveAddress() {
		return liveAddress;
	}
	/**
	 * 设置：爱好
	 */
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	/**
	 * 获取：爱好
	 */
	public String getHobby() {
		return hobby;
	}
	/**
	 * 设置：省份
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：省份
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：所在城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：所在城市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：所在地区
	 */
	public void setDistrict(String district) {
		this.district = district;
	}
	/**
	 * 获取：所在地区
	 */
	public String getDistrict() {
		return district;
	}
	/**
	 * 设置：是否实名认证0否1:是
	 */
	public void setIsIdentification(Integer isIdentification) {
		this.isIdentification = isIdentification;
	}
	/**
	 * 获取：是否实名认证0否1:是
	 */
	public Integer getIsIdentification() {
		return isIdentification;
	}
	/**
	 * 设置：身份证号
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * 获取：身份证号
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * 设置：身份证正面照
	 */
	public void setCardFrontImg(String cardFrontImg) {
		this.cardFrontImg = cardFrontImg;
	}
	/**
	 * 获取：身份证正面照
	 */
	public String getCardFrontImg() {
		return cardFrontImg;
	}
	/**
	 * 设置：身份证背面照
	 */
	public void setCardBackImg(String cardBackImg) {
		this.cardBackImg = cardBackImg;
	}
	/**
	 * 获取：身份证背面照
	 */
	public String getCardBackImg() {
		return cardBackImg;
	}
	/**
	 * 设置：民族
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}
	/**
	 * 获取：民族
	 */
	public String getNation() {
		return nation;
	}
	/**
	 * 设置：区域编号
	 */
	public void setRegionCode(Integer regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * 获取：区域编号
	 */
	public Integer getRegionCode() {
		return regionCode;
	}
	/**
	 * 设置：政治面貌
	 */
	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}
	/**
	 * 获取：政治面貌
	 */
	public String getPoliticalStatus() {
		return politicalStatus;
	}
	/**
	 * 设置：是否为管理员0:否1:是
	 */
	public void setIsManage(Integer isManage) {
		this.isManage = isManage;
	}
	/**
	 * 获取：是否为管理员0:否1:是
	 */
	public Integer getIsManage() {
		return isManage;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	
	
	public Integer getIsBinding() {
		return isBinding;
	}
	public void setIsBinding(Integer isBinding) {
		this.isBinding = isBinding;
	}
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	@Override
	public String toString() {
		return "UserDO [userId=" + userId + ", username=" + username + ", name=" + name + ", password=" + password
				+ ", nikeName=" + nikeName + ", isBinding=" + isBinding + ", deptId=" + deptId + ", deptName="
				+ deptName + ", headImg=" + headImg + ", account=" + account + ", platformIntegral=" + platformIntegral
				+ ", email=" + email + ", mobile=" + mobile + ", status=" + status + ", userIdCreate=" + userIdCreate
				+ ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", roleIds=" + roleIds + ", sex=" + sex
				+ ", birth=" + birth + ", picId=" + picId + ", liveAddress=" + liveAddress + ", hobby=" + hobby
				+ ", province=" + province + ", city=" + city + ", district=" + district + ", street=" + street
				+ ", isIdentification=" + isIdentification + ", cardNo=" + cardNo + ", cardFrontImg=" + cardFrontImg
				+ ", cardBackImg=" + cardBackImg + ", nation=" + nation + ", regionCode=" + regionCode
				+ ", politicalStatus=" + politicalStatus + ", isManage=" + isManage + ", openId=" + openId + "]";
	}
	
	
	
}
