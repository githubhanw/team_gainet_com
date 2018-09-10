package com.zzidc.my.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantUtils;
import com.giant.zzidc.base.utils.PlusCut;
import com.giant.zzidc.base.utils.ResultInfo;
import com.zzidc.team.entity.Member;

/**
 * [说明/描述]
 */
@Service("myService")
public class MyService extends GiantBaseService{
	
	/**
	 * 通过子查询的方式，获取所有任务相关统计数据
	 */
	public Map<String, Object> getTaskCount(){
		String sql = "SELECT count(0) 'count',SUM(IF(state=1,1,0)) 'wait',SUM(IF(state=2,1,0)) 'doing'," + 
				"SUM(IF(state=3,1,0)) 'checking',SUM(IF(state=4,1,0)) 'done',(SELECT count(0) " + 
				"FROM task WHERE deleted=0 AND state=3 AND checked_id=" + super.getMemberId() + 
				") 'wait_check',SUM(IF(delay>0,1,0)) 'delay',SUM(IF(overdue=1,1,0)) 'overdue' " + 
				"FROM `task` WHERE deleted=0 AND assigned_id=" + super.getMemberId();
		List<Map<String, Object>> list = super.dao.getMapListBySQL(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过子查询的方式，获取所有需求相关统计数据
	 */
	public Map<String, Object> getNeedCount(){
		String sql = "SELECT count(0) 'count',SUM(IF(stage=1,1,0)) 'checking'," + 
				"(SELECT count(0) FROM task_need WHERE state!=0 AND stage=1 AND member_id=" + 
				super.getMemberId() + ") 'wait_check' " + 
				"FROM task_need WHERE state!=0 AND assigned_id=" + super.getMemberId();
		List<Map<String, Object>> list = super.dao.getMapListBySQL(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取待我验收需求
	 */
	public Map<String, Object> getCheckedMineNeed(){
		String sql = "SELECT COUNT(0) 'count' FROM task_need tn LEFT JOIN task_project tp ON tn.project_id=tp.id WHERE tn.state!=0 AND tn.member_id="+super.getMemberId();
		List<Map<String, Object>> list = super.dao.getMapListBySQL(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过子查询的方式，获取所有测试相关统计数据
	 */
	public Map<String, Object> getTestCount(){
		String sql ="";
		if (super.getRoleIds() != null && (super.getRoleIds() == 9 || super.getRoleIds() == 5)) {
			sql = "SELECT count(0) 'count' FROM test_apply WHERE state=1";
		}else {
			sql = "SELECT count(0) 'count' FROM test_apply WHERE apply_id=" + super.getMemberId();
		}
		List<Map<String, Object>> list = super.dao.getMapListBySQL(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 通过子查询的方式，获取所有Bug相关统计数据
	 */
	public Map<String, Object> getBugCount(){
		String sql = "SELECT count(0) 'count' FROM test_bug WHERE solvestatus=0 AND developer_id=" + super.getMemberId();
		List<Map<String, Object>> list = super.dao.getMapListBySQL(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 设置openID
	 */
	public boolean updateOpenId(String openId,int adminid){
		Member login = (Member)dao.getEntityByPrimaryKey(new Member(),adminid);
		login.setNewOpenid(openId);
		return super.dao.saveUpdateOrDelete(login, null);
	}
	
	/**
	 * 解绑微信
	 */
	public boolean unbindwx(Member login) {
		login.setNewOpenid("");
		boolean b=super.dao.saveUpdateOrDelete(login, null);
		return  b;
	}

	/**
	 * 检查微信绑定状态
	 */
	public ResultInfo checkbindwx(Member login) {
		if(login == null){
			return new ResultInfo(1, "获取会员信息失败");
		}
		if(!GiantUtils.isEmpty(login.getNewOpenid())){//已绑定微信
			return new ResultInfo(0,"绑定成功");
		}
		return new ResultInfo(2, "未绑定微信");
	}

}
