package com.fly.proSetup.domain;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 项目分佣配置
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-10-22 15:22:20
 */
public class ProjectSetupDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//团队分佣比例
	private BigDecimal teamExtract;
	//街道办代理分佣比例
	private BigDecimal agencyExtract;
	//县级分佣比例
	private BigDecimal areaExtract;
	//市级分佣比例
	private BigDecimal cityExtract;
	//省级分佣比例
	private BigDecimal provinceExtract;
	//平台分佣比例
	private BigDecimal headExtract;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：团队分佣比例
	 */
	public void setTeamExtract(BigDecimal teamExtract) {
		this.teamExtract = teamExtract;
	}
	/**
	 * 获取：团队分佣比例
	 */
	public BigDecimal getTeamExtract() {
		return teamExtract;
	}
	/**
	 * 设置：街道办代理分佣比例
	 */
	public void setAgencyExtract(BigDecimal agencyExtract) {
		this.agencyExtract = agencyExtract;
	}
	/**
	 * 获取：街道办代理分佣比例
	 */
	public BigDecimal getAgencyExtract() {
		return agencyExtract;
	}
	/**
	 * 设置：县级分佣比例
	 */
	public void setAreaExtract(BigDecimal areaExtract) {
		this.areaExtract = areaExtract;
	}
	/**
	 * 获取：县级分佣比例
	 */
	public BigDecimal getAreaExtract() {
		return areaExtract;
	}
	/**
	 * 设置：市级分佣比例
	 */
	public void setCityExtract(BigDecimal cityExtract) {
		this.cityExtract = cityExtract;
	}
	/**
	 * 获取：市级分佣比例
	 */
	public BigDecimal getCityExtract() {
		return cityExtract;
	}
	/**
	 * 设置：省级分佣比例
	 */
	public void setProvinceExtract(BigDecimal provinceExtract) {
		this.provinceExtract = provinceExtract;
	}
	/**
	 * 获取：省级分佣比例
	 */
	public BigDecimal getProvinceExtract() {
		return provinceExtract;
	}
	/**
	 * 设置：平台分佣比例
	 */
	public void setHeadExtract(BigDecimal headExtract) {
		this.headExtract = headExtract;
	}
	/**
	 * 获取：平台分佣比例
	 */
	public BigDecimal getHeadExtract() {
		return headExtract;
	}
}
