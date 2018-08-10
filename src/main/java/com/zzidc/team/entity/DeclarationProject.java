package com.zzidc.team.entity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DeclarationProject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "declaration_project")
public class DeclarationProject implements java.io.Serializable {

	// Fields

	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = -5666554868533825835L;
	private Integer id;
	private String declarationNumber;
	private String projectName;
	private String company;
	private Date startDate;
	private Date endDate;
	private String stage;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer state;

	// Constructors

	/** default constructor */
	public DeclarationProject() {
	}

	/** full constructor */
	public DeclarationProject(String declarationNumber, String projectName,
			String company, Date startDate, Date endDate, String stage,
			Timestamp createTime, Timestamp updateTime, Integer state) {
		this.declarationNumber = declarationNumber;
		this.projectName = projectName;
		this.company = company;
		this.startDate = startDate;
		this.endDate = endDate;
		this.stage = stage;
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

	@Column(name = "declaration_number")
	public String getDeclarationNumber() {
		return this.declarationNumber;
	}

	public void setDeclarationNumber(String declarationNumber) {
		this.declarationNumber = declarationNumber;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", length = 10)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", length = 10)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "stage", length = 8)
	public String getStage() {
		return this.stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
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
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}