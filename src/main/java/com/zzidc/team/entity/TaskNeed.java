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
 * TaskNeed entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "task_need", catalog = "team_gainet_com")
public class TaskNeed implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer projectId;
	private Integer productId;
	private String interfaceImg;
	private String flowImg;
	private String needName;
	private Integer departmentId;
	private String departmentName;
	private Integer memberId;
	private String memberName;
	private Integer createId;
	private String createName;
	private Integer srcId;
	private String srcRemark;
	private Integer level;
	private Date startDate;
	private Date cendDate;
	private Date tendDate;
	private Date endDate;
	private Date planEndDate;
	private String needRemark;
	private String checkRemark;
	private Integer assignedId;
	private String assignedName;
	private Timestamp assignedTime;
	private Integer changedId;
	private String changedName;
	private Timestamp changedTime;
	private Short changedCount;
	private Integer closedId;
	private String closedName;
	private Timestamp closedTime;
	private String closedReason;
	private Integer checkedId;
	private String checkedName;
	private Timestamp checkedTime;
	private Short resolved;
	private Integer parentId;
	private String link;
	private Short full;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Short state;
	private Integer meetingId;
	private Integer openedId;
	private String openedName;
	private Timestamp openedTime;
	private Integer finishedId;
	private String finishedName;
	private Timestamp finishedTime;
	private Short overdue;
	private Timestamp realStartDate;
	private Timestamp realEndDate;
	private Short changedStatus; 
	// Constructors

	/** default constructor */
	public TaskNeed() {
	}

	/** full constructor */
	public TaskNeed(Integer projectId, Integer productId, String interfaceImg,
			String flowImg, String needName, Integer departmentId, String departmentName,
			Integer memberId, String memberName, Integer createId, String createName,
			Integer srcId, String srcRemark, Integer level, Date startDate, Date cendDate, Date tendDate,
			Date endDate, Date planEndDate, String needRemark,
			String checkRemark, Integer assignedId, String assignedName,
			Timestamp assignedTime, Integer changedId, String changedName,
			Timestamp changedTime, Short changedCount, Integer closedId,
			String closedName, Timestamp closedTime, String closedReason,
			Integer checkedId, String checkedName, Timestamp checkedTime,
			Short resolved, Integer parentId, String link, Short full,
			Timestamp createTime, Timestamp updateTime, Short state,
			Integer meetingId, Integer openedId, String openedName,
			Timestamp openedTime, Integer finishedId, String finishedName,
			Timestamp finishedTime, Short overdue, Timestamp realStartDate,
			Timestamp realEndDate, Short changedStatus) {
		this.projectId = projectId;
		this.productId = productId;
		this.interfaceImg=interfaceImg;
		this.flowImg=flowImg;
		this.needName = needName;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.memberId = memberId;
		this.memberName = memberName;
		this.createId = createId;
		this.createName = createName;
		this.srcId = srcId;
		this.srcRemark = srcRemark;
		this.level = level;
		this.startDate = startDate;
		this.cendDate = cendDate;
		this.tendDate = tendDate;
		this.endDate = endDate;
		this.planEndDate = planEndDate;
		this.needRemark = needRemark;
		this.checkRemark = checkRemark;
		this.assignedId = assignedId;
		this.assignedName = assignedName;
		this.assignedTime = assignedTime;
		this.changedId = changedId;
		this.changedName = changedName;
		this.changedTime = changedTime;
		this.changedCount = changedCount;
		this.closedId = closedId;
		this.closedName = closedName;
		this.closedTime = closedTime;
		this.closedReason = closedReason;
		this.checkedId = checkedId;
		this.checkedName = checkedName;
		this.checkedTime = checkedTime;
		this.resolved = resolved;
		this.parentId = parentId;
		this.link = link;
		this.full = full;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
		this.meetingId = meetingId;
		this.openedId = openedId;
		this.openedName = openedName;
		this.openedTime = openedTime;
		this.finishedId = finishedId;
		this.finishedName = finishedName;
		this.finishedTime = finishedTime;
		this.overdue = overdue;
		this.realStartDate = realStartDate;
		this.realEndDate = realEndDate;
		this.changedStatus = changedStatus;
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

	@Column(name = "product_id")
	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "interface_img")
	public String getInterfaceImg() {
		return interfaceImg;
	}

	public void setInterfaceImg(String interfaceImg) {
		this.interfaceImg = interfaceImg;
	}

	@Column(name = "flow_img")
	public String getFlowImg() {
		return flowImg;
	}

	public void setFlowImg(String flowImg) {
		this.flowImg = flowImg;
	}

	@Column(name = "need_name", length = 64)
	public String getNeedName() {
		return this.needName;
	}

	public void setNeedName(String needName) {
		this.needName = needName;
	}
	

	@Column(name = "department_id")
	public Integer getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "department_name", length = 64)
	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	@Column(name = "member_id")
	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Column(name = "member_name", length = 64)
	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Column(name = "create_id")
	public Integer getCreateId() {
		return this.createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	@Column(name = "create_name", length = 32)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "src_id")
	public Integer getSrcId() {
		return this.srcId;
	}

	public void setSrcId(Integer srcId) {
		this.srcId = srcId;
	}

	@Column(name = "src_remark", length = 100)
	public String getSrcRemark() {
		return this.srcRemark;
	}

	public void setSrcRemark(String srcRemark) {
		this.srcRemark = srcRemark;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", length = 10)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cend_date", length = 10)
	public Date getCEndDate() {
		return this.cendDate;
	}

	public void setCEndDate(Date cendDate) {
		this.cendDate = cendDate;
	}
	

	@Temporal(TemporalType.DATE)
	@Column(name = "tend_date", length = 10)
	public Date getTEndDate() {
		return this.tendDate;
	}

	public void setTEndDate(Date tendDate) {
		this.tendDate = tendDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", length = 10)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "plan_end_date", length = 10)
	public Date getPlanEndDate() {
		return this.planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	@Column(name = "need_remark", length = 16777215)
	public String getNeedRemark() {
		return this.needRemark;
	}

	public void setNeedRemark(String needRemark) {
		this.needRemark = needRemark;
	}

	@Column(name = "check_remark", length = 16777215)
	public String getCheckRemark() {
		return this.checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	@Column(name = "assigned_id")
	public Integer getAssignedId() {
		return this.assignedId;
	}

	public void setAssignedId(Integer assignedId) {
		this.assignedId = assignedId;
	}

	@Column(name = "assigned_name", length = 16)
	public String getAssignedName() {
		return this.assignedName;
	}

	public void setAssignedName(String assignedName) {
		this.assignedName = assignedName;
	}

	@Column(name = "assigned_time", length = 19)
	public Timestamp getAssignedTime() {
		return this.assignedTime;
	}

	public void setAssignedTime(Timestamp assignedTime) {
		this.assignedTime = assignedTime;
	}

	@Column(name = "changed_id")
	public Integer getChangedId() {
		return this.changedId;
	}

	public void setChangedId(Integer changedId) {
		this.changedId = changedId;
	}

	@Column(name = "changed_name", length = 16)
	public String getChangedName() {
		return this.changedName;
	}

	public void setChangedName(String changedName) {
		this.changedName = changedName;
	}

	@Column(name = "changed_time", length = 19)
	public Timestamp getChangedTime() {
		return this.changedTime;
	}

	public void setChangedTime(Timestamp changedTime) {
		this.changedTime = changedTime;
	}

	@Column(name = "changed_count")
	public Short getChangedCount() {
		return this.changedCount;
	}

	public void setChangedCount(Short changedCount) {
		this.changedCount = changedCount;
	}

	@Column(name = "closed_id")
	public Integer getClosedId() {
		return this.closedId;
	}

	public void setClosedId(Integer closedId) {
		this.closedId = closedId;
	}

	@Column(name = "closed_name", length = 16)
	public String getClosedName() {
		return this.closedName;
	}

	public void setClosedName(String closedName) {
		this.closedName = closedName;
	}

	@Column(name = "closed_time", length = 19)
	public Timestamp getClosedTime() {
		return this.closedTime;
	}

	public void setClosedTime(Timestamp closedTime) {
		this.closedTime = closedTime;
	}

	@Column(name = "closed_reason", length = 16)
	public String getClosedReason() {
		return this.closedReason;
	}

	public void setClosedReason(String closedReason) {
		this.closedReason = closedReason;
	}

	@Column(name = "checked_id")
	public Integer getCheckedId() {
		return this.checkedId;
	}

	public void setCheckedId(Integer checkedId) {
		this.checkedId = checkedId;
	}

	@Column(name = "checked_name", length = 16)
	public String getCheckedName() {
		return this.checkedName;
	}

	public void setCheckedName(String checkedName) {
		this.checkedName = checkedName;
	}

	@Column(name = "checked_time", length = 19)
	public Timestamp getCheckedTime() {
		return this.checkedTime;
	}

	public void setCheckedTime(Timestamp checkedTime) {
		this.checkedTime = checkedTime;
	}

	@Column(name = "resolved")
	public Short getResolved() {
		return this.resolved;
	}

	public void setResolved(Short resolved) {
		this.resolved = resolved;
	}

	@Column(name = "parent_id")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "link")
	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name = "full")
	public Short getFull() {
		return this.full;
	}

	public void setFull(Short full) {
		this.full = full;
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

	@Column(name = "state")
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@Column(name = "meeting_id")
	public Integer getMeetingId() {
		return this.meetingId;
	}

	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}

	@Column(name = "opened_id")
	public Integer getOpenedId() {
		return this.openedId;
	}

	public void setOpenedId(Integer openedId) {
		this.openedId = openedId;
	}

	@Column(name = "opened_name", length = 16)
	public String getOpenedName() {
		return this.openedName;
	}

	public void setOpenedName(String openedName) {
		this.openedName = openedName;
	}

	@Column(name = "opened_time", length = 19)
	public Timestamp getOpenedTime() {
		return this.openedTime;
	}

	public void setOpenedTime(Timestamp openedTime) {
		this.openedTime = openedTime;
	}

	@Column(name = "finished_id")
	public Integer getFinishedId() {
		return this.finishedId;
	}

	public void setFinishedId(Integer finishedId) {
		this.finishedId = finishedId;
	}

	@Column(name = "finished_name", length = 16)
	public String getFinishedName() {
		return this.finishedName;
	}

	public void setFinishedName(String finishedName) {
		this.finishedName = finishedName;
	}

	@Column(name = "finished_time", length = 19)
	public Timestamp getFinishedTime() {
		return this.finishedTime;
	}

	public void setFinishedTime(Timestamp finishedTime) {
		this.finishedTime = finishedTime;
	}

	@Column(name = "overdue")
	public Short getOverdue() {
		return this.overdue;
	}

	public void setOverdue(Short overdue) {
		this.overdue = overdue;
	}

	@Column(name = "real_start_date", length = 19)
	public Timestamp getRealStartDate() {
		return this.realStartDate;
	}

	public void setRealStartDate(Timestamp realStartDate) {
		this.realStartDate = realStartDate;
	}

	@Column(name = "real_end_date", length = 19)
	public Timestamp getRealEndDate() {
		return this.realEndDate;
	}

	public void setRealEndDate(Timestamp realEndDate) {
		this.realEndDate = realEndDate;
	}

	@Column(name = "changed_status")
	public Short getChangedStatus() {
		return this.changedStatus;
	}

	public void setChangedStatus(Short changedStatus) {
		this.changedStatus = changedStatus;
	}

}