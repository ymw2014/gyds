package com.fly.team.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 团队表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-04 10:09:06
 */
public class TeamDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Integer id;
	//团队名称
	private String teamName;
	//团长名称
	private String colonelName;
	//用户ID
	private Integer userId;
	//团队类型
	private Integer teamType;
	//团队编号
	private Integer teamCode;
	//团队封面图
	private String teamTitleImg;
	//团队图片，多张以逗号分隔
	private String teamImg;
	//地区Code
	private Integer regCode;

	/**
	 * 设置：自增编号
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：自增编号
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：团队名称
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	/**
	 * 获取：团队名称
	 */
	public String getTeamName() {
		return teamName;
	}
	/**
	 * 设置：团长名称
	 */
	public void setColonelName(String colonelName) {
		this.colonelName = colonelName;
	}
	/**
	 * 获取：团长名称
	 */
	public String getColonelName() {
		return colonelName;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：团队类型
	 */
	public void setTeamType(Integer teamType) {
		this.teamType = teamType;
	}
	/**
	 * 获取：团队类型
	 */
	public Integer getTeamType() {
		return teamType;
	}
	/**
	 * 设置：团队编号
	 */
	public void setTeamCode(Integer teamCode) {
		this.teamCode = teamCode;
	}
	/**
	 * 获取：团队编号
	 */
	public Integer getTeamCode() {
		return teamCode;
	}
	/**
	 * 设置：团队封面图
	 */
	public void setTeamTitleImg(String teamTitleImg) {
		this.teamTitleImg = teamTitleImg;
	}
	/**
	 * 获取：团队封面图
	 */
	public String getTeamTitleImg() {
		return teamTitleImg;
	}
	/**
	 * 设置：团队图片，多张以逗号分隔
	 */
	public void setTeamImg(String teamImg) {
		this.teamImg = teamImg;
	}
	/**
	 * 获取：团队图片，多张以逗号分隔
	 */
	public String getTeamImg() {
		return teamImg;
	}
	/**
	 * 设置：地区Code
	 */
	public void setRegCode(Integer regCode) {
		this.regCode = regCode;
	}
	/**
	 * 获取：地区Code
	 */
	public Integer getRegCode() {
		return regCode;
	}
}
