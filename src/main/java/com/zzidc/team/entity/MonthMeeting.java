package com.zzidc.team.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MonthMeeting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "month_meeting", catalog = "team_gainet_com")
public class MonthMeeting implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String remark;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Short state;

	// Constructors

	/** default constructor */
	public MonthMeeting() {
	}

	/** minimal constructor */
	public MonthMeeting(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public MonthMeeting(Integer id, String name, String remark,
			Timestamp createTime, Timestamp updateTime, Short state) {
		this.id = id;
		this.name = name;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

}