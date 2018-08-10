package com.zzidc.team.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Department entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "department")
public class Department implements java.io.Serializable {

	// Fields

	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = 8643745490307098639L;
	private Integer id;
	private Integer sort;
	private String name;
	private Integer leaderId;
	private String leaderName;
	private Integer parentId;
	private short state;
	private Timestamp createTime;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public Department() {
	}

	/** full constructor */
	public Department(Integer sort, String name, Integer leaderId, Integer parentId) {
		this.sort = sort;
		this.name = name;
		this.leaderId = leaderId;
		this.parentId = parentId;
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

	@Column(name = "sort", nullable = false)
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "leader_id", nullable = false)
	public Integer getLeaderId() {
		return this.leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	@Column(name = "parent_id", nullable = false)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "leader_name", nullable = false)
	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
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

	@Column(name = "state", nullable = false)
	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", sort=" + sort + ", name=" + name + ", leaderId=" + leaderId + ", leaderName="
				+ leaderName + ", parentId=" + parentId + ", state=" + state + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
}