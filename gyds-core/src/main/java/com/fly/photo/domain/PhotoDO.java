package com.fly.photo.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 相册表
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-24 14:00:11
 */
public class PhotoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Integer id;
	//会员编号
	private Long memberId;
	//图片地址
	private String imgUrl;
	//上传日期
	private Date createTime;

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
	 * 设置：会员编号
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	/**
	 * 获取：会员编号
	 */
	public Long getMemberId() {
		return memberId;
	}
	/**
	 * 设置：图片地址
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 获取：图片地址
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * 设置：上传日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：上传日期
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
