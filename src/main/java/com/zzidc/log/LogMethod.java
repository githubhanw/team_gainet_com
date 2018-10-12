package com.zzidc.log;
/**
 * [日志-方法]
 * @author likai
 * @date 2018年8月6日 上午11:53:39
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
public enum LogMethod {
	ADD("创建"),
	EDIT("编辑"),
	OPEN("接收"),
	ASSIGN("指派"),
	DISMISSAL("驳回"),
	RECEIVE("领取"),
	RESOLVED("分解"),
	PERFECT("完善"),
	CHANGE("变更"),
	CLOSE("关闭"),
	ACTIVE("激活"),
	PAUSE("暂停"),
	CANCEL("取消"),
	FINISH("完成"),
	FINISHCHECK("完成审核"),
	RELEVANCE("关联"),
	RELATE("关联月会议"),
	DELAY("延期"),
	DELAYCHECK("延期审核"),
	CHECK("验收"),
	HANDOVER("交接"),
	SUBMITCHECK("提交验收"),
	REJECTED("驳回原型图");
	
	private LogMethod(String value) {
		this.value = value;
	}
	
	private String value;
	
	public String value() {
		return value;
	}
}
