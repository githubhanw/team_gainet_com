package com.zzidc.declaration.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtils;

/**
 * [项目成果文档列表]
 */
@Service("projectDocService")
public class ProjectDocService extends GiantBaseService{
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "select pd.id, pd.doc_name, pd.project_doc_url, pd.doc_state, pd.state, pd.provide_date, " + 
				"pdt.project_doc_type, pr.project_result_name, p.project_name, pd.create_time " + 
				"from declaration_project_doc pd " + 
				"left join declaration_project_doc_type pdt on pdt.id = pd.type_id " + 
				"left join declaration_project_result pr on pr.id = pd.result_id " + 
				"left join declaration_project p on p.id = pd.project_id where 1=1 ";
		String countSql = "SELECT count(0) " + 
				"from declaration_project_doc pd " + 
				"left join declaration_project_doc_type pdt on pdt.id = pd.type_id " + 
				"left join declaration_project_result pr on pr.id = pd.result_id " + 
				"left join declaration_project p on p.id = pd.project_id where 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			String temp = "";
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("search"))) {
				sql += "AND pd.doc_name LIKE :search";
				countSql += "AND pd.doc_name LIKE :search";
				conditionMap.put("search", temp + "%");
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type")) && !"2".equals(temp)) {
				sql += "AND pd.state=:state";
				countSql += "AND pd.state=:state";
				conditionMap.put("state", temp);
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
	 * [获取可用的文档类型列表] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月13日 下午4:23:12 <br>
	 * @return <br>
	 */
	public List<Map<String, Object>> getDocType() {
		String sql = "select id, project_doc_type from declaration_project_doc_type where state = 1";
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * [获取可用的项目成果列表] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月13日 下午4:23:12 <br>
	 * @return <br>
	 */
	public List<Map<String, Object>> getProjectResult() {
		String sql = "select pr.id, pr.project_result_name, pr.project_id from declaration_project_result pr left join declaration_project p on p.id = pr.project_id where pr.state > 0 and p.state = 1";
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * [获取可用的项目列表] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月13日 下午4:23:12 <br>
	 * @return <br>
	 */
	public List<Map<String, Object>> getProject() {
		String sql = "select id, project_name from declaration_project where state = 1";
		return super.getMapListBySQL(sql, null);
	}

}
