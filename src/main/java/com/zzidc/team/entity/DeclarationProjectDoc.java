package com.zzidc.team.entity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DeclarationProjectDoc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "declaration_project_doc")
public class DeclarationProjectDoc implements java.io.Serializable {

	// Fields

	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = 8798900341735066779L;
	private Integer id;
	private Timestamp createTime;
	private String docName;
	private String originalName;
	private Integer docState;
	private String projectDocUrl;
	private Integer projectId;
	private Date provideDate;
	private Integer resultId;
	private Integer state;
	private Integer typeId;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public DeclarationProjectDoc() {
	}

	/** full constructor */
	public DeclarationProjectDoc(Timestamp createTime, String docName, String originalName,
			Integer docState, String projectDocUrl,
			Integer projectId, Date provideDate, Integer resultId,
			Integer state, Integer typeId, Timestamp updateTime) {
		this.createTime = createTime;
		this.docName = docName;
		this.originalName = originalName;
		this.docState = docState;
		this.projectDocUrl = projectDocUrl;
		this.projectId = projectId;
		this.provideDate = provideDate;
		this.resultId = resultId;
		this.state = state;
		this.typeId = typeId;
		this.updateTime = updateTime;
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

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "doc_name", length = 128)
	public String getDocName() {
		return this.docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	@Column(name = "original_name", length = 255)
	public String getOriginalName() {
		return this.originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	@Column(name = "doc_state")
	public Integer getDocState() {
		return this.docState;
	}

	public void setDocState(Integer docState) {
		this.docState = docState;
	}

	@Column(name = "project_doc_url")
	public String getProjectDocUrl() {
		return this.projectDocUrl;
	}

	public void setProjectDocUrl(String projectDocUrl) {
		this.projectDocUrl = projectDocUrl;
	}

	@Column(name = "project_id")
	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "provide_date", length = 10)
	public Date getProvideDate() {
		return this.provideDate;
	}

	public void setProvideDate(Date provideDate) {
		this.provideDate = provideDate;
	}

	@Column(name = "result_id")
	public Integer getResultId() {
		return this.resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "type_id")
	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Column(name = "update_time", length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}