package com.zzidc.team.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.team.entity.Department;
import com.zzidc.team.entity.Member;

/**
 * [组织->团队管理 Service]
 * @author likai
 * @date 2018年7月30日 下午2:55:52
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
@Service("organizationDepartmentService")
public class OrganizationDepartmentService extends GiantBaseService {
	
	/**
	 * [获取团队的树形列表] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月30日 下午3:01:03 <br>
	 * @return <br>
	 */
	public List<Map<String, Object>> getDepartments() {
		List<Map<String, Object>> result = this.getDepartmentsByParentId(0);
		if(result == null) {
			return result;
		}
		for(Map<String, Object> item : result) {
			int parentId = GiantUtil.intOf(item.get("id"), 0);
			if(parentId == 0) {
				continue;
			}
			item.put("childrens", this.getDepartmentsByParentId(parentId));
		}
		return result;
	}
	
	/**
	 * [获取当前父ID下都所有团队] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月30日 下午4:54:32 <br>
	 * @param parentId 父ID
	 * @return <br>
	 */
	public List<Map<String, Object>> getDepartmentsByParentId(int parentId) {
		String sql = "select * from department where parent_id = :parentId order by sort desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", parentId);
		return super.dao.getListMapBySql(sql, params);
	}
	
	/**
	 * [获取用户列表] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月30日 下午5:02:43 <br>
	 * @return <br>
	 */
	public List<Map<String, Object>> getMembers() {
		String sql = "select id, `name` from member order by id desc;";
		return super.dao.getListMapBySql(sql, null);
	}

	/**
	 * [添加/修改] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月30日 下午5:26:13 <br>
	 * @param mvm
	 * @return <br>
	 */
	public boolean addOrUpdate(Map<String, String> mvm) {
		Department department = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			department = (Department) super.getEntityByPrimaryKey(new Department(), GiantUtil.intOf(mvm.get("id"), 0));
		}
		if(department == null) {
			department = new Department();
			department.setState((short) 1);  // 1 正常
		}
		int leaderId = GiantUtil.intOf(mvm.get("leader_id"), 0);
		if(leaderId > 0) {
			Member member = (Member) super.getEntityByPrimaryKey(new Member(), leaderId);
			department.setLeaderId(leaderId);
			department.setLeaderName(member.getName());
		}
		department.setParentId(GiantUtil.intOf(mvm.get("parent_id"), 0));
		department.setName(GiantUtil.stringOf(mvm.get("name")));
		department.setCreateTime(new Timestamp(System.currentTimeMillis()));
		department.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		department.setSort(GiantUtil.intOf(mvm.get("sort"), 0));
		return super.dao.saveUpdateOrDelete(department, null);
	}
	
	/**
	 * [获取所有角色列表] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年8月3日 下午6:04:24 <br>
	 * @return <br>
	 */
	public List<Map<String, Object>> getRoles() {
		String sql = "select id, name from role where status = 1";
		return super.dao.getListMapBySql(sql, null);
	}
}