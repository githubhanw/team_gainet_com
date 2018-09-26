package com.zzidc.team.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 里程碑关联模块表
 * @author chenmenghao
 *
 */
@Entity
@Table(name = "milepost_taskneed")
public class MilepostTaskneed {
	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = 8643745490307098638L;
	private Integer id;
	private Integer taskneedId;
	private Integer milepostId;
	private String associationTime;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "taskneed_id", nullable = false)
	public Integer getTaskneedId() {
		return taskneedId;
	}
	public void setTaskneedId(Integer taskneedId) {
		this.taskneedId = taskneedId;
	}
	@Column(name = "milepost_id", nullable = false)
	public Integer getMilepostId() {
		return milepostId;
	}
	public void setMilepostId(Integer milepostId) {
		this.milepostId = milepostId;
	}
	@Column(name = "association_time", nullable = false)
	public String getAssociationTime() {
		return associationTime;
	}
	public void setAssociationTime(String associationTime) {
		this.associationTime = associationTime;
	}
	@Override
	public String toString() {
		return "MilepostTaskneed [id=" + id + ", taskneedId=" + taskneedId + ", milepostId=" + milepostId + ", associationTime=" + associationTime + "]";
	}
}
