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
@Table(name = "task_project", catalog = "team_gainet_com")
public class TaskProject implements java.io.Serializable {

	// Fields

	private Integer id;
	private String projectName;
	private String company;
	private Integer memberId;
	private String remark;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Short state;
	private String startTime;
	private String endTime;
	private String projectType;
	private String projectContent;
	private String customerName;
	private Integer demandId;
	private String timeLimit;
	private String budget;
	private String fileUrl;
	private Integer overtime;
	private String assessment;
	private String actualEndTime;

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
			Short state, String startTime, String endTime, String projectType,
			String projectContent, String customerName, Integer demandId,
			String timeLimit, String budget, String fileUrl, Integer overtime,
			String assessment, String actualEndTime) {
		this.projectName = projectName;
		this.company = company;
		this.memberId = memberId;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
		this.startTime = startTime;
		this.endTime = endTime;
		this.projectType = projectType;
		this.projectContent = projectContent;
		this.customerName = customerName;
		this.demandId = demandId;
		this.timeLimit = timeLimit;
		this.budget = budget;
		this.fileUrl = fileUrl;
		this.overtime = overtime;
		this.assessment = assessment;
		this.actualEndTime = actualEndTime;
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

	@Column(name = "start_time", length = 25)
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 25)
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "project_type", length = 2)
	public String getProjectType() {
		return this.projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	@Column(name = "project_content", length = 65535)
	public String getProjectContent() {
		return this.projectContent;
	}

	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}

	@Column(name = "customer_name")
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "demand_id")
	public Integer getDemandId() {
		return this.demandId;
	}

	public void setDemandId(Integer demandId) {
		this.demandId = demandId;
	}

	@Column(name = "time_limit")
	public String getTimeLimit() {
		return this.timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Column(name = "budget")
	public String getBudget() {
		return this.budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	@Column(name = "file_url")
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name = "overtime")
	public Integer getOvertime() {
		return this.overtime;
	}

	public void setOvertime(Integer overtime) {
		this.overtime = overtime;
	}

	@Column(name = "assessment")
	public String getAssessment() {
		return this.assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}
	
	@Column(name = "actual_end_time", length = 25)
	public String getActualEndTime() {
		return this.actualEndTime;
	}

	public void setActualEndTime(String actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

}