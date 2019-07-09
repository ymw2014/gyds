package com.fly.domain;

import java.io.Serializable;


/**
 * InnoDB free: 13312 kB
 * 
 * @author malingbing
 * @email
 * @date 2018-09-20 14:42:53
 */
public class TaProDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//省名称
	private String provincename;
	//省简称
	private String provincealias;
	//是否支持投保
	private String provincecode;
	//
	private Integer state;

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
	 * 设置：省名称
	 */
	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}
	/**
	 * 获取：省名称
	 */
	public String getProvincename() {
		return provincename;
	}
	/**
	 * 设置：省简称
	 */
	public void setProvincealias(String provincealias) {
		this.provincealias = provincealias;
	}
	/**
	 * 获取：省简称
	 */
	public String getProvincealias() {
		return provincealias;
	}
	/**
	 * 设置：是否支持投保
	 */
	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}
	/**
	 * 获取：是否支持投保
	 */
	public String getProvincecode() {
		return provincecode;
	}
	/**
	 * 设置：
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：
	 */
	public Integer getState() {
		return state;
	}
}
