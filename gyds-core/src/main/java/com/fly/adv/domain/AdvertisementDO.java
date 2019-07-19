package com.fly.adv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 广告
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-12 11:20:07
 */
public class AdvertisementDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//广告名称
	private String advName;
	//广告外链接
	private String url;
	//图片
	private String advImg;
	//区域编码
	private Integer regionCode;
	//
	private Date createTime;
	//位置编号
	private Integer positionNum;
	//展示开始时间
	private Date showStartTime;
	//展现结束时间
	private Date showEndTime;


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
	 * 设置：广告名称
	 */
	public void setAdvName(String advName) {
		this.advName = advName;
	}
	/**
	 * 获取：广告名称
	 */
	public String getAdvName() {
		return advName;
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
	
	public String getAdvImg() {
		return advImg;
	}
	public void setAdvImg(String advImg) {
		this.advImg = advImg;
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
	/**
	 * 设置：位置编号
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}
	/**
	 * 获取：位置编号
	 */
	public Integer getPositionNum() {
		return positionNum;
	}
	/**
	 * 设置：展示开始时间
	 */
	public void setShowStartTime(Date showStartTime) {
		this.showStartTime = showStartTime;
	}
	/**
	 * 获取：展示开始时间
	 */
	public Date getShowStartTime() {
		return showStartTime;
	}
	/**
	 * 设置：展现结束时间
	 */
	public void setShowEndTime(Date showEndTime) {
		this.showEndTime = showEndTime;
	}
	/**
	 * 获取：展现结束时间
	 */
	public Date getShowEndTime() {
		return showEndTime;
	}
	/**
	 * 设置：费用(人均费用基数)
	 */

	
	
}
