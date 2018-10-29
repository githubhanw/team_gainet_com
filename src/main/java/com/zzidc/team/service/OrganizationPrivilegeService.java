package com.zzidc.team.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.Privilege;
import com.zzidc.team.entity.Role;
import com.zzidc.team.entity.RolePrivilege;

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
	 * 添加、修改权限管理信息
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
        /*if (parentId == 0) {
        	p.setRank(null);
		}*/
		return super.dao.saveUpdateOrDelete(p, null);
	}
	
	public List<Map<String, Object>> getPrivileges() {
		List<Map<String, Object>> result = getPrivilegesByParentId(0);
		if(result == null) {
			return result;
		}
		for(Map<String, Object> item : result) {
			int parentId = GiantUtil.intOf(item.get("id"), 0);
			if(parentId == 0) {
				continue;
			}
			List<Map<String, Object>> childrens = getPrivilegesByParentId(parentId);
			item.put("childrens", childrens);
		}
		return result;
	}
	
	public List<Map<String, Object>> getPrivilegesByParentId(int parentId) {
		String sql = "select * from privilege where state = 1 and parent_id = :parentId order by rank ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", parentId);
		return super.dao.getListMapBySql(sql, params);
	}

	/**
	 * 将制定权限分配给多个角色
	 */
	public boolean saveRole(Map<String, String> mvm) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			Privilege privilege = (Privilege) super.getEntityByPrimaryKey(new Privilege(), GiantUtil.intOf(mvm.get("id"), 0));
			if(privilege != null) {
				//获取当前角色已有权限列表
				List<Object> delList = super.getEntityListBySQL("select * from role_privilege where privilege_id=" + privilege.getId(), null, new RolePrivilege());
				//生成当前角色新权限对象列表
				List<RolePrivilege> saveList = new ArrayList<RolePrivilege>();
				if(!GiantUtil.isEmpty(mvm.get("allRole"))) {
					String[] roleIds = GiantUtil.stringOf(mvm.get("allRole")).split(",");
					for(String roleId : roleIds) {
						Role role = (Role) super.getEntityByPrimaryKey(new Role(), GiantUtil.intOf(roleId, 0));
						if(role != null) {
							RolePrivilege rp = new RolePrivilege();
							rp.setPrivilegeId(privilege.getId());
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
