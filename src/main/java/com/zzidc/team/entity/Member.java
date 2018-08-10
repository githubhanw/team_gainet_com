package com.zzidc.team.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Member entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "member")
public class Member implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private String password;
	private String password1;
	private String phone;
	private String name;
	private String rights;
	private String roleId;
	private String lastLogin;
	private String ip;
	private String status;
	private String bz;
	private String skin;
	private String email;
	private String number;
	private String sex;
	private String companyId;
	private String isAdmin;
	private String openid;
	private String newOpenid;
	private String wechatFlag;
	private String staffId;
	private String deptId;
	private String tel;
	private String machineId;
	private String nameEn;
	private String bianma;
	private String functions;
	private String birthday;
	private String nation;
	private String jobtype;
	private String jobjointime;
	private String faddress;
	private String political;
	private String sfid;
	private String marital;
	private String post;
	private String pojointime;
	private String education;
	private String school;
	private String major;
	private String ftitle;
	private String certificate;
	private Integer contractlength;
	private String cstarttime;
	private String cendtime;
	private String address;
	private String qq;
	private Date quitsTime;

	@Transient
	private MemberConfig config;

	// Constructors

	/** default constructor */
	public Member() {
	}

	/** full constructor */
	public Member(String username, String password, String password1,
			String phone, String name, String rights, String roleId,
			String lastLogin, String ip, String status, String bz, String skin,
			String email, String number, String sex, String companyId,
			String isAdmin, String openid, String newOpenid, String wechatFlag,
			String staffId, String deptId, String tel, String machineId,
			String nameEn, String bianma, String functions, String birthday,
			String nation, String jobtype, String jobjointime, String faddress,
			String political, String sfid, String marital, String post,
			String pojointime, String education, String school, String major,
			String ftitle, String certificate, Integer contractlength,
			String cstarttime, String cendtime, String address, String qq,
			Date quitsTime, MemberConfig config) {
		this.username = username;
		this.password = password;
		this.password1 = password1;
		this.phone = phone;
		this.name = name;
		this.rights = rights;
		this.roleId = roleId;
		this.lastLogin = lastLogin;
		this.ip = ip;
		this.status = status;
		this.bz = bz;
		this.skin = skin;
		this.email = email;
		this.number = number;
		this.sex = sex;
		this.companyId = companyId;
		this.isAdmin = isAdmin;
		this.openid = openid;
		this.newOpenid = newOpenid;
		this.wechatFlag = wechatFlag;
		this.staffId = staffId;
		this.deptId = deptId;
		this.tel = tel;
		this.machineId = machineId;
		this.nameEn = nameEn;
		this.bianma = bianma;
		this.functions = functions;
		this.birthday = birthday;
		this.nation = nation;
		this.jobtype = jobtype;
		this.jobjointime = jobjointime;
		this.faddress = faddress;
		this.political = political;
		this.sfid = sfid;
		this.marital = marital;
		this.post = post;
		this.pojointime = pojointime;
		this.education = education;
		this.school = school;
		this.major = major;
		this.ftitle = ftitle;
		this.certificate = certificate;
		this.contractlength = contractlength;
		this.cstarttime = cstarttime;
		this.cendtime = cendtime;
		this.address = address;
		this.qq = qq;
		this.quitsTime = quitsTime;
		this.config = config;
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

	@Column(name = "USERNAME")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "PASSWORD1")
	public String getPassword1() {
		return this.password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	@Column(name = "PHONE", length = 32)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "RIGHTS")
	public String getRights() {
		return this.rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "LAST_LOGIN")
	public String getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Column(name = "IP", length = 100)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "STATUS", length = 32)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "BZ")
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name = "SKIN", length = 100)
	public String getSkin() {
		return this.skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	@Column(name = "EMAIL", length = 32)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "NUMBER", length = 100)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "SEX", length = 10)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "company_id", length = 100)
	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Column(name = "isAdmin")
	public String getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "OPENID", length = 100)
	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "NEW_OPENID", length = 100)
	public String getNewOpenid() {
		return this.newOpenid;
	}

	public void setNewOpenid(String newOpenid) {
		this.newOpenid = newOpenid;
	}

	@Column(name = "WECHAT_FLAG", length = 32)
	public String getWechatFlag() {
		return this.wechatFlag;
	}

	public void setWechatFlag(String wechatFlag) {
		this.wechatFlag = wechatFlag;
	}

	@Column(name = "staff_id", length = 50)
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	@Column(name = "deptID", length = 50)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "TEL", length = 50)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "machine_id", length = 50)
	public String getMachineId() {
		return this.machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	@Column(name = "NAME_EN", length = 50)
	public String getNameEn() {
		return this.nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	@Column(name = "BIANMA", length = 100)
	public String getBianma() {
		return this.bianma;
	}

	public void setBianma(String bianma) {
		this.bianma = bianma;
	}

	@Column(name = "FUNCTIONS")
	public String getFunctions() {
		return this.functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	@Column(name = "BIRTHDAY", length = 32)
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "NATION", length = 10)
	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "JOBTYPE", length = 30)
	public String getJobtype() {
		return this.jobtype;
	}

	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}

	@Column(name = "JOBJOINTIME", length = 32)
	public String getJobjointime() {
		return this.jobjointime;
	}

	public void setJobjointime(String jobjointime) {
		this.jobjointime = jobjointime;
	}

	@Column(name = "FADDRESS", length = 100)
	public String getFaddress() {
		return this.faddress;
	}

	public void setFaddress(String faddress) {
		this.faddress = faddress;
	}

	@Column(name = "POLITICAL", length = 30)
	public String getPolitical() {
		return this.political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	@Column(name = "SFID", length = 20)
	public String getSfid() {
		return this.sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	@Column(name = "MARITAL", length = 10)
	public String getMarital() {
		return this.marital;
	}

	public void setMarital(String marital) {
		this.marital = marital;
	}

	@Column(name = "POST", length = 30)
	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "POJOINTIME", length = 32)
	public String getPojointime() {
		return this.pojointime;
	}

	public void setPojointime(String pojointime) {
		this.pojointime = pojointime;
	}

	@Column(name = "EDUCATION", length = 10)
	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "SCHOOL", length = 30)
	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "MAJOR", length = 30)
	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "FTITLE", length = 30)
	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	@Column(name = "CERTIFICATE", length = 30)
	public String getCertificate() {
		return this.certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Column(name = "CONTRACTLENGTH")
	public Integer getContractlength() {
		return this.contractlength;
	}

	public void setContractlength(Integer contractlength) {
		this.contractlength = contractlength;
	}

	@Column(name = "CSTARTTIME", length = 32)
	public String getCstarttime() {
		return this.cstarttime;
	}

	public void setCstarttime(String cstarttime) {
		this.cstarttime = cstarttime;
	}

	@Column(name = "CENDTIME", length = 32)
	public String getCendtime() {
		return this.cendtime;
	}

	public void setCendtime(String cendtime) {
		this.cendtime = cendtime;
	}

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "QQ")
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "quitsTime", length = 10)
	public Date getQuitsTime() {
		return this.quitsTime;
	}

	public void setQuitsTime(Date quitsTime) {
		this.quitsTime = quitsTime;
	}
	
	@Transient
	public MemberConfig getConfig() {
		return this.config;
	}

	public void setConfig(MemberConfig config) {
		this.config = config;
	}

}