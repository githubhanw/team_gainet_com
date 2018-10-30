package com.zzidc.team.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.ProjectMember;
import com.zzidc.team.entity.TaskProject;

import net.sf.json.JSONObject;
/**
 * [说明/描述]
 */
@Service("teamProjectService")
public class TeamProjectService extends GiantBaseService{
	@Autowired
	private FilemanageService filemanageService;
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "SELECT p.*,m.`name` 'member_name',(SELECT count(0) FROM task_need tn WHERE tn.project_id=p.id AND state!=0) needCount, " + 
				"(SELECT count(0) FROM task t WHERE t.project_id=p.id AND deleted=0) taskCount FROM task_project p LEFT JOIN member m ON p.member_id=m.id WHERE 1=1 ";
		String countSql = "SELECT count(0) FROM task_project p LEFT JOIN member m ON p.member_id=m.id WHERE 1=1 ";
		
		if (conditionPage.getQueryCondition() != null) {
			String temp = "";
			
			if ("10".equals(conditionPage.getQueryCondition().get("type"))) {
				if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("search"))) {
					temp = temp.trim();
					String searchType = "";
					if (!StringUtils.isEmpty(searchType = conditionPage.getQueryCondition().get("searchType"))) {
						if ("1".equals(searchType)) {
							try {
								// 如果成功则为编号
								Integer.parseInt(temp);	
								sql += "AND p.id =:search ";
								countSql += "AND p.id =:search ";
								conditionMap.put("search", temp);
							} catch (Exception e) {
								// 名称条件
								sql += "AND p.project_name LIKE :search ";
								countSql += "AND p.project_name LIKE :search ";
								conditionMap.put("search", temp + "%");
							}
						} else if("2".equals(searchType)) {//项目备注
							sql += "AND p.remark LIKE :search ";
							countSql += "AND p.remark LIKE :search ";
							conditionMap.put("search", "%" + temp + "%");
						}
					}
				}

				String Company="";//公司
				if (!StringUtils.isEmpty(Company = conditionPage.getQueryCondition().get("company"))) {
					sql += "AND p.company=:company ";
					countSql += "AND p.company=:company ";
					conditionMap.put("company", Company);
				}
				
				String MemberId="";//项目负责人
				if (!StringUtils.isEmpty(MemberId = conditionPage.getQueryCondition().get("member_id"))) {
					sql += "AND p.member_id=:member_id ";
					countSql += "AND p.member_id=:member_id ";
					conditionMap.put("member_id", MemberId);
				}
				
				String State="";//状态
				if (!StringUtils.isEmpty(State = conditionPage.getQueryCondition().get("state"))) {
					sql += "AND p.state=:state ";
					countSql += "AND p.state=:state ";
					conditionMap.put("state", State);
				}
				
				String Createtime="";//开始时间
				if (!StringUtils.isEmpty(Createtime = conditionPage.getQueryCondition().get("createtime"))) {
					sql += "AND p.create_time>:createtime ";
					countSql += "AND p.create_time>:createtime ";
					conditionMap.put("createtime", Createtime);
				}
				
				String Endtime="";//结束时间
				if (!StringUtils.isEmpty(Endtime = conditionPage.getQueryCondition().get("endtime"))) {
					sql += "AND p.create_time<:endtime ";
					countSql += "AND p.create_time<:endtime ";
					conditionMap.put("endtime", Endtime);
				}
			}
			
			
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("0".equals(temp)) {//已删除
					sql += "AND p.state=0";
					countSql += "AND p.state=0"; 
				} else if ("1".equals(temp)) {//正常
					sql += "AND p.state!=0";
					countSql += "AND p.state!=0";
				} else if ("2".equals(temp)) {//待验收
					sql += "AND p.state=2";
					countSql += "AND p.state=2";
				} else if ("3".equals(temp)) {//已验收
					sql += "AND p.state=3";
					countSql += "AND p.state=3";
				} else if ("4".equals(temp)) {//已完成
					sql += "AND p.state=4";
					countSql += "AND p.state=4";
//				} else if ("3".equals(temp)) {//阶段：研究
//					sql += "AND p.stage='研究'";
//					countSql += "AND p.stage='研究'";
//				} else if ("4".equals(temp)) {//阶段：开发
//					sql += "AND p.stage='开发'";
//					countSql += "AND p.stage='开发'";
//				} else if ("5".equals(temp)) {//阶段：完成
//					sql += "AND p.stage='完成'";
//					countSql += "AND p.stage='完成'";
				}
			}
		}
		// 字段倒叙或升序排列
		if (conditionPage.getOrderColumn() != null && !"".equals(conditionPage.getOrderColumn())) {
			sql += " ORDER BY " + conditionPage.getOrderColumn()
					+ (conditionPage.get("orderByValue") == null ? " DESC" : " " + conditionPage.get("orderByValue"));
		}
		GiantPager resultPage = super.dao.getPage(sql, conditionPage.getCurrentPage(), conditionPage.getPageSize(), conditionMap);
		resultPage.setQueryCondition(GiantUtils.filterSQLMap(conditionPage.getQueryCondition()));
		resultPage.setTotalCounts(super.dao.getGiantCounts(countSql, conditionMap));
		return resultPage;
	}

	/**
	 * 添加项目信息
	 */
	public boolean addOrUpd(Map<String, String> mvm, HttpServletRequest request, int id,String name,MultipartFile[] file1,MultipartFile[] file2,MultipartFile[] file3,MultipartFile[] file4) {
		TaskProject p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			p = (TaskProject) super.dao.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			p = new TaskProject();
			p.setCreateTime(new Timestamp(System.currentTimeMillis()));
			p.setState((short)1);
		}
		p.setProjectName(GiantUtil.stringOf(mvm.get("project_name")));
		p.setStartTime(mvm.get("start_date"));
		p.setEndTime(mvm.get("end_date"));
		p.setCompany(GiantUtil.stringOf(mvm.get("company")));
		p.setMemberId(GiantUtil.intOf(mvm.get("member_id"), 0));
		p.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		p.setCreateTime(new Timestamp(System.currentTimeMillis()));
		p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//		p.setProjectType(mvm.get("type"));
		p.setCustomerName(GiantUtil.stringOf(mvm.get("customer_name")));
		p.setDemandId(GiantUtil.intOf(mvm.get("demand_id"), 0));
		p.setTimeLimit(GiantUtil.stringOf(mvm.get("time_limit")));
		p.setBudget(GiantUtil.stringOf(mvm.get("budget")));		
		p.setFileUrl(GiantUtil.stringOf(mvm.get("file_url")));
		p.setAssessment(GiantUtil.stringOf(mvm.get("assessment")));
		p.setOvertime(GiantUtil.intOf(mvm.get("overtime"), 0));
		p.setProjectContent(GiantUtil.stringOf(mvm.get("project_content")));
		boolean a=super.dao.saveUpdateOrDelete(p, null);
		String[] projectMembers = request.getParameterValues("projectMembers");
		String[] testMembers = request.getParameterValues("testMembers");
		String[] ParticipantsString = ArrayUtils.addAll(projectMembers, testMembers);
		Integer[] Participants = new Integer[ParticipantsString.length];
		for (int i = 0; i < ParticipantsString.length; i++) {
			Participants[i] = Integer.valueOf(ParticipantsString[i]);
		}
		String Participantsname = "";//参与人名称
		if (projectMembers != null && projectMembers.length > 0) {
			ProjectMember projectMember = new ProjectMember();
			for (int i = 0; i < projectMembers.length; i++) {
				Member member = (Member)getEntityByPrimaryKey(new Member(), Integer.valueOf(projectMembers[i]));
				Participantsname += member.getName() + ",";
				projectMember.setId(null);
				projectMember.setProjectId(p.getId());
				projectMember.setMemberType((short)1);
				projectMember.setMemberId(Integer.valueOf(projectMembers[i]));
				projectMember.setState((short)1);
				super.dao.saveUpdateOrDelete(projectMember, null);
			}
		}
		if (testMembers != null && testMembers.length > 0) {
			ProjectMember projectMember = new ProjectMember();
			for (int i = 0; i < testMembers.length; i++) {
				Member member = (Member)getEntityByPrimaryKey(new Member(), Integer.valueOf(testMembers[i]));
				Participantsname += member.getName() + ",";
				projectMember.setId(null);
				projectMember.setProjectId(p.getId());
				projectMember.setMemberType((short)2);
				projectMember.setMemberId(Integer.valueOf(testMembers[i]));
				projectMember.setState((short)1);
				super.dao.saveUpdateOrDelete(projectMember, null);
			}
		}
		
		// 创建文档
		if ("0".equals(mvm.get("type"))) {// 如果等于0，就是内部文件
			JSONObject jsonupload = new JSONObject();
			if(file2 != null && file2.length > 0) {
				String fileName = file2[0].getOriginalFilename();
				if(fileName != null && !"".equals(fileName)) {
					jsonupload = filemanageService.uploadfiles(file2);
					if (jsonupload != null) {
						filemanageService.addxm(mvm, id, name, jsonupload.getString("gs"), jsonupload.getString("url"), jsonupload.getString("fileName"),
								p.getId(), "2");
					}
				}
			}
			if (file3 != null && file3.length > 0) {
				String fileName = file3[0].getOriginalFilename();
				if(fileName != null && !"".equals(fileName)) {
					jsonupload = filemanageService.uploadfiles(file3);
					if (jsonupload != null) {
						filemanageService.addxm(mvm, id, name, jsonupload.getString("gs"), jsonupload.getString("url"), jsonupload.getString("fileName"),
								p.getId(), "3");
					}
				}
			}
			if (file4 != null && file4.length > 0) {
				String fileName = file4[0].getOriginalFilename();
				if(fileName != null && !"".equals(fileName)) {
					jsonupload = filemanageService.uploadfiles(file4);
					if (jsonupload != null) {
						filemanageService.addxm(mvm, id, name, jsonupload.getString("gs"), jsonupload.getString("url"), jsonupload.getString("fileName"),
								p.getId(), "4");
					}
				}
			}
		} else {
			JSONObject jsonupload = new JSONObject();
			if (file1 != null && file1.length > 0) {
				String fileName = file1[0].getOriginalFilename();
				if(fileName != null && !"".equals(fileName)) {
					jsonupload = filemanageService.uploadfiles(file1);
					if (jsonupload != null) {
						filemanageService.addxm(mvm, id, name, jsonupload.getString("gs"), jsonupload.getString("url"), jsonupload.getString("fileName"),
								p.getId(), "1");
					}
				}
			}
			if (file2 != null && file2.length > 0) {
				String fileName = file2[0].getOriginalFilename();
				if(fileName != null && !"".equals(fileName)) {
					jsonupload = filemanageService.uploadfiles(file2);
					if (jsonupload != null) {
						filemanageService.addxm(mvm, id, name, jsonupload.getString("gs"), jsonupload.getString("url"), jsonupload.getString("fileName"),
								p.getId(), "2");
					}
				}
			}
			if (file3 != null && file3.length > 0) {
				String fileName = file3[0].getOriginalFilename();
				if(fileName != null && !"".equals(fileName)) {
					jsonupload = filemanageService.uploadfiles(file3);
					if (jsonupload != null) {
						filemanageService.addxm(mvm, id, name, jsonupload.getString("gs"), jsonupload.getString("url"), jsonupload.getString("fileName"),
								p.getId(), "3");
					}
				}
			}
			if (file4 != null && file4.length > 0) {
				String fileName = file4[0].getOriginalFilename();
				if(fileName != null && !"".equals(fileName)) {
					jsonupload = filemanageService.uploadfiles(file4);
					if (jsonupload != null) {
						filemanageService.addxm(mvm, id, name, jsonupload.getString("gs"), jsonupload.getString("url"), jsonupload.getString("fileName"),
								p.getId(), "4");
					}
				}
			}
		}
		
		// 调用OA待办接口
		if (a) {
			Participantsname = Participantsname.substring(0, Participantsname.length() - 1);
//			OAToDo(p.getProjectName(), p.getStartTime(),p.getEndTime(),
//					p.getMemberId(), p.getMemberId(),
//					Participantsname, p.getProjectContent(), "preSales/goClueDetails.do?cluesID=" + p.getId(),
//					getMemberId(),  Participants);
		}
		
		return a;
	}
	/**
	 * 修改项目信息
	 */
	public boolean edit(Map<String, String> mvm, HttpServletRequest request) {
		TaskProject p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			p = (TaskProject) super.dao.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			p = new TaskProject();
			p.setCreateTime(new Timestamp(System.currentTimeMillis()));
			p.setState((short)1);
		}
		p.setProjectName(GiantUtil.stringOf(mvm.get("project_name")));
		p.setStartTime(mvm.get("start_date"));
		p.setEndTime(mvm.get("end_date"));
		p.setCompany(GiantUtil.stringOf(mvm.get("company")));
		p.setMemberId(GiantUtil.intOf(mvm.get("member_id"), 0));
		p.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//		p.setProjectType(mvm.get("type"));
		p.setCustomerName(GiantUtil.stringOf(mvm.get("customer_name")));
		p.setDemandId(GiantUtil.intOf(mvm.get("demand_id"), 0));
		p.setTimeLimit(GiantUtil.stringOf(mvm.get("time_limit")));
		p.setBudget(GiantUtil.stringOf(mvm.get("budget")));		
		p.setFileUrl(GiantUtil.stringOf(mvm.get("file_url")));
		p.setAssessment(GiantUtil.stringOf(mvm.get("assessment")));
		p.setOvertime(GiantUtil.intOf(mvm.get("overtime"), 0));
		p.setProjectContent(GiantUtil.stringOf(mvm.get("project_content")));
		boolean result = super.dao.saveUpdateOrDelete(p, null);
		String querySql = "select * from project_member where project_id=" + p.getId();
		List<Object> list = super.dao.getEntityListBySQL(querySql, null, new ProjectMember());
		if (list != null && list.size() > 0 ) {
			for (int i = 0; i < list.size(); i++) {
				ProjectMember pro = (ProjectMember) list.get(i);
				super.dao.deleteOne(pro);
			}
		}
		
		String[] projectMembers = request.getParameterValues("projectMembers");
		String[] testMembers = request.getParameterValues("testMembers");
		if (projectMembers != null && projectMembers.length > 0) {
			ProjectMember projectMember = new ProjectMember();
			for (int i = 0; i < projectMembers.length; i++) {
				projectMember.setId(null);
				projectMember.setProjectId(p.getId());
				projectMember.setMemberType((short)1);
				projectMember.setMemberId(Integer.valueOf(projectMembers[i]));
				projectMember.setState((short)1);
				super.dao.saveUpdateOrDelete(projectMember, null);
			}
		}
		if (testMembers != null && testMembers.length > 0) {
			ProjectMember projectMember = new ProjectMember();
			for (int i = 0; i < testMembers.length; i++) {
				projectMember.setId(null);
				projectMember.setProjectId(p.getId());
				projectMember.setMemberType((short)2);
				projectMember.setMemberId(Integer.valueOf(testMembers[i]));
				projectMember.setState((short)1);
				super.dao.saveUpdateOrDelete(projectMember, null);
			}
		}
		return result;
	}
	/**
	 * 获取项目信息
	 * @param projectId
	 * @return
	 */
	public Map<String, Object> getProjectDetail(int projectId){
		String sql = "SELECT * FROM declaration_project WHERE id=" + projectId;
		List<Map<String, Object>> list = super.getMapListBySQL(sql, null);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取项目成果
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getProjectResult(int projectId){
		String sql = "SELECT pr.* FROM declaration_project_result pr WHERE pr.project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 获取项目文档
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getProjectDoc(int projectId){
		String sql = "SELECT pd.*,pdt.project_doc_type FROM declaration_project_doc pd, declaration_project_doc_type pdt "
				+ "WHERE pd.type_id=pdt.id AND pd.state>0 AND project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 项目详情页面获取同项目模块。
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getRelationTaskList(Integer needId) {
		List<Map<String, Object>> relationTaskList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT * FROM `task_need` WHERE 1=1 AND project_id=" + needId + " ORDER BY create_time";
		relationTaskList = super.getMapListBySQL(sql, null);
		return relationTaskList;
	}
	
	
	
	
	/**
	 * 获取项目下所有模块【获取已验收模块】
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getNeedByProject(int projectId){
		String sql = "SELECT id,need_name,state FROM task_need WHERE state=4 AND parent_id=0 AND project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 获取项目下所有子模块【获取已验收模块】
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getSubNeedByProject(int projectId){
		String sql = "SELECT id,need_name,state,parent_id FROM task_need WHERE state=4 AND parent_id>0 AND project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 获取所有模块下任务【获取已完成任务】
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getNeedTaskByProject(int projectId){
		String sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img FROM task t, task_need n "
				+ "WHERE t.need_id=n.id AND deleted=0 AND t.state=4 AND n.state=4 AND n.parent_id=0 AND n.project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 获取所有子模块下任务【获取已完成任务】
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getSubNeedTaskByProject(int projectId){
		String sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img FROM task t, task_need n "
				+ "WHERE t.need_id=n.id AND deleted=0 AND t.state=4 AND n.state=4 AND n.parent_id>0 AND n.project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 获取所有任务下的所有测试用例列表
	 * @param needId
	 * @return
	 */
	public List<Map<String, Object>> getTestCaseByProject(int projectId){
		String sql = "SELECT c.id,c.task_id,c.case_name,c.case_type,c.precondition FROM task t, task_need n, test_case c WHERE c.task_id=t.id AND t.need_id=n.id "
				+ "AND c.state=1 AND t.deleted=0 AND t.state=4 AND n.state=4 AND n.project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 获取所有测试用例下的所有步骤
	 * @param needId
	 * @return
	 */
	public List<Map<String, Object>> getTestCaseStepByProject(int projectId){
		String sql = "SELECT s.case_id,s.step,s.expect FROM task t, task_need n, test_case c, test_case_step s WHERE s.case_id=c.id AND s.version=c.version " + 
				"AND c.task_id=t.id AND t.need_id=n.id AND c.state=1 AND t.deleted=0 AND t.state=4 AND n.state=4 AND n.project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}
}
