package com.zzidc.team.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TaskProject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "task_project")
public class TaskProject implements java.io.Serializable {

	// Fields

	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = -2509771681973641094L;
	private Integer id;
	private String projectName;
	private String company;
	private Integer memberId;
	private String remark;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Short state;

	// Constructors

	/** default constructor */
	public TaskProject() {
	}

	/** minimal constructor */
	public TaskProject(Short state) {
		this.state = state;
	}

	/** full constructor */
	public TaskProject(String projectName, String company, Integer memberId,
			String remark, Timestamp createTime, Timestamp updateTime,
			Short state) {
		this.projectName = projectName;
		this.company = company;
		this.memberId = memberId;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
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

	@Column(name = "project_name", length = 64)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "company", length = 64)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "member_id")
	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Column(name = "remark", length = 65535)
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

	@Column(name = "state", nullable = false)
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

}