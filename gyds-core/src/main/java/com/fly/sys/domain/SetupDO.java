package com.fly.sys.domain;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 配置表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-11 17:03:43
 */
public class SetupDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//红包抽取比例
	private BigDecimal redPacketExtract;
	//团队分佣比例
	private BigDecimal teamExtract;
	//县级分佣比例
	private BigDecimal areaExtract;
	//市级分佣比例
	private BigDecimal cityExtract;
	//省级分佣比例
	private BigDecimal provinceExtract;
	//平台分佣比例
	private BigDecimal headExtract;
	//公司名称
	private String corporateName;
	//公众号二维码
	private String officialAccountImg;
	//公司LOGO
	private String logo;
	//备案号
	private String preparationNo;
	//网站标题
	private String title;
	//关键字
	private String keywords;
	//客服电话
	private String serviceTelephone;
	//版权信息
	private String copyrightInto;
	//短信接口账号
	private String messageLogin;
	//短信接口秘钥
	private String messagePassword;
	//支付接口账号
	private String payLogin;
	//支付接口秘钥
	private String payPassword;
	//广告基数最低标准
	private BigDecimal advertMin;
	//广告基数最高标准
	private BigDecimal advertMax;
	//置顶最低标准
	private BigDecimal stickTopMin;
	//置顶基数最高标准
	private BigDecimal stickTopMax;
	
	//
	private BigDecimal withdrawalFee;
	//签到 转发 评论 点赞 送积分
	private Integer punchTheClockIntegral;
	//活动参与积分
	private Integer rechargeIntegral;
	//省代理保证金
	private BigDecimal provincialBail;
	//市代理保证金
	private BigDecimal cityBail;
	//县级代理保证金
	private BigDecimal areaBail;
	//办事处代理保证金
	private BigDecimal agencyBail;
	//红包金额配置
	private String redPriceSetup;
	//打赏金额配置
	private String rewardPriceSetup;
	//置顶天数
	private String topCount;
	
	

	public String getTopCount() {
		return topCount;
	}
	public void setTopCount(String topCount) {
		this.topCount = topCount;
	}
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
	 * 设置：红包抽取比例
	 */
	public void setRedPacketExtract(BigDecimal redPacketExtract) {
		this.redPacketExtract = redPacketExtract;
	}
	/**
	 * 获取：红包抽取比例
	 */
	public BigDecimal getRedPacketExtract() {
		return redPacketExtract;
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
	/**
	 * 设置：公司名称
	 */
	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}
	/**
	 * 获取：公司名称
	 */
	public String getCorporateName() {
		return corporateName;
	}
	/**
	 * 设置：公众号二维码
	 */
	public void setOfficialAccountImg(String officialAccountImg) {
		this.officialAccountImg = officialAccountImg;
	}
	/**
	 * 获取：公众号二维码
	 */
	public String getOfficialAccountImg() {
		return officialAccountImg;
	}
	/**
	 * 设置：公司LOGO
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取：公司LOGO
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * 设置：备案号
	 */
	public void setPreparationNo(String preparationNo) {
		this.preparationNo = preparationNo;
	}
	/**
	 * 获取：备案号
	 */
	public String getPreparationNo() {
		return preparationNo;
	}
	/**
	 * 设置：网站标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：网站标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：关键字
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	/**
	 * 获取：关键字
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * 设置：客服电话
	 */
	public void setServiceTelephone(String serviceTelephone) {
		this.serviceTelephone = serviceTelephone;
	}
	/**
	 * 获取：客服电话
	 */
	public String getServiceTelephone() {
		return serviceTelephone;
	}
	/**
	 * 设置：版权信息
	 */
	public void setCopyrightInto(String copyrightInto) {
		this.copyrightInto = copyrightInto;
	}
	/**
	 * 获取：版权信息
	 */
	public String getCopyrightInto() {
		return copyrightInto;
	}
	/**
	 * 设置：短信接口账号
	 */
	public void setMessageLogin(String messageLogin) {
		this.messageLogin = messageLogin;
	}
	/**
	 * 获取：短信接口账号
	 */
	public String getMessageLogin() {
		return messageLogin;
	}
	/**
	 * 设置：短信接口秘钥
	 */
	public void setMessagePassword(String messagePassword) {
		this.messagePassword = messagePassword;
	}
	/**
	 * 获取：短信接口秘钥
	 */
	public String getMessagePassword() {
		return messagePassword;
	}
	/**
	 * 设置：支付接口账号
	 */
	public void setPayLogin(String payLogin) {
		this.payLogin = payLogin;
	}
	/**
	 * 获取：支付接口账号
	 */
	public String getPayLogin() {
		return payLogin;
	}
	/**
	 * 设置：支付接口秘钥
	 */
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	/**
	 * 获取：支付接口秘钥
	 */
	public String getPayPassword() {
		return payPassword;
	}
	/**
	 * 设置：广告基数最低标准
	 */
	public void setAdvertMin(BigDecimal advertMin) {
		this.advertMin = advertMin;
	}
	/**
	 * 获取：广告基数最低标准
	 */
	public BigDecimal getAdvertMin() {
		return advertMin;
	}
	/**
	 * 设置：广告基数最高标准
	 */
	public void setAdvertMax(BigDecimal advertMax) {
		this.advertMax = advertMax;
	}
	/**
	 * 获取：广告基数最高标准
	 */
	public BigDecimal getAdvertMax() {
		return advertMax;
	}
	
	public BigDecimal getStickTopMin() {
		return stickTopMin;
	}
	public void setStickTopMin(BigDecimal stickTopMin) {
		this.stickTopMin = stickTopMin;
	}
	public BigDecimal getStickTopMax() {
		return stickTopMax;
	}
	public void setStickTopMax(BigDecimal stickTopMax) {
		this.stickTopMax = stickTopMax;
	}
	/**
	 * 设置：
	 */
	public void setWithdrawalFee(BigDecimal withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getWithdrawalFee() {
		return withdrawalFee;
	}
	/**
	 * 设置：签到送积分
	 */
	public void setPunchTheClockIntegral(Integer punchTheClockIntegral) {
		this.punchTheClockIntegral = punchTheClockIntegral;
	}
	/**
	 * 获取：签到送积分
	 */
	public Integer getPunchTheClockIntegral() {
		return punchTheClockIntegral;
	}
	/**
	 * 设置：充值送积分比例
	 */
	public void setRechargeIntegral(Integer rechargeIntegral) {
		this.rechargeIntegral = rechargeIntegral;
	}
	/**
	 * 获取：充值送积分比例
	 */
	public Integer getRechargeIntegral() {
		return rechargeIntegral;
	}
	/**
	 * 设置：省代理保证金
	 */
	public void setProvincialBail(BigDecimal provincialBail) {
		this.provincialBail = provincialBail;
	}
	/**
	 * 获取：省代理保证金
	 */
	public BigDecimal getProvincialBail() {
		return provincialBail;
	}
	/**
	 * 设置：市代理保证金
	 */
	public void setCityBail(BigDecimal cityBail) {
		this.cityBail = cityBail;
	}
	/**
	 * 获取：市代理保证金
	 */
	public BigDecimal getCityBail() {
		return cityBail;
	}
	/**
	 * 设置：县级代理保证金
	 */
	public void setAreaBail(BigDecimal areaBail) {
		this.areaBail = areaBail;
	}
	/**
	 * 获取：县级代理保证金
	 */
	public BigDecimal getAreaBail() {
		return areaBail;
	}
	public BigDecimal getAgencyBail() {
		return agencyBail;
	}
	public void setAgencyBail(BigDecimal agencyBail) {
		this.agencyBail = agencyBail;
	}
	public String getRedPriceSetup() {
		return redPriceSetup;
	}
	public void setRedPriceSetup(String redPriceSetup) {
		this.redPriceSetup = redPriceSetup;
	}
	public String getRewardPriceSetup() {
		return rewardPriceSetup;
	}
	public void setRewardPriceSetup(String rewardPriceSetup) {
		this.rewardPriceSetup = rewardPriceSetup;
	}
	
	
	
}
