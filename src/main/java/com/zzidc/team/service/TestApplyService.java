package com.zzidc.team.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.log.LogMethod;
import com.zzidc.log.LogModule;
import com.zzidc.log.PMLog;
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.Task;
import com.zzidc.team.entity.TestApply;

/**
 * [测试申请单业务逻辑层]
 * @author likai
 * @date 2018年7月29日 下午1:49:37
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
@Service("testApplyService")
public class TestApplyService extends GiantBaseService {
	
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		Integer	memberId = 71;
		try {
			memberId = super.getMemberId();
		} catch (Exception e) {
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "select ta.id, ta.apply_id, ta.apply_name, ta.apply_time, ta.test_content, ta.state, ta.dismissal, ta.task_id, t.task_name from test_apply ta left join task t on t.id = ta.task_id where 1=1 ";
		String countSql = "SELECT COUNT(0) from test_apply ta left join task t on t.id = ta.task_id where 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			String temp = "";
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("0".equals(temp)) {//全部
					// 不做过滤
				} else if ("1".equals(temp)) {//待测试
					sql += "AND ta.state = 1";
					countSql += "AND ta.state = 1";
				} else if ("2".equals(temp)) {//测试中
					sql += "AND ta.state = 2";
					countSql += "AND ta.state = 2";
				} else if ("3".equals(temp)) {//已测试
					sql += "AND ta.state = 3";
					countSql += "AND ta.state = 3";
				} else if ("4".equals(temp)) {//驳回
					sql += "AND ta.state = 4";
					countSql += "AND ta.state = 4";
				} else if ("5".equals(temp)) {//由我提测
					sql += "AND ta.apply_id = " + memberId;
					countSql += "AND ta.apply_id = " + memberId;
				} else if ("10".equals(temp)) {//由我提测的搜索
					if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("search"))) {
						sql += "AND t.task_name LIKE :search ";
						countSql += "AND t.task_name LIKE :search ";
						conditionMap.put("search", temp + "%");
					}
					// 如果状态不存在时，默认状态为待测试（1）
					int state = GiantUtil.intOf(conditionPage.getQueryCondition().get("state"), 1);
					if (state > 0) {
						sql += "AND ta.state = :state ";
						countSql += "AND ta.state = :state ";
						conditionMap.put("state", state);
					}
				} else if ("11".equals(temp)) {//由我提测的搜索
					if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("search"))) {
						sql += "AND t.task_name LIKE :search ";
						countSql += "AND t.task_name LIKE :search ";
						conditionMap.put("search", temp + "%");
					}
					// 如果状态不存在时，默认状态为待测试（1）
					int state = GiantUtil.intOf(conditionPage.getQueryCondition().get("state"), 1);
					if (state > 0) {
						sql += "AND (ta.state = :state OR ta.apply_id = :memberId) ";
						countSql += "AND (ta.state = :state OR ta.apply_id = :memberId) ";
						conditionMap.put("state", state);
						conditionMap.put("memberId", memberId);
					}
				}
			}

			String Createtime="";//开始时间
			if (!StringUtils.isEmpty(Createtime = conditionPage.getQueryCondition().get("createtime"))) {
				sql += "AND ta.apply_time>:createtime ";
				countSql += "AND ta.apply_time>:createtime ";
				conditionMap.put("createtime", super.returnTime(Createtime));
			}
			
			String Endtime="";//结束时间
			if (!StringUtils.isEmpty(Endtime = conditionPage.getQueryCondition().get("endtime"))) {
				sql += "AND ta.apply_time<:endtime ";
				countSql += "AND ta.apply_time<:endtime ";
				conditionMap.put("endtime", super.returnTime(Endtime));
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
	 * @date 2018年7月29日 下午3:37:42 <br>
	 * @param mvm
	 * @return <br>
	 */
	public boolean addOrUpdate(Map<String, String> mvm) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			PMLog pmLog = new PMLog(LogModule.TEST, LogMethod.EDIT, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			TestApply testApply = (TestApply) super.getEntityByPrimaryKey(new TestApply(), GiantUtil.intOf(mvm.get("id"), 0));
			TestApply oldTestApply = new TestApply();
			BeanUtils.copyProperties(testApply, oldTestApply);
			testApply.setTaskId(GiantUtil.intOf(mvm.get("task_id"), 0));
			// pmLog.add("test_content", testApply.getTestContent(), GiantUtil.stringOf(mvm.get("test_content")));
			testApply.setTestContent(GiantUtil.stringOf(mvm.get("test_content"))); // 测试内容
			testApply.setUpdateTime(new Timestamp(System.currentTimeMillis())); // 修改时间
			boolean b = super.dao.saveUpdateOrDelete(testApply, null);
			pmLog.add(oldTestApply, testApply, new String[] {"test_content"}, "test_content");
			pmLog.setObjectId(testApply.getId());
			this.log(pmLog);
			return b;
		} else {
			PMLog pmLog = new PMLog(LogModule.TEST, LogMethod.ADD, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			TestApply testApply = new TestApply();
			testApply.setState((short) 1);  // 1 表示待测试
			testApply.setApplyId(super.getMemberId()); // 当前用户ID
			testApply.setApplyName(super.getMemberName()); // 当前用户名称
			testApply.setApplyTime(new Timestamp(System.currentTimeMillis())); // 申请测试时间
			testApply.setTaskId(GiantUtil.intOf(mvm.get("task_id"), 0));
			testApply.setTestContent(GiantUtil.stringOf(mvm.get("test_content"))); // 测试内容
			testApply.setUpdateTime(new Timestamp(System.currentTimeMillis())); // 修改时间
			boolean b = super.dao.saveUpdateOrDelete(testApply, null);
			pmLog.setObjectId(testApply.getId());
			this.log(pmLog);
			return b;
		}
	}

	/**
	 * [验证测试申请单的状态] <br>
	 * <pre>
	 * 只有待测试的申请单才能驳回
	 * 只有待测试的申请单才能领取
	 * </pre>
	 * @author likai <br>
	 * @date 2018年7月29日 下午3:37:42 <br>
	 * @param mvm
	 * @param state
	 * @return <br>
	 */
	public boolean checkState(Map<String, String> mvm, int state) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			TestApply testApply = (TestApply) super.getEntityByPrimaryKey(new TestApply(), GiantUtil.intOf(mvm.get("id"), 0));
			if(testApply != null) {
				if(state == testApply.getState()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * [驳回] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月29日 下午3:37:42 <br>
	 * @param mvm
	 * @return <br>
	 */
	public boolean dismissal(Map<String, String> mvm) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			TestApply testApply = (TestApply) super.getEntityByPrimaryKey(new TestApply(), GiantUtil.intOf(mvm.get("id"), 0));
			if(testApply != null) {
				PMLog pmLog = new PMLog(LogModule.TEST, LogMethod.DISMISSAL, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
				TestApply oldTestApply = new TestApply();
				BeanUtils.copyProperties(testApply, oldTestApply);
				pmLog.setObjectId(testApply.getApplyId());
				testApply.setState((short) 4);  // 4 表示驳回
				testApply.setDismissal(GiantUtil.stringOf(mvm.get("comment")));
				testApply.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				boolean b = super.dao.saveUpdateOrDelete(testApply, null);
				if(b) {
					pmLog.add(oldTestApply, testApply, new String[] {"dismissal"}, "state", "dismissal");
					this.log(pmLog);
				}
				return b;
			}
		}
		return false;
	}

	/**
	 * 获取当前用户下可以添加测试申请单的任务列表
	 * <pre>
	 * 条件：
	 * 1. 已完成的任务
	 * 2. 未删除的任务
	 * 3. 任务类型不为测试的
	 * 4. 满足1.2.3后，同时没有提交过测试申请单的
	 * </pre>
	 * 
	 * @param id 当前测试申请单的ID
	 * @return
	 */
	public List<Map<String, Object>> getFinishedTasksByMember(int id){
		// FIXME 如果测试申请单有删除操作时，此处的子查询中应该加上 删除状态判断
		String sql = "select id, task_name from task where state = 4 and deleted = 0 and task_type != 2 " + 
				"and id not in (select task_id from test_apply where 1=1 and id != :id) " + 
				"order by update_time desc";
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("id", id);
		return super.getMapListBySQL(sql, conditionMap);
	}
	/**
	 * [获取所有的任务列表] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年8月7日 下午9:18:09 <br>
	 * @return <br>
	 */
	public List<Map<String, Object>> getAllTasks(){
		String sql = "select id, task_name from task order by update_time desc";
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 领取
	 */
	public boolean receive(Map<String, String> mvm) {
		Task task = new Task();
		task.setResolved((short)0);
		Integer developerTaskId = null;
		Integer parentId = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestApply apply = (TestApply) super.dao.getEntityByPrimaryKey(new TestApply(), GiantUtil.intOf(mvm.get("id"), 0));
			if(apply == null) {
				return false;
			}
			PMLog pmLog = new PMLog(LogModule.TEST, LogMethod.RECEIVE, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			TestApply oldTestApply = new TestApply();
			BeanUtils.copyProperties(apply, oldTestApply);
			pmLog.setObjectId(apply.getApplyId());
			// 设置测试申请单的状态
			apply.setState((short) 2);
			// 更新测试申请单
			boolean b = this.saveUpdateOrDelete(apply, null);
			if(!b) {
				return false;
			}
			pmLog.add(oldTestApply, apply, "state");
			this.log(pmLog);
			
			developerTaskId = apply.getTaskId();
			parentId = apply.getTaskId();
			Task applyTask = (Task) super.dao.getEntityByPrimaryKey(new Task(), apply.getTaskId());
			if(applyTask.getParentId() > 0) {
				parentId = applyTask.getParentId();
			}
		}
		task.setNeedId(GiantUtil.intOf(mvm.get("need_id"), 0));
		task.setTaskName(GiantUtil.stringOf(mvm.get("task_name")));
		task.setTaskType(GiantUtil.intOf(mvm.get("task_type"), 0));
		task.setLevel(GiantUtil.intOf(mvm.get("level"), 0));
		task.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		try {
			task.setStartDate(super.returnTime(mvm.get("start_date")));
		} catch (Exception e) {
			task.setStartDate(new Timestamp(System.currentTimeMillis()));
		}
		try {
			task.setEndDate(super.returnTime(mvm.get("end_date")));
		} catch (Exception e) {
			task.setEndDate(new Timestamp(System.currentTimeMillis()));
		}
		task.setMemberId(super.getMemberId());
		task.setMemberName(super.getMemberName());
		Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("assigned_id"), 0));
		task.setAssignedId(member == null ? 0 : member.getId());
		task.setAssignedName(member == null ? "" : member.getName());
		task.setAssignedTime(new Timestamp(System.currentTimeMillis()));
		task.setOpenedId(0);
		task.setOpenedName("");
		task.setCheckedId(0);
		task.setCheckedName("");
		task.setCanceledId(0);
		task.setCanceledName("");
		task.setClosedId(0);
		task.setClosedName("");
		task.setPercent(0);
		task.setState((short) 1);
		task.setDelay((short) 0);
		task.setOverdue((short) 0);
		task.setDeleted((short) 0);
		task.setParentId(parentId);
		task.setDeveloperTaskId(developerTaskId);
		task.setCreateTime(new Timestamp(System.currentTimeMillis()));
		task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.ADD, task.toString(), null);
		boolean b =  super.dao.saveUpdateOrDelete(task, null);
		if(b) {
			pmLog.setObjectId(task.getId());
			this.log(pmLog);
		}
		return b;
	}
}
