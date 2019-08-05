package com.fly.news.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 咨询动态表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-16 11:50:51
 */
public class DynamicDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Long id;
	//评论新闻编号
	private Long newsId;
	//用户编号
	private Long memberId;
	//转发类型1:微信 2:QQ空间 3:QQ 4:微博
	private Integer transpondType;
	//0:转发 1:点赞 2:收藏
	private Integer type;
	//创建时间
	private Date creatTime;
	//类型:1:新闻 2:活动
	private int actType;
	

	public int getActType() {
		return actType;
	}
	public void setActType(int actType) {
		this.actType = actType;
	}
	/**
	 * 设置：自增编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：自增编号
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：评论新闻编号
	 */
	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}
	/**
	 * 获取：评论新闻编号
	 */
	public Long getNewsId() {
		return newsId;
	}
	/**
	 * 设置：用户编号
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	/**
	 * 获取：用户编号
	 */
	public Long getMemberId() {
		return memberId;
	}
	/**
	 * 设置：转发类型1:微信 2:QQ空间 3:QQ 4:微博
	 */
	public void setTranspondType(Integer transpondType) {
		this.transpondType = transpondType;
	}
	/**
	 * 获取：转发类型1:微信 2:QQ空间 3:QQ 4:微博
	 */
	public Integer getTranspondType() {
		return transpondType;
	}
	/**
	 * 设置：0:转发 1:点赞 2:收藏
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：0:转发 1:点赞 2:收藏
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreatTime() {
		return creatTime;
	}
}
