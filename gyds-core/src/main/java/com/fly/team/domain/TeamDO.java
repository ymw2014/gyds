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
	private Long userId;
	//团队类型
	private Integer teamType;
	//团队类型名称
	private String typeName;
	//团队编号
	private Integer teamCode;
	//团队封面图
	private String teamTitleImg;
	//团队图片，多张以逗号分隔
	private String teamImg;
	//地区Code
	private Integer regCode;
	//地区
	private String addres;
	//地区
	private String regionName;
	//状态
	private Integer status;
	//备注
	private String remark;
	//手机
	private String colonelTel;
	//简介
	private String teamIntroduce;
	//积分
	private Integer integral ; 
	
	
	public String getAddres() {
		return addres;
	}
	public void setAddres(String addres) {
		this.addres = addres;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getTeamIntroduce() {
		return teamIntroduce;
	}
	public void setTeamIntroduce(String teamIntroduce) {
		this.teamIntroduce = teamIntroduce;
	}
	public String getColonelTel() {
		return colonelTel;
	}
	public void setColonelTel(String colonelTel) {
		this.colonelTel = colonelTel;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
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
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
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
