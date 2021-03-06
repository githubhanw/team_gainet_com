package com.zzidc.team.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.Privilege;
import com.zzidc.team.entity.Role;
import com.zzidc.team.entity.RolePrivilege;

/**
 * [角色管理 Service]
 * @author likai
 * @date 2018年8月3日 下午1:53:49
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
@Service("organizationRoleService")
public class OrganizationRoleService extends GiantBaseService{
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "select * from role r where 1=1 ";
		String countSql = "select count(0) from role r where 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			String temp = "";
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("search"))) {
				sql += "AND r.name LIKE :search ";
				countSql += "AND r.name LIKE :search ";
				conditionMap.put("search", "%" + temp + "%");
			}
		}
		String temp = "";
		if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
			if ("1".equals(temp)) {//正常
				sql += "AND status=1";
				countSql += "AND status=1";
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
	 * [添加/修改] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年8月3日 下午3:08:14 <br>
	 * @param mvm
	 * @return <br>
	 */
	public boolean addOrUpdate(Map<String, String> mvm) {
		Role role = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			role = (Role) super.getEntityByPrimaryKey(new Role(), GiantUtil.intOf(mvm.get("id"), 0));
			if(role == null) {
				return false;
			}
			role.setStatus((short) GiantUtil.intOf(mvm.get("status"), 1));
		} else {
			role = new Role();
			role.setCreateTime(new Timestamp(System.currentTimeMillis()));
			role.setStatus((short) 1);
		}
		role.setName(GiantUtil.stringOf(mvm.get("name")));
		role.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		role.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return super.dao.saveUpdateOrDelete(role, null);
	}

	/**
	 * 获取已有权限关联表数据
	 */
	public List<Map<String, Object>> getAuthorized(Integer roleId) {
		String sql = "select * from role_privilege where role_id=" + roleId;
		List<Map<String, Object>> list = dao.getMapListBySQL(sql, null);
		if(list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		return list;
	}

	/**
	 * [获取所有权限列表] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年8月3日 下午3:48:34 <br>
	 * @return <br>
	 */
	public List<Map<String, Object>> getAllPrivileges() {
		String sql = "select id, name, parent_id, url from privilege where state = 1 order by parent_id asc, rank asc";
		List<Map<String, Object>> list = dao.getMapListBySQL(sql, null);
		if(list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		return list;
	}

	/**
	 * 授权
	 */
	public boolean auth(Map<String, String> mvm) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			Role role = (Role) super.getEntityByPrimaryKey(new Role(), GiantUtil.intOf(mvm.get("id"), 0));
			if(role != null) {
				//获取当前角色已有权限列表
				List<Object> delList = super.getEntityListBySQL("select * from role_privilege where role_id=" + role.getId(), null, new RolePrivilege());
				//生成当前角色新权限对象列表
				List<RolePrivilege> saveList = new ArrayList<RolePrivilege>();
				if(!GiantUtil.isEmpty(mvm.get("privileges"))) {
					String[] privileges = GiantUtil.stringOf(mvm.get("privileges")).split(",");
					for(String privilege : privileges) {
						Privilege item = (Privilege) super.getEntityByPrimaryKey(new Privilege(), GiantUtil.intOf(privilege, 0));
						if(item != null) {
							RolePrivilege rp = new RolePrivilege();
							rp.setPrivilegeId(item.getId());
							rp.setRoleId(role.getId());
							saveList.add(rp);
						}
					}
				}
				return super.dao.saveUpdateOrDelete(saveList, delList);
			}
		}
		return false;
	}
}
