package com.zzidc.team.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * CodeReport entity. @author chenmenghao
 */
@Entity
@Table(name = "code_report")
public class CodeReport {
	
	
	
   private Integer id;
   private Integer taskId;
   private String reportInterface;
   private String entryPoint;
   private String onlineUrl;
   private String sourceFile;
   private String reportType;
   private String examination;
   private Integer authorId;
   private String authorTime;
   private Integer examinationId;
   private String examinationTime;
   private String reportRemark;
   
   /** default constructor */
	public CodeReport() {
	}

	/** full constructor */
	public CodeReport( Integer id,
			Integer taskId,
		    String reportInterface,
		    String entryPoint,
		    String onlineUrl,
		    String sourceFile,
		    String reportType,
		    String examination,
		    Integer authorId,
		    String authorTime,
		    Integer examinationId,
		    String examinationTime,
		    String reportRemark) {
			this.id = id;
			this.taskId = taskId;
			this.reportInterface = reportInterface;
			this.entryPoint = entryPoint;
			this.onlineUrl = onlineUrl;
			this.sourceFile = sourceFile;
			this.reportType = reportType;
			this.examination = examination;
			this.authorId = authorId;
			this.authorTime = authorTime;
			this.examinationId = examinationId;
			this.examinationTime = examinationTime;
			this.reportRemark = reportRemark;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "task_id")
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	@Column(name = "report_interface")
	public String getReportInterface() {
		return reportInterface;
	}
	public void setReportInterface(String reportInterface) {
		this.reportInterface = reportInterface;
	}
	@Column(name = "entry_point")
	public String getEntryPoint() {
		return entryPoint;
	}
	public void setEntryPoint(String entryPoint) {
		this.entryPoint = entryPoint;
	}
	@Column(name = "online_url")
	public String getOnlineUrl() {
		return onlineUrl;
	}
	public void setOnlineUrl(String onlineUrl) {
		this.onlineUrl = onlineUrl;
	}
	@Column(name = "source_file")
	public String getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	@Column(name = "report_type")
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	@Column(name = "examination")
	public String getExamination() {
		return examination;
	}
	public void setExamination(String examination) {
		this.examination = examination;
	}
	@Column(name = "author_id")
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	@Column(name = "author_time")
	public String getAuthorTime() {
		return authorTime;
	}
	public void setAuthorTime(String authorTime) {
		this.authorTime = authorTime;
	}
	@Column(name = "examination_id")
	public Integer getExaminationId() {
		return examinationId;
	}
	public void setExaminationId(Integer examinationId) {
		this.examinationId = examinationId;
	}
	@Column(name = "examination_time")
	public String getExaminationTime() {
		return examinationTime;
	}
	public void setExaminationTime(String examinationTime) {
		this.examinationTime = examinationTime;
	}
	@Column(name = "report_remark")
	public String getReportRemark() {
		return reportRemark;
	}
	public void setReportRemark(String reportRemark) {
		this.reportRemark = reportRemark;
	}
   
	
}
