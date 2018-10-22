package com.zzidc.declaration.controller;

import java.sql.Timestamp;
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
import com.zzidc.declaration.service.ProjectDocService;
import com.zzidc.team.entity.DeclarationProjectDoc;

import net.sf.json.JSONObject;

/**
 * 项目申报--项目成果文档列表
 */
@Controller
@RequestMapping({ "/declaration/doc" })
public class ProjectDocController extends GiantBaseController {
	@Autowired
	private ProjectDocService projectDocService;

	private GiantPager conditionPage = null;

	public void publicResult(Model model) {
		model.addAttribute("m", "pd");//模块
		model.addAttribute("s", "doc");//子模块
	}
	
	/**
	 * 项目成果文档列表
	 */
	@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "pd.id");
			mvm.put("orderByValue", "DESC");
			mvm.put("currentPage", "1");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "2");
		}
		Map<String, String> queryCondition = conditionPage.getQueryCondition();
		//查询条件封装
		queryCondition.putAll(mvm);
		conditionPage.setCurrentPage(GiantUtil.intOf(mvm.get("currentPage"), 1));
		conditionPage.setPageSize(GiantUtil.intOf(mvm.get("pageSize"), 15));
		conditionPage.setOrderColumn(GiantUtil.stringOf(mvm.get("orderColumn")));
		pageList = projectDocService.getPageList(conditionPage);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "declaration/project_doc/list";
	}
	
	/**
	 * 跳转添加 / 修改项目成果文档页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		//添加成果文档页面的文档类型列表
		model.addAttribute("pdt", projectDocService.getDocType());
		//添加成果文档页面的成果列表
		model.addAttribute("pr", projectDocService.getProjectResult());
		//添加成果文档页面的项目列表
		model.addAttribute("p", projectDocService.getProject());
		
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			DeclarationProjectDoc pdt = (DeclarationProjectDoc) projectDocService.getEntityByPrimaryKey(new DeclarationProjectDoc(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("obj", pdt);
		}
		publicResult(model);
		return "declaration/project_doc/add";
	}
	
	/**
	 * 添加 / 修改项目成果文档
	 */
	@RequestMapping("/addOrUpd")
	public void addOrUpd(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		
		DeclarationProjectDoc pd = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			pd = (DeclarationProjectDoc) projectDocService.getEntityByPrimaryKey(new DeclarationProjectDoc(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			pd = new DeclarationProjectDoc();
			pd.setCreateTime(new Timestamp(System.currentTimeMillis()));
			pd.setState(1);
		}
		pd.setDocName(GiantUtil.stringOf(mvm.get("doc_name")));
		pd.setProjectDocUrl(GiantUtil.stringOf(mvm.get("project_doc_url")));
		pd.setProvideDate(Timestamp.valueOf(mvm.get("provide_date") + " 00:00:00"));
		pd.setDocState(GiantUtil.intOf(mvm.get("doc_state"), 0)); // 默认未提供
		pd.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		// 文档类型ID，当文档类型ID为空时表示没有选择文档类型
		int typeId = GiantUtil.intOf(mvm.get("type_id"), 0);
		if(typeId > 0) {
			pd.setTypeId(typeId);
		} 
		// 项目ID，当项目ID为空时表示没有选择项目
		int projectId = GiantUtil.intOf(mvm.get("project_id"), 0);
		if(projectId > 0) {
			pd.setProjectId(projectId);
		}
		// 成果ID，当成果ID为空时表示没有选择成果
		int resultId = GiantUtil.intOf(mvm.get("result_id"), 0);
		if(resultId > 0) {
			pd.setResultId(resultId);
		} 
		
		boolean flag = projectDocService.saveUpdateOrDelete(pd, null);
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
	 * 删除项目成果文档类型页面
	 */
	@RequestMapping("/del")
	public void del(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			DeclarationProjectDoc pd = (DeclarationProjectDoc) projectDocService.getEntityByPrimaryKey(new DeclarationProjectDoc(), GiantUtil.intOf(mvm.get("id"), 0));
			pd.setState(0);
			boolean flag = projectDocService.saveUpdateOrDelete(pd, null);
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
	
	public ProjectDocService getProjectDocService() {
		return projectDocService;
	}

	public void setProjectDocService(ProjectDocService projectDocService) {
		this.projectDocService = projectDocService;
	}
	
}
