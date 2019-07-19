package com.fly.news.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 咨询评论表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-09 17:41:16
 */
public class CommentDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Integer id;
	//评论新闻编号
	private Integer newsId;
	//评论新闻
	private String title;
	//用户编号
	private Integer memberId;
	//用户名
	private String name;
	//评论内容
	private String criticismContent;
	//是否置顶0：否1：是 2：申请中
	private Integer isTop;
	//评论时间
	private Date createTime;
	
	private String  createTimeStr;
	//评论被赞次数
	private Integer numberOfLikes;
	//父级id
	private Integer prenId;
	//新闻封面
	private String titleImg;
	
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getTitleImg() {
		return titleImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	 * 设置：评论新闻编号
	 */
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	/**
	 * 获取：评论新闻编号
	 */
	public Integer getNewsId() {
		return newsId;
	}
	/**
	 * 设置：用户编号
	 */
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	/**
	 * 获取：用户编号
	 */
	public Integer getMemberId() {
		return memberId;
	}
	/**
	 * 设置：评论内容
	 */
	public void setCriticismContent(String criticismContent) {
		this.criticismContent = criticismContent;
	}
	/**
	 * 获取：评论内容
	 */
	public String getCriticismContent() {
		return criticismContent;
	}
	/**
	 * 设置：是否置顶0：否1：是 2：申请中
	 */
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	/**
	 * 获取：是否置顶0：否1：是 2：申请中
	 */
	public Integer getIsTop() {
		return isTop;
	}
	/**
	 * 设置：评论时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：评论时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：评论被赞次数
	 */
	public void setNumberOfLikes(Integer numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}
	/**
	 * 获取：评论被赞次数
	 */
	public Integer getNumberOfLikes() {
		return numberOfLikes;
	}
	/**
	 * 设置：父级id
	 */
	public void setPrenId(Integer prenId) {
		this.prenId = prenId;
	}
	/**
	 * 获取：父级id
	 */
	public Integer getPrenId() {
		return prenId;
	}
}
