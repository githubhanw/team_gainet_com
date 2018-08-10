package com.zzidc.team.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TestApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "test_apply")
public class TestApply implements java.io.Serializable {

	// Fields

	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = 4657631029422153205L;
	private Integer id;
	private Integer taskId;
	private Integer applyId;
	private String applyName;
	private Timestamp applyTime;
	private Short state;
	private String testContent;
	private String dismissal;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public TestApply() {
	}

	/** full constructor */
	public TestApply(Integer taskId, Integer applyId, String applyName,
			Timestamp applyTime, Short state, String testContent,
			String dismissal) {
		this.taskId = taskId;
		this.applyId = applyId;
		this.applyName = applyName;
		this.applyTime = applyTime;
		this.state = state;
		this.testContent = testContent;
		this.dismissal = dismissal;
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

	@Column(name = "apply_id")
	public Integer getApplyId() {
		return this.applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	@Column(name = "apply_name")
	public String getApplyName() {
		return this.applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	@Column(name = "apply_time", length = 19)
	public Timestamp getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@Column(name = "test_content", length = 65535)
	public String getTestContent() {
		return this.testContent;
	}

	public void setTestContent(String testContent) {
		this.testContent = testContent;
	}

	@Column(name = "dismissal")
	public String getDismissal() {
		return this.dismissal;
	}

	public void setDismissal(String dismissal) {
		this.dismissal = dismissal;
	}

	@Column(name = "update_time", length = 19)
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "TestApply [id=" + id + ", taskId=" + taskId + ", applyId=" + applyId + ", applyName=" + applyName
				+ ", applyTime=" + applyTime + ", state=" + state + ", testContent=" + testContent + ", dismissal="
				+ dismissal + "]";
	}

}