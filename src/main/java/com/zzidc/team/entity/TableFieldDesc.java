package com.zzidc.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TableFieldDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "table_field_desc", catalog = "team_gainet_com")
public class TableFieldDesc implements java.io.Serializable {

	// Fields

	private Integer id;
	private String tableName;
	private String fieldName;
	private String fieldDesc;
	private Short state;

	// Constructors

	/** default constructor */
	public TableFieldDesc() {
	}

	/** full constructor */
	public TableFieldDesc(String tableName, String fieldName, String fieldDesc,
			Short state) {
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.fieldDesc = fieldDesc;
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

	@Column(name = "table_name", length = 32)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "field_name", length = 32)
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Column(name = "field_desc")
	public String getFieldDesc() {
		return this.fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

}