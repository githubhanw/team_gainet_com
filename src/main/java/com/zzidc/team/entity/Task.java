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
 * Task entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "task", catalog = "team_gainet_com")
public class Task implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer productId;
	private Integer projectId;
	private Integer needId;
	private String taskName;
	private Integer taskType;
	private Integer level;
	private String remark;
	private String executeSql;
	private Timestamp startDate;
	private Timestamp endDate;
	private String interfaceImg;
	private String flowImg;
	private Timestamp planEndDate;
	private Timestamp realStartDate;
	private Timestamp realEndDate;
	private Integer memberId;
	private String memberName;
	private Integer openedId;
	private String openedName;
	private Timestamp openedTime;
	private Integer handoverId;
	private String handoverName;
	private String handoverInfo;
	private Integer handoverState;
	private Timestamp handoverTime;
	private Integer assignedId;
	private String assignedName;
	private Timestamp assignedTime;
	private Integer delayedId;
	private String delayedName;
	private Timestamp delayedTime;
	private Date delayedDate;
	private Integer delayedReviewId;
	private Integer canceledId;
	private String canceledName;
	private Timestamp canceledTime;
	private Integer closedId;
	private String closedName;
	private Timestamp closedTime;
	private String closedReason;
	private Integer checkedId;
	private String checkedName;
	private Integer checkedNum;
	private String checkedReason;
	private Timestamp checkedTime;
	private Integer finishedId;
	private String finishedName;
	private Timestamp finishedTime;
	private Integer testApplyId;
	private String testId;
	private String testName;
	private Timestamp testTime;
	private Integer testState;
	private Integer percent;
	private Short resolved;
	private Integer parentId;
	private String link;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Short state;
	private Short delay;
	private Short overdue;
	private Short deleted;
	private String developerTaskId;

	// Constructors

	/** default constructor */
	public Task() {
	}

	/** full constructor */
	public Task(Integer productId, Integer projectId, Integer needId,
			String taskName, Integer taskType, Integer level, String remark,
			String executeSql, Timestamp startDate, Timestamp endDate,
			String interfaceImg, String flowImg, Timestamp planEndDate,
			Timestamp realStartDate, Timestamp realEndDate, Integer memberId,
			String memberName, Integer openedId, String openedName,
			Timestamp openedTime, Integer handoverId, String handoverName,
			String handoverInfo, Integer handoverState, Timestamp handoverTime,
			Integer assignedId, String assignedName, Timestamp assignedTime,
			Integer delayedId, String delayedName, Timestamp delayedTime,
			Date delayedDate, Integer delayedReviewId, Integer canceledId,
			String canceledName, Timestamp canceledTime, Integer closedId,
			String closedName, Timestamp closedTime, String closedReason,
			Integer checkedId, String checkedName, Integer checkedNum,
			String checkedReason, Timestamp checkedTime, Integer finishedId,
			String finishedName, Timestamp finishedTime, Integer testApplyId,
			String testId, String testName, Timestamp testTime,
			Integer testState, Integer percent, Short resolved,
			Integer parentId, String link, Timestamp createTime,
			Timestamp updateTime, Short state, Short delay, Short overdue,
			Short deleted, String developerTaskId) {
		this.productId = productId;
		this.projectId = projectId;
		this.needId = needId;
		this.taskName = taskName;
		this.taskType = taskType;
		this.level = level;
		this.remark = remark;
		this.executeSql = executeSql;
		this.startDate = startDate;
		this.endDate = endDate;
		this.interfaceImg = interfaceImg;
		this.flowImg = flowImg;
		this.planEndDate = planEndDate;
		this.realStartDate = realStartDate;
		this.realEndDate = realEndDate;
		this.memberId = memberId;
		this.memberName = memberName;
		this.openedId = openedId;
		this.openedName = openedName;
		this.openedTime = openedTime;
		this.handoverId = handoverId;
		this.handoverName = handoverName;
		this.handoverInfo = handoverInfo;
		this.handoverState = handoverState;
		this.handoverTime = handoverTime;
		this.assignedId = assignedId;
		this.assignedName = assignedName;
		this.assignedTime = assignedTime;
		this.delayedId = delayedId;
		this.delayedName = delayedName;
		this.delayedTime = delayedTime;
		this.delayedDate = delayedDate;
		this.delayedReviewId = delayedReviewId;
		this.canceledId = canceledId;
		this.canceledName = canceledName;
		this.canceledTime = canceledTime;
		this.closedId = closedId;
		this.closedName = closedName;
		this.closedTime = closedTime;
		this.closedReason = closedReason;
		this.checkedId = checkedId;
		this.checkedName = checkedName;
		this.checkedNum = checkedNum;
		this.checkedReason = checkedReason;
		this.checkedTime = checkedTime;
		this.finishedId = finishedId;
		this.finishedName = finishedName;
		this.finishedTime = finishedTime;
		this.testApplyId = testApplyId;
		this.testId = testId;
		this.testName = testName;
		this.testTime = testTime;
		this.testState = testState;
		this.percent = percent;
		this.resolved = resolved;
		this.parentId = parentId;
		this.link = link;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
		this.delay = delay;
		this.overdue = overdue;
		this.deleted = deleted;
		this.developerTaskId = developerTaskId;
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

	@Column(name = "product_id")
	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "project_id")
	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name = "need_id")
	public Integer getNeedId() {
		return this.needId;
	}

	public void setNeedId(Integer needId) {
		this.needId = needId;
	}

	@Column(name = "task_name")
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(name = "task_type")
	public Integer getTaskType() {
		return this.taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "remark", length = 65535)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "execute_sql", length = 65535)
	public String getExecuteSql() {
		return this.executeSql;
	}

	public void setExecuteSql(String executeSql) {
		this.executeSql = executeSql;
	}

	@Column(name = "start_date", length = 19)
	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date", length = 19)
	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	@Column(name = "interface_img", length = 65535)
	public String getInterfaceImg() {
		return this.interfaceImg;
	}

	public void setInterfaceImg(String interfaceImg) {
		this.interfaceImg = interfaceImg;
	}

	@Column(name = "flow_img", length = 65535)
	public String getFlowImg() {
		return this.flowImg;
	}

	public void setFlowImg(String flowImg) {
		this.flowImg = flowImg;
	}

	@Column(name = "plan_end_date", length = 19)
	public Timestamp getPlanEndDate() {
		return this.planEndDate;
	}

	public void setPlanEndDate(Timestamp planEndDate) {
		this.planEndDate = planEndDate;
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

	@Column(name = "member_id")
	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Column(name = "member_name", length = 16)
	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	@Column(name = "handover_id")
	public Integer getHandoverId() {
		return this.handoverId;
	}

	public void setHandoverId(Integer handoverId) {
		this.handoverId = handoverId;
	}

	@Column(name = "handover_name", length = 16)
	public String getHandoverName() {
		return this.handoverName;
	}

	public void setHandoverName(String handoverName) {
		this.handoverName = handoverName;
	}

	@Column(name = "handover_info", length = 65535)
	public String getHandoverInfo() {
		return this.handoverInfo;
	}

	public void setHandoverInfo(String handoverInfo) {
		this.handoverInfo = handoverInfo;
	}

	@Column(name = "handover_state")
	public Integer getHandoverState() {
		return this.handoverState;
	}

	public void setHandoverState(Integer handoverState) {
		this.handoverState = handoverState;
	}

	@Column(name = "handover_time", length = 19)
	public Timestamp getHandoverTime() {
		return this.handoverTime;
	}

	public void setHandoverTime(Timestamp handoverTime) {
		this.handoverTime = handoverTime;
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

	@Column(name = "delayed_id")
	public Integer getDelayedId() {
		return this.delayedId;
	}

	public void setDelayedId(Integer delayedId) {
		this.delayedId = delayedId;
	}

	@Column(name = "delayed_name", length = 16)
	public String getDelayedName() {
		return this.delayedName;
	}

	public void setDelayedName(String delayedName) {
		this.delayedName = delayedName;
	}

	@Column(name = "delayed_time", length = 19)
	public Timestamp getDelayedTime() {
		return this.delayedTime;
	}

	public void setDelayedTime(Timestamp delayedTime) {
		this.delayedTime = delayedTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "delayed_date", length = 10)
	public Date getDelayedDate() {
		return this.delayedDate;
	}

	public void setDelayedDate(Date delayedDate) {
		this.delayedDate = delayedDate;
	}

	@Column(name = "delayed_review_id")
	public Integer getDelayedReviewId() {
		return this.delayedReviewId;
	}

	public void setDelayedReviewId(Integer delayedReviewId) {
		this.delayedReviewId = delayedReviewId;
	}

	@Column(name = "canceled_id")
	public Integer getCanceledId() {
		return this.canceledId;
	}

	public void setCanceledId(Integer canceledId) {
		this.canceledId = canceledId;
	}

	@Column(name = "canceled_name", length = 16)
	public String getCanceledName() {
		return this.canceledName;
	}

	public void setCanceledName(String canceledName) {
		this.canceledName = canceledName;
	}

	@Column(name = "canceled_time", length = 19)
	public Timestamp getCanceledTime() {
		return this.canceledTime;
	}

	public void setCanceledTime(Timestamp canceledTime) {
		this.canceledTime = canceledTime;
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

	@Column(name = "checked_num")
	public Integer getCheckedNum() {
		return this.checkedNum;
	}

	public void setCheckedNum(Integer checkedNum) {
		this.checkedNum = checkedNum;
	}

	@Column(name = "checked_reason", length = 200)
	public String getCheckedReason() {
		return this.checkedReason;
	}

	public void setCheckedReason(String checkedReason) {
		this.checkedReason = checkedReason;
	}

	@Column(name = "checked_time", length = 19)
	public Timestamp getCheckedTime() {
		return this.checkedTime;
	}

	public void setCheckedTime(Timestamp checkedTime) {
		this.checkedTime = checkedTime;
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

	@Column(name = "test_apply_id")
	public Integer getTestApplyId() {
		return this.testApplyId;
	}

	public void setTestApplyId(Integer testApplyId) {
		this.testApplyId = testApplyId;
	}

	@Column(name = "test_id", length = 11)
	public String getTestId() {
		return this.testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	@Column(name = "test_name")
	public String getTestName() {
		return this.testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	@Column(name = "test_time", length = 19)
	public Timestamp getTestTime() {
		return this.testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}

	@Column(name = "test_state")
	public Integer getTestState() {
		return this.testState;
	}

	public void setTestState(Integer testState) {
		this.testState = testState;
	}

	@Column(name = "percent")
	public Integer getPercent() {
		return this.percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
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

	@Column(name = "delay")
	public Short getDelay() {
		return this.delay;
	}

	public void setDelay(Short delay) {
		this.delay = delay;
	}

	@Column(name = "overdue")
	public Short getOverdue() {
		return this.overdue;
	}

	public void setOverdue(Short overdue) {
		this.overdue = overdue;
	}

	@Column(name = "deleted")
	public Short getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Short deleted) {
		this.deleted = deleted;
	}

	@Column(name = "developer_task_id", length = 65535)
	public String getDeveloperTaskId() {
		return this.developerTaskId;
	}

	public void setDeveloperTaskId(String developerTaskId) {
		this.developerTaskId = developerTaskId;
	}

}