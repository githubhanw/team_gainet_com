/**
 * Qyb
 */
package com.zzidc.team.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.Task;
import com.zzidc.team.service.TestCaseService;

import net.sf.json.JSONObject;

/**
 * Case模块
 * @author Qyb
 *
 */
@Controller
@RequestMapping({ "/test/case" })
public class TestCaseController extends GiantBaseController {
	@Autowired
	private TestCaseService testCaseService;
	private GiantPager conditionPage = null;
	private String requestURL = "test/case/index";
	
	public void publicResult(Model model) {
		model.addAttribute("m", "task");//模块
		model.addAttribute("s", "task");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 测试用例列表
	 */
	@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "tb.id");
			mvm.put("orderByValue", "DESC");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "0");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("search")))){
			mvm.put("search", "");
		}
		Map<String, String> queryCondition = conditionPage.getQueryCondition();
		//查询条件封装
		queryCondition.clear();
		queryCondition.putAll(mvm);
		conditionPage.setCurrentPage(GiantUtil.intOf(mvm.get("currentPage"), 1));
		conditionPage.setPageSize(GiantUtil.intOf(mvm.get("pageSize"), 15));
		conditionPage.setOrderColumn(GiantUtil.stringOf(mvm.get("orderColumn")));
		pageList = testCaseService.getPageList(conditionPage);
		requestURL = "test/bug/index?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("members", testCaseService.getAllMember());
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "test/bug/list";
	}

	/**
	 * 跳转添加测试用例页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		if (GiantUtils.isEmpty(mvm.get("id"))) {
			
		}
		Task t = (Task) testCaseService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
		model.addAttribute("task", t);
		publicResult(model);
		model.addAttribute("s", "task");//子模块
		return "test/case/add";
	}

	/**
	 * 添加测试用例
	 */
	@RequestMapping("/add")
	public void add(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("task_id")) || GiantUtil.isEmpty(mvm.get("case_name")) || 
				GiantUtil.isEmpty(mvm.get("case_type")) || GiantUtil.isEmpty(mvm.get("condition")) || 
				GiantUtil.isEmpty(mvm.get("steps[1]"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = testCaseService.add(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "添加成功");
		}else{
			json.put("code",1);
			json.put("message", "添加失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 跳转删除页面
	 * @param mvm
	 * @param model
	 * @return
	 */
	@RequestMapping("/toDelete")
	public String toDelete(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
//			TestCase t = (TestCase) testCaseService.getEntityByPrimaryKey(new TestCase(), GiantUtil.intOf(mvm.get("id"), 0));
//			model.addAttribute("t", t);
		}
		publicResult(model);
		return "test/bug/delete";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping("/delete")
	public void del(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
//			TestCase p = (TestCase) testCaseService.getEntityByPrimaryKey(new TestCase(), GiantUtil.intOf(mvm.get("id"), 0));
//			p.setSolvestatus(3);
//			boolean flag = testCaseService.saveUpdateOrDelete(p, null);
//			if(flag){
//				json.put("code",0);
//				json.put("message", "删除成功");
//			}else{
//				json.put("code",1);
//				json.put("message", "删除失败");
//			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 跳转编辑页面
	 */
	@RequestMapping("/toEdit")
	public String toEdit(@RequestParam Map<String, String> mvm, Model model) {
		model.addAttribute("task", testCaseService.getFinishedTasksByMember());
		model.addAttribute("members", testCaseService.getAllMember());
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
//			TestCase t = (TestCase) testCaseService.getEntityByPrimaryKey(new TestCase(), GiantUtil.intOf(mvm.get("id"), 0));
//			model.addAttribute("t", t);
//			model.addAttribute("taskId", t.getTaskid());
		}
		publicResult(model);
		model.addAttribute("s", "add");//子模块
		return "test/bug/edit";
	}
	
	/**
	 * 确认编辑
	 */
	@RequestMapping("/edit")
	public void edit(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("taskid")) || GiantUtil.isEmpty(mvm.get("bugproject")) || GiantUtil.isEmpty(mvm.get("bugrank")) || 
				GiantUtil.isEmpty(mvm.get("bugfen")) || GiantUtil.isEmpty(mvm.get("bugtype")) || GiantUtil.isEmpty(mvm.get("bugdes")) || 
				GiantUtil.isEmpty(mvm.get("creater_id")) || GiantUtil.isEmpty(mvm.get("developer_id")) || GiantUtil.isEmpty(mvm.get("mark"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = testCaseService.edit(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "编辑成功");
		}else{
			json.put("code",1);
			json.put("message", "编辑失败");
		}
		resultresponse(response,json);
	}

}
