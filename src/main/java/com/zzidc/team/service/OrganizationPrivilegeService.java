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

/**
 * 权限管理业务逻辑层
 * @author hanwei
 *
 */
@Service("organizationPrivilegeService")
public class OrganizationPrivilegeService extends GiantBaseService {
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "select p.*,(select distinct name from privilege pr where pr.id=p.parent_id) parentName from privilege p where 1=1 ";
		String countSql = "select count(0) from privilege where 1=1 ";
		
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
	 * 添加、修改月会议信息
	 */
	public boolean addOrUpd(Map<String, String> mvm) {
		Privilege p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			p = (Privilege) super.dao.getEntityByPrimaryKey(new Privilege(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			p = new Privilege();
			p.setCreateTime(new Timestamp(System.currentTimeMillis()));
			p.setState((short)1);
		}
		Integer parentId = GiantUtil.intOf(mvm.get("parent_id"), 0);
		p.setUrl(GiantUtil.stringOf(mvm.get("url")));
		p.setRank(GiantUtil.intOf(mvm.get("rank"), 0));
		p.setParentId(parentId);	
		p.setName(GiantUtil.stringOf(mvm.get("name")));
		p.setRemark(GiantUtil.stringOf(mvm.get("remark")));	
		p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        if (parentId == 0) {
        	p.setRank(null);
		}
		return super.dao.saveUpdateOrDelete(p, null);
	}

}
