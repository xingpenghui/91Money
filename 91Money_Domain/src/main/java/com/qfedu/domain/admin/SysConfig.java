package com.qfedu.domain.admin;


/**
 *@Author feri
 *@Date Created in 2018/7/29 23:32
 */
public class SysConfig {
	private Long id;
	private String key;
	private String value;
	private String remark;
	/**
	 * 状态    0：隐藏   1：显示
	 */
	private Byte status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	
}
