package com.zzidc.team.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ResultTypeDocType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "result_type_doc_type", catalog = "team_gainet_com")
public class ResultTypeDocType implements java.io.Serializable {

	// Fields

	private Integer id;
	private Short resultType;
	private Integer docTypeId;
	private Short requiredOrOptional;

	// Constructors

	/** default constructor */
	public ResultTypeDocType() {
	}

	/** minimal constructor */
	public ResultTypeDocType(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public ResultTypeDocType(Integer id, Short resultType, Integer docTypeId,
			Short requiredOrOptional) {
		this.id = id;
		this.resultType = resultType;
		this.docTypeId = docTypeId;
		this.requiredOrOptional = requiredOrOptional;
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

	@Column(name = "result_type")
	public Short getResultType() {
		return this.resultType;
	}

	public void setResultType(Short resultType) {
		this.resultType = resultType;
	}

	@Column(name = "doc_type_id")
	public Integer getDocTypeId() {
		return this.docTypeId;
	}

	public void setDocTypeId(Integer docTypeId) {
		this.docTypeId = docTypeId;
	}

	@Column(name = "required_or_optional")
	public Short getRequiredOrOptional() {
		return this.requiredOrOptional;
	}

	public void setRequiredOrOptional(Short requiredOrOptional) {
		this.requiredOrOptional = requiredOrOptional;
	}

}