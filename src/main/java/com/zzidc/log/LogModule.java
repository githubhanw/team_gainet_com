package com.zzidc.log;
/**
 * [日志-模块]
 * @author likai
 * @date 2018年8月6日 上午11:53:17
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
public enum LogModule {
	TASK("task"),
	NEED("need"),
	TEST("test"),
	BUG("bug"),
	RESULT("result");
	
	private LogModule(String value) {
		this.value = value;
	}
	
	private String value;
	
	public String value() {
		return value;
	}
}
