package com.zzidc.team.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
import com.zzidc.team.entity.TaskNeed;
import com.zzidc.team.entity.TaskProduct;
import com.zzidc.team.entity.TaskProject;
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
		String sql = "SELECT ta.*,t.task_name,"
				+ "(SELECT GROUP_CONCAT(tt.assigned_name) FROM task tt WHERE tt.task_type=2 AND "
				+ "(tt.developer_task_id=ta.task_id OR tt.test_apply_id=ta.id) GROUP BY tt.test_apply_id) tester "
				+ "FROM test_apply ta LEFT JOIN task t ON t.id = ta.task_id WHERE 1=1 ";
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
				conditionMap.put("createtime", Createtime);
			}
			
			String Endtime="";//结束时间
			if (!StringUtils.isEmpty(Endtime = conditionPage.getQueryCondition().get("endtime"))) {
				sql += "AND ta.apply_time<:endtime ";
				countSql += "AND ta.apply_time<:endtime ";
				conditionMap.put("endtime", Endtime);
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
	 * 显示添加/编辑页面树信息
	 * @param model
	 * @param taskId
	 * @param needId
	 * @param projectId
	 * @param productId
	 */
	public void showTreeMsg(Model model, Integer testId, Integer taskId, Integer needId, Integer projectId, Integer productId, String testState, Integer tester) {
		int applyType = 0;
		if (taskId != null && taskId > 0) {//原来的不动
			applyType = 1;
			model.addAttribute("tasks", getFinishedTasksByMember(taskId));
			model.addAttribute("task_id", taskId);
		} else if (needId != null && needId > 0) {//需求（模块）：获取模块下所有子模块，模块、子模块下所有任务（包含原型图、流程图），所有任务下的测试用例；【获取已完成的任务、已验收的模块】
			applyType = 2;
			TaskNeed n = (TaskNeed) getEntityByPrimaryKey(new TaskNeed(), needId);
			if(n != null && n.getState() == 4) {
				//获取子模块
				List<Map<String, Object>> subNeed = getSubNeedByNeed(needId);
				//获取子模块下的任务
				List<Map<String, Object>> subNeedTask = getSubNeedTaskByNeed(testId, needId, testState, tester);
				//获取模块下的任务
				List<Map<String, Object>> needTask = getTaskByNeed(testId, needId, testState, tester);
				//获取所有任务下的所有测试用例列表
				List<Map<String, Object>> testCase = getTestCaseByNeed(testId, needId, testState, tester);
				//获取所有测试用例下的所有步骤
				List<Map<String, Object>> testCaseStep = getTestCaseStepByNeed(testId, needId, testState, tester);
				
				model.addAttribute("n", n);
				model.addAttribute("subNeed", subNeed);
				model.addAttribute("subNeedTask", subNeedTask);
				model.addAttribute("needTask", needTask);
				model.addAttribute("testCase", testCase);
				model.addAttribute("testCaseStep", testCaseStep);
			}
		} else if (projectId != null && projectId > 0) {//项目：获取项目下所有模块，模块下所有子模块，模块、子模块下所有任务（包含原型图、流程图），任务下的测试用例；【获取已完成的任务、已验收的模块】
			applyType = 3;
			TaskProject p = (TaskProject) getEntityByPrimaryKey(new TaskProject(), projectId);
			if(p != null && p.getState() > 8) {
				//获取项目下所有模块
				List<Map<String, Object>> need = getNeedByProject(projectId);
				//获取项目下所有子模块
				List<Map<String, Object>> subNeed = getSubNeedByProject(projectId);
				//获取所有模块下任务
				List<Map<String, Object>> needTask = getNeedTaskByProject(testId, projectId, testState, tester);
				//获取所有子模块下任务
				List<Map<String, Object>> subNeedTask = getSubNeedTaskByProject(testId, projectId, testState, tester);
				//获取所有任务下的所有测试用例列表
				List<Map<String, Object>> testCase = getTestCaseByProject(testId, projectId, testState, tester);
				//获取所有测试用例下的所有步骤
				List<Map<String, Object>> testCaseStep = getTestCaseStepByProject(testId, projectId, testState, tester);
				
				model.addAttribute("p", p);
				model.addAttribute("need", need);
				model.addAttribute("subNeed", subNeed);
				model.addAttribute("needTask", needTask);
				model.addAttribute("subNeedTask", subNeedTask);
				model.addAttribute("testCase", testCase);
				model.addAttribute("testCaseStep", testCaseStep);
			}
		} else if (productId != null && productId > 0) {//产品：获取产品下所有模块，模块下所有子模块，模块、子模块下所有任务（包含原型图、流程图），任务下的测试用例；【获取已完成的任务、已验收的模块】
			applyType = 4;
			TaskProduct product = (TaskProduct) getEntityByPrimaryKey(new TaskProduct(), productId);
			if(product != null && product.getState() == 4) {
				//获取项目下所有模块
				List<Map<String, Object>> need = getNeedByProduct(productId);
				//获取项目下所有子模块
				List<Map<String, Object>> subNeed = getSubNeedByProduct(productId);
				//获取所有模块下任务
				List<Map<String, Object>> needTask = getNeedTaskByProduct(testId, productId, testState, tester);
				//获取所有子模块下任务
				List<Map<String, Object>> subNeedTask = getSubNeedTaskByProduct(testId, productId, testState, tester);
				//获取所有任务下的所有测试用例列表
				List<Map<String, Object>> testCase = getTestCaseByProduct(testId, productId, testState, tester);
				//获取所有测试用例下的所有步骤
				List<Map<String, Object>> testCaseStep = getTestCaseStepByProduct(testId, productId, testState, tester);
				model.addAttribute("product", product);
				model.addAttribute("need", need);
				model.addAttribute("subNeed", subNeed);
				model.addAttribute("needTask", needTask);
				model.addAttribute("subNeedTask", subNeedTask);
				model.addAttribute("testCase", testCase);
				model.addAttribute("testCaseStep", testCaseStep);
			}
		} else {//原来的不动
			applyType = 1;
			model.addAttribute("tasks", getFinishedTasksByMember(GiantUtil.intOf(taskId, 0)));
			model.addAttribute("task_id", taskId);
		}
		model.addAttribute("applyType", applyType);
	}
	
	/**
	 * 获取子模块【获取已验收模块】
	 * @param needId
	 * @return
	 */
	private List<Map<String, Object>> getSubNeedByNeed(int needId){
		String sql = "SELECT id,need_name,state FROM task_need WHERE state=4 AND parent_id=" + needId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取子模块下的任务【获取已完成任务】
	 * @param needId
	 * @return
	 */
	private List<Map<String, Object>> getSubNeedTaskByNeed(int testId, int needId, String testState, int tester){
		String sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img,test_state FROM task t, task_need n WHERE t.need_id=n.id AND deleted=0 "
				+ "AND t.test_state in (" + testState + ") AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.parent_id=" + needId;
		if(testId > 0) {
			sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img,test_state FROM task t, task_need n WHERE t.need_id=n.id AND deleted=0 "
					+ "AND t.test_apply_id=" + testId + " AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.parent_id=" + needId;
		}
		if(tester > 0) {
			sql += " and t.test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取模块下的任务【获取已完成任务】
	 * @param needId
	 * @return
	 */
	private List<Map<String, Object>> getTaskByNeed(int testId, int needId, String testState, int tester){
		String sql = "SELECT id,need_id,task_name,interface_img,flow_img,test_state FROM task WHERE deleted=0 AND test_state in (" + testState + ") AND task_type!=2 AND state=4 AND need_id=" + needId;
		if(testId > 0) {
			sql = "SELECT id,need_id,task_name,interface_img,flow_img,test_state FROM task WHERE deleted=0 AND test_apply_id=" + testId + " AND task_type!=2 AND state=4 AND need_id=" + needId;
		}
		if(tester > 0) {
			sql += " and test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有任务下的所有测试用例列表
	 * @param needId
	 * @return
	 */
	private List<Map<String, Object>> getTestCaseByNeed(int testId, int needId, String testState, int tester){
		String sql = "SELECT c.id,c.task_id,c.case_name,c.case_type,c.precondition FROM task t, task_need n, test_case c WHERE c.task_id=t.id AND t.need_id=n.id "
				+ "AND c.state=1 AND t.deleted=0 AND t.test_state in (" + testState + ") AND t.task_type!=2 AND t.state=4 AND n.state=4 AND (n.parent_id=" + needId + " OR n.id=" + needId + ")";
		if(testId > 0) {
			sql = "SELECT c.id,c.task_id,c.case_name,c.case_type,c.precondition FROM task t, task_need n, test_case c WHERE c.task_id=t.id AND t.need_id=n.id "
					+ "AND c.state=1 AND t.deleted=0 AND t.test_apply_id=" + testId + " AND t.task_type!=2 AND t.state=4 AND n.state=4 AND (n.parent_id=" + needId + " OR n.id=" + needId + ")";
		}
		if(tester > 0) {
			sql += " and t.test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有测试用例下的所有步骤
	 * @param needId
	 * @return
	 */
	private List<Map<String, Object>> getTestCaseStepByNeed(int testId, int needId, String testState, int tester){
		String sql = "SELECT s.case_id,s.step,s.expect FROM task t, task_need n, test_case c, test_case_step s WHERE s.case_id=c.id AND s.version=c.version " + 
				"AND c.task_id=t.id AND t.need_id=n.id AND c.state=1 AND t.deleted=0 AND t.test_state in (" + testState + ") "
				+ "AND t.task_type!=2 AND t.state=4 AND n.state=4 AND (n.parent_id=" + needId + " OR n.id=" + needId + ")";
		if(testId > 0) {
			sql = "SELECT s.case_id,s.step,s.expect FROM task t, task_need n, test_case c, test_case_step s WHERE s.case_id=c.id AND s.version=c.version " + 
					"AND c.task_id=t.id AND t.need_id=n.id AND c.state=1 AND t.deleted=0 AND t.test_apply_id=" + testId
					+ " AND t.task_type!=2 AND t.state=4 AND n.state=4 AND (n.parent_id=" + needId + " OR n.id=" + needId + ")";
		}
		if(tester > 0) {
			sql += " and t.test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取项目下所有模块【获取已验收模块】
	 * @param projectId
	 * @return
	 */
	private List<Map<String, Object>> getNeedByProject(int projectId){
		String sql = "SELECT id,need_name,state FROM task_need WHERE state=4 AND parent_id=0 AND project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取项目下所有子模块【获取已验收模块】
	 * @param projectId
	 * @return
	 */
	private List<Map<String, Object>> getSubNeedByProject(int projectId){
		String sql = "SELECT id,need_name,state,parent_id FROM task_need WHERE state=4 AND parent_id>0 AND project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有模块下任务【获取已完成任务】
	 * @param projectId
	 * @return
	 */
	private List<Map<String, Object>> getNeedTaskByProject(int testId, int projectId, String testState, int tester){
		String sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img,test_state FROM task t, task_need n "
				+ "WHERE t.need_id=n.id AND deleted=0 AND t.test_state in (" + testState + ") AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.parent_id=0 AND n.project_id=" + projectId;
		if(testId > 0) {
			sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img,test_state FROM task t, task_need n "
					+ "WHERE t.need_id=n.id AND deleted=0 AND t.test_apply_id=" + testId + " AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.parent_id=0 AND n.project_id=" + projectId;
		}
		if(tester > 0) {
			sql += " and t.test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有子模块下任务【获取已完成任务】
	 * @param projectId
	 * @return
	 */
	private List<Map<String, Object>> getSubNeedTaskByProject(int testId, int projectId, String testState, int tester){
		String sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img,test_state FROM task t, task_need n "
				+ "WHERE t.need_id=n.id AND deleted=0 AND t.test_state in (" + testState + ") AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.parent_id>0 AND n.project_id=" + projectId;
		if(testId > 0) {
			sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img,test_state FROM task t, task_need n "
					+ "WHERE t.need_id=n.id AND deleted=0 AND t.test_apply_id=" + testId + " AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.parent_id>0 AND n.project_id=" + projectId;
		}
		if(tester > 0) {
			sql += " and t.test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有任务下的所有测试用例列表
	 * @param needId
	 * @return
	 */
	private List<Map<String, Object>> getTestCaseByProject(int testId, int projectId, String testState, int tester){
		String sql = "SELECT c.id,c.task_id,c.case_name,c.case_type,c.precondition FROM task t, task_need n, test_case c WHERE c.task_id=t.id AND t.need_id=n.id "
				+ "AND c.state=1 AND t.deleted=0 AND t.test_state in (" + testState + ") AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.project_id=" + projectId;
		if(testId > 0) {
			sql = "SELECT c.id,c.task_id,c.case_name,c.case_type,c.precondition FROM task t, task_need n, test_case c WHERE c.task_id=t.id AND t.need_id=n.id "
					+ "AND c.state=1 AND t.deleted=0 AND t.test_apply_id=" + testId + " AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.project_id=" + projectId;
		}
		if(tester > 0) {
			sql += " and t.test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有测试用例下的所有步骤
	 * @param needId
	 * @return
	 */
	private List<Map<String, Object>> getTestCaseStepByProject(int testId, int projectId, String testState, int tester){
		String sql = "SELECT s.case_id,s.step,s.expect FROM task t, task_need n, test_case c, test_case_step s WHERE s.case_id=c.id AND s.version=c.version " + 
				"AND c.task_id=t.id AND t.need_id=n.id AND c.state=1 AND t.deleted=0 AND t.test_state in (" + testState + ") AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.project_id=" + projectId;
		if(testId > 0) {
			sql = "SELECT s.case_id,s.step,s.expect FROM task t, task_need n, test_case c, test_case_step s WHERE s.case_id=c.id AND s.version=c.version " + 
					"AND c.task_id=t.id AND t.need_id=n.id AND c.state=1 AND t.deleted=0 AND t.test_apply_id=" + testId + " AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.project_id=" + projectId;
		}
		if(tester > 0) {
			sql += " and t.test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取产品下所有模块【获取已验收模块】
	 * @param productId
	 * @return
	 */
	private List<Map<String, Object>> getNeedByProduct(int productId){
		String sql = "SELECT id,need_name,state FROM task_need WHERE state=4 AND parent_id=0 AND product_id=" + productId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取产品下所有子模块【获取已验收模块】
	 * @param productId
	 * @return
	 */
	private List<Map<String, Object>> getSubNeedByProduct(int productId){
		String sql = "SELECT id,need_name,state,parent_id FROM task_need WHERE state=4 AND parent_id>0 AND product_id=" + productId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有模块下任务【获取已完成任务】
	 * @param productId
	 * @return
	 */
	private List<Map<String, Object>> getNeedTaskByProduct(int testId, int productId, String testState, int tester){
		String sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img,test_state FROM task t, task_need n "
				+ "WHERE t.need_id=n.id AND deleted=0 AND t.test_state in (" + testState + ") AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.parent_id=0 AND n.product_id=" + productId;
		if(testId > 0) {
			sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img,test_state FROM task t, task_need n "
					+ "WHERE t.need_id=n.id AND deleted=0 AND t.test_apply_id=" + testId + " AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.parent_id=0 AND n.product_id=" + productId;
		}
		if(tester > 0) {
			sql += " and t.test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有子模块下任务【获取已完成任务】
	 * @param productId
	 * @return
	 */
	private List<Map<String, Object>> getSubNeedTaskByProduct(int testId, int productId, String testState, int tester){
		String sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img,test_state FROM task t, task_need n "
				+ "WHERE t.need_id=n.id AND deleted=0 AND t.test_state in (" + testState + ") AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.parent_id>0 AND n.product_id=" + productId;
		if(testId > 0) {
			sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img,test_state FROM task t, task_need n "
					+ "WHERE t.need_id=n.id AND deleted=0 AND t.test_apply_id=" + testId + " AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.parent_id>0 AND n.product_id=" + productId;
		}
		if(tester > 0) {
			sql += " and t.test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有任务下的所有测试用例列表
	 * @param needId
	 * @return
	 */
	private List<Map<String, Object>> getTestCaseByProduct(int testId, int productId, String testState, int tester){
		String sql = "SELECT c.id,c.task_id,c.case_name,c.case_type,c.precondition FROM task t, task_need n, test_case c WHERE c.task_id=t.id AND t.need_id=n.id "
				+ "AND c.state=1 AND t.deleted=0 AND t.test_state in (" + testState + ") AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.product_id=" + productId;
		if(testId > 0) {
			sql = "SELECT c.id,c.task_id,c.case_name,c.case_type,c.precondition FROM task t, task_need n, test_case c WHERE c.task_id=t.id AND t.need_id=n.id "
					+ "AND c.state=1 AND t.deleted=0 AND t.test_apply_id=" + testId + " AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.product_id=" + productId;
		}
		if(tester > 0) {
			sql += " and t.test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有测试用例下的所有步骤
	 * @param needId
	 * @return
	 */
	private List<Map<String, Object>> getTestCaseStepByProduct(int testId, int productId, String testState, int tester){
		String sql = "SELECT s.case_id,s.step,s.expect FROM task t, task_need n, test_case c, test_case_step s WHERE s.case_id=c.id AND s.version=c.version " + 
				"AND c.task_id=t.id AND t.need_id=n.id AND c.state=1 AND t.deleted=0 AND t.test_state in (" + testState + ") AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.product_id=" + productId;
		if(testId > 0) {
			sql = "SELECT s.case_id,s.step,s.expect FROM task t, task_need n, test_case c, test_case_step s WHERE s.case_id=c.id AND s.version=c.version " + 
					"AND c.task_id=t.id AND t.need_id=n.id AND c.state=1 AND t.deleted=0 AND t.test_apply_id=" + testId + " AND t.task_type!=2 AND t.state=4 AND n.state=4 AND n.product_id=" + productId;
		}
		if(tester > 0) {
			sql += " and t.test_id=" + tester;
		}
		return super.getMapListBySQL(sql, null);
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
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){//修改
			PMLog pmLog = new PMLog(LogModule.TEST, LogMethod.EDIT, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			TestApply testApply = (TestApply) super.getEntityByPrimaryKey(new TestApply(), GiantUtil.intOf(mvm.get("id"), 0));
			TestApply oldTestApply = new TestApply();
			BeanUtils.copyProperties(testApply, oldTestApply);
			testApply.setTaskId(GiantUtil.intOf(mvm.get("task_id"), 0));
			testApply.setNeedId(GiantUtil.intOf(mvm.get("need_id"), 0));
			testApply.setProjectId(GiantUtil.intOf(mvm.get("project_id"), 0));
			testApply.setProductId(GiantUtil.intOf(mvm.get("product_id"), 0));
			// pmLog.add("test_content", testApply.getTestContent(), GiantUtil.stringOf(mvm.get("test_content")));
			testApply.setTestName(GiantUtil.stringOf(mvm.get("test_name"))); // 测试名称
			testApply.setTestContent(GiantUtil.stringOf(mvm.get("test_content"))); // 测试内容
			testApply.setExecuteSql(GiantUtil.stringOf(mvm.get("execute_sql"))); // 要执行的sql
			testApply.setUpdateTime(new Timestamp(System.currentTimeMillis())); // 修改时间
			boolean b = super.dao.saveUpdateOrDelete(testApply, null);
			pmLog.add(oldTestApply, testApply, new String[] {"test_content"}, "test_content");
			pmLog.setObjectId(testApply.getId());
			this.log(pmLog);
			return b;
		} else {//添加
			PMLog pmLog = new PMLog(LogModule.TEST, LogMethod.ADD, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			TestApply testApply = new TestApply();
			testApply.setState((short) 1);  // 1 表示待测试
			testApply.setApplyId(super.getMemberId()); // 当前用户ID
			testApply.setApplyName(super.getMemberName()); // 当前用户名称
			testApply.setApplyTime(new Timestamp(System.currentTimeMillis())); // 申请测试时间
			testApply.setTaskId(GiantUtil.intOf(mvm.get("task_id"), 0));
			testApply.setNeedId(GiantUtil.intOf(mvm.get("need_id"), 0));
			testApply.setProjectId(GiantUtil.intOf(mvm.get("project_id"), 0));
			testApply.setProductId(GiantUtil.intOf(mvm.get("product_id"), 0));
			testApply.setApplyType(0);
			if(!GiantUtil.isEmpty(mvm.get("task_id"))) {
				testApply.setApplyType(1);
			}
			if(!GiantUtil.isEmpty(mvm.get("need_id"))) {
				testApply.setApplyType(2);
			}
			if(!GiantUtil.isEmpty(mvm.get("project_id"))) {
				testApply.setApplyType(3);
			}
			if(!GiantUtil.isEmpty(mvm.get("product_id"))) {
				testApply.setApplyType(4);
			}
			testApply.setTestName(GiantUtil.stringOf(mvm.get("test_name"))); // 测试名称
			testApply.setTestContent(GiantUtil.stringOf(mvm.get("test_content"))); // 测试内容
			testApply.setExecuteSql(GiantUtil.stringOf(mvm.get("execute_sql"))); // 要执行的sql
			testApply.setUpdateTime(new Timestamp(System.currentTimeMillis())); // 修改时间
			boolean b = super.dao.saveUpdateOrDelete(testApply, null);
			pmLog.setObjectId(testApply.getId());
			this.log(pmLog);
			List<Map<String, Object>> list = null;
			if(!GiantUtil.isEmpty(mvm.get("task_id"))) {
				String sql = "select id from task WHERE test_state=1 AND state=4 AND id IN (:taskId)";
				Map<String, Object> prm = new HashMap<String, Object>();
				prm.put("taskId", testApply.getTaskId());
				list = super.getMapListBySQL(sql, prm);
			}
			if(!GiantUtil.isEmpty(mvm.get("need_id"))) {
				String sql = "select t.id from task t, task_need n WHERE t.need_id=n.id AND t.task_type=1 AND test_state=1 AND t.state=4 "
						+ "AND (n.id IN (:needId) OR n.parent_id in (:needId))";
				Map<String, Object> prm = new HashMap<String, Object>();
				prm.put("needId", testApply.getNeedId());
				list = super.getMapListBySQL(sql, prm);
			}
			if(!GiantUtil.isEmpty(mvm.get("project_id"))) {
				String sql = "select id from task WHERE test_state=1 AND state=4 AND project_id IN (:projectId)";
				Map<String, Object> prm = new HashMap<String, Object>();
				prm.put("projectId", testApply.getProjectId());
				list = super.getMapListBySQL(sql, prm);
				
				TaskProject project = (TaskProject) super.getEntityByPrimaryKey(new TaskProject(), testApply.getProjectId());
				project.setState((short) 9);
				project.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				super.saveUpdateOrDelete(project, null);
			}
			if(!GiantUtil.isEmpty(mvm.get("product_id"))) {
				String sql = "select id from task WHERE test_state=1 AND state=4 AND product_id IN (:productId)";
				Map<String, Object> prm = new HashMap<String, Object>();
				prm.put("productId", testApply.getProductId());
				list = super.getMapListBySQL(sql, prm);
			}
			if(list != null && list.size() > 0) {
				for(Map<String, Object> map: list) {
					Task task = (Task) super.getEntityByPrimaryKey(new Task(), map.get("id"));
					task.setTestState(2);
					task.setTestApplyId(testApply.getId());
					super.saveUpdateOrDelete(task, null);
				}
			}
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
				"and id not in (select task_id from test_apply where state!=4 and id != :id) " + 
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
		task.setDeveloperTaskId(String.valueOf(developerTaskId));
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
	/**
	 * 领取
	 */
	public boolean receive2(Map<String, String> mvm) {
		//1、生成每个测试人员对应的开发任务ID组
		String taskIdArr[] = mvm.get("taskIds").split(",");
		//测试人员列表
		List<String> testerList = new ArrayList<String>();
		//测试人员对应开发任务列表
		Map<String, String> testerTaskId = new HashMap<String, String>();
		for(String taskId: taskIdArr) {
			String testerId = mvm.get("assigned_id[" + taskId + "]");
			if(testerId == null || "".equals(testerId) || "0".equals(testerId)) {
				return false;
			}
			if(!testerList.contains(testerId)) {
				testerList.add(testerId);
				testerTaskId.put(testerId, taskId);
			}else {
				testerTaskId.put(testerId, testerTaskId.get(testerId) + "," + taskId);
			}
		}
		int applyId = 0;
		//2、修改测试单状态
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
			applyId = apply.getId();
			pmLog.add(oldTestApply, apply, "state");
			this.log(pmLog);
		}
		//3、循环生成测试任务
		List<Object> list = new ArrayList<Object>();
		for(String tester: testerList) {
			Member member = (Member) super.getEntityByPrimaryKey(new Member(), Integer.valueOf(tester));
			//生成测试任务
			Task task = new Task();
			task.setResolved((short)0);
			task.setTaskName(GiantUtil.stringOf(mvm.get("task_name")));
			task.setTaskType(2);
			task.setLevel(1);
			task.setRemark(GiantUtil.stringOf(mvm.get("remark")));
			task.setExecuteSql(GiantUtil.stringOf(mvm.get("execute_sql")));
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
			task.setParentId(0);
			task.setTestApplyId(applyId);
			
			task.setDeveloperTaskId(testerTaskId.get(tester));
			task.setCreateTime(new Timestamp(System.currentTimeMillis()));
			task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			list.add(task);
		}
		boolean flag = super.saveUpdateOrDelete(list, null);
		if(!flag) {
			return false;
		}
		//4、修改开发任务测试信息
		for(String tester: testerList) {
			Member member = (Member) super.getEntityByPrimaryKey(new Member(), Integer.valueOf(tester));
			if(testerTaskId.get(tester) != null && !"".equals(testerTaskId.get(tester))) {
				for(String taskId: testerTaskId.get(tester).split(",")) {
					Task task = (Task) super.getEntityByPrimaryKey(new Task(), Integer.valueOf(taskId));
					task.setTestId(member.getId());
					task.setTestName(member.getName());
					task.setTestState(3);
					task.setTestTime(new Timestamp(System.currentTimeMillis()));
					super.saveUpdateOrDelete(task, null);
				}
			}
		}
		return true;
	}
}
