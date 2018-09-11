package com.giant.zzidc.base.utils;

/**
 * 
 * @description:用来存储增删改查等的操作结果的POJO
 * @author: Shell
 * @date: 2011-11-14上午09:45:28
 * @file: com.zzidc.idc.util.Result.java
 */
public class Result {
	private boolean success;
	private String msg;
	public Result(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
