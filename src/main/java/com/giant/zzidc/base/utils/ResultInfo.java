package com.giant.zzidc.base.utils;

import java.io.Serializable;
import java.util.Map;

/**
 * 结果信息封装类
 */
public class ResultInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 结果代码（默认值为-1，未知错误） */
	private int code = -1;
	/** 信息 */
	private Object info;
	//备注信息
    private Map<String,Object> extend;
    
    private String errorcode;

	/* ------------------------ Constructors ------------------------ */

	public Map<String, Object> getExtend() {
		return extend;
	}

	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}

	public ResultInfo() {
		super();
	}

	public ResultInfo(int code, Object info, Map<String, Object> extend) {
		super();
		this.code = code;
		this.info = info;
		this.extend = extend;
	}

	public ResultInfo(int code, Object info) {
		super();
		this.code = code;
		this.info = info;
	}
	
	

	public ResultInfo(int code, Object info, Map<String, Object> extend,
			String errorcode) {
		super();
		this.code = code;
		this.info = info;
		this.extend = extend;
		this.errorcode = errorcode;
	}

	/* -------------------- Getters and Setters -------------------- */

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	
}
