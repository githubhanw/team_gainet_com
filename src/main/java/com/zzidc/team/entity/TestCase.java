package com.zzidc.team.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TestCase entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "test_case", catalog = "team_gainet_com")
public class TestCase implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer taskId;
	private String caseName;
	private Integer createId;
	private String createName;
	private Integer updateId;
	private String updateName;
	private Integer caseType;
	private String precondition;
	private Integer version;
	private String remark;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Integer state;

	// Constructors

	/** default constructor */
	public TestCase() {
	}

	/** full constructor */
	public TestCase(Integer taskId, String caseName, Integer createId,
			String createName, Integer updateId, String updateName,
			Integer caseType, String precondition, Integer version, String remark,
			Timestamp createtime, Timestamp updatetime, Integer state) {
		this.taskId = taskId;
		this.caseName = caseName;
		this.createId = createId;
		this.createName = createName;
		this.updateId = updateId;
		this.updateName = updateName;
		this.caseType = caseType;
		this.precondition = precondition;
		this.version = version;
		this.remark = remark;
		this.createtime = createtime;
		this.updatetime = updatetime;
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

	@Column(name = "task_id")
	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Column(name = "case_name")
	public String getCaseName() {
		return this.caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	@Column(name = "create_id")
	public Integer getCreateId() {
		return this.createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	@Column(name = "create_name")
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "update_id")
	public Integer getUpdateId() {
		return this.updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	@Column(name = "update_name")
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "case_type")
	public Integer getCaseType() {
		return this.caseType;
	}

	public void setCaseType(Integer caseType) {
		this.caseType = caseType;
	}

	@Column(name = "precondition", length = 65535)
	public String getPrecondition() {
		return this.precondition;
	}

	public void setPrecondition(String precondition) {
		this.precondition = precondition;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "remark", length = 65535)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "createtime", length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "updatetime", length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}