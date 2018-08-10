package com.giant.zzidc.base.utils;

/**
 * Payconfig entity. @author MyEclipse Persistence Tools
 */

public class PayConfig implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String mark;
	private String markName;
	private String appid;
	private String mchid;
	private String deviceInfo;
	private String signkey;
	private String tradeType;
	private String notifyurl;
	private String signtype;
	private String remark;

	// Constructors

	/** default constructor */
	public PayConfig() {
	}

	/** full constructor */
	public PayConfig(String mark, String markName, String appid, String mchid,
			String deviceInfo, String signkey, String tradeType,
			String notifyurl, String signtype, String remark) {
		this.mark = mark;
		this.markName = markName;
		this.appid = appid;
		this.mchid = mchid;
		this.deviceInfo = deviceInfo;
		this.signkey = signkey;
		this.tradeType = tradeType;
		this.notifyurl = notifyurl;
		this.signtype = signtype;
		this.remark = remark;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getMarkName() {
		return this.markName;
	}

	public void setMarkName(String markName) {
		this.markName = markName;
	}

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchid() {
		return this.mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getDeviceInfo() {
		return this.deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getSignkey() {
		return this.signkey;
	}

	public void setSignkey(String signkey) {
		this.signkey = signkey;
	}

	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getNotifyurl() {
		return this.notifyurl;
	}

	public void setNotifyurl(String notifyurl) {
		this.notifyurl = notifyurl;
	}

	public String getSigntype() {
		return this.signtype;
	}

	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}