package com.fly.news.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-02 14:39:24
 */
public class RewardInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//打赏时间
	private Date createTime;
	//打赏金额
	private BigDecimal rewardPrice;
	//打赏资讯ID
	private Integer newsId;
	//用户编号
	private Long memberId;
	//自增编号
	private Integer id;

	/**
	 * 设置：打赏时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：打赏时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：打赏金额
	 */
	public void setRewardPrice(BigDecimal rewardPrice) {
		this.rewardPrice = rewardPrice;
	}
	/**
	 * 获取：打赏金额
	 */
	public BigDecimal getRewardPrice() {
		return rewardPrice;
	}
	/**
	 * 设置：打赏资讯ID
	 */
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	/**
	 * 获取：打赏资讯ID
	 */
	public Integer getNewsId() {
		return newsId;
	}
	/**
	 * 设置：用户编号
	 */
	
	/**
	 * 设置：自增编号
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	/**
	 * 获取：自增编号
	 */
	public Integer getId() {
		return id;
	}
}
