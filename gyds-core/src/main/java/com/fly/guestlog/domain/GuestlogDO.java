package com.fly.guestlog.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 访客记录表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-23 17:07:37
 */
public class GuestlogDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增id
	private Long id;
	//访客id
	private Integer guestId;
	//访客头像
	private String guestHeadimg;
	//访客姓名
	private String guestName;
	//被访问用户id
	private Integer userId;
	//被访问用户头像
	private String userHeadimg;
	//被访问用户姓名
	private String userName;
	//访问类型 1 TA浏览的  2 TA的访客
	private Integer guestType;
	//访问时间
	private Date guestTime;
	
	private String guestTimeStr;
	
	public String getGuestTimeStr() {
		return guestTimeStr;
	}
	public void setGuestTimeStr(String guestTimeStr) {
		this.guestTimeStr = guestTimeStr;
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
	 * 设置：访客id
	 */
	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}
	/**
	 * 获取：访客id
	 */
	public Integer getGuestId() {
		return guestId;
	}
	/**
	 * 设置：访客头像
	 */
	public void setGuestHeadimg(String guestHeadimg) {
		this.guestHeadimg = guestHeadimg;
	}
	/**
	 * 获取：访客头像
	 */
	public String getGuestHeadimg() {
		return guestHeadimg;
	}
	/**
	 * 设置：访客姓名
	 */
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	/**
	 * 获取：访客姓名
	 */
	public String getGuestName() {
		return guestName;
	}
	/**
	 * 设置：被访问用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：被访问用户id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：被访问用户头像
	 */
	public void setUserHeadimg(String userHeadimg) {
		this.userHeadimg = userHeadimg;
	}
	/**
	 * 获取：被访问用户头像
	 */
	public String getUserHeadimg() {
		return userHeadimg;
	}
	/**
	 * 设置：被访问用户姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：被访问用户姓名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：访问类型 1 TA浏览的  2 TA的访客
	 */
	public void setGuestType(Integer guestType) {
		this.guestType = guestType;
	}
	/**
	 * 获取：访问类型 1 TA浏览的  2 TA的访客
	 */
	public Integer getGuestType() {
		return guestType;
	}
	/**
	 * 设置：访问时间
	 */
	public void setGuestTime(Date guestTime) {
		this.guestTime = guestTime;
	}
	/**
	 * 获取：访问时间
	 */
	public Date getGuestTime() {
		return guestTime;
	}
}
