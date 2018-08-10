package com.zzidc.team.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ActionHistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "action_history")
public class ActionHistory implements java.io.Serializable {

	// Fields

	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = 6219798504333520737L;
	private Integer id;
	private Integer actionId;
	private String field;
	private String oldData;
	private String newData;
	private Short diff;

	// Constructors

	/** default constructor */
	public ActionHistory() {
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

	@Column(name = "action_id")
	public Integer getActionId() {
		return this.actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	@Column(name = "field", length = 30)
	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Column(name = "old_data", length = 65535)
	public String getOldData() {
		return this.oldData;
	}

	public void setOldData(String oldData) {
		this.oldData = oldData;
	}

	@Column(name = "new_data", length = 65535)
	public String getNewData() {
		return this.newData;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}

	@Column(name = "diff", length = 16777215)
	public Short getDiff() {
		return this.diff;
	}

	public void setDiff(Short diff) {
		this.diff = diff;
	}

	@Override
	public String toString() {
		return "ActionHistory [id=" + id + ", actionId=" + actionId + ", field=" + field + ", oldData=" + oldData
				+ ", newData=" + newData + ", diff=" + diff + "]";
	}
}