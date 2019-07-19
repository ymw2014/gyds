package com.fly.adv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 广告记录
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-19 15:16:53
 */
public class DetailDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//用户编号
	private Integer userId;
	//广告位置
	private Integer positionNum;
	//发布区域
	private Integer regionCode;
	//广告链接
	private String advUrl;
	//提交时间
	private Date createTime;
	//图片
	private String advImg;
	//开始时间
	private Date showStartTime;
	//结束时间
	private Date showEndTime;
	//价格:(按照展现时间计算总价=每天*上架天数)
	private BigDecimal price;
	//处理状态 0未审核1:已审核2:已拒绝
	private Integer status;

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
	 * 设置：用户编号
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户编号
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：广告位置
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}
	/**
	 * 获取：广告位置
	 */
	public Integer getPositionNum() {
		return positionNum;
	}
	/**
	 * 设置：发布区域
	 */
	public void setRegionCode(Integer regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * 获取：发布区域
	 */
	public Integer getRegionCode() {
		return regionCode;
	}
	/**
	 * 设置：广告链接
	 */
	public void setAdvUrl(String advUrl) {
		this.advUrl = advUrl;
	}
	/**
	 * 获取：广告链接
	 */
	public String getAdvUrl() {
		return advUrl;
	}
	/**
	 * 设置：提交时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：提交时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：图片
	 */
	public void setAdvImg(String advImg) {
		this.advImg = advImg;
	}
	/**
	 * 获取：图片
	 */
	public String getAdvImg() {
		return advImg;
	}
	/**
	 * 设置：开始时间
	 */
	public void setShowStartTime(Date showStartTime) {
		this.showStartTime = showStartTime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getShowStartTime() {
		return showStartTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setShowEndTime(Date showEndTime) {
		this.showEndTime = showEndTime;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getShowEndTime() {
		return showEndTime;
	}
	/**
	 * 设置：价格:(按照展现时间计算总价=每天*上架天数)
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：价格:(按照展现时间计算总价=每天*上架天数)
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：处理状态 0未审核1:已审核2:已拒绝
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：处理状态 0未审核1:已审核2:已拒绝
	 */
	public Integer getStatus() {
		return status;
	}
}
