package com.fly.verifyName.domain;

import java.io.Serializable;
import java.util.Date;

import com.fly.proxybusi.domain.ProxybusiDO;
import com.fly.team.domain.TeamDO;

import io.swagger.models.auth.In;



/**
 * 实名认证申请表/入团/建团/代理商入驻
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-08-07 14:06:00
 */
public class NameDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增id
	private Integer id;
	//用户表id
	private Long userId;
	//团队id
	private Integer teamId;
	//姓名
	private String name;
	//email
	private String email;
	//手机
	private String mobile;
	//创建时间
	private Date creadTime;
	//性别
	private Integer sex;
	//出身日期
	private Date birth;
	//政治面貌
	private String politicalStatus;
	//详细地址
	private String address;
	//省份
	private String province;
	//所在城市
	private String city;
	//所在地区
	private String district;
	//街道
	private String street;
	//1:未审核 2:审核 3:拒绝
	private Integer status;
	//身份证号
	private String cardNo;
	//身份证正面照
	private String cardFrontImg;
	//身份证背面照
	private String cardBackImg;
	//民族
	private String nation;
	//1:入团申请2:建团申请3:代理商入驻
	private Integer type;
	//创建团队信息
	private String team;
	//代理商信息
	private ProxybusiDO proxybusi;
	//信息
	private String text;
	//
	private Integer regionCode;
	
	
	public Integer getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(Integer regionCode) {
		this.regionCode = regionCode;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
	 * 设置：用户表id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户表id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：团队id
	 */
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	/**
	 * 获取：团队id
	 */
	public Integer getTeamId() {
		return teamId;
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
	 * 设置：email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreadTime(Date creadTime) {
		this.creadTime = creadTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreadTime() {
		return creadTime;
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
	 * 设置：详细地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：详细地址
	 */
	public String getAddress() {
		return address;
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
	 * 设置：街道
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * 获取：街道
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * 设置：1:未审核 2:审核 3:拒绝
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：1:未审核 2:审核 3:拒绝
	 */
	public Integer getStatus() {
		return status;
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
	 * 设置：1:入团申请2:建团申请3:代理商入驻
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：1:入团申请2:建团申请3:代理商入驻
	 */
	public Integer getType() {
		return type;
	}
	
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public ProxybusiDO getProxybusi() {
		return proxybusi;
	}
	public void setProxybusi(ProxybusiDO proxybusi) {
		this.proxybusi = proxybusi;
	}
	
}
