package com.fly.activity.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 活动类型表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-04 14:40:10
 */
public class TypeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增编号
	private Long id;
	//创建时间
	private Date createTime;
	//类型名称
	private String typeName;
	//是否删除0：否1：是
	private Integer isDel;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	public String getTypeName() {
		return typeName;
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
}
