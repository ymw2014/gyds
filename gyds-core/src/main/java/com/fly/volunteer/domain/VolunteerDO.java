package com.fly.volunteer.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 志愿者表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-09 15:08:21
 */
public class VolunteerDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Long id;
	//志愿者名称
	private String volunteerName;
	//
	private Long userId;
	//所属团队编号
	private Integer teamId;
	//所属团队名称
	private String teamName;
	//家庭住址
	private String address;
	//电话
	private String telephone;
	//性别 0:男 1:女
	private Integer sex;
	//年龄
	private Integer age;
	//志愿者编号
	private String volunteerNumber;
	//身份证号
	private String cardNo;
	//转发次数
	private Integer sharesNumber;
	//评论量
	private Integer commentNumber;
	//点击量
	private Integer clickNumber;
	//参与活动次数
	private Integer actNumber;
	//志愿者等级
	private Integer level;
	//省
	private String province;
	//市
	private String city;
	//县
	private String county;
	//所属区域编号
	private Integer regioncode;

	private Date createTime;
	
	private int auditStatus;
	
	private String headImg;
	
	private String remark;
		
	private Date enterTeamTime;
	//积分
	private Integer integral;
	
	private String introduction;
	
	
	
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Date getEnterTeamTime() {
		return enterTeamTime;
	}
	public void setEnterTeamTime(Date enterTeamTime) {
		this.enterTeamTime = enterTeamTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
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
	 * 设置：志愿者名称
	 */
	public void setVolunteerName(String volunteerName) {
		this.volunteerName = volunteerName;
	}
	/**
	 * 获取：志愿者名称
	 */
	public String getVolunteerName() {
		return volunteerName;
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
	 * 设置：性别 0:男 1:女
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别 0:男 1:女
	 */
	public Integer getSex() {
		return sex;
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
	 * 设置：志愿者编号
	 */
	public void setVolunteerNumber(String volunteerNumber) {
		this.volunteerNumber = volunteerNumber;
	}
	/**
	 * 获取：志愿者编号
	 */
	public String getVolunteerNumber() {
		return volunteerNumber;
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
	 * 设置：转发次数
	 */
	public void setSharesNumber(Integer sharesNumber) {
		this.sharesNumber = sharesNumber;
	}
	/**
	 * 获取：转发次数
	 */
	public Integer getSharesNumber() {
		return sharesNumber;
	}
	/**
	 * 设置：评论量
	 */
	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}
	/**
	 * 获取：评论量
	 */
	public Integer getCommentNumber() {
		return commentNumber;
	}
	/**
	 * 设置：点击量
	 */
	public void setClickNumber(Integer clickNumber) {
		this.clickNumber = clickNumber;
	}
	/**
	 * 获取：点击量
	 */
	public Integer getClickNumber() {
		return clickNumber;
	}
	/**
	 * 设置：参与活动次数
	 */
	public void setActNumber(Integer actNumber) {
		this.actNumber = actNumber;
	}
	/**
	 * 获取：参与活动次数
	 */
	public Integer getActNumber() {
		return actNumber;
	}
	/**
	 * 设置：志愿者等级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：志愿者等级
	 */
	public Integer getLevel() {
		return level;
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
}
