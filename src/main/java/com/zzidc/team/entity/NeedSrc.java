package com.zzidc.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * NeedSrc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "need_src")
public class NeedSrc implements java.io.Serializable {

	// Fields

	private Integer id;
	private String needSrc;
	private Integer sort;

	// Constructors

	/** default constructor */
	public NeedSrc() {
	}

	/** full constructor */
	public NeedSrc(String needSrc, Integer sort) {
		this.needSrc = needSrc;
		this.sort = sort;
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

	@Column(name = "need_src", length = 32)
	public String getNeedSrc() {
		return this.needSrc;
	}

	public void setNeedSrc(String needSrc) {
		this.needSrc = needSrc;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}