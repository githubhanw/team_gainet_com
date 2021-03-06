package com.zzidc.team.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.FileUploadUtil;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.giant.zzidc.base.utils.HttpUtils;
import com.zzidc.log.LogMethod;
import com.zzidc.log.LogModule;
import com.zzidc.log.PMLog;
import com.zzidc.team.entity.CodeReport;
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.Task;
import com.zzidc.team.entity.TaskNeed;
import com.zzidc.team.entity.TaskProject;
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
		String sql = "SELECT t.*,tn.need_name,(SELECT d.assigned_name from task d WHERE d.id=t.developer_task_id) developer FROM task t LEFT JOIN task_need tn ON t.need_id=tn.id WHERE 1=1 ";
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
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type")) && "96".equals(temp)) {
				if (!StringUtils.isEmpty(temp1 = conditionPage.getQueryCondition().get("project_id"))) {
					sql += "AND t.project_id=:project_id ";
					countSql += "AND t.project_id=:project_id ";
					conditionMap.put("project_id", temp1);
				}
				if (!StringUtils.isEmpty(temp1 = conditionPage.getQueryCondition().get("product_id"))) {
					sql += "AND tn.product_id=:product_id ";
					countSql += "AND tn.product_id=:product_id ";
					conditionMap.put("product_id", temp1);
				}
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("need_id"))) {
				if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
					if ("95".equals(temp)) {// 所属模块
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
				} else if ("25".equals(temp)) {// 状态：审核中任务
					sql += "AND t.state=3 ";
					countSql += "AND t.state=3 ";
				}  else if ("5".equals(temp)) {// 状态：已完成
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
					sql += "AND t.state=4 AND t.assigned_id=" + memberId;
					countSql += "AND t.state=4 AND t.assigned_id=" + memberId;
				} else if ("11".equals(temp)) {// 状态：由我创建
					sql += "AND t.member_id=" + memberId;
					countSql += "AND t.member_id=" + memberId;
				} else if ("12".equals(temp)) {// 状态：由我关闭
					sql += "AND t.state=7 AND t.closed_id=" + memberId;
					countSql += "AND t.state=7 AND t.closed_id=" + memberId;
				} else if ("13".equals(temp)) {// 状态：待我审核
					sql += "AND (t.delayed_review_id=" + memberId +" OR (t.state=3 AND t.checked_id=" + memberId + "))";
					countSql += "AND (t.delayed_review_id=" + memberId +" OR (t.state=3 AND t.checked_id=" + memberId + "))";
				} else if ("14".equals(temp)) {// 状态：已逾期（我的）
					sql += "AND t.overdue=1 AND t.assigned_id=" + memberId;
					countSql += "AND t.overdue=1 AND t.assigned_id=" + memberId;
				} else if ("15".equals(temp)) {// 状态：已延期（我的）
					sql += "AND t.delay>0 AND t.assigned_id=" + memberId;
					countSql += "AND t.delay>0 AND t.assigned_id=" + memberId;
				} else if ("20".equals(temp)) {// 状态：进行中（我的）
					sql += "AND t.state=2 AND t.assigned_id=" + memberId;
					countSql += "AND t.state=2 AND t.assigned_id=" + memberId;
				} else if ("16".equals(temp)) {// 状态：今日任务（已接收）
					sql += "AND DATE(t.real_start_date)=CURDATE() AND t.assigned_id=" + memberId;
					countSql += "AND DATE(t.real_start_date)=CURDATE() AND t.assigned_id=" + memberId;
				} else if ("17".equals(temp)) {// 状态：昨日任务
					sql += "AND DATE(t.real_start_date)=DATE_SUB(curdate(),INTERVAL 1 DAY) AND t.assigned_id=" + memberId;
					countSql += "AND DATE(t.real_start_date)=DATE_SUB(curdate(),INTERVAL 1 DAY) AND t.assigned_id=" + memberId;
				} else if ("18".equals(temp)) {// 状态：未接收任务
					sql += "AND t.state=1 AND t.assigned_id=" + memberId;
					countSql += "AND t.state=1 AND t.assigned_id=" + memberId;
				} else if ("23".equals(temp)) {// 状态：审核中任务
					sql += "AND t.state=3 AND t.assigned_id=" + memberId;
					countSql += "AND t.state=3 AND t.assigned_id=" + memberId;
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
	 * 获取每个月每天没有任务的人员
	 * @param type
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>> getNoTaskMemberByMonth(String type, String date, String depId){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if(date != null && !"".equals(date)) {
			try {
				cal.setTime(df.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
				cal.setTime(new Date());
			}
		}else {
			cal.setTime(new Date());
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int x = 1; x <= lastDay; x++) {
			Map<String, Object> map = new HashMap<String, Object>();
			date = df.format(cal.getTime());
			map.put("start", date);
			if (isFestivalOrHoliday(cal)) {
				map.put("title", "节假日");
				list.add(map);
			} else {
				List<Map<String, Object>> dayList = getNoTaskMemberByday(type, date, depId);
				if(dayList != null && dayList.size() > 0) {
					Map<String, Object> dayMap = new HashMap<String, Object>();
					String name = "";
					int sort = 0;
					for(int i=0; i< dayList.size(); i++) {
						Map<String, Object> m = dayList.get(i);
						//如果 i+1是3的倍数 或 最后一个时
						if ((i + 1) % 3 == 0 || i + 1 == dayList.size()) {
							name += "，" + m.get("title");
							dayMap = new HashMap<String, Object>();
							dayMap.put("sortId", sort ++);
							dayMap.put("start", date);
							dayMap.put("className", "label-grey");
							dayMap.put("title", name.substring(1));
							list.add(dayMap);
							name = "";
						} else {
							name += "，" + m.get("title");
						}
						if (i + 1 == dayList.size()) {
							dayMap = new HashMap<String, Object>();
							dayMap.put("sortId", sort ++);
							dayMap.put("start", date);
							dayMap.put("className", "label-grey");
							dayMap.put("title", "总计：" + dayList.size() + "人");
							list.add(dayMap);
						}
					}
				}
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		return list;
	}
	
	/**
	 * 是否节假日
	 */
	private boolean isFestivalOrHoliday(Calendar cal) {
		String holiday[] = {"2018-09-22", "2018-09-23", "2018-09-24", 
				"2018-10-01", "2018-10-02", "2018-10-03", "2018-10-04", "2018-10-05", "2018-10-06", "2018-10-07", 
				""};//假日
		String overtime[] = {"2018-09-29", "2018-09-30", ""};//加班
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(cal.getTime());
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {//是否周末
			if (ArrayUtils.contains(overtime, date)) {//是否加班
				return false;
			} else {
				return true;
			}
		} else if(ArrayUtils.contains(holiday, date)) {//是否假日
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取指定日期没有任务的人员
	 * 规则：1、开始时间 小于 某天0点 并且 结束时间 大于 某天24点；
	 * 		2、开始时间 在 某天0 - 24点之间；
	 * 		3、结束时间 在 某天0 - 24点之间。
	 */
	private List<Map<String, Object>> getNoTaskMemberByday(String type, String date, String depId){
		Map<String, Object> prm = new HashMap<String, Object>();
		String sql = "SELECT '" + date + "' start, m.`NAME` title, 'label-info' className, ";
		if(date != null && !"".equals(date)) {
			if("2".equals(type)) {
				sql += "(SELECT count(0) FROM task t WHERE t.assigned_id=m.id AND t.deleted=0 AND "
						+ "(real_start_date<=:startDate AND real_end_date>=:endDate OR "
						+ "real_start_date>=:startDate AND real_start_date<=:endDate OR "
						+ "real_end_date>=:startDate AND real_end_date<=:endDate)) sortId ";
				prm.put("startDate", date + " 00:00:00");
				prm.put("endDate", date + " 23:59:59");
			} else {
				sql += "(SELECT count(0) FROM task t WHERE t.assigned_id=m.id AND t.deleted=0 AND "
						+ "(start_date<=:startDate AND end_date>=:endDate OR "
						+ "start_date>=:startDate AND start_date<=:endDate OR "
						+ "end_date>=:startDate AND end_date<=:endDate)) sortId ";
				prm.put("startDate", date + " 00:00:00");
				prm.put("endDate", date + " 23:59:59");
			}
		} else {
			sql += "(SELECT count(0) FROM task t WHERE t.assigned_id=m.id AND t.deleted=0) tc ";
		}
		sql += "FROM member m, oa_department d1, oa_department d2, oa_department d3 " + 
				"WHERE m.deptID=d1.DEPARTMENT_ID AND d1.PARENT_ID=d2.DEPARTMENT_ID AND d2.PARENT_ID=d3.DEPARTMENT_ID AND m.`STATUS`=0 " + 
				"AND (d1.DEPARTMENT_ID=:depId OR d2.DEPARTMENT_ID=:depId OR d3.DEPARTMENT_ID=:depId) " + 
				"GROUP BY m.number HAVING sortId=0 ORDER BY d1.DEPARTMENT_ID";
		prm.put("depId", depId);
		return super.getMapListBySQL(sql, prm);
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
	 * 详情页面获取同模块任务和测试任务 1、非测试任务的关联任务为：同模块的任务； 2、测试任务的关联任务为：同开发任务的测试任务。
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
	 * 任务详情页面获取测试用例
	 * @return
	 */
	public List<Map<String, Object>> getTestCase(int taskId){
		List<Map<String, Object>> testCase = new ArrayList<Map<String, Object>>();
		String sql = "SELECT id,case_name,case_type,precondition FROM test_case WHERE state=1 AND task_id=" + taskId;
		testCase = super.getMapListBySQL(sql, null);
		return testCase;
	}

	/**
	 * 任务详情页面获取测试用例步骤
	 * @return
	 */
	public List<Map<String, Object>> getTestCaseStep(int taskId){
		List<Map<String, Object>> testCaseStep = new ArrayList<Map<String, Object>>();
		String sql = "SELECT case_id,step,expect FROM test_case_step tcs, test_case tc "
				+ "WHERE tcs.case_id=tc.id AND tcs.`version`=tc.`version` AND task_id=" + taskId;
		testCaseStep = super.getMapListBySQL(sql, null);
		return testCaseStep;
	}

	/**
	 * 任务详情页面获取代码审查
	 * @return
	 */
	public List<Map<String, Object>> getCodeReport(int taskId){
		List<Map<String, Object>> codeReport = new ArrayList<Map<String, Object>>();
		String sql = "SELECT report_type,report_interface,entry_point,online_url,source_file,examination FROM code_report WHERE task_id=" + taskId + " ORDER BY report_type";
		codeReport = super.getMapListBySQL(sql, null);
		return codeReport;
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
	 * 添加原型图和流程图
	 */
	public boolean addPicture(Map<String, String> mvm,MultipartFile[] filePrototype,MultipartFile[] filetree) {
		Task task = new Task();
		String prototypeName = filePrototype[0].getOriginalFilename();
		String treeName = filetree[0].getOriginalFilename();
		//上传界面原型图和流程图文件
		String interfaceImg="";//界面原型图（格式：url,url）
		String flowImg="";//流程图（格式：url,url）
		if(!prototypeName.equals("")){
			for (int i = 0; i < filePrototype.length; i++) {
				Map<String, String> conf = super.getSysConfig();
				FileUploadUtil.SetParam(conf.get("accesskey"), conf.get("secreteky"), conf.get("resource"));
				MultipartFile file = filePrototype[i];
				interfaceImg+=FileUploadUtil.uploadFiles(file).toString()+",";
			}
			interfaceImg = interfaceImg.substring(0,interfaceImg.length() - 1);
		}
		if(!treeName.equals("")){
			for (int i = 0; i < filetree.length; i++) {
				Map<String, String> conf = super.getSysConfig();
				FileUploadUtil.SetParam(conf.get("accesskey"), conf.get("secreteky"), conf.get("resource"));
				MultipartFile file = filetree[i];
				flowImg+=FileUploadUtil.uploadFiles(file).toString()+",";
			}
			flowImg = flowImg.substring(0,flowImg.length() - 1);
		}
		if(GiantUtil.intOf(mvm.get("task_id"), 0) != 0){
			//获取对象
			task = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("task_id"), 0));
		    if(!interfaceImg.equals("")){
				if(task.getInterfaceImg()==null || task.getInterfaceImg().equals("")){
			    task.setInterfaceImg(interfaceImg);	
			    }else{
			    task.setInterfaceImg(task.getInterfaceImg()+","+interfaceImg);	
			    }
		    }  
		    if(!flowImg.equals("")){
			    if(task.getFlowImg()==null || task.getFlowImg().equals("")){
			    task.setFlowImg(flowImg);	
			    }else{
			    task.setFlowImg(task.getFlowImg()+","+flowImg);
			    }
		    }
		}
		boolean b =  super.dao.saveUpdateOrDelete(task, null);
		return b;
	}
	/**
	 * 删除原型图和流程图
	 */
	public boolean delPicture(Map<String, String> mvm,HttpServletRequest request) {
		Task task = new Task();
		String [] interfaceImg=request.getParameterValues("checkinterface");//选中要删除的原型图数组
		String [] flowImg=request.getParameterValues("checkfolw");//选中要删除的流程图数组
		if(GiantUtil.intOf(mvm.get("task_id"), 0) != 0){
			//获取对象
			task = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("task_id"), 0));
		    if(task.getInterfaceImg()!=null &&interfaceImg!=null){
		      String [] stringInterfaceImg= task.getInterfaceImg().split(",");
		      //删除选中的原型图
		      List<String> list = new ArrayList<String>();
		      for (int i=0; i<stringInterfaceImg.length; i++) {
		          list.add(stringInterfaceImg[i]);
		      }
		      for (String string : interfaceImg) {
		     	 list.remove(string);
		 	  }
		      String[] newStr =  list.toArray(new String[1]);
		      String temp = String.join(",", newStr);
		      if(temp.equals("null")){
		    	  task.setInterfaceImg(null);
		      }else{
		    	  task.setInterfaceImg(temp);
		      }
		    }
		    if(task.getFlowImg()!=null &&flowImg!=null){
		      String [] stringFlowImg= task.getFlowImg().split(",");
		      //删除选中的流程图
		      List<String> list = new ArrayList<String>();
		      for (int i=0; i<stringFlowImg.length; i++) {
		          list.add(stringFlowImg[i]);
		      }
		      for (String string : flowImg) {
		     	 list.remove(string);
		 	  }
		      String[] newStr =  list.toArray(new String[1]);
		      String temp = String.join(",", newStr);
		      if(temp.equals("null")){
		    	  task.setFlowImg(null);
		      }else{
		    	  task.setFlowImg(temp);
		      }
		    }
		}
		boolean b =  super.dao.saveUpdateOrDelete(task, null);
		return b;
	}
	/**
	 * 添加任务
	 */
	public boolean addTask(Map<String, String> mvm,MultipartFile[] filePrototype,MultipartFile[] filetree) {
		String prototypeName = filePrototype[0].getOriginalFilename();
		String treeName = filetree[0].getOriginalFilename();
		//上传界面原型图和流程图文件
		String interfaceImg="";//界面原型图（格式：url,url）
		String flowImg="";//流程图（格式：url,url）
		if(!prototypeName.equals("")){
			for (int i = 0; i < filePrototype.length; i++) {
				Map<String, String> conf = super.getSysConfig();
				FileUploadUtil.SetParam(conf.get("accesskey"), conf.get("secreteky"), conf.get("resource"));
				MultipartFile file = filePrototype[i];
				interfaceImg+=FileUploadUtil.uploadFiles(file).toString()+",";
			}
			interfaceImg = interfaceImg.substring(0,interfaceImg.length() - 1);
		}
		if(!treeName.equals("")){
			for (int i = 0; i < filetree.length; i++) {
				Map<String, String> conf = super.getSysConfig();
				FileUploadUtil.SetParam(conf.get("accesskey"), conf.get("secreteky"), conf.get("resource"));
				MultipartFile file = filetree[i];
				flowImg+=FileUploadUtil.uploadFiles(file).toString()+",";
			}
			flowImg = flowImg.substring(0,flowImg.length() - 1);
		}
		
		Task task = new Task();
		task.setResolved((short) 0);
		task.setParentId(GiantUtil.intOf(mvm.get("id"), 0));
		if(!interfaceImg.equals("")){
			if(task.getInterfaceImg()==null || task.getInterfaceImg().equals("")){
		    task.setInterfaceImg(interfaceImg);	
		    }else{
		    task.setInterfaceImg(task.getInterfaceImg()+","+interfaceImg);	
		    }
	    }  
	    if(!flowImg.equals("")){
		    if(task.getFlowImg()==null || task.getFlowImg().equals("")){
		    task.setFlowImg(flowImg);	
		    }else{
		    task.setFlowImg(task.getFlowImg()+","+flowImg);
		    }
	    }
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
		task.setTestState(1);
		
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
			
			//调用OA待办接口
			String Title = "任务待接收";
			OAToDo(Title, task.getAssignedId(), task.getMemberId(), task.getTaskName());
		}
		return b;
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
		task.setProductId(need == null ? 0 : need.getProductId());
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
		task.setTestState(1);
		
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
			
			//调用OA待办接口
			String Title = "任务待接收";
			OAToDo(Title, task.getAssignedId(), task.getMemberId(), task.getTaskName());
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
		PMLog pmLog = new PMLog(LogModule.TASK, LogMethod.EDIT, task.toString(), GiantUtil.stringOf(mvm.get("comment")));
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
		if(!GiantUtil.isEmpty(mvm.get("real_start_date"))) {
			try {//实际开始
				task.setRealStartDate(super.returnTime(mvm.get("real_start_date")));
			} catch (Exception e) {
				task.setRealStartDate(new Timestamp(System.currentTimeMillis()));
			}
		}
		try {//初始结束
			task.setEndDate(super.returnTime(mvm.get("end_date")));
		} catch (Exception e) {
			task.setEndDate(new Timestamp(System.currentTimeMillis()));
		}
		if(!GiantUtil.isEmpty(mvm.get("plan_end_date"))) {
			try {//计划结束
				task.setPlanEndDate(super.returnTime(mvm.get("plan_end_date")));
			} catch (Exception e) {
				task.setPlanEndDate(new Timestamp(System.currentTimeMillis()));
			}
		}
		if(!GiantUtil.isEmpty(mvm.get("real_end_date"))) {
			try {//实际结束
				task.setRealEndDate(super.returnTime(mvm.get("real_end_date")));
			} catch (Exception e) {
				task.setRealEndDate(new Timestamp(System.currentTimeMillis()));
			}
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
			pmLog.add(task.getId(), oldTask, task, new String[] {"task_name", "remark", "closed_reason"}, 
					"task_name", "remark", "need_id", "assigned_name", "task_type", "state", "level", "delay", "overdue", "finished_name", "canceled_name", "closed_name", "closed_reason");
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
			t.setTestState(1);
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
				
				//调用OA待办接口
				String Title = "任务待审核";
				OAToDo(Title, t.getCheckedId(), t.getAssignedId(), t.getTaskName());
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
		String isPass = (mvm.get("stage"))!=null? mvm.get("stage"):"n";
		if ("y".equals(isPass)) {//通过审核
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
				t.setCheckedReason(mvm.get("checked_reason"));
				t.setCheckedTime(new Timestamp(System.currentTimeMillis()));
				
				t.setFinishedId(t.getAssignedId());
				t.setFinishedName(t.getAssignedName());
				t.setFinishedTime(new Timestamp(System.currentTimeMillis()));
				t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				t.setTestState(1);
				boolean b = super.dao.saveUpdateOrDelete(t, null);
				if(b) {
					pmLog.add(t.getId(), oldTask, t, new String[] {"checked_reason"}, "state", "checked_num", "checked_reason", "finished_name", "real_end_date");
					this.log(pmLog);
				}
				//更新任务的父任务状态
				this.updateParentTaskState(GiantUtil.intOf(mvm.get("id"), 0));
				if (t.getTaskType() == 2) {
					//判断：如果测试单下其他测试任务都已完成，修改测试单状态为：已测试
					//获取未完成条数
					String sql = "SELECT 1 FROM task WHERE state IN (1,2,3) AND deleted=0 AND task_type=2 AND test_apply_id=" + t.getTestApplyId() + " AND id!=" + t.getId();
					List<Map<String, Object>> list = super.getMapListBySQL(sql, null);
					if(list == null || list.size() == 0) {
						TestApply test = (TestApply) super.getEntityByPrimaryKey(new TestApply(), t.getTestApplyId());
						if (test != null) {
							test.setState((short) 3);
							super.dao.saveUpdateOrDelete(test, null);
						}
					}
					//修改测试任务（项目提测）对应项目状态
					TestApply test = (TestApply) super.getEntityByPrimaryKey(new TestApply(), t.getTestApplyId());
					if(test != null && test.getApplyType() == 3) {
						TaskProject project = (TaskProject) super.getEntityByPrimaryKey(new TaskProject(), test.getProjectId());
						project.setState((short) 10);
						project.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						super.saveUpdateOrDelete(project, null);
					}
					//修改开发任务测试状态
					String developerTaskIds[] = t.getDeveloperTaskId().split(",");
					for(String taskId: developerTaskIds) {
						Task task = (Task) super.dao.getEntityByPrimaryKey(new Task(), Integer.valueOf(taskId));
						if (task != null) {
							//获取未完成的测试任务数，如果大于1，表示还有其他测试任务未完成，不修改开发任务测试状态。
							sql = "SELECT 1 FROM task WHERE deleted=0 AND state<4 AND developer_task_id=" + taskId;
							list = super.getMapListBySQL(sql, null);
							if((list == null || list.size() == 0) && task.getTestState() == 3) {
								task.setTestState(4);
								task.setTestTime(new Timestamp(System.currentTimeMillis()));
								super.saveUpdateOrDelete(task, null);
							}
							if(test != null) {
								/**
								 * 1、模块、项目提交的测试单；
								 * 2、模块，直接修改测试状态；子模块，获取模块修改测试状态；
								 * 3、判断模块状态，等2，不修改。
								 */
								if(test.getApplyType() == 2 || test.getApplyType() == 3) {
									TaskNeed need = (TaskNeed) super.getEntityByPrimaryKey(new TaskNeed(), task.getNeedId());
									if(need.getParentId() > 0) {
										TaskNeed parentNeed = (TaskNeed) super.getEntityByPrimaryKey(new TaskNeed(), need.getParentId());
										if(parentNeed.getTestState() == 3) {
											parentNeed.setTestState((short) 4);
											super.saveUpdateOrDelete(parentNeed, null);
										}
									}else {
										if(need.getTestState() == 3) {
											need.setTestState((short) 4);
											super.saveUpdateOrDelete(need, null);
										}
									}
								}
							}
						}
					}
				}
				
				
				if (b) {
					//调用OA待办接口
					//子模块下所有任务都审核完成后(状态为已完成)通知子模块负责人提交验收
					if (t.getTaskType() == 1) {//开发任务
						Integer needId = t.getNeedId();
						TaskNeed need = (TaskNeed) getEntityByPrimaryKey(new TaskNeed(), needId);
						String querySql = "select * from task where state != 4 and task_type = 1 and need_id = " + needId ;
						List<Map<String, Object>> list = getMapListBySQL(querySql, null);
						if (list == null || list.size() == 0) {
							//调用OA待办接口
							String Title = "模块需要提交验收";
							OAToDo(Title, need.getAssignedId(), t.getAssignedId(), need.getNeedName());
						}
					}
				}
				
				return b;
			}
		}
		if ("n".equals(isPass)) {//不通过审核
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
				t.setTestState(1);
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
			t.setHandoverInfo(mvm.get("handover_info"));
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

	/**
	 * 任务审核,界面审核
	 * @return
	 */
	public List<Map<String, Object>> getTaskCodeReport(Integer taskId) {
		List<Map<String, Object>> subTaskList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT * FROM `code_report` WHERE report_type=0 AND task_id="+ taskId;
		subTaskList = super.getMapListBySQL(sql, null);
		return subTaskList;
	}
	
	/**
	 * 任务审核,流程审核
	 * @return
	 */
	public List<Map<String, Object>> getTaskCodeInterface(Integer taskId) {
		List<Map<String, Object>> subTaskList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT * FROM `code_report` WHERE report_type=1 AND task_id="+ taskId;
		subTaskList = super.getMapListBySQL(sql, null);
		return subTaskList;
	}
	
	/**
	 * 任务审核,查询代码审查状态未审查个数
	 * @return
	 */
	public int getCodeNum(Integer taskId) {
		String sql = "SELECT COUNT(0)codenum FROM `code_report` WHERE examination=0 AND task_id="+ taskId;
		int subTaskList = Integer.parseInt(super.getMapListBySQL(sql, null).get(0).get("codenum").toString());
		return subTaskList;
	}
	
	/**
	 * 代码审查-编辑状态
	 */
	public boolean updateCodeReport(Map<String, String> mvm) {
		CodeReport ce = null;
		String examination = "";
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			ce = (CodeReport) super.dao.getEntityByPrimaryKey(new CodeReport(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			return false;
		}
		if ("y".equals(mvm.get("isPass"))) {
			examination = "2";
		} else if ("n".equals(mvm.get("isPass"))) {
			examination = "1";
		}else {
			examination = "0";
		}
		ce.setExamination(examination);
		ce.setExaminationId(super.getMemberId());
		return super.dao.saveUpdateOrDelete(ce, null);
	}

}
