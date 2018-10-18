package com.zzidc.team.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TestBug entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "test_bug", catalog = "team_gainet_com")
public class TestBug implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer taskid;
	private String tasktype;
	private Integer productId;
	private Integer projectId;
	private Integer needId;
	private String bugproject;
	private Integer bugrank;
	private Integer bugfen;
	private Integer bugtype;
	private String bugtypefen;
	private String bugdes;
	private Integer checkId;
	private String checkName;
	private String checkmark;
	private Integer createrId;
	private String creater;
	private Integer developerId;
	private String developer;
	private Integer solverId;
	private String solver;
	private Integer solution;
	private Integer solvestatus;
	private String mark;
	private String kaifamark;
	private Timestamp createtime;
	private Timestamp solvetime;
	private Timestamp validatetime;
	private String header;
	private String leader;
	private String taskdes;

	// Constructors

	/** default constructor */
	public TestBug() {
	}

	/** full constructor */
	public TestBug(Integer taskid, String tasktype, Integer productId,
			Integer projectId, Integer needId, String bugproject,
			Integer bugrank, Integer bugfen, Integer bugtype,
			String bugtypefen, String bugdes, Integer checkId, String checkName,
			String checkmark, Integer createrId, String creater,
			Integer developerId, String developer, Integer solverId,
			String solver, Integer solution, Integer solvestatus, String mark,
			String kaifamark, Timestamp createtime, Timestamp solvetime,
			Timestamp validatetime, String header, String leader, String taskdes) {
		this.taskid = taskid;
		this.tasktype = tasktype;
		this.productId = productId;
		this.projectId = projectId;
		this.needId = needId;
		this.bugproject = bugproject;
		this.bugrank = bugrank;
		this.bugfen = bugfen;
		this.bugtype = bugtype;
		this.bugtypefen = bugtypefen;
		this.bugdes = bugdes;
		this.checkId = checkId;
		this.checkName = checkName;
		this.checkmark = checkmark;
		this.createrId = createrId;
		this.creater = creater;
		this.developerId = developerId;
		this.developer = developer;
		this.solverId = solverId;
		this.solver = solver;
		this.solution = solution;
		this.solvestatus = solvestatus;
		this.mark = mark;
		this.kaifamark = kaifamark;
		this.createtime = createtime;
		this.solvetime = solvetime;
		this.validatetime = validatetime;
		this.header = header;
		this.leader = leader;
		this.taskdes = taskdes;
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

	@Column(name = "taskid")
	public Integer getTaskid() {
		return this.taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}

	@Column(name = "tasktype")
	public String getTasktype() {
		return this.tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
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

	@Column(name = "bugproject")
	public String getBugproject() {
		return this.bugproject;
	}

	public void setBugproject(String bugproject) {
		this.bugproject = bugproject;
	}

	@Column(name = "bugrank")
	public Integer getBugrank() {
		return this.bugrank;
	}

	public void setBugrank(Integer bugrank) {
		this.bugrank = bugrank;
	}

	@Column(name = "bugfen")
	public Integer getBugfen() {
		return this.bugfen;
	}

	public void setBugfen(Integer bugfen) {
		this.bugfen = bugfen;
	}

	@Column(name = "bugtype")
	public Integer getBugtype() {
		return this.bugtype;
	}

	public void setBugtype(Integer bugtype) {
		this.bugtype = bugtype;
	}

	@Column(name = "bugtypefen")
	public String getBugtypefen() {
		return this.bugtypefen;
	}

	public void setBugtypefen(String bugtypefen) {
		this.bugtypefen = bugtypefen;
	}

	@Column(name = "bugdes", length = 128)
	public String getBugdes() {
		return this.bugdes;
	}

	public void setBugdes(String bugdes) {
		this.bugdes = bugdes;
	}

	@Column(name = "check_id")
	public Integer getCheckId() {
		return this.checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	@Column(name = "check_name", length = 32)
	public String getCheckName() {
		return this.checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	@Column(name = "checkmark")
	public String getCheckmark() {
		return this.checkmark;
	}

	public void setCheckmark(String checkmark) {
		this.checkmark = checkmark;
	}

	@Column(name = "creater_id")
	public Integer getCreaterId() {
		return this.createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}

	@Column(name = "creater", length = 32)
	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "developer_id")
	public Integer getDeveloperId() {
		return this.developerId;
	}

	public void setDeveloperId(Integer developerId) {
		this.developerId = developerId;
	}

	@Column(name = "developer", length = 32)
	public String getDeveloper() {
		return this.developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	@Column(name = "solver_id")
	public Integer getSolverId() {
		return this.solverId;
	}

	public void setSolverId(Integer solverId) {
		this.solverId = solverId;
	}

	@Column(name = "solver", length = 32)
	public String getSolver() {
		return this.solver;
	}

	public void setSolver(String solver) {
		this.solver = solver;
	}

	@Column(name = "solution")
	public Integer getSolution() {
		return this.solution;
	}

	public void setSolution(Integer solution) {
		this.solution = solution;
	}

	@Column(name = "solvestatus")
	public Integer getSolvestatus() {
		return this.solvestatus;
	}

	public void setSolvestatus(Integer solvestatus) {
		this.solvestatus = solvestatus;
	}

	@Column(name = "mark")
	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Column(name = "kaifamark")
	public String getKaifamark() {
		return this.kaifamark;
	}

	public void setKaifamark(String kaifamark) {
		this.kaifamark = kaifamark;
	}

	@Column(name = "createtime", length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "solvetime", length = 19)
	public Timestamp getSolvetime() {
		return this.solvetime;
	}

	public void setSolvetime(Timestamp solvetime) {
		this.solvetime = solvetime;
	}

	@Column(name = "validatetime", length = 19)
	public Timestamp getValidatetime() {
		return this.validatetime;
	}

	public void setValidatetime(Timestamp validatetime) {
		this.validatetime = validatetime;
	}

	@Column(name = "header")
	public String getHeader() {
		return this.header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Column(name = "leader")
	public String getLeader() {
		return this.leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	@Column(name = "taskdes")
	public String getTaskdes() {
		return this.taskdes;
	}

	public void setTaskdes(String taskdes) {
		this.taskdes = taskdes;
	}

}