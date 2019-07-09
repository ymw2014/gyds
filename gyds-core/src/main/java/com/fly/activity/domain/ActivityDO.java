package com.fly.activity.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 活动表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-04 14:26:40
 */
public class ActivityDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Long id;
	//活动简介
	private String actIntro;
	//活动名称
	private String actTitle;
	//活动详情
	private String actContent;
	//发起人id
	private Long  memberId;
	//发起人
	private String name;
	//活动报名费用
	private BigDecimal actPrice;
	//活动封面图
	private String actTitleImg;
	//活动类型ID
	private Long actTypeId;
	//活动类型
	private String typeName;
	//状态
	private Integer status;
	//报名开始时间
	private Date startTime;
	//报名截止时间
	private Date endTime;
	//活动创建时间
	private Date createTime;
	//门票数量
	private Integer ticketCount;
	//0.免费 1.付费
	private Integer actType;
	//活动限制
	private Integer restrictions;
	//活动分享次数
	private Integer numberOfShares;
	//活动收藏次数
	private Integer numberOfCollection;
	//预览次数
	private Integer numberOfPreviews;
	//已报名人数
	private Integer numberOfApplicants;
	//最大报名人数
	private Integer applicantsNumMax;
	//拒绝理由
	private String reasonForRefusal;
	
	public String getReasonForRefusal() {
		return reasonForRefusal;
	}
	public void setReasonForRefusal(String reasonForRefusal) {
		this.reasonForRefusal = reasonForRefusal;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setActTitle(String actTitle) {
		this.actTitle = actTitle;
	}
	public void setActTypeId(Long actTypeId) {
		this.actTypeId = actTypeId;
	}
	/**
	 * 设置：活动简介
	 */
	public void setActIntro(String actIntro) {
		this.actIntro = actIntro;
	}
	/**
	 * 获取：活动简介
	 */
	public String getActIntro() {
		return actIntro;
	}
	/**
	 * 设置：活动详情
	 */
	public void setActContent(String actContent) {
		this.actContent = actContent;
	}
	/**
	 * 获取：活动详情
	 */
	public String getActContent() {
		return actContent;
	}
	/**
	 * 设置：活动报名费用
	 */
	public void setActPrice(BigDecimal actPrice) {
		this.actPrice = actPrice;
	}
	/**
	 * 获取：活动报名费用
	 */
	public BigDecimal getActPrice() {
		return actPrice;
	}
	/**
	 * 设置：活动封面图
	 */
	public void setActTitleImg(String actTitleImg) {
		this.actTitleImg = actTitleImg;
	}
	/**
	 * 获取：活动封面图
	 */
	public String getActTitleImg() {
		return actTitleImg;
	}
	/**
	 * 设置：报名开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：报名开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：报名截止时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getActTitle() {
		return actTitle;
	}
	public Long getActTypeId() {
		return actTypeId;
	}
	/**
	 * 获取：报名截止时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：活动创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：活动创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：门票数量
	 */
	public void setTicketCount(Integer ticketCount) {
		this.ticketCount = ticketCount;
	}
	/**
	 * 获取：门票数量
	 */
	public Integer getTicketCount() {
		return ticketCount;
	}
	/**
	 * 设置：0.免费 1.付费
	 */
	public void setActType(Integer actType) {
		this.actType = actType;
	}
	/**
	 * 获取：0.免费 1.付费
	 */
	public Integer getActType() {
		return actType;
	}
	/**
	 * 设置：活动限制
	 */
	public void setRestrictions(Integer restrictions) {
		this.restrictions = restrictions;
	}
	/**
	 * 获取：活动限制
	 */
	public Integer getRestrictions() {
		return restrictions;
	}
	
	public Integer getNumberOfShares() {
		return numberOfShares;
	}
	public void setNumberOfShares(Integer numberOfShares) {
		this.numberOfShares = numberOfShares;
	}
	public Integer getNumberOfCollection() {
		return numberOfCollection;
	}
	public void setNumberOfCollection(Integer numberOfCollection) {
		this.numberOfCollection = numberOfCollection;
	}
	public Integer getNumberOfPreviews() {
		return numberOfPreviews;
	}
	public void setNumberOfPreviews(Integer numberOfPreviews) {
		this.numberOfPreviews = numberOfPreviews;
	}
	/**
	 * 设置：已报名人数
	 */
	public void setNumberOfApplicants(Integer numberOfApplicants) {
		this.numberOfApplicants = numberOfApplicants;
	}
	/**
	 * 获取：已报名人数
	 */
	public Integer getNumberOfApplicants() {
		return numberOfApplicants;
	}
	/**
	 * 设置：最大报名人数
	 */
	public void setApplicantsNumMax(Integer applicantsNumMax) {
		this.applicantsNumMax = applicantsNumMax;
	}
	/**
	 * 获取：最大报名人数
	 */
	public Integer getApplicantsNumMax() {
		return applicantsNumMax;
	}
}
