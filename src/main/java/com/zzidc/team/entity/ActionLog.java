package com.zzidc.team.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ActionLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "action_log")
public class ActionLog implements java.io.Serializable {

	// Fields

	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = -8178428382449637086L;
	private Integer id;
	private String loginName;
	private String memberName;
	private String module;
	private Integer objectId;
	private String method;
	private String params;
	private Timestamp endTime;
	private Timestamp startTime;
	private Integer timeLength;
	private String comment;

	// Constructors

	/** default constructor */
	public ActionLog() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "login_name", length = 30)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "member_name", length = 30)
	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Column(name = "module", length = 30)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Column(name = "object_id")
	public Integer getObjectId() {
		return this.objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	@Column(name = "method", length = 64)
	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name = "params", length = 65535)
	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Column(name = "end_time", length = 19)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "start_time", length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "time_length")
	public Integer getTimeLength() {
		return this.timeLength;
	}

	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
	}

	@Column(name = "comment", length = 65535)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "ActionLog [id=" + id + ", loginName=" + loginName + ", memberName=" + memberName + ", module=" + module
				+ ", objectId=" + objectId + ", method=" + method + ", params=" + params
				+ ", endTime=" + endTime + ", startTime=" + startTime + ", timeLength=" + timeLength + ", comment="
				+ comment + "]";
	}

}