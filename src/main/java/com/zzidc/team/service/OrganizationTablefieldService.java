package com.zzidc.team.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.MonthMeeting;
import com.zzidc.team.entity.Privilege;
import com.zzidc.team.entity.TableFieldDesc;

/**
 * 表字段管理业务逻辑层
 * @author hanwei
 *
 */
@Service("organizationTablefieldService")
public class OrganizationTablefieldService extends GiantBaseService {
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "select * from table_field_desc where 1 = 1 ";
		String countSql = "select count(0) from table_field_desc where 1 = 1 ";
		
		String temp = "";
		if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
			if ("0".equals(temp)) {//已删除
				sql += "AND state=0";
				countSql += "AND state=0";
			} else if ("1".equals(temp)) {//正常
				sql += "AND state=1";
				countSql += "AND state=1";
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
	 * 添加、修改表字段信息
	 */
	public boolean addOrUpd(Map<String, String> mvm) {
		TableFieldDesc p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			p = (TableFieldDesc) super.dao.getEntityByPrimaryKey(new TableFieldDesc(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			p = new TableFieldDesc();
			p.setState((short)1);
		}
		p.setTableName(GiantUtil.stringOf(mvm.get("tableName")));
		p.setFieldName(GiantUtil.stringOf(mvm.get("fieldName")));
		p.setFieldDesc(GiantUtil.stringOf(mvm.get("fieldNesc")));	
		return super.dao.saveUpdateOrDelete(p, null);
	}

}
