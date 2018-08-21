package com.zzidc.my.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.team.entity.Task;
import com.zzidc.team.entity.TaskNeed;
import com.zzidc.team.entity.TaskProject;
import com.zzidc.team.entity.TestApply;
import com.zzidc.team.service.TestApplyService;

import net.sf.json.JSONObject;

/**
 * [说明/描述]
 * @author likai
 * @date 2018年7月29日 下午1:52:12
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
@Controller
@RequestMapping({ "/my/test" })
public class MyTestController extends GiantBaseController {
	@Autowired
	private TestApplyService testApplyService;
//	private GiantPager conditionPage = null;
	private String requestURL = "my/test/index";
	
	public void publicResult(Model model) {
		model.addAttribute("m", "my");//模块
		model.addAttribute("s", "test");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 测试单列表
	 */
	/*@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "ta.id");
			mvm.put("orderByValue", "DESC");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "1");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("search")))){
			mvm.put("search", "");
		}
		Map<String, String> queryCondition = conditionPage.getQueryCondition();
		//查询条件封装
		queryCondition.putAll(mvm);
		conditionPage.setCurrentPage(GiantUtil.intOf(mvm.get("currentPage"), 1));
		conditionPage.setPageSize(GiantUtil.intOf(mvm.get("pageSize"), 15));
		conditionPage.setOrderColumn(GiantUtil.stringOf(mvm.get("orderColumn")));
		pageList = testApplyService.getPageList(conditionPage);
		requestURL = "my/test/index?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		model.addAttribute("tasks", this.testApplyService.getAllTasks());
		publicResult(model);
		return "my/test/list";
	}*/

	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		publicResult(model);
		model.addAttribute("task_id", mvm.get("id").toString());
		model.addAttribute("tasks", testApplyService.getFinishedTasksByMember(GiantUtil.intOf(mvm.get("id"), 0)));
		return "my/test/add";
	}

	/**
	 * 添加/修改操作
	 */
	@RequestMapping("/addOrUpdate")
	public void addOrUpdate(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		// mvm eg :{r=0.29616789999172366, comment=, id=25}
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("test_content"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = testApplyService.addOrUpdate(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "操作成功");
		}else{
			json.put("code",1);
			json.put("message", "操作失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/toEdit")
	public String toEdit(@RequestParam Map<String, String> mvm, Model model) {
		publicResult(model);
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestApply n = (TestApply) testApplyService.getEntityByPrimaryKey(new TestApply(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("entity", n);
		}
		model.addAttribute("tasks", testApplyService.getFinishedTasksByMember(GiantUtil.intOf(mvm.get("id"), 0)));
		return "my/test/add";
	}

	/**
	 * 跳转到驳回页面
	 */
	@RequestMapping("/toDismissal")
	public String toDismissal(@RequestParam Map<String, String> mvm, Model model) {
		// 只有测试负责人能驳回
		if(!testApplyService.isTestLeaderMember()) {
			return "nopower";
		}
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestApply n = (TestApply) testApplyService.getEntityByPrimaryKey(new TestApply(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("entity", n);
		}
		publicResult(model);
		return "my/test/dismissal";
	}

	/**
	 * 驳回操作
	 */
	@RequestMapping("/dismissal")
	public void dismissal(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		// mvm eg :{r=0.29616789999172366, comment=, id=25}
		JSONObject json=new JSONObject();
		// 只有测试负责人能领取
		if(!testApplyService.isTestLeaderMember()) {
			json.put("code",1);
			json.put("message", "不能驳回该测试单");
			resultresponse(response,json);
			return;
		}
		if(GiantUtil.isEmpty(mvm.get("id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		// 只有待测试的申请单才能驳回
		if(!testApplyService.checkState(mvm, 1)) {
			json.put("code",1);
			json.put("message", "只有待测试的测试单才能驳回");
			resultresponse(response,json);
			return;
		}
		boolean flag = testApplyService.dismissal(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "操作成功");
		}else{
			json.put("code",1);
			json.put("message", "操作失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 跳转到详情页面
	 */
	@RequestMapping("/detail")
	public String detail(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestApply n = (TestApply) testApplyService.getEntityByPrimaryKey(new TestApply(), GiantUtil.intOf(mvm.get("id"), 0));
			if(n != null) {
				model.addAttribute("entity", n);
				Task task = (Task) testApplyService.getEntityByPrimaryKey(new Task(), n.getTaskId());
				if(task != null) {
					model.addAttribute("task", task);
					model.addAttribute("project", testApplyService.getEntityByPrimaryKey(new TaskProject(), task.getProjectId()));
					model.addAttribute("need", testApplyService.getEntityByPrimaryKey(new TaskNeed(), task.getNeedId()));
				}
			}
		}
		publicResult(model);
		return "my/test/detail";
	}

	/**
	 * 跳转到领取页面
	 */
	@RequestMapping("/toReceive")
	public String toReceive(@RequestParam Map<String, String> mvm, Model model) {
		// 只有测试负责人能领取
		if(!testApplyService.isTestLeaderMember()) {
			return "nopower";
		}
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			TestApply apply = (TestApply) testApplyService.getEntityByPrimaryKey(new TestApply(), GiantUtil.intOf(mvm.get("id"), 0));
			if(apply != null) {
				model.addAttribute("t", apply);
				Task task = (Task) testApplyService.getEntityByPrimaryKey(new Task(), apply.getTaskId());
				if(task != null) {
					model.addAttribute("taskName", task.getTaskName());
					TaskNeed taskNeed = (TaskNeed) testApplyService.getEntityByPrimaryKey(new TaskNeed(),task.getNeedId());
					if(taskNeed != null) {
						model.addAttribute("needName", taskNeed.getNeedName());
						model.addAttribute("needId", taskNeed.getId());
					}
				}
				model.addAttribute("members", testApplyService.getAllMember());
			}
		}
		publicResult(model);
		return "my/test/receive";
	}

	/**
	 * 领取
	 */
	@RequestMapping("/receive")
	public void receive(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		// 只有测试负责人能领取
		if(!testApplyService.isTestLeaderMember()) {
			json.put("code",1);
			json.put("message", "不能领取该测试单");
			resultresponse(response,json);
			return;
		}
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("task_name")) || GiantUtil.isEmpty(mvm.get("assigned_id")) || GiantUtil.isEmpty(mvm.get("task_type")) || 
				GiantUtil.isEmpty(mvm.get("start_date")) || GiantUtil.isEmpty(mvm.get("end_date")) || GiantUtil.isEmpty(mvm.get("need_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		// 只有待测试的申请单才能领取
		if(!testApplyService.checkState(mvm, 1)) {
			json.put("code",1);
			json.put("message", "只有待测试的测试单才能驳回");
			resultresponse(response,json);
			return;
		}
		// 调用任务添加方法，页面上都做过处理了
		boolean flag = testApplyService.receive(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "操作成功");
		}else{
			json.put("code",1);
			json.put("message", "操作失败");
		} 
		resultresponse(response,json);
	}
}
