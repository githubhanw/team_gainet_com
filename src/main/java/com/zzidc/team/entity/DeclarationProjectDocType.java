package com.zzidc.team.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DeclarationProjectDocType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "declaration_project_doc_type")
public class DeclarationProjectDocType implements java.io.Serializable {

	// Fields

	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = 8019192960938597256L;
	private Integer id;
	private String projectDocType;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer state;

	// Constructors

	/** default constructor */
	public DeclarationProjectDocType() {
	}

	/** full constructor */
	public DeclarationProjectDocType(String projectDocType,
			Timestamp createTime, Timestamp updateTime, Integer state) {
		this.projectDocType = projectDocType;
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

	@Column(name = "project_doc_type", length = 64)
	public String getProjectDocType() {
		return this.projectDocType;
	}

	public void setProjectDocType(String projectDocType) {
		this.projectDocType = projectDocType;
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