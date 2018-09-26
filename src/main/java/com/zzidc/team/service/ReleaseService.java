package com.zzidc.team.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtils;

/**
 * 发布业务处理类
 * @author hanw
 *
 */
@Service("releaseService")
public class ReleaseService extends GiantBaseService {
	
	/**
	 * 发布列表
	 * @param conditionPage
	 * @return
	 */
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "select * from publish where 1=1 ";
		String countSql = "select count(0) from publish where 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			String temp = "";			
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("0".equals(temp)) {//未发布
					sql += "AND state=0 ";
					countSql += "AND state=0 ";
				} else if ("1".equals(temp)) {//待发布
					sql += "AND state=1 ";
					countSql += "AND state=1 ";
				} else if ("2".equals(temp)) {//已发布
					sql += "AND state=2 ";
					countSql += "AND state=2 ";
				} else if ("3".equals(temp)) {//延期发布
					sql += "AND state=3 ";
					countSql += "AND state=3 ";
				}				
			}
		}
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
