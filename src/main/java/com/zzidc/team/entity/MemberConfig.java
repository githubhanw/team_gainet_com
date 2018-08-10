package com.zzidc.team.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Member entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "member_config")
public class MemberConfig implements java.io.Serializable {

	// Fields
	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = 7394961844399168119L;
	private Integer id;
	private String number;
	private Integer departmentId;
	private String roleIds;
	private Timestamp createTime;
	private Timestamp updateTime;
	
	// Constructors

	/** default constructor */
	public MemberConfig() {
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

	@Column(name = "role_ids", length = 100)
	public String getRoleIds() {
		return this.roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	@Column(name = "number", unique = true, nullable = false)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", length = 19)
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "department_id", nullable = false)
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public String toString() {
		return "MemberConfig [id=" + id + ", number=" + number + ", departmentId=" + departmentId + ", roleIds="
				+ roleIds + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

}