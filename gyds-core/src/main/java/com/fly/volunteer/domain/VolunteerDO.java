package com.fly.volunteer.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 志愿者表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-09 13:42:20
 */
public class VolunteerDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Long id;
	//志愿者名称
	private String volunteerName;
	//
	private Long memId;
	//所属团队编号
	private Integer teamId;
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
	public void setMemId(Long memId) {
		this.memId = memId;
	}
	/**
	 * 获取：
	 */
	public Long getMemId() {
		return memId;
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
}
