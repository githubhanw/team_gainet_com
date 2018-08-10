package com.zzidc.declaration.controller;

import java.util.List;
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
import com.zzidc.declaration.service.ProjectResultService;
import com.zzidc.team.entity.DeclarationProjectResult;

import net.sf.json.JSONObject;

/**
 * 科技（science and technology，简称st）申报--项目成果管理
 */
@Controller
@RequestMapping({ "/declaration/result" })
public class ProjectResultController extends GiantBaseController {
	@Autowired
	private ProjectResultService projectResultService;
	private GiantPager conditionPage = null;
	private String requestURL = "declaration/result/index";

	public void publicResult(Model model) {
		model.addAttribute("m", "pd");//模块
		model.addAttribute("s", "result");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 项目成果列表
	 */
	@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "pr.id");
			mvm.put("orderByValue", "DESC");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "2");
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
		pageList = projectResultService.getPageList(conditionPage);
		requestURL = "declaration/result/index?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "declaration/project_result/list";
	}

	/**
	 * 项目成果详情
	 */
	@RequestMapping("/detail")
	public String detail(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			List<Map<String, Object>> pr = projectResultService.getProjectResult(GiantUtil.intOf(mvm.get("id"), 0));
			if (pr != null && pr.size() > 0) {
				model.addAttribute("pr", pr.get(0));
			}
			//获取成果文档
			List<Map<String, Object>> doc = projectResultService.getResultDoc(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("doc", doc);
		}
		publicResult(model);
		return "declaration/project_result/detail";
	}
	
	/**
	 * 跳转添加 / 修改项目成果页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		//添加项目成果页面的项目列表
		model.addAttribute("project", projectResultService.getDeclarationProject());
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			DeclarationProjectResult pr = (DeclarationProjectResult) projectResultService.getEntityByPrimaryKey(new DeclarationProjectResult(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("pr", pr);
		}
		publicResult(model);
		return "declaration/project_result/add";
	}
	
	/**
	 * 添加 / 修改项目成果
	 */
	@RequestMapping("/addOrUpd")
	public void addOrUpd(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(mvm.get("type") == null || mvm.get("company") == null || mvm.get("agent") == null || mvm.get("state") == null || 
				mvm.get("registration_number") == null || mvm.get("project_id") == null || mvm.get("project_result_name") == null || 
				mvm.get("member_name") == null || mvm.get("apply_date") == null){
			json.put("code",1);
			json.put("message", "参数不完整！！");
			resultresponse(response,json);
			return;
		}
		
		boolean flag = projectResultService.addOrUpd(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "添加/修改成功");
		}else{
			json.put("code",1);
			json.put("message", "添加/修改失败");
		}
		resultresponse(response, json);
	}

	/**
	 * 删除项目成果
	 */
	@RequestMapping("/del")
	public void del(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			DeclarationProjectResult pr = (DeclarationProjectResult) projectResultService.getEntityByPrimaryKey(new DeclarationProjectResult(), GiantUtil.intOf(mvm.get("id"), 0));
			pr.setState((short) 0);
			boolean flag = projectResultService.saveUpdateOrDelete(pr, null);
			if(flag){
				json.put("code",0);
				json.put("message", "删除成功");
			}else{
				json.put("code",1);
				json.put("message", "删除失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}
	
}
