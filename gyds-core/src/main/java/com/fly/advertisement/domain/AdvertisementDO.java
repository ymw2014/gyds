package com.fly.advertisement.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 广告
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-11 14:24:33
 */
public class AdvertisementDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//广告外链接
	private String url;
	//位置
	private String position;
	//区域编码
	private Integer regionCode;
	//
	private Date createTime;

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
	 * 设置：广告外链接
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：广告外链接
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：位置
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * 获取：位置
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * 设置：区域编码
	 */
	public void setRegionCode(Integer regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * 获取：区域编码
	 */
	public Integer getRegionCode() {
		return regionCode;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
