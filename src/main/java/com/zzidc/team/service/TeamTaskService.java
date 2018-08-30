package com.zzidc.team.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.giant.zzidc.base.utils.HttpUtils;
import com.zzidc.log.LogMethod;
import com.zzidc.log.LogModule;
import com.zzidc.log.PMLog;
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.Task;
import com.zzidc.team.entity.TaskNeed;
import com.zzidc.team.entity.TestApply;

import net.sf.json.JSONObject;

/**
 * 任务管理
 */
@Service("teamTaskService")
public class TeamTaskService extends GiantBaseService {

	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		Integer memberId = 157;
		try {
			memberId = super.getMemberId();
		} catch (Exception e) {
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "SELECT t.*,tn.need_name FROM task t LEFT JOIN task_need tn ON t.need_id=tn.id WHERE 1=1 ";
		String countSql = "SELECT count(0) FROM task t LEFT JOIN task_need tn ON t.need_id=tn.id WHERE 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			String temp = "";
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("98".equals(temp) || "97".equals(temp)) {//当前用户
					sql += "AND (t.member_id=:memberId OR t.opened_id=:memberId OR t.assigned_id=:memberId OR t.delayed_id=:memberId OR "
							+ "t.delayed_review_id=:memberId OR t.closed_id=:memberId OR t.checked_id=:memberId OR t.finished_id=:memberId) ";
					countSql += "AND (t.member_id=:memberId OR t.opened_id=:memberId OR t.assigned_id=:memberId OR t.delayed_id=:memberId OR "
							+ "t.delayed_review_id=:memberId OR t.closed_id=:memberId OR t.checked_id=:memberId OR t.finished_id=:memberId) ";
					conditionMap.put("memberId", memberId);
				}
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("nameOrId"))) {
				sql += "AND (t.id=:id OR t.task_name LIKE :name) ";
				countSql += "AND (t.id=:id OR t.task_name LIKE :name) ";
				conditionMap.put("id", temp);
				conditionMap.put("name", "%" + temp + "%");
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("search"))) {
				temp = temp.trim();
				String searchType = "";
				if (!StringUtils.isEmpty(searchType = conditionPage.getQueryCondition().get("searchType"))) {
					if ("1".equals(searchType)) {//任务名称
						sql += "AND (t.id=:id OR t.task_name LIKE :name) ";
						countSql += "AND (t.id=:id OR t.task_name LIKE :name) ";
						conditionMap.put("id", temp);
						conditionMap.put("name", "%" + temp + "%");
					} else if("2".equals(searchType)) {//任务描述
						sql += "AND t.remark LIKE :search ";
						countSql += "AND t.remark LIKE :search ";
						conditionMap.put("search", "%" + temp + "%");
					}
				}
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("memberSearch"))) {
				temp = temp.trim();
				String memberType = "";
				if (!StringUtils.isEmpty(memberType = conditionPage.getQueryCondition().get("memberType"))) {
					if ("1".equals(memberType)) {//指派人
						sql += "AND t.assigned_name=:memberSearch ";
						countSql += "AND t.assigned_name=:memberSearch ";
					} else if("2".equals(memberType)) {//关闭人
						sql += "AND t.finished_name=:memberSearch ";
						countSql += "AND t.finished_name=:memberSearch ";
					}
					conditionMap.put("memberSearch", temp);
				}
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("start_date"))) {
				String dateType = "";
				if (!StringUtils.isEmpty(dateType = conditionPage.getQueryCondition().get("dateType"))) {
					if ("1".equals(dateType)) {//计划结束日期
						sql += "AND t.plan_end_date>:start_date ";
						countSql += "AND t.plan_end_date>:start_date ";
					} else if("2".equals(dateType)) {//实际结束日期
						sql += "AND t.real_end_date>:start_date ";
						countSql += "AND t.real_end_date>:start_date ";
					}
					conditionMap.put("start_date", temp);
				}
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("end_date"))) {
				String dateType = "";
				if (!StringUtils.isEmpty(dateType = conditionPage.getQueryCondition().get("dateType"))) {
					if ("1".equals(dateType)) {//计划结束日期
						sql += "AND t.plan_end_date<:end_date ";
						countSql += "AND t.plan_end_date<:end_date ";
					} else if("2".equals(dateType)) {//实际结束日期
						sql += "AND t.real_end_date<:end_date ";
						countSql += "AND t.real_end_date<:end_date ";
					}
					conditionMap.put("end_date", temp);
				}
			}
			String temp1 = "";
			if (!StringUtils.isEmpty(temp1 = conditionPage.getQueryCondition().get("project_id"))) {
				if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
					if ("96".equals(temp)) {// 所属项目
						sql += "AND t.project_id=:project_id ";
						countSql += "AND t.project_id=:project_id ";
						conditionMap.put("project_id", temp1);
					}
				}
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("need_id"))) {
				if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
					if ("95".equals(temp)) {// 所属需求
						sql += "AND t.need_id=:need_id ";
						countSql += "AND t.need_id=:need_id ";
						conditionMap.put("need_id", temp);
					}
				}
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("state"))) {
				sql += "AND t.state=:state ";
				countSql += "AND t.state=:state ";
				conditionMap.put("state", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("taskType"))) {
				sql += "AND t.task_type=:task_type ";
				countSql += "AND t.task_type=:task_type ";
				conditionMap.put("task_type", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("level"))) {
				sql += "AND t.level=:level ";
				countSql += "AND t.level=:level ";
				conditionMap.put("level", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("delay"))) {
				sql += "AND t.delay=:delay ";
				countSql += "AND t.delay=:delay ";
				conditionMap.put("delay", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("overdue"))) {
				sql += "AND t.overdue=:overdue ";
				countSql += "AND t.overdue=:overdue ";
				conditionMap.put("overdue", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("0".equals(temp)) {// 已删除
					sql += "AND t.deleted=1 ";
					countSql += "AND t.deleted=1 ";
				} else {// 未删除
					sql += "AND t.deleted=0 ";
					countSql += "AND t.deleted=0 ";
				}

				if ("1".equals(temp)) {// 正常
					sql += "AND t.parent_id=0 AND t.state<7";
					countSql += "AND t.parent_id=0 AND t.state<7";
				} else if ("2".equals(temp)) {// 状态：所有
					sql += "AND t.parent_id=0";
					countSql += "AND t.parent_id=0";
				} else if ("3".equals(temp)) {// 状态：未开始
					sql += "AND t.state=1";
					countSql += "AND t.state=1";
				} else if ("4".equals(temp)) {// 状态：进行中
					sql += "AND t.state=2";
					countSql += "AND t.state=2";
				} else if ("5".equals(temp)) {// 状态：已完成
					sql += "AND t.state=4";
					countSql += "AND t.state=4";
				} else if ("6".equals(temp)) {// 状态：已取消
					sql += "AND t.state=6";
					countSql += "AND t.state=6";
				} else if ("7".equals(temp)) {// 状态：已关闭
					sql += "AND t.state=7";
					countSql += "AND t.state=7";
				} else if ("8".equals(temp)) {// 状态：指派给我
					sql += "AND t.assigned_id=" + memberId;
					countSql += "AND t.assigned_id=" + memberId;
				} else if ("9".equals(temp)) {// 状态：已延期
					sql += "AND t.delay>0";
					countSql += "AND t.delay>0";
				} else if ("19".equals(temp)) {// 状态：已逾期
					sql += "AND t.overdue=1";
					countSql += "AND t.overdue=1";
				} else if ("10".equals(temp)) {// 状态：我完成
					sql += "AND t.state=4 AND t.finished_id=" + memberId;
					countSql += "AND t.state=4 AND t.finished_id=" + memberId;
				} else if ("11".equals(temp)) {// 状态：由我创建
					sql += "AND t.member_id=" + memberId;
					countSql += "AND t.member_id=" + memberId;
				} else if ("12".equals(temp)) {// 状态：由我关闭
					sql += "AND t.state=7 AND t.closed_id=" + memberId;
					countSql += "AND t.state=7 AND t.closed_id=" + memberId;
				} else if ("13".equals(temp)) {// 状态：待我审核
					sql += "AND (t.delayed_review_id=" + memberId +" OR (t.state=3 AND t.checked_id=" + memberId + "))";
					countSql += "AND (t.delayed_review_id=" + memberId +" OR (t.state=3 AND t.checked_id=" + memberId + "))";
				} else if ("19".equals(temp)) {// 状态：已逾期（我的）
					sql += "AND t.overdue=1 AND t.assigned_id=" + memberId;
					countSql += "AND t.overdue=1 AND t.assigned_id=" + memberId;
				} else if ("15".equals(temp)) {// 状态：已延期（我的）
					sql += "AND t.delay>0 AND t.assigned_id=" + memberId;
					countSql += "AND t.delay>0 AND t.assigned_id=" + memberId;
				} else if ("16".equals(temp)) {// 状态：今日任务（已接收）
					sql += "AND DATE(t.real_start_date)=CURDATE() AND t.assigned_id=" + memberId;
					countSql += "AND DATE(t.real_start_date)=CURDATE() AND t.assigned_id=" + memberId;
				} else if ("17".equals(temp)) {// 状态：昨日任务
					sql += "AND DATE(t.real_start_date)=DATE_SUB(curdate(),INTERVAL 1 DAY) AND t.assigned_id=" + memberId;
					countSql += "AND DATE(t.real_start_date)=DATE_SUB(curdate(),INTERVAL 1 DAY) AND t.assigned_id=" + memberId;
				} else if ("18".equals(temp)) {// 状态：未接收任务
					sql += "AND t.state=1 AND t.assigned_id=" + memberId;
					countSql += "AND t.state=1 AND t.assigned_id=" + memberId;
				}
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
	 * 列表主任务获取子任务
	 */
	public void getSubTaskList(List<?> taskList, String type, String orderColumn, String orderByValue) {
		if(taskList == null || taskList.size() == 0) {
			return;
		}
		for (Object obj : taskList) {
			@SuppressWarnings("unchecked")
			Map<String, Object> taskMap = (Map<String, Object>) obj;
			String sql = "SELECT t.*,tn.need_name FROM task t LEFT JOIN task_need tn ON t.need_id=tn.id WHERE (t.parent_id=" + taskMap.get("id")
					+ " AND t.task_type!=3 OR t.developer_task_id=" + taskMap.get("id") + " AND t.task_type=3) AND t.deleted=0";
			if ("1".equals(type)) {
				sql += " AND t.state<7";
			}
			sql += " ORDER BY " + orderColumn + (StringUtils.isEmpty(orderByValue) ? " DESC" : " " + orderByValue);
			List<Map<String, Object>> list = super.getMapListBySQL(sql, null);
			if (list != null && list.size() > 0) {
				taskMap.put("subTask", list);
				taskMap.put("subCount", list.size());
			}
		}
	}

	/**
	 * 获取任务详情
	 * 
	 * @return
	 */
	public Map<String, Object> getTaskDetail(Integer taskId) {
		String sql = "SELECT t.*,tn.need_name,tn.need_remark,tn.check_remark FROM task t LEFT JOIN task_need tn ON t.need_id=tn.id WHERE t.id=" + taskId;
		List<Map<String, Object>> list = super.getMapListBySQL(sql, null);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 详情页面获取子任务
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getSubTaskList(Integer parentTaskId) {
		List<Map<String, Object>> subTaskList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT * FROM `task` WHERE deleted=0 AND (developer_task_id IS NULL OR developer_task_id!=" + parentTaskId + ") AND parent_id="
				+ parentTaskId + " ORDER BY state";
		subTaskList = super.getMapListBySQL(sql, null);
		return subTaskList;
	}

	/**
	 * 详情页面获取同需求任务和测试任务 1、非测试任务的关联任务为：同需求的任务； 2、测试任务的关联任务为：同开发任务的测试任务。
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getRelationTaskList(Integer needId, Integer taskId, Integer type) {
		List<Map<String, Object>> relationTaskList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT * FROM `task` WHERE deleted=0 AND parent_id=0 AND id!=" + taskId + " AND need_id=" + needId + " ORDER BY state";
		if (type == 2) {
			sql = "SELECT * FROM `task` WHERE deleted=0 AND parent_id=0 AND developer_task_id=" + taskId + " ORDER BY state";
		}
		relationTaskList = super.getMapListBySQL(sql, null);
		return relationTaskList;
	}

	/**
	 * 任务详情页面获取关联任务
	 * @return
	 */
	public List<Map<String, Object>> getLinkTask(String linkTask){
		List<Map<String, Object>> linkTaskList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT * FROM task WHERE id in (" + linkTask + ")";
		linkTaskList = super.getMapListBySQL(sql, null);
		return linkTaskList;
	}

	/**
	 * 获取日志
	 * @param taskId
	 * @return
	 */
	public List<Map<String, Object>> getLogList(Integer taskId){
		String sql = "SELECT * FROM `action_log` where module='task' and object_id=" + taskId;
		List<Map<String, Object>> logList = super.getMapListBySQL(sql, null);
		if(logList != null && logList.size() > 0) {
			for(Map<String, Object> log: logList) {
				sql = "SELECT tfd.field_desc,ah.old_data,ah.new_data,ah.diff FROM action_history ah LEFT JOIN table_field_desc tfd ON ah.field=tfd.field_name WHERE tfd.table_name='task' AND action_id=" + log.get("id");
				List<Map<String, Object>> historyList = super.getMapListBySQL(sql, null);
				if(historyList != null && historyList.size() > 0) {
					log.put("history", historyList);
				}
			}
		}
		return logList;
	}
	
	public List<Map<String, Object>> getNeed() {
		String sql = "SELECT id, need_name FROM `task_need` WHERE state>0;";
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 添加任务信息（批量、分解）
	 */
	public boolean add(Map<String, String> mvm) {
		Task task = new Task();
		task.setResolved((short) 0);
		task.setParentId(GiantUtil.intOf(mvm.get("id"), 0));
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			Task parentTask = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if (parentTask.getResolved() == 0) {
				PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.RESOLVED, task.toString(), null);
				Task oldParentTask = new Task();
				BeanUtils.copyProperties(parentTask, oldParentTask);
				parentTask.setResolved((short) 1);
				parentTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				super.dao.saveUpdateOrDelete(parentTask, null);
				pmLog.add(parentTask.getId(), oldParentTask, parentTask, "resolved");
				this.log(pmLog);
			}
		}
		TaskNeed need = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("need_id"), 0));
		task.setNeedId(need == null ? GiantUtil.intOf(mvm.get("need_id"), 0) : need.getId());
		task.setProjectId(need == null ? 0 : need.getProjectId());
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
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
			date = calendar.getTime();
			task.setEndDate(new Timestamp(date.getTime()));
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
		task.setCreateTime(new Timestamp(System.currentTimeMillis()));
		task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		boolean b =  super.dao.saveUpdateOrDelete(task, null);
		if(b) {
			//微信提醒
			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd E HH:mm");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String start_date = sdf.format(task.getStartDate());
			String end_date = sdf.format(task.getEndDate());
			Date date = new Date();
			String time = form.format(date);
			if (GiantUtils.isEmpty(member) || GiantUtils.isEmpty(member.getNewOpenid())) {
				
			}else {
				String openid = member.getNewOpenid();//"o-GQDj8vVvfH2715yROC1aqY4YM0";
				String first = "你好,收到一个【领取任务】提醒";
				String keyword1 = "任务领取";
				String keyword2 = this.getMemberName();
				String keyword3 = time;
				String remark = "总任务标题："+task.getTaskName()+"\\n总任务领取ID："+task.getId()+"\\n总任务负责人："+task.getMemberName()+"\\n总任务开始时间："+start_date+"\\n总任务结束时间："+end_date;//自定义通知，以换行符隔开 \n
				String str=sendWeChatUtil(openid,first,keyword1,keyword2,keyword3,remark);
				String a1 = JSONObject.fromObject(str).toString();
				HttpUtils.weiXinSendPost(a1);
			}
		}
		return b;
	}

	/**
	 * 编辑任务信息
	 */
	public boolean edit(Map<String, String> mvm) {
		Task task = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			task = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(task == null) {
				return false;
			}
		}
		PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.EDIT, task.toString(), null);
		Task oldTask = new Task();
		BeanUtils.copyProperties(task, oldTask);
		task.setTaskName(GiantUtil.stringOf(mvm.get("task_name")));
		task.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		task.setNeedId(GiantUtil.intOf(mvm.get("need_id"), 0));
		Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("assigned_id"), 0));
		task.setAssignedId(member == null ? 0 : member.getId());
		task.setAssignedName(member == null ? "" : member.getName());
		if (member != null) {
			task.setAssignedTime(new Timestamp(System.currentTimeMillis()));
		}
		task.setTaskType(GiantUtil.intOf(mvm.get("task_type"), 0));
		task.setState((short) GiantUtil.intOf(mvm.get("state"), 0));
		task.setDeleted((short) GiantUtil.intOf(mvm.get("deleted"), 0));
		task.setLevel(GiantUtil.intOf(mvm.get("level"), 0));

		try {//初始开始日期
			task.setStartDate(super.returnTime(mvm.get("start_date")));
		} catch (Exception e) {
			task.setStartDate(new Timestamp(System.currentTimeMillis()));
		}
		try {//实际开始
			task.setRealStartDate(super.returnTime(mvm.get("real_start_date")));
		} catch (Exception e) {
			task.setRealStartDate(new Timestamp(System.currentTimeMillis()));
		}
		try {//初始结束
			task.setEndDate(super.returnTime(mvm.get("end_date")));
		} catch (Exception e) {
			task.setEndDate(new Timestamp(System.currentTimeMillis()));
		}
		try {//计划结束
			task.setPlanEndDate(super.returnTime(mvm.get("plan_end_date")));
		} catch (Exception e) {
			task.setPlanEndDate(new Timestamp(System.currentTimeMillis()));
		}
		try {//实际结束
			task.setRealEndDate(super.returnTime(mvm.get("real_end_date")));
		} catch (Exception e) {
			task.setRealEndDate(new Timestamp(System.currentTimeMillis()));
		}
		task.setDelay((short) GiantUtil.intOf(mvm.get("delay"), 0));
		task.setOverdue((short) GiantUtil.intOf(mvm.get("overdue"), 0));

		member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("finished_id"), 0));
		task.setFinishedId(member == null ? 0 : member.getId());
		task.setFinishedName(member == null ? "" : member.getName());
		try {
			task.setFinishedTime(super.returnTime(mvm.get("finished_time")));
		} catch (Exception e) {
		}
		member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("checked_id"), 0));
		task.setCheckedId(member == null ? 0 : member.getId());
		task.setCheckedName(member == null ? "" : member.getName());
		
		member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("closed_id"), 0));
		task.setClosedId(member == null ? 0 : member.getId());
		task.setClosedName(member == null ? "" : member.getName());
		try {
			task.setClosedTime(super.returnTime(mvm.get("closed_time")));
		} catch (Exception e) {
		}
		task.setClosedReason(GiantUtil.stringOf(mvm.get("closed_reason")));

		task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		boolean b = super.dao.saveUpdateOrDelete(task, null);
		if(b) {
			pmLog.add(task.getId(), oldTask, task, new String[] {"task_name", "remark", "closed_reason"}, "task_name", "remark", "need_id", "assigned_name", "task_type", "state", "level", "delay", "overdue", "finished_name", "canceled_name", "closed_name", "closed_reason");
			this.log(pmLog);
		}
		return b;
	}

	/**
	 * 指派给
	 */
	public boolean assign(Map<String, String> mvm) {
		Task t = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t == null) {
				return false;
			}
			PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.ASSIGN, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			Task oldTask = new Task();
			BeanUtils.copyProperties(t, oldTask);
			
			Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("assigned_id"), 0));
			t.setAssignedId(member == null ? 0 : member.getId());
			t.setAssignedName(member == null ? "" : member.getName());
			t.setAssignedTime(new Timestamp(System.currentTimeMillis()));
			t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(t, null);
			if(b) {
				pmLog.add(t.getId(), oldTask, t, "assigned_name");
				this.log(pmLog);
			}
			return b;
		}
		return false;
	}

	/**
	 * 变更任务信息
	 */
	public boolean change(Map<String, String> mvm) {
		Task t = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t == null) {
				return false;
			}
			PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.CHANGE, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			Task oldTask = new Task();
			BeanUtils.copyProperties(t, oldTask);
			t.setTaskName(GiantUtil.stringOf(mvm.get("task_name")));
			t.setRemark(GiantUtil.stringOf(mvm.get("remark")));
			t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(t, null);
			if(b) {
				pmLog.add(t.getId(), oldTask, t, new String[] {"task_name", "remark"}, "task_name", "remark");
				this.log(pmLog);
			}
			return b;
		}
		return false;
	}

	/**
	 * 关闭任务
	 */
	public boolean close(Map<String, String> mvm) {
		Task t = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t == null) {
				return false;
			}
			PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.CLOSE, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			Task oldTask = new Task();
			BeanUtils.copyProperties(t, oldTask);
			t.setClosedId(super.getMemberId());
			t.setClosedName(super.getMemberName());
			if (t.getState() == 4) {
				t.setClosedReason("已完成");
			}
			if (t.getState() == 6) {
				t.setClosedReason("已取消");
			}
			t.setClosedTime(new Timestamp(System.currentTimeMillis()));
			t.setState((short) 7);
			t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(t, null);
			if(b) {
				pmLog.add(t.getId(), oldTask, t, "closed_name", "state");
				this.log(pmLog);
			}
			this.updateParentTaskState(GiantUtil.intOf(mvm.get("id"), 0));
			
			return b;
		}
		return false;
	}

	/**
	 * 激活任务
	 */
	public boolean active(Map<String, String> mvm) {
		Task t = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t == null) {
				return false;
			}
			PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.ACTIVE, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			Task oldTask = new Task();
			BeanUtils.copyProperties(t, oldTask);
			t.setState((short) 2);
			t.setCheckedId(0);
			t.setCheckedName("");
			t.setCheckedTime(null);
			t.setCanceledId(0);
			t.setCanceledName("");
			t.setCanceledTime(null);
			t.setClosedId(0);
			t.setClosedName("");
			t.setClosedTime(null);
			t.setClosedReason("");
			t.setRealEndDate(null);
			t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(t, null);
			if(b) {
				pmLog.setObjectId(t.getId());
				this.log(pmLog);
			}
			this.updateParentTaskState(GiantUtil.intOf(mvm.get("id"), 0));
			
			return b;
		}
		return false;
	}

	/**
	 * 开始任务
	 */
	public boolean open(Map<String, String> mvm) {
		Task t = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t == null) {
				return false;
			}
			PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.OPEN, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			Task oldTask = new Task();
			BeanUtils.copyProperties(t, oldTask);
			t.setState((short) 2);
			t.setOpenedId(0);
			t.setOpenedName("");
			t.setOpenedTime(null);
			try {
				t.setPlanEndDate(super.returnTime(mvm.get("plan_end_date")));
			} catch (Exception e) {
				t.setPlanEndDate(new Timestamp(System.currentTimeMillis()));
			}
			t.setRealStartDate(new Timestamp(System.currentTimeMillis()));
			t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(t, null);
			if(b) {
				pmLog.add(t.getId(), oldTask, t, "opend_name", "state", "plan_end_date", "real_start_date");
				this.log(pmLog);
			}
			if(t.getTaskType() != 2) {
				this.updateParentTaskState(GiantUtil.intOf(mvm.get("id"), 0));
			}
			
			return b;
		}
		return false;
	}

	/**
	 * 任务暂停
	 */
	public boolean pause(Map<String, String> mvm) {
		Task t = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t == null) {
				return false;
			}
			PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.PAUSE, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			Task oldTask = new Task();
			BeanUtils.copyProperties(t, oldTask);
			t.setState((short) 5);
			boolean b = super.dao.saveUpdateOrDelete(t, null);
			if(b) {
				pmLog.add(t.getId(), oldTask, t, "state");
				this.log(pmLog);
			}
			return b;
		}
		return false;
	}

	/**
	 * 取消任务
	 */
	public boolean cancel(Map<String, String> mvm) {
		Task t = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t == null) {
				return false;
			}
			PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.CANCEL, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			Task oldTask = new Task();
			BeanUtils.copyProperties(t, oldTask);
			t.setCanceledId(super.getMemberId());
			t.setCanceledName(super.getMemberName());
			t.setCanceledTime(new Timestamp(System.currentTimeMillis()));
			t.setState((short) 6);
			t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			t.setCheckedTime(null);
			boolean b = super.dao.saveUpdateOrDelete(t, null);
			if(b) {
				pmLog.add(t.getId(), oldTask, t, "state", "canceled_name");
				this.log(pmLog);
			}
			return b;
		}
		return false;
	}

	/**
	 * 完成任务
	 */
	public boolean finish(Map<String, String> mvm) {
		Task t = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t == null) {
				return false;
			}
			PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.FINISH, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			Task oldTask = new Task();
			BeanUtils.copyProperties(t, oldTask);
			Member check = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("checkedid"), 0));
			t.setCheckedId(check == null ? 0 : check.getId());
			t.setCheckedName(check == null ? "" : check.getName());
			t.setState((short) 3);
			t.setRealEndDate(new Timestamp(System.currentTimeMillis()));
			t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(t, null);
			if(b) {
				pmLog.add(t.getId(), oldTask, t, "state", "checked_name");
				this.log(pmLog);
				//微信提醒
				SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd E HH:mm");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String start_date = sdf.format(t.getStartDate());
				String end_date = sdf.format(t.getEndDate());
				Date date = new Date();
				String time = form.format(date);
				if (GiantUtils.isEmpty(check) || GiantUtils.isEmpty(check.getNewOpenid())) {
					
				}else {
					String openid = check.getNewOpenid().toString();//"o-GQDj8vVvfH2715yROC1aqY4YM0";
					String first = "你好,收到一个【审核任务】提醒";
					String keyword1 = "任务审核";
					String keyword2 = this.getMemberName();
					String keyword3 = time;
					String remark = "总任务标题："+t.getTaskName()+"\\n总任务领取ID："+t.getId()+"\\n总任务负责人："+t.getMemberName()+"\\n总任务开始时间："+start_date+"\\n总任务结束时间："+end_date;//自定义通知，以换行符隔开 \n
					String str=sendWeChatUtil(openid,first,keyword1,keyword2,keyword3,remark);
					String a1 = JSONObject.fromObject(str).toString();
					HttpUtils.weiXinSendPost(a1);
				}
			}
			return b;
		}
		return false;
	}

	/**
	 * 完成任务的审核
	 */
	public boolean finishCheck(Map<String, String> mvm) {
		Task t = null;
		String isPass = (mvm.get("is").toString())!=null? mvm.get("is").toString():"0";
		if ("1".equals(isPass)) {//通过审核
			if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
				// 获取对象
				t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
				if(t == null) {
					return false;
				}
				PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.FINISHCHECK, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
				Task oldTask = new Task();
				BeanUtils.copyProperties(t, oldTask);
				t.setState((short) 4);
				t.setCheckedNum((t.getCheckedNum()==null?0:t.getCheckedNum())+1);
				t.setCheckedReason(mvm.get("checked_reason").toString());
				t.setCheckedTime(new Timestamp(System.currentTimeMillis()));
				
				t.setFinishedId(t.getAssignedId());
				t.setFinishedName(t.getAssignedName());
				t.setFinishedTime(new Timestamp(System.currentTimeMillis()));
				t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				boolean b = super.dao.saveUpdateOrDelete(t, null);
				if(b) {
					pmLog.add(t.getId(), oldTask, t, new String[] {"checked_reason"}, "state", "checked_num", "checked_reason", "finished_name", "real_end_date");
					this.log(pmLog);
				}
				//更新任务的父任务状态
				this.updateParentTaskState(GiantUtil.intOf(mvm.get("id"), 0));
				if (t.getTaskType() == 2) {
					//修改测试单状态为：已测试
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("taskId", t.getDeveloperTaskId());
					TestApply test = (TestApply) super.getEntityByHQL("TestApply", map);
					if (test != null) {
						test.setState((short) 3);
						super.dao.saveUpdateOrDelete(test, null);
					}
				}
				return b;
			}
		}
		if ("0".equals(isPass)) {//不通过审核
			if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
				// 获取对象
				t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
				if(t == null) {
					return false;
				}
				PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.FINISHCHECK, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
				Task oldTask = new Task();
				BeanUtils.copyProperties(t, oldTask);
				t.setState((short) 2);
				t.setCheckedNum((t.getCheckedNum()==null?0:t.getCheckedNum())+1);
				t.setCheckedReason(String.valueOf(mvm.get("checked_reason")));
				t.setCheckedTime(new Timestamp(System.currentTimeMillis()));
				t.setRealEndDate(null);
				t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				boolean b = super.dao.saveUpdateOrDelete(t, null);
				if(b) {
					pmLog.add(t.getId(), oldTask, t, new String[] {"checked_reason"}, "state", "checked_num", "checked_reason");
					this.log(pmLog);
				}
				this.updateParentTaskState(GiantUtil.intOf(mvm.get("id"), 0));
				return b;
			}
		}else {
			return false;
		}
		return false;
	}

	/**
	 * 获取任务信息
	 * 
	 * @param projectId
	 * @return
	 */
	public Map<String, Object> getProjectDetail(int projectId) {
		String sql = "SELECT * FROM declaration_project WHERE id=" + projectId;
		List<Map<String, Object>> list = super.getMapListBySQL(sql, null);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取任务成果
	 * 
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getProjectResult(int projectId) {
		String sql = "SELECT pr.*,m.`name` 'member_name' FROM declaration_project_result pr " + "LEFT JOIN member m ON pr.member_id=m.id WHERE pr.project_id="
				+ projectId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取任务文档
	 * 
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getProjectDoc(int projectId) {
		String sql = "SELECT pd.*,pdt.project_doc_type FROM declaration_project_doc pd, declaration_project_doc_type pdt "
				+ "WHERE pd.type_id=pdt.id AND pd.state>0 AND project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * [判断任务是否能进行关联操作] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月26日 下午9:10:04 <br>
	 * @param id
	 *            任务ID
	 * @return <br>
	 */
	public boolean isCanRelevance(int id) {
		Task task = (Task) super.getEntityByPrimaryKey(new Task(), id);
		if (task != null) {
			if (task.getParentId() > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * [获取当前任务可关联的任务列表] <br>
	 * 
	 * <pre>
	 *  符合条件：
	 *  1. 不是子任务的， 即parent_id 为 0；
	 *  2. 没有子任务的任务，即id 不在 parent_id中的，除了当前任务id；
	 *  3. state不为删除的，及 state != 0的；
	 * </pre>
	 * 
	 * @author likai <br>
	 * @date 2018年7月26日 下午9:10:04 <br>
	 * @param id
	 *            任务ID
	 * @return <br>
	 */
	public List<Map<String, Object>> getCanRelevanceTasks(int id) {
		if (isCanRelevance(id)) {
			String sql = "select id, task_name, parent_id from task where (parent_id = 0 or parent_id = :parentId) and deleted = 0 and id != :id and id not in (select parent_id from task where parent_id > 0)";
			Map<String, Object> conditionMap = new HashMap<String, Object>();
			conditionMap.put("id", id);
			conditionMap.put("parentId", id);
			return super.getMapListBySQL(sql, conditionMap);
		} else {
			return new ArrayList<Map<String, Object>>();
		}
	}

	/**
	 * [关联] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月26日 下午8:27:39 <br>
	 * @param mvm
	 *            参数{r=0.4018686015158184, id=25, needs=1,3,5, comment=dsdsdsd}
	 * @return 是否成功 <br>
	 */
	public boolean relevance(Map<String, String> mvm) {
		if (!isCanRelevance(GiantUtil.intOf(mvm.get("id"), 0))) {
			return false;
		}
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
//			String needs = GiantUtil.stringOf(mvm.get("needs"));
//			String[] needIds = needs.split(",");
//			Map<String, Object> conditionMap = new HashMap<String, Object>();
//			StringBuffer sb = new StringBuffer();
//			sb.append("select * from task where id in (");
//			for (int i = 0; i < needIds.length; i++) {
//				sb.append(":id" + i + ",");
//				conditionMap.put("id" + i, GiantUtil.intOf(needIds[i], 0));
//			}
//			String sql = sb.substring(0, sb.length() - 1) + ")";
//			List<Object> list = super.dao.getEntityListBySQL(sql, conditionMap, new Task());
//			if (list != null && list.size() > 0) {
//				List<Task> taskNeeds = new ArrayList<Task>();
//				for (Object obj : list) {
//					Task need = (Task) obj;
//					need.setParentId(GiantUtil.intOf(mvm.get("id"), 0));
//					need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//					taskNeeds.add(need);
//				}
//				return super.dao.saveUpdateOrDelete(taskNeeds, null);
//			}
			Task task = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(task != null) {
				PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.RELEVANCE, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
				Task oldTask = new Task();
				BeanUtils.copyProperties(task, oldTask);
				task.setLink(GiantUtil.stringOf(mvm.get("needs")));
				task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				boolean b = super.dao.saveOrUpdateOne(task);
				if(b) {
					pmLog.add(task.getId(), oldTask, task, "link");
					this.log(pmLog);
				}
				return b;
			}
		}
		return false;
	}

	/**
	 * [延期] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月26日 下午8:27:39 <br>
	 * @param mvm
	 *            参数{r=0.16247026563442657, id=18, delayed_date=2018-07-03,
	 *            delayed_review_id=1, comment=d}
	 * @return 是否成功 <br>
	 */
	public boolean delay(Map<String, String> mvm) {
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			Task task = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if (task != null) {
				PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.DELAY, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
				Task oldTask = new Task();
				BeanUtils.copyProperties(task, oldTask);
				try {
					task.setDelayedDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("delayed_date")));
				} catch (ParseException e) {
					task.setDelayedDate(new Date());
				}
				task.setDelayedId(super.getMemberId());
				task.setDelayedName(super.getMemberName());
				task.setDelayedReviewId(GiantUtil.intOf(mvm.get("delayed_review_id"), 0));
				task.setDelayedTime(new Timestamp(System.currentTimeMillis()));
				task.setDelay((short) 1); // 设置延期状态为待延期
				task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				boolean b = super.dao.saveUpdateOrDelete(task, null);
				if(b) {
					pmLog.add(task.getId(), oldTask, task, "delayed_date", "delayed_name", "delay", "delayed_review_id");
					this.log(pmLog);
				}
				return b;
			}
		}
		return false;
	}

	/**
	 * 延期任务的审核
	 */
	public boolean delayCheck(Map<String, String> mvm) {
		Task t = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if (t.getDelayedDate() != null && t.getDelayedId() != null && t.getDelayedName() != null && t.getDelayedReviewId() != null
					&& t.getDelayedTime() != null) {
				PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.DELAYCHECK, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
				Task oldTask = new Task();
				BeanUtils.copyProperties(t, oldTask);
				t.setDelay((short) 2);
				Date temp = t.getPlanEndDate();
				t.setPlanEndDate(Timestamp.valueOf(t.getDelayedDate().toString()));
				t.setDelayedDate(temp);
				t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				boolean b = super.dao.saveUpdateOrDelete(t, null);
				if(b) {
					pmLog.add(t.getId(), oldTask, t, "delayed_date", "plan_end_date", "delay");
					this.log(pmLog);
				}
				this.updateParentTaskState(GiantUtil.intOf(mvm.get("id"), 0));
				
				return b;
			}
		}
		return false;
	}

	/**
	 * 交接任务
	 */
	public boolean handover(Map<String, String> mvm) {
		Task t = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			t = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t == null) {
				return false;
			}
			PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.HANDOVER, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			Task oldTask = new Task();
			BeanUtils.copyProperties(t, oldTask);
			t.setHandoverId(t.getAssignedId());
			t.setHandoverName(t.getAssignedName());
			t.setHandoverInfo(mvm.get("handover_info").toString());
			t.setHandoverState(1);
			t.setHandoverTime(new Timestamp(System.currentTimeMillis()));
			
			Member handover = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("handover_id"), 0));
			t.setAssignedId(handover == null ? 0 : handover.getId());
			t.setAssignedName(handover == null ? "" : handover.getName());
			t.setAssignedTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(t, null);
			if(b) {
				pmLog.add(t.getId(), oldTask, t, new String[] {"handover_info"}, "handover_name", "handover_info", "handover_state", "assigned_name");
				this.log(pmLog);
			}
			return b;
		}
		return false;
	}

}
