package com.zzidc.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TestCaseStep entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "test_case_step", catalog = "team_gainet_com")
public class TestCaseStep implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer caseId;
	private Short version;
	private String step;
	private String expect;

	// Constructors

	/** default constructor */
	public TestCaseStep() {
	}

	/** full constructor */
	public TestCaseStep(Integer caseId, Short version, String step,
			String expect) {
		this.caseId = caseId;
		this.version = version;
		this.step = step;
		this.expect = expect;
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

	@Column(name = "case_id", nullable = false)
	public Integer getCaseId() {
		return this.caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	@Column(name = "version", nullable = false)
	public Short getVersion() {
		return this.version;
	}

	public void setVersion(Short version) {
		this.version = version;
	}

	@Column(name = "step", nullable = false, length = 65535)
	public String getStep() {
		return this.step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	@Column(name = "expect", nullable = false, length = 65535)
	public String getExpect() {
		return this.expect;
	}

	public void setExpect(String expect) {
		this.expect = expect;
	}

}