package com.zzidc.team.entity;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 里程碑管理表
 * @author chenmenghao
 *
 */
@Entity
@Table(name = "milepost_manage")
public class MilepostManage implements java.io.Serializable{
	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = 8643745490307098649L;
	private Integer id;
	private String milepostName;
	private String milepostDescribe;
	private String startTime;
	private String endTime;
	private String editTime;
	private String createTime;
	private String authorId;
	private String authorName;
	private String milepostState;
	private String milepostRemarks;
	private String checkTime;
	private String checkName;
	private Integer projectId;
	private Integer checkId;
	private String finishTime;
	/** default constructor */
	public MilepostManage() {
	}

	/** full constructor */
//	public MilepostManage(Integer sort, String name, Integer leaderId, Integer parentId) {
//		this.sort = sort;
//		this.name = name;
//		this.leaderId = leaderId;
//		this.parentId = parentId;
//	}
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "milepost_name", nullable = false)
	public String getMilepostName() {
		return milepostName;
	}
	public void setMilepostName(String milepostName) {
		this.milepostName = milepostName;
	}
	@Column(name = "milepost_describe", nullable = true)
	public String getMilepostDescribe() {
		return milepostDescribe;
	}
	public void setMilepostDescribe(String milepostDescribe) {
		this.milepostDescribe = milepostDescribe;
	}
	@Column(name = "start_time", nullable = false)
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(name = "end_time", nullable = false)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(name = "edit_time", nullable = true)
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	@Column(name = "create_time", nullable = false)
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name = "author_id", nullable = false)
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	@Column(name = "author_name", nullable = false)
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	@Column(name = "milepost_state", nullable = false)
	public String getMilepostState() {
		return milepostState;
	}
	public void setMilepostState(String milepostState) {
		this.milepostState = milepostState;
	}
	@Column(name = "milepost_remarks", nullable = true)
	public String getMilepostRemarks() {
		return milepostRemarks;
	}

	public void setMilepostRemarks(String milepostRemarks) {
		this.milepostRemarks = milepostRemarks;
	}
	@Column(name = "check_time", nullable = true)
	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	@Column(name = "check_name", nullable = true)
	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	@Column(name = "project_id", nullable = true)
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	@Column(name = "check_id", nullable = true)
	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}
	@Column(name = "finish_time", nullable = true)
	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public String toString() {
		return "MilepostManage [id=" + id + ", milepostName=" + milepostName + ", milepostDescribe=" + milepostDescribe + ", startTime=" + startTime + ", endTime="
				+ endTime + ", editTime=" + editTime + ", createTime=" + createTime + ", authorId=" + authorId
				+ ", authorName=" + authorName + ", milepostState=" + milepostState
				+  ", milepostRemarks=" + milepostRemarks + ", checkTime=" + checkTime+ ", checkName=" + checkName+ ", projectId=" + projectId+ ", checkId=" + checkId
				+  ", finishTime=" + finishTime+ "]";
	}
	
	
	
}
