package com.zzidc.team.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Publish entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "publish", catalog = "team_gainet_com")
public class Publish implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String content;
	private String site;
	private String performSql;
	private String special;
	private String remark;
	private Short state;
	private String version;
	private Timestamp createTime;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public Publish() {
	}

	/** minimal constructor */
	public Publish(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Publish(Integer id, String name, String content, String site,
			String performSql, String special, String remark, Short state,String version,
			Timestamp createTime, Timestamp updateTime) {
		this.id = id;
		this.name = name;
		this.content = content;
		this.site = site;
		this.performSql = performSql;
		this.special = special;
		this.remark = remark;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.version = version;
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

	@Column(name = "name", length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "site", length = 65535)
	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	@Column(name = "perform_sql", length = 65535)
	public String getPerformSql() {
		return this.performSql;
	}

	public void setPerformSql(String performSql) {
		this.performSql = performSql;
	}

	@Column(name = "special", length = 65535)
	public String getSpecial() {
		return this.special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	@Column(name = "remark", length = 65535)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}
	
	@Column(name = "version", length = 255)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
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

}