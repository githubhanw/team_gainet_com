package com.zzidc.declaration.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtils;

/**
 * [项目成果文档类型]
 */
@Service("projectDocTypeService")
public class ProjectDocTypeService extends GiantBaseService{
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "SELECT id, project_doc_type, create_time, update_time, state FROM declaration_project_doc_type where id > 0 ";
		String countSql = "SELECT count(0) FROM declaration_project_doc_type where id > 0 ";
		if (conditionPage.getQueryCondition() != null) {
			String temp = "";
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("search"))) {
				sql += "AND project_doc_type LIKE :search";
				countSql += "AND project_doc_type LIKE :search";
				conditionMap.put("search", temp + "%");
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type")) && !"2".equals(temp)) {
				sql += "AND state=:state";
				countSql += "AND state=:state";
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

}
