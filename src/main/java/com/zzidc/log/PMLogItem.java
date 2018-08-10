package com.zzidc.log;

/**
 * [日志记录-每项变更]
 * 
 * @author likai
 * @date 2018年8月6日 下午3:08:50
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
public class PMLogItem {
	/**
	 * 字段
	 */
	private String field;
	/**
	 * 历史数据
	 */
	private String oldData;
	/**
	 * 新数据
	 */
	private String newData;
	/**
	 * 区别
	 */
	private Short diff;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOldData() {
		return oldData;
	}

	public void setOldData(String oldData) {
		this.oldData = oldData;
	}

	public String getNewData() {
		return newData;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}

	public Short getDiff() {
		return diff;
	}

	public void setDiff(Short diff) {
		this.diff = diff;
	}
}
