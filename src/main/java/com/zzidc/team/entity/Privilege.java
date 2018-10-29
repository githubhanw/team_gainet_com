package com.zzidc.team.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Privilege entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "privilege", catalog = "team_gainet_com")
public class Privilege implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String url;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String remark;
	private Integer parentId;
	private Integer rank;
	private Short state;

	// Constructors

	/** default constructor */
	public Privilege() {
	}

	/** minimal constructor */
	public Privilege(String name, Timestamp updateTime, Integer parentId) {
		this.name = name;
		this.updateTime = updateTime;
		this.parentId = parentId;
	}

	/** full constructor */
	public Privilege(String name, String url, Timestamp createTime,
			Timestamp updateTime, String remark, Integer parentId,
			Integer rank, Short state) {
		this.name = name;
		this.url = url;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remark = remark;
		this.parentId = parentId;
		this.rank = rank;
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

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "url")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", nullable = false, length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "parent_id", nullable = false)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "rank")
	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

}