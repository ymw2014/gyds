package com.fly.level.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 积分区间表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-09-20 10:20:09
 */
public class LevelDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//等级
	private Integer level;
	//等级名称
	private String levelname;
	//开始积分
	private Long staIntegral;
	//结束积分
	private Long endIntegral;
	//1:志愿者 2:团队
	private Integer type;
	//创建时间
	private Date crateTime;
	//url
	private String url;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：等级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：等级
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：等级名称
	 */
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	/**
	 * 获取：等级名称
	 */
	public String getLevelname() {
		return levelname;
	}
	
	public Long getStaIntegral() {
		return staIntegral;
	}
	public void setStaIntegral(Long staIntegral) {
		this.staIntegral = staIntegral;
	}
	public Long getEndIntegral() {
		return endIntegral;
	}
	public void setEndIntegral(Long endIntegral) {
		this.endIntegral = endIntegral;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 设置：1:志愿者 2:团队
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：1:志愿者 2:团队
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCrateTime() {
		return crateTime;
	}
}
