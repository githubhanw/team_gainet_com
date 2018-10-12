/**
 * [说明/描述]
 * @author likai
 * @date 2018年7月30日 下午7:15:47
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
package com.zzidc.team.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.MemberConfig;
import com.zzidc.team.task.SyncUser;

/**
 * [说明/描述]
 * @author likai
 * @date 2018年7月30日 下午7:15:47
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
/**
 * @author likai
 *
 */
@Service("organizationUserService")
public class OrganizationUserService extends GiantBaseService {
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "SELECT m.id, m.`name`, m.number, m.sex, m.status, m.phone, m.email,cnf.role_ids roleId, oad.`NAME` oldDptName,d.`name` dptName,r.`name` roleName "
				+ "FROM member m LEFT JOIN oa_department oad ON m.deptID=oad.DEPARTMENT_ID LEFT JOIN member_config cnf ON m.NUMBER = cnf.number "
				+ "LEFT JOIN department d ON cnf.department_id=d.id LEFT JOIN role r ON cnf.role_ids=r.id WHERE 1=1 ";
		String countSql = "select count(0) from member m where 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			int sex = GiantUtil.intOf(conditionPage.getQueryCondition().get("sex"), 0);
			if (sex == 2) {
				sql += "AND m.sex = '男' ";
				countSql += "AND m.sex = '男' ";
			} else if (sex == 3) {
				sql += "AND m.sex = '女' ";
				countSql += "AND m.sex = '女' ";
			}
			String search = "";
			if (!StringUtils.isEmpty(search = conditionPage.getQueryCondition().get("search"))) {
				int searchType = GiantUtil.intOf(conditionPage.getQueryCondition().get("search_type"), 0);
				switch (searchType) {
				case 1: // 姓名
					sql += "AND m.`name` LIKE :search ";
					countSql += "AND `name` LIKE :search ";
					conditionMap.put("search", "%" + search + "%");
					break;
				case 2: // 工号
					sql += "AND m.number LIKE :search ";
					countSql += "AND number LIKE :search ";
					conditionMap.put("search", "%" + search + "%");
					break;
				case 3: // 电话
					sql += "AND m.phone LIKE :search ";
					countSql += "AND phone LIKE :search ";
					conditionMap.put("search", "%" + search + "%");
					break;
				case 4: // 邮箱
					sql += "AND m.email LIKE :search ";
					countSql += "AND email LIKE :search ";
					conditionMap.put("search", "%" + search + "%");
					break;
				default:
					break;
				}
			}
			String temp = "";
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("0".equals(temp)) {// 所有
					
				} else if ("1".equals(temp)) {// 正常
					sql += " AND m.STATUS!=1 ";
					countSql += "AND m.STATUS!=1 ";
				}
			}
		}
		// 字段倒叙或升序排列 {search=, type=0, orderColumn=id, orderByValue=DESC}
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
	 * [配置] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月31日 下午2:29:19 <br>
	 * @param mvm
	 * @return <br>
	 */
	public boolean config(Map<String, String> mvm) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			Member member = (Member) super.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("id"), 0));
			if(member != null) {
				MemberConfig config = null;
				if(GiantUtil.intOf(mvm.get("config_id"), 0) != 0) {
					config = (MemberConfig) super.getEntityByPrimaryKey(new MemberConfig(), GiantUtil.intOf(mvm.get("config_id"), 0));
					if(config == null) {
						return false;
					}
				}
				if(config == null) {
					config = new MemberConfig();
					String number = member.getNumber();
					config.setNumber(number);
					config.setCreateTime(new Timestamp(System.currentTimeMillis()));
				}
				config.setDepartmentId(GiantUtil.intOf(mvm.get("department_id"), 0));
				config.setRoleIds(GiantUtil.stringOf(mvm.get("role_ids")));
				config.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				return super.dao.saveUpdateOrDelete(config, null);
			}
		}
		return false;
	}

	/**
	 * [同步] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月31日 下午2:29:19 <br>
	 * @return <br>
	 */
	public boolean sync() {
		new SyncUser(this).start();
		return true;
	}
	
}
