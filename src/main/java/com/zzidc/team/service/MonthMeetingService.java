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
import com.zzidc.team.entity.TaskProject;

/**
 * 月会议管理业务逻辑层
 * @author hanwei
 *
 */
@Service("monthMeetingService")
public class MonthMeetingService extends GiantBaseService {
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "select mm.id id, mm.name name, mm.create_time create_time, mm.state state, " + 
				"(SELECT count(0) FROM task_need tn WHERE tn.meeting_id=mm.id AND tn.state!=0) totalCount, " +
				"(SELECT count(0) FROM task_need tn WHERE tn.meeting_id=mm.id AND tn.state!=0 and tn.state=4) approvedCount, " +
				"(SELECT count(0) FROM task_need tn WHERE tn.meeting_id=mm.id AND tn.state!=0 and tn.state=3) waitApprovedCount " +
				"from month_meeting mm where 1 = 1 ";
		String countSql = "SELECT count(0) FROM month_meeting mm where 1 = 1 ";
		
		/*String temp1 = "";
		if (!StringUtils.isEmpty(temp1 = conditionPage.getQueryCondition().get("search"))) {
			temp1 = temp1.trim();
			String searchType = "";
			if (!StringUtils.isEmpty(searchType = conditionPage.getQueryCondition().get("searchType"))) {
				if ("1".equals(searchType)) {
					try {
						Integer.parseInt(temp1);	
						sql += "AND mm.id =:search ";
						countSql += "AND mm.id =:search ";
						conditionMap.put("search", temp1);
					} catch (Exception e) {
						// 名称条件
						sql += "AND mm.name LIKE :search ";
						countSql += "AND mm.name LIKE :search ";
						conditionMap.put("search", temp1 + "%");
					}
				} else if("2".equals(searchType)) {
					sql += "AND mm.remark LIKE :search ";
					countSql += "AND mm.remark LIKE :search ";
					conditionMap.put("search", "%" + temp1 + "%");
				}
			}
		}
		
		String State="";//状态
		if (!StringUtils.isEmpty(State = conditionPage.getQueryCondition().get("state"))) {
			sql += "AND mm.state=:state ";
			countSql += "AND mm.state=:state ";
			conditionMap.put("state", State);
		}*/
		
		String temp = "";
		if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
			if ("0".equals(temp)) {//已删除
				sql += "AND state=0";
				countSql += "AND state=0";
			} else if ("1".equals(temp)) {//进行中
				sql += "AND state=1";
				countSql += "AND state=1";
			} else if ("2".equals(temp)) {//已完结
				sql += "AND state=2";
				countSql += "AND state=2";
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
		MonthMeeting p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			p = (MonthMeeting) super.dao.getEntityByPrimaryKey(new MonthMeeting(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			p = new MonthMeeting();
			p.setCreateTime(new Timestamp(System.currentTimeMillis()));
			p.setState((short)1);
		}
		p.setName(GiantUtil.stringOf(mvm.get("name")));
		p.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return super.dao.saveUpdateOrDelete(p, null);
	}

}
