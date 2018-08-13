package com.zzidc.my.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.my.service.MyService;
import com.zzidc.team.service.TeamNeedService;
import com.zzidc.team.service.TeamTaskService;
import com.zzidc.team.service.TestApplyService;
import com.zzidc.team.service.TestBugService;

/**
 * 我的地盘
 */
@Controller
@RequestMapping({ "/my" })
public class MyController extends GiantBaseController {
	@Autowired
	private MyService myService;
	@Autowired
	private TeamTaskService teamTaskService;
	@Autowired
	private TestBugService testBugService;
	@Autowired
	private TestApplyService testApplyService;
	@Autowired
	private TeamNeedService teamNeedService;
	private GiantPager conditionPage = null;
	private String requestURL = "my/task";

	public void publicResult(Model model) {
		model.addAttribute("m", "my");//模块
		model.addAttribute("s", "task");//子模块
		model.addAttribute("u", requestURL);//请求地址 
	}
	
	/**
	 * 首页
	 */
	@RequestMapping("")
	public String index(@RequestParam Map<String, String> mvm, Model model) {
		//统计数据
		//任务
		Map<String, Object> taskCount = myService.getTaskCount();
		//bug
		Map<String, Object> bugCount = myService.getBugCount();
		//测试
		Map<String, Object> testCount = myService.getTestCount();
		//需求
		Map<String, Object> needCount = myService.getNeedCount();

		model.addAttribute("taskCount", taskCount);
		model.addAttribute("bugCount", bugCount);
		model.addAttribute("testCount", testCount);
		model.addAttribute("needCount", needCount);
		publicResult(model);
		model.addAttribute("s", "");//子模块
		return "my/index";
	}
	
	/**
	 * 我的任务
	 */
	@RequestMapping("/task")
	public String task(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "t.state");
			mvm.put("orderByValue", "ASC");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "8");
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
		pageList = teamTaskService.getPageList(conditionPage);
		requestURL = "my/task?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		if("1".equals(mvm.get("type")) || "2".equals(mvm.get("type"))) {
			teamTaskService.getSubTaskList(pageList.getPageResult(), mvm.get("type"), mvm.get("orderColumn"), mvm.get("orderByValue"));
		}
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "my/task";
	}

	/**
	 * 我的Bug
	 */
	@RequestMapping("/bug")
	public String bug(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "tb.id");
			mvm.put("orderByValue", "DESC");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "6");
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
		pageList = testBugService.getPageList(conditionPage);
		requestURL = "my/bug?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		model.addAttribute("s", "bug");//子模块
		return "my/bug";
	}

	/**
	 * 我的测试
	 */
	@RequestMapping("/test")
	public String test(@RequestParam Map<String, String> mvm, Model model) {
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
		requestURL = "my/test?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		model.addAttribute("s", "test");//子模块
		return "my/test/list";
	}

	/**
	 * 我的需求
	 */
	@RequestMapping("/need")
	public String need(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "tn.update_time");
			mvm.put("orderByValue", "DESC");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "7");
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
		pageList = teamNeedService.getPageList(conditionPage);
		requestURL = "my/need?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		if("1".equals(mvm.get("type")) || "2".equals(mvm.get("type"))) {
			teamNeedService.getSubNeedList(pageList.getPageResult(), mvm.get("type"));
		}
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		model.addAttribute("s", "need");//子模块
		return "my/need/list";
	}

}
