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
 * DeclarationProjectResult entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "declaration_project_result", catalog = "team_gainet_com")
public class DeclarationProjectResult implements java.io.Serializable {

	// Fields

	private Integer id;
	private Short type;
	private String certNumber;
	private String registrationNumber;
	private String projectResultName;
	private Integer memberId;
	private String memberName;
	private Date applyDate;
	private Date acceptDate;
	private Date downDate;
	private String company;
	private Integer projectId;
	private Integer payment;
	private Integer invoice;
	private Integer receipt;
	private Integer isAllDoc;
	private String agent;
	private String version;
	private String inventor;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String remark;
	private Short state;

	// Constructors

	/** default constructor */
	public DeclarationProjectResult() {
	}

	/** full constructor */
	public DeclarationProjectResult(Short type, String certNumber,
			String registrationNumber, String projectResultName,
			Integer memberId, String memberName, Date applyDate,
			Date acceptDate, Date downDate, String company, Integer projectId,
			Integer payment, Integer invoice, Integer receipt,
			Integer isAllDoc, String agent, String version, String inventor,
			Timestamp createTime, Timestamp updateTime, String remark,
			Short state) {
		this.type = type;
		this.certNumber = certNumber;
		this.registrationNumber = registrationNumber;
		this.projectResultName = projectResultName;
		this.memberId = memberId;
		this.memberName = memberName;
		this.applyDate = applyDate;
		this.acceptDate = acceptDate;
		this.downDate = downDate;
		this.company = company;
		this.projectId = projectId;
		this.payment = payment;
		this.invoice = invoice;
		this.receipt = receipt;
		this.isAllDoc = isAllDoc;
		this.agent = agent;
		this.version = version;
		this.inventor = inventor;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remark = remark;
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

	@Column(name = "type")
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "cert_number", length = 64)
	public String getCertNumber() {
		return this.certNumber;
	}

	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}

	@Column(name = "registration_number", length = 32)
	public String getRegistrationNumber() {
		return this.registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	@Column(name = "project_result_name", length = 64)
	public String getProjectResultName() {
		return this.projectResultName;
	}

	public void setProjectResultName(String projectResultName) {
		this.projectResultName = projectResultName;
	}

	@Column(name = "member_id")
	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Column(name = "member_name", length = 32)
	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "apply_date", length = 10)
	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "accept_date", length = 10)
	public Date getAcceptDate() {
		return this.acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "down_date", length = 10)
	public Date getDownDate() {
		return this.downDate;
	}

	public void setDownDate(Date downDate) {
		this.downDate = downDate;
	}

	@Column(name = "company", length = 32)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "project_id")
	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name = "payment")
	public Integer getPayment() {
		return this.payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	@Column(name = "invoice")
	public Integer getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Integer invoice) {
		this.invoice = invoice;
	}

	@Column(name = "receipt")
	public Integer getReceipt() {
		return this.receipt;
	}

	public void setReceipt(Integer receipt) {
		this.receipt = receipt;
	}

	@Column(name = "is_all_doc")
	public Integer getIsAllDoc() {
		return this.isAllDoc;
	}

	public void setIsAllDoc(Integer isAllDoc) {
		this.isAllDoc = isAllDoc;
	}

	@Column(name = "agent", length = 32)
	public String getAgent() {
		return this.agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	@Column(name = "version")
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "inventor", length = 128)
	public String getInventor() {
		return this.inventor;
	}

	public void setInventor(String inventor) {
		this.inventor = inventor;
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

	@Column(name = "remark")
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

}