package com.fly.helpCenter.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 帮助中心信息表
 * 
 * @author Wangyalei
 * @email 15638836857@163.com
 * @date 2019-07-03 13:57:16
 */
public class CenterDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键id
	private Long id;
	//用户id
	private Long memberId;
	//用户名
	private String name;
	//标题
	private String title;
	//链接地址
	private String url;
	//类型
	private Long helpTypeId;
	//类型名称
	private String helpTitle;
	//内容
	private String content;
	//0:发布 1 未发布 
	private String status;
	//删除时间
	private Date delTime;
	//修改时间
	private Date updateTime;
	//发布时间
	private Date publicTime;
	//创建时间
	private Date creatTime;
	//0:未删除 1:删除
	private Integer del;
	//等级
	private Integer level;
	//地区
	private Integer districtId;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHelpTitle() {
		return helpTitle;
	}
	public void setHelpTitle(String helpTitle) {
		this.helpTitle = helpTitle;
	}
	/**
	 * 设置：主键id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：用户id
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getMemberId() {
		return memberId;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：类型
	 */
	public void setHelpTypeId(Long helpTypeId) {
		this.helpTypeId = helpTypeId;
	}
	/**
	 * 获取：类型
	 */
	public Long getHelpTypeId() {
		return helpTypeId;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：0:发布 1 未发布 
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：0:发布 1 未发布 
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：删除时间
	 */
	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}
	/**
	 * 获取：删除时间
	 */
	public Date getDelTime() {
		return delTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
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
	/**
	 * 设置：0:未删除 1:删除
	 */
	public void setDel(Integer del) {
		this.del = del;
	}
	/**
	 * 获取：0:未删除 1:删除
	 */
	public Integer getDel() {
		return del;
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
	 * 设置：地区
	 */
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	/**
	 * 获取：地区
	 */
	public Integer getDistrictId() {
		return districtId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
