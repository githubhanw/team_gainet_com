package com.zzidc.team.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 文档管理表
 * @author chenmenghao
 *
 */
@Entity
@Table(name = "file_manage")
public class FileManage  implements java.io.Serializable{
	/**
	 * serialVersionUID long
	 */
	 private static final long serialVersionUID = 8643745490307098601L;
     private Integer id;
     private String fileClassification;
     private Integer glId;
     private String fileName;
     private String fileText;
     private String createId;
     private String addName;
     private String createTime;
     private String editTime;
     private String fileFormat;
     private String fileUrl;
     private String fileRealname;
     private String fileRemarks;
     private String accessControl;
    @Id
 	@GeneratedValue(strategy = IDENTITY)
 	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "file_classification", nullable = false)
	public String getFileClassification() {
		return fileClassification;
	}
	public void setFileClassification(String fileClassification) {
		this.fileClassification = fileClassification;
	}
	@Column(name = "gl_id", nullable = false)
	public Integer getGlId() {
		return glId;
	}
	public void setGlId(Integer glId) {
		this.glId = glId;
	}
	@Column(name = "file_name", nullable = false)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(name = "file_text", nullable = true)
	public String getFileText() {
		return fileText;
	}
	public void setFileText(String fileText) {
		this.fileText = fileText;
	}
	@Column(name = "create_id", nullable = false)
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	@Column(name = "add_name", nullable = false)
	public String getAddName() {
		return addName;
	}
	public void setAddName(String addName) {
		this.addName = addName;
	}
	@Column(name = "create_time", nullable = false)
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name = "edit_time", nullable = true)
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	@Column(name = "file_format", nullable = false)
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	@Column(name = "file_url", nullable = false)
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	@Column(name = "file_realname", nullable = false)
	public String getFileRealname() {
		return fileRealname;
	}
	public void setFileRealname(String fileRealname) {
		this.fileRealname = fileRealname;
	}
	@Column(name = "file_remarks", nullable = true)
	public String getFileRemarks() {
		return fileRemarks;
	}
	public void setFileRemarks(String fileRemarks) {
		this.fileRemarks = fileRemarks;
	}
	@Column(name = "access_control", nullable = false)
	public String getAccessControl() {
		return accessControl;
	}
	public void setAccessControl(String accessControl) {
		this.accessControl = accessControl;
	}
	@Override
	public String toString() {
		return "FileManage [id=" + id + ", fileClassification=" + fileClassification + ", glId=" + glId + ", fileName=" + fileName + ", fileText=" + fileText + ", createId=" + createId + ", addName=" + addName +", createTime="+createTime + ", editTime=" + editTime
				+ ", fileFormat=" + fileFormat + ", fileUrl=" + fileUrl
				+ ", fileRealname=" + fileRealname + ", fileRemarks=" + fileRemarks + ", accessControl=" + accessControl + "]";
	}
}
