package com.zzidc.declaration.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.log.LogMethod;
import com.zzidc.log.LogModule;
import com.zzidc.log.PMLog;
import com.zzidc.team.entity.DeclarationProject;
import com.zzidc.team.entity.DeclarationProjectResult;

/**
 * [说明/描述]
 */
@Service("projectService")
public class ProjectService extends GiantBaseService{
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "SELECT p.*,count(0) resultCount FROM declaration_project p LEFT JOIN declaration_project_result pr ON p.id=pr.project_id WHERE 1=1 ";
		String countSql = "SELECT count(0) FROM declaration_project p WHERE 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			String temp = "";
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("search"))) {
				sql += "AND p.project_name LIKE :search ";
				countSql += "AND p.project_name LIKE :search ";
				conditionMap.put("search", temp + "%");
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("0".equals(temp)) {//已删除
					sql += "AND p.state=0";
					countSql += "AND p.state=0";
				} else if ("1".equals(temp)) {//正常
					sql += "AND p.state=1";
					countSql += "AND p.state=1";
				} else if ("3".equals(temp)) {//阶段：研究
					sql += "AND p.stage='研究'";
					countSql += "AND p.stage='研究'";
				} else if ("4".equals(temp)) {//阶段：开发
					sql += "AND p.stage='开发'";
					countSql += "AND p.stage='开发'";
				} else if ("5".equals(temp)) {//阶段：完成
					sql += "AND p.stage='完成'";
					countSql += "AND p.stage='完成'";
				}
			}
		}
		sql += " GROUP BY p.id";
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
	 * 添加、修改项目信息
	 */
	public boolean addOrUpd(Map<String, String> mvm) {
		DeclarationProject p = null;
		DeclarationProject oldpr = new DeclarationProject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			p = (DeclarationProject) super.dao.getEntityByPrimaryKey(new DeclarationProject(), GiantUtil.intOf(mvm.get("id"), 0));
			BeanUtils.copyProperties(p, oldpr);
		} else {
			p = new DeclarationProject();
			p.setCreateTime(new Timestamp(System.currentTimeMillis()));
			p.setState(1);
		}
		p.setDeclarationNumber(GiantUtil.stringOf(mvm.get("declaration_number")));
		p.setProjectName(GiantUtil.stringOf(mvm.get("project_name")));
		p.setCompany(GiantUtil.stringOf(mvm.get("company")));
		p.setStage(GiantUtil.stringOf(mvm.get("stage")));
		try {
			p.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("start_date")));
			p.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("end_date")));
		} catch (ParseException e) {
			p.setStartDate(new Date());
			p.setEndDate(new Date());
		}
		p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		boolean flag = super.dao.saveUpdateOrDelete(p, null);
		//添加日志
		PMLog pmLog = null;
		if (flag) {
			if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {//编辑
				pmLog = new PMLog(LogModule.DECLAREPROJECT, LogMethod.EDIT, mvm.toString(), GiantUtil.stringOf(mvm.get("remark")));
				pmLog.add(p.getId(), oldpr, p, "declaration_number", "project_name", "company"
						, "stage", "start_date", "end_date");
			} else {//创建
				pmLog = new PMLog(LogModule.DECLAREPROJECT, LogMethod.ADD, mvm.toString(), GiantUtil.stringOf(mvm.get("remark")));
				pmLog.setObjectId(p.getId());
			}
			this.log(pmLog);
		}
		return flag;
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
	 * 获取日志
	 * @param taskId
	 * @return
	 */
	public List<Map<String, Object>> getLogList(Integer projectId){
		String sql = "SELECT * FROM `action_log` where module='declare_project' and object_id=" + projectId;
		List<Map<String, Object>> logList = super.getMapListBySQL(sql, null);
		if(logList != null && logList.size() > 0) {
			for(Map<String, Object> log: logList) {
				sql = "SELECT tfd.field_desc,ah.old_data,ah.new_data,ah.diff FROM action_history ah LEFT JOIN table_field_desc tfd ON ah.field=tfd.field_name WHERE tfd.table_name='declare_project' AND action_id=" + log.get("id");
				List<Map<String, Object>> historyList = super.getMapListBySQL(sql, null);
				if(historyList != null && historyList.size() > 0) {
					log.put("history", historyList);
				}
			}
		}
		return logList;
	}
}
