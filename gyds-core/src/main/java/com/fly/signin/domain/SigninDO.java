package com.fly.signin.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 签到表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-29 11:28:41
 */
public class SigninDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增主键
	private Integer id;
	//年
	private Integer year;
	//月
	private Integer month;
	//日
	private Integer day;
	//志愿者id
	private Long voId;

	/**
	 * 设置：自增主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：自增主键
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：年
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * 获取：年
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * 设置：月
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}
	/**
	 * 获取：月
	 */
	public Integer getMonth() {
		return month;
	}
	/**
	 * 设置：日
	 */
	public void setDay(Integer day) {
		this.day = day;
	}
	/**
	 * 获取：日
	 */
	public Integer getDay() {
		return day;
	}
	/**
	 * 设置：志愿者id
	 */
	public void setVoId(Long voId) {
		this.voId = voId;
	}
	/**
	 * 获取：志愿者id
	 */
	public Long getVoId() {
		return voId;
	}
}
