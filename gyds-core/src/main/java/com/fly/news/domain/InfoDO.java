package com.fly.news.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-02 14:35:59
 */
public class InfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	//序号
	private Integer i;
	//是否删除0：否1：是
	private Integer isDel;
	//是否有红包0：否1：是
	private Integer isRedPeper;
	//是否置顶0：否1：是 2：申请中
	private Integer isTop;
	//类型
	private Integer type;
	//转发次数
	private Integer numberOfShares;
	//评论次数
	private Integer criticismOfCount;
	//文章被赞次数
	private Integer numberOfLikes;
	//等级
	private Integer level;
	//最多打赏额度
	private BigDecimal rewardMax;
	//打赏人数
	private Integer rewardNumOfPeople;
	//打赏次数
	private Integer rewardCount;
	//打赏总金额
	private BigDecimal rewardPrice;
	//支付结束时间
	private Date pymentEndTime;
	//支付开始时间
	private Date pymentStartTime;
	//付费基数（用来竞价置顶）
	private BigDecimal paymentCriterion;
	//置顶付费时长
	private Integer paymentDuration;
	//置顶付费
	private BigDecimal paymentTop;
	//红包个数
	private Integer redPeperId;
	//红包
	private BigDecimal redPeper;
	//创建日期
	private Date createTime;
	//新闻封面图
	private String titleImg;
	//来源
	private String source;
	//发布时间
	private Date publicTime;
	//0:未发布1:发布
	private Integer status;
	//新闻发布团队
	private Integer teamId;
	
	//团队名称
	private String teamName;
	//团队图片
	private String teamTitleImg;

	//简介
	private String introduction;
	//新闻内容
	private String content;
	//作者
	private String author;
	//新闻标题
	private String title;
	//自增编号
	private Long id;
	//置顶区域
	private Integer topRegion;
	
	private String createTimeStr;
	
	public String getTeamTitleImg() {
		return teamTitleImg;
	}
	public void setTeamTitleImg(String teamTitleImg) {
		this.teamTitleImg = teamTitleImg;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public Integer getTopRegion() {
		return topRegion;
	}
	public void setTopRegion(Integer topRegion) {
		this.topRegion = topRegion;
	}
	public Integer getI() {
		return i;
	}
	public void setI(Integer i) {
		this.i = i;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * 设置：是否删除0：否1：是
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	/**
	 * 获取：是否删除0：否1：是
	 */
	public Integer getIsDel() {
		return isDel;
	}
	/**
	 * 设置：是否有红包0：否1：是
	 */
	public void setIsRedPeper(Integer isRedPeper) {
		this.isRedPeper = isRedPeper;
	}
	/**
	 * 获取：是否有红包0：否1：是
	 */
	public Integer getIsRedPeper() {
		return isRedPeper;
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
	 * 设置：类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：转发次数
	 */
	public void setNumberOfShares(Integer numberOfShares) {
		this.numberOfShares = numberOfShares;
	}
	/**
	 * 获取：转发次数
	 */
	public Integer getNumberOfShares() {
		return numberOfShares;
	}
	/**
	 * 设置：评论次数
	 */
	public void setCriticismOfCount(Integer criticismOfCount) {
		this.criticismOfCount = criticismOfCount;
	}
	/**
	 * 获取：评论次数
	 */
	public Integer getCriticismOfCount() {
		return criticismOfCount;
	}
	/**
	 * 设置：文章被赞次数
	 */
	public void setNumberOfLikes(Integer numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}
	/**
	 * 获取：文章被赞次数
	 */
	public Integer getNumberOfLikes() {
		return numberOfLikes;
	}
	/**
	 * 设置：等级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：等级
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：最多打赏额度
	 */
	public void setRewardMax(BigDecimal rewardMax) {
		this.rewardMax = rewardMax;
	}
	/**
	 * 获取：最多打赏额度
	 */
	public BigDecimal getRewardMax() {
		return rewardMax;
	}
	/**
	 * 设置：打赏人数
	 */
	public void setRewardNumOfPeople(Integer rewardNumOfPeople) {
		this.rewardNumOfPeople = rewardNumOfPeople;
	}
	/**
	 * 获取：打赏人数
	 */
	public Integer getRewardNumOfPeople() {
		return rewardNumOfPeople;
	}
	/**
	 * 设置：打赏次数
	 */
	public void setRewardCount(Integer rewardCount) {
		this.rewardCount = rewardCount;
	}
	/**
	 * 获取：打赏次数
	 */
	public Integer getRewardCount() {
		return rewardCount;
	}
	/**
	 * 设置：打赏总金额
	 */
	public void setRewardPrice(BigDecimal rewardPrice) {
		this.rewardPrice = rewardPrice;
	}
	/**
	 * 获取：打赏总金额
	 */
	public BigDecimal getRewardPrice() {
		return rewardPrice;
	}
	/**
	 * 设置：支付结束时间
	 */
	public void setPymentEndTime(Date pymentEndTime) {
		this.pymentEndTime = pymentEndTime;
	}
	/**
	 * 获取：支付结束时间
	 */
	public Date getPymentEndTime() {
		return pymentEndTime;
	}
	/**
	 * 设置：支付开始时间
	 */
	public void setPymentStartTime(Date pymentStartTime) {
		this.pymentStartTime = pymentStartTime;
	}
	/**
	 * 获取：支付开始时间
	 */
	public Date getPymentStartTime() {
		return pymentStartTime;
	}
	/**
	 * 设置：付费基数（用来竞价置顶）
	 */
	public void setPaymentCriterion(BigDecimal paymentCriterion) {
		this.paymentCriterion = paymentCriterion;
	}
	/**
	 * 获取：付费基数（用来竞价置顶）
	 */
	public BigDecimal getPaymentCriterion() {
		return paymentCriterion;
	}
	/**
	 * 设置：置顶付费时长
	 */
	public void setPaymentDuration(Integer paymentDuration) {
		this.paymentDuration = paymentDuration;
	}
	/**
	 * 获取：置顶付费时长
	 */
	public Integer getPaymentDuration() {
		return paymentDuration;
	}
	/**
	 * 设置：置顶付费
	 */
	public void setPaymentTop(BigDecimal paymentTop) {
		this.paymentTop = paymentTop;
	}
	/**
	 * 获取：置顶付费
	 */
	public BigDecimal getPaymentTop() {
		return paymentTop;
	}
	/**
	 * 设置：红包个数
	 */
	public void setRedPeperId(Integer redPeperId) {
		this.redPeperId = redPeperId;
	}
	/**
	 * 获取：红包个数
	 */
	public Integer getRedPeperId() {
		return redPeperId;
	}
	/**
	 * 设置：红包
	 */
	public void setRedPeper(BigDecimal redPeper) {
		this.redPeper = redPeper;
	}
	/**
	 * 获取：红包
	 */
	public BigDecimal getRedPeper() {
		return redPeper;
	}
	/**
	 * 设置：创建日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建日期
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：新闻封面图
	 */
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	/**
	 * 获取：新闻封面图
	 */
	public String getTitleImg() {
		return titleImg;
	}
	/**
	 * 设置：来源
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * 获取：来源
	 */
	public String getSource() {
		return source;
	}
	/**
	 * 设置：发布时间
	 */
	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}
	/**
	 * 获取：发布时间
	 */
	public Date getPublicTime() {
		return publicTime;
	}
	/**
	 * 设置：0:未发布1:发布
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：0:未发布1:发布
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：新闻发布团队
	 */
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	/**
	 * 获取：新闻发布团队
	 */
	public Integer getTeamId() {
		return teamId;
	}
	/**
	 * 设置：简介
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	/**
	 * 获取：简介
	 */
	public String getIntroduction() {
		return introduction;
	}
	/**
	 * 设置：新闻内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：新闻内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * 获取：作者
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 设置：新闻标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：新闻标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：自增编号
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
