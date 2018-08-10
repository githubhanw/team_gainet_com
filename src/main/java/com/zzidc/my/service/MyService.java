package com.zzidc.my.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;

/**
 * [说明/描述]
 */
@Service("myService")
public class MyService extends GiantBaseService{
	
	/**
	 * 获取任务统计数据
	 */
	public Map<String, Object> getTaskCount(){
		String sql = "SELECT count(0) 'count',IF(real_start_date=CURDATE(),1,0) 'today',IF(state=1,1,0) 'noopen', "
				+ "IF(real_start_date=DATE_SUB(curdate(),INTERVAL 1 DAY),1,0) 'yesteday',IF(delay=1,1,0) 'delay',"
				+ "IF(overdue=1,1,0) 'overdue' FROM `task` WHERE assigned_id=" + super.getMemberId();
		List<Map<String, Object>> list = super.dao.getMapListBySQL(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取Bug统计数据
	 */
	public Map<String, Object> getBugCount(){
		String sql = "SELECT count(0) 'count' FROM test_bug WHERE developer_id=" + super.getMemberId() + " OR solver_id=" + super.getMemberId();
		List<Map<String, Object>> list = super.dao.getMapListBySQL(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取测试统计数据
	 */
	public Map<String, Object> getTestCount(){
		String sql = "SELECT count(0) 'count' FROM test_apply WHERE state=1 OR apply_id=" + super.getMemberId();
		List<Map<String, Object>> list = super.dao.getMapListBySQL(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取需求统计数据
	 */
	public Map<String, Object> getNeedCount(){
		String sql = "SELECT count(0) 'count' FROM task_need WHERE assigned_id=" + super.getMemberId();
		List<Map<String, Object>> list = super.dao.getMapListBySQL(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
