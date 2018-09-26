package com.zzidc.team.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProjectMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project_member", catalog = "team_gainet_com")
public class ProjectMember implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer projectId;
	private Short memberType;
	private Integer memberId;
	private Short state;

	// Constructors

	/** default constructor */
	public ProjectMember() {
	}

	/** minimal constructor */
	public ProjectMember(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public ProjectMember(Integer id, Integer projectId, Short memberType,
			Integer memberId, Short state) {
		this.id = id;
		this.projectId = projectId;
		this.memberType = memberType;
		this.memberId = memberId;
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

	@Column(name = "project_id")
	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name = "member_type")
	public Short getMemberType() {
		return this.memberType;
	}

	public void setMemberType(Short memberType) {
		this.memberType = memberType;
	}

	@Column(name = "member_id")
	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

}