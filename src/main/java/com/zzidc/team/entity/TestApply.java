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
@Table(name = "test_apply", catalog = "team_gainet_com")
public class TestApply implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer taskId;
	private Integer needId;
	private Integer projectId;
	private Integer productId;
	private String testName;
	private Integer applyType;
	private Integer applyId;
	private String applyName;
	private Timestamp applyTime;
	private Short state;
	private String testContent;
	private String executeSql;
	private String dismissal;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public TestApply() {
	}

	/** full constructor */
	public TestApply(Integer taskId, Integer needId, Integer projectId,
			Integer productId, String testName, Integer applyType,
			Integer applyId, String applyName, Timestamp applyTime,
			Short state, String testContent, String executeSql,
			String dismissal, Timestamp updateTime) {
		this.taskId = taskId;
		this.needId = needId;
		this.projectId = projectId;
		this.productId = productId;
		this.testName = testName;
		this.applyType = applyType;
		this.applyId = applyId;
		this.applyName = applyName;
		this.applyTime = applyTime;
		this.state = state;
		this.testContent = testContent;
		this.executeSql = executeSql;
		this.dismissal = dismissal;
		this.updateTime = updateTime;
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

	@Column(name = "need_id")
	public Integer getNeedId() {
		return this.needId;
	}

	public void setNeedId(Integer needId) {
		this.needId = needId;
	}

	@Column(name = "project_id")
	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name = "product_id")
	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "test_name")
	public String getTestName() {
		return this.testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	@Column(name = "apply_type")
	public Integer getApplyType() {
		return this.applyType;
	}

	public void setApplyType(Integer applyType) {
		this.applyType = applyType;
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

	@Column(name = "execute_sql", length = 65535)
	public String getExecuteSql() {
		return this.executeSql;
	}

	public void setExecuteSql(String executeSql) {
		this.executeSql = executeSql;
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
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}