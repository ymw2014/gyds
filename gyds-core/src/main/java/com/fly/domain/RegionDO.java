package com.fly.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-03 15:01:06
 */
public class RegionDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long regionCode;
	//
	private String regionName;
	//
	private Integer regionLevel;
	
	private boolean selected;
	//
	private Long parentRegionCode;
	
	private Integer regionType;

	/**
	 * 设置：
	 */
	public void setRegionCode(Long regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * 获取：
	 */
	public Long getRegionCode() {
		return regionCode;
	}
	/**
	 * 设置：
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	/**
	 * 获取：
	 */
	public String getRegionName() {
		return regionName;
	}
	/**
	 * 设置：
	 */
	public void setRegionLevel(Integer regionLevel) {
		this.regionLevel = regionLevel;
	}
	/**
	 * 获取：
	 */
	public Integer getRegionLevel() {
		return regionLevel;
	}
	/**
	 * 设置：
	 */
	public void setParentRegionCode(Long parentRegionCode) {
		this.parentRegionCode = parentRegionCode;
	}
	/**
	 * 获取：
	 */
	public Long getParentRegionCode() {
		return parentRegionCode;
	}
	
	public Integer getRegionType() {
		return regionType;
	}
	
	public void setRegionType(Integer regionType) {
		this.regionType = regionType;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	
}
